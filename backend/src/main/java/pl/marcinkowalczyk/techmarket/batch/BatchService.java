package pl.marcinkowalczyk.techmarket.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {

    private final BatchRepository batchRepository;

    public void runBatch(String name) {
        BatchEntity batch = BatchEntity.builder()
                .name(name)
                .build();
        batchRepository.save(batch);
        log.info("Saved batch: {}", batch);
    }
}
