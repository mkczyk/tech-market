package pl.marcinkowalczyk.techmarket.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final NoFluffJobsClient noFluffJobsClient;
    private final BatchMapper batchMapper;

    public void runBatch(String name) {
        BatchEntity batch = BatchEntity.builder()
                .status(BatchStatus.NEW)
                .name(name)
                .date(LocalDateTime.now())
                .content(noFluffJobsClient.getBatch())
                .build();

        batchRepository.save(batch);
        log.info("Saved batch '{}' with id {}", name, batch.getId());
    }

    public String readBatchContent(String name) {
        BatchEntity batch = batchRepository.findFirstByName(name);
        return batch.getContent();
    }

    public List<BatchDetailsResponseDto> findBatches() {
        return batchRepository.findAll().stream()
                .map(batchMapper::mapToBatchDetailsResponseDto)
                .collect(Collectors.toList());
    }
}
