package pl.marcinkowalczyk.techmarket.technologylink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;

import java.util.Optional;

@Repository
public interface TechnologyLinkRepository extends JpaRepository<TechnologyLinkEntity, Long> {

    @Query("select t from TechnologyLinkEntity t where t.batch = :batch and " +
            "((t.source = :source and t.target = :target) or (t.source = :target and t.target = :source))")
    Optional<TechnologyLinkEntity> findByBatchAndPair(@Param("batch") BatchEntity batch,
                                                      @Param("source") String source, @Param("target") String target);

}
