package pl.marcinkowalczyk.techmarket.technologylink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;
import pl.marcinkowalczyk.techmarket.batch.BatchRepository;
import pl.marcinkowalczyk.techmarket.batch.BatchStatus;
import pl.marcinkowalczyk.techmarket.offer.OfferEntity;
import pl.marcinkowalczyk.techmarket.offer.OfferRepository;
import pl.marcinkowalczyk.techmarket.offer.OfferStatus;
import pl.marcinkowalczyk.techmarket.technologylink.graph.GraphHelper;
import pl.marcinkowalczyk.techmarket.technologylink.graph.WeightedGraphCalculator;
import pl.marcinkowalczyk.techmarket.technologylink.graph.WeightedGraphEdge;
import pl.marcinkowalczyk.techmarket.technologylink.json.OfferJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TechnologyLinkService {

    public static final int LINKING_SIZE = 1000;

    private final BatchRepository batchRepository;
    private final OfferRepository offerRepository;
    private final ObjectMapper objectMapper;
    private final TechnologyLinkRepository technologyLinkRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void link(String name) {
        BatchEntity batch = batchRepository.findFirstByName(name);
        log.info("Linking technologies from batch '{}' with id {}", batch.getName(), batch.getId());
        linkTechnologies(batch);
        batch.setStatus(BatchStatus.OFFER_LINKED_TECHNOLOGIES);
        log.info("Linked technologies from batch '{}' with id {}", batch.getName(), batch.getId());
    }

    private void linkTechnologies(BatchEntity batch) {
        Page<OfferEntity> page = getOffersPageToLink(batch, 0);
        int totalPages = page.getTotalPages();
        log.info("Linking technologies. Total {} offers, {} pages", page.getTotalElements(), totalPages);
        for (int i = 0; i < totalPages; i++) {
            List<OfferEntity> offers = page.getContent();
            log.info("Linking technologies: {} page, {} elements", i, offers.size());
            linkTechnologiesFromOffers(batch, offers);
            page = getOffersPageToLink(batch, i);
        }
    }

    private Page<OfferEntity> getOffersPageToLink(BatchEntity batch, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, LINKING_SIZE);
        return offerRepository.findOfferEntitiesByBatchAndStatusOrderById(batch,
                OfferStatus.DOWNLOADED, pageRequest);
    }

    private void linkTechnologiesFromOffers(BatchEntity batch, List<OfferEntity> offers) {
        List<Set<String>> technologiesFromOffers = getTechnologiesFromOffers(offers);
        List<WeightedGraphEdge> graphEdges = getGraphPairLinks(technologiesFromOffers);
        List<WeightedGraphEdge> weightedEdgesToApply = WeightedGraphCalculator.calculateWeightedEdges(graphEdges);
        List<TechnologyLinkEntity> existingLinksWithAddedWeight = new ArrayList<>();
        List<TechnologyLinkEntity> newLinks = new ArrayList<>();
        for (WeightedGraphEdge edge : weightedEdgesToApply) {
            Optional<TechnologyLinkEntity> link = technologyLinkRepository.findByBatchAndPair(batch,
                    edge.getSource(), edge.getTarget());
            if (link.isPresent()) {
                TechnologyLinkEntity existingLinkWithAddedWeight = addWeightToLink(link.get(), edge);
                existingLinksWithAddedWeight.add(existingLinkWithAddedWeight);
            } else {
                TechnologyLinkEntity newLink = newLink(batch, edge);
                newLinks.add(newLink);
            }
        }
        log.info("Applying technologies links: {} existing links with added weight and {} new links",
                existingLinksWithAddedWeight.size(), newLinks.size());
        technologyLinkRepository.saveAll(existingLinksWithAddedWeight);
        technologyLinkRepository.saveAll(newLinks);
        offers.forEach(offer -> offer.setStatus(OfferStatus.LINKED_TECHNOLOGIES));
    }

    private List<Set<String>> getTechnologiesFromOffers(List<OfferEntity> offers) {
        return offers.stream()
                .map(offer -> contentToOfferJson(offer.getContent()))
                .map(OfferJson::getAllTechnologies)
                .collect(Collectors.toList());
    }

    private List<WeightedGraphEdge> getGraphPairLinks(List<Set<String>> technologiesFromOffers) {
        return technologiesFromOffers.stream()
                .map(GraphHelper::generatePairLinks)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private TechnologyLinkEntity newLink(BatchEntity batch, WeightedGraphEdge edge) {
        return TechnologyLinkEntity.builder()
                .batch(batch)
                .source(edge.getSource())
                .target(edge.getTarget())
                .weight(edge.getWeight())
                .build();
    }

    private TechnologyLinkEntity addWeightToLink(TechnologyLinkEntity link, WeightedGraphEdge edge) {
        Long newWeight = link.getWeight() + edge.getWeight();
        link.setWeight(newWeight);
        return link;
    }

    private OfferJson contentToOfferJson(String content) {
        try {
            return objectMapper.readValue(content, OfferJson.class);
        } catch (JsonProcessingException e) {
            throw new TechnologyLinkException("Can't parse offer content", e);
        }
    }
}
