package pl.marcinkowalczyk.techmarket.batchoffer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;
import pl.marcinkowalczyk.techmarket.batch.BatchRepository;
import pl.marcinkowalczyk.techmarket.batch.BatchStatus;
import pl.marcinkowalczyk.techmarket.batchoffer.json.OfferFromBatchJson;
import pl.marcinkowalczyk.techmarket.offer.OfferEntity;
import pl.marcinkowalczyk.techmarket.offer.OfferMapper;
import pl.marcinkowalczyk.techmarket.offer.OfferRepository;
import pl.marcinkowalczyk.techmarket.offer.OfferStatus;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchExplodeService {

    private final BatchRepository batchRepository;
    private final OfferRepository offerRepository;
    private final ObjectMapper objectMapper;
    private final OfferMapper offerMapper;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void explode(String name) {
        BatchEntity batch = batchRepository.findFirstByName(name);
        log.info("Exploding batch '{}' with id {}", batch.getName(), batch.getId());
        explodeBatch(batch);
        batch.setStatus(BatchStatus.EXPLODED);
        log.info("Batch '{}' with id {} exploded", batch.getName(), batch.getId());
    }

    private void explodeBatch(BatchEntity batch) {
        String content = batch.getContent();
        List<OfferFromBatchJson> offerFromBatchJsons = contentToOffersJson(content);
        log.info("Adding {} offers", offerFromBatchJsons.size());
        offerFromBatchJsons.forEach(offerFromBatchJson -> addOffer(batch, offerFromBatchJson));
        log.info("Added {} offers", offerFromBatchJsons.size());
    }

    private void addOffer(BatchEntity batch, OfferFromBatchJson offerFromBatchJson) {
        OfferEntity offerEntity = OfferEntity.builder()
                .status(OfferStatus.NEW)
                .batch(batch)
                .build();
        offerMapper.updateOfferFromOfferFromBatchJson(offerFromBatchJson, offerEntity);
        offerRepository.save(offerEntity);
    }

    private List<OfferFromBatchJson> contentToOffersJson(String content) {
        try {
            JsonNode json = objectMapper.readTree(content);
            ArrayNode postingsJson = (ArrayNode) json.get("postings");
            return objectMapper.convertValue(postingsJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new BatchExplodeException("Can't parse batch content", e);
        }
    }

}
