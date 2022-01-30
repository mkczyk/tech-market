package pl.marcinkowalczyk.techmarket.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;
import pl.marcinkowalczyk.techmarket.batch.BatchRepository;
import pl.marcinkowalczyk.techmarket.presentation.dto.GraphLinkDto;
import pl.marcinkowalczyk.techmarket.presentation.dto.GraphNodeDto;
import pl.marcinkowalczyk.techmarket.presentation.dto.GraphPresentationDto;
import pl.marcinkowalczyk.techmarket.technologylink.TechnologyLinkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PresentationService {

    private final BatchRepository batchRepository;
    private final PresentationMapper presentationMapper;
    private final TechnologyLinkRepository technologyLinkRepository;

    @Transactional
    public GraphPresentationDto getPresentation(String name) {
        BatchEntity batch = batchRepository.findFirstByName(name);
        List<GraphNodeDto> nodes = technologyLinkRepository.findAllTechnologies(batch).stream()
                .map(GraphNodeDto::new)
                .collect(Collectors.toList());
        List<GraphLinkDto> links = technologyLinkRepository.findAllByBatch(batch).stream()
                .map(presentationMapper::mapToGraphLinkDto)
                .collect(Collectors.toList());
        return GraphPresentationDto.builder()
                .nodes(nodes)
                .links(links)
                .build();
    }
}
