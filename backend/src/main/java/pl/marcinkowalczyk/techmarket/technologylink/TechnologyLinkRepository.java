package pl.marcinkowalczyk.techmarket.technologylink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnologyLinkRepository extends JpaRepository<TechnologyLinkEntity, Long> {

    @Query("select t from TechnologyLinkEntity t where t.batch = :batch and " +
            "((t.source = :source and t.target = :target) or (t.source = :target and t.target = :source))")
    Optional<TechnologyLinkEntity> findByBatchAndPair(@Param("batch") BatchEntity batch,
                                                      @Param("source") String source, @Param("target") String target);

    List<TechnologyLinkEntity> findAllByBatch(BatchEntity batch);

    @Query(value = "select source from technology_link where batch_id = :batch " +
            "union " +
            "select target from technology_link where batch_id = :batch",
            nativeQuery = true)
    List<String> findAllTechnologies(@Param("batch") BatchEntity batch);

}
