package pl.marcinkowalczyk.techmarket.batchdownload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;
import pl.marcinkowalczyk.techmarket.batch.BatchRepository;
import pl.marcinkowalczyk.techmarket.batch.BatchStatus;
import pl.marcinkowalczyk.techmarket.offer.OfferEntity;
import pl.marcinkowalczyk.techmarket.offer.OfferRepository;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchDownloadService {

    private final BatchRepository batchRepository;
    private final OfferRepository offerRepository;
    private final OfferDownloadService offerDownloadService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void download(String batchName, Integer number) {
        BatchEntity batch = batchRepository.findFirstByName(batchName);
        Page<OfferEntity> page = offerRepository.findOfferEntitiesByBatchOrderById(batch, PageRequest.of(0, number));
        log.info("Downloading {} offers from batch '{}' with id {}, left {} offers to download",
                number, batch.getName(), batch.getId(), page.getTotalElements());
        if (page.hasContent()) {
            offerDownloadService.download(page.getContent());
        } else if (page.isEmpty() || number == page.getTotalElements()) {
            log.info("All offers downloaded for '{}' with id {}", batch.getName(), batch.getId());
            batch.setStatus(BatchStatus.OFFERS_DOWNLOADED);
        }
        log.info("End downloading {} offer from batch '{}' with id {}, left {} offers to download",
                number, batch.getName(), batch.getId(), page.getTotalElements() - number);
    }
}
