package pl.marcinkowalczyk.techmarket.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Long> {

}
