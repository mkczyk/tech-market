package pl.marcinkowalczyk.techmarket.offer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    Page<OfferEntity> findOfferEntitiesByBatchOrderById(BatchEntity batch, Pageable page);

    Page<OfferEntity> findOfferEntitiesByBatchAndStatusOrderById(BatchEntity batch, OfferStatus offerStatus,
                                                                 Pageable page);

}
