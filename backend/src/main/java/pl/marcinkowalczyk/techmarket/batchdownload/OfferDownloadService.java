package pl.marcinkowalczyk.techmarket.batchdownload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.marcinkowalczyk.techmarket.nofluffjobs.NoFluffJobsClient;
import pl.marcinkowalczyk.techmarket.offer.OfferEntity;
import pl.marcinkowalczyk.techmarket.offer.OfferStatus;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfferDownloadService {

    private final NoFluffJobsClient noFluffJobsClient;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void download(List<OfferEntity> offers) {
        List<Long> offerIds = offers.stream()
                .map(OfferEntity::getId)
                .collect(Collectors.toList());
        log.info("Downloading {} offers with ids: {}", offers.size(), offerIds);
        offers.forEach(this::download);
    }

    private void download(OfferEntity offer) {
        log.debug("Downloading offer with id {} and identifier '{}'", offer.getId(), offer.getIdentifier());
        String content = noFluffJobsClient.getOffer(offer.getIdentifier());
        offer.setContent(content);
        offer.setDownloadedDate(LocalDateTime.now());
        offer.setStatus(OfferStatus.DOWNLOADED);
    }
}
