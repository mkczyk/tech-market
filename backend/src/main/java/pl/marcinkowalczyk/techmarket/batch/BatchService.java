package pl.marcinkowalczyk.techmarket.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final NoFluffJobsClient noFluffJobsClient;

    public void runBatch(String name) {
        BatchEntity batch = BatchEntity.builder()
                .name(name)
                .date(LocalDateTime.now())
                .content(noFluffJobsClient.getBatch())
                .build();

        batchRepository.save(batch);
        log.info("Saved batch '{}' with id {}", name, batch.getId());

    }
}
