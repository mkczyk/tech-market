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
import pl.marcinkowalczyk.techmarket.technologylink.json.OfferJson;

import java.util.List;
import java.util.Set;

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
            offers.forEach(offer -> linkTechnologiesFromOffer(batch, offer));
            page = getOffersPageToLink(batch, i);
        }
    }

    private Page<OfferEntity> getOffersPageToLink(BatchEntity batch, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, LINKING_SIZE);
        return offerRepository.findOfferEntitiesByBatchAndStatusOrderById(batch,
                OfferStatus.DOWNLOADED, pageRequest);
    }

    private void linkTechnologiesFromOffer(BatchEntity batch, OfferEntity offer) {
        OfferJson offerJson = contentToOfferJson(offer.getContent());
        Set<String> technologies = offerJson.getAllTechnologies();
        List<List<String>> combinations = GraphHelper.generatePairs(technologies);
        combinations.forEach(combination -> linkTechnologiesFromOffer(batch, offer,
                combination.get(0), combination.get(1)));
    }

    private void linkTechnologiesFromOffer(BatchEntity batch, OfferEntity offer, String source, String target) {
        TechnologyLinkEntity link = technologyLinkRepository.findByBatchAndPair(batch, source, target)
                .map(this::addWeightToLink)
                .orElse(newLink(batch, offer, source, target));
        technologyLinkRepository.save(link);
        offer.setStatus(OfferStatus.LINKED_TECHNOLOGIES);
    }

    private TechnologyLinkEntity newLink(BatchEntity batch, OfferEntity offer, String source, String target) {
        return TechnologyLinkEntity.builder()
                .batch(batch)
                .offer(offer)
                .source(source)
                .target(target)
                .weight(1L)
                .build();
    }

    private TechnologyLinkEntity addWeightToLink(TechnologyLinkEntity link) {
        link.setWeight(link.getWeight() + 1);
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
