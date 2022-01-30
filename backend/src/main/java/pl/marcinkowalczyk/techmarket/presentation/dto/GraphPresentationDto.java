package pl.marcinkowalczyk.techmarket.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphPresentationDto {

    private List<GraphNodeDto> nodes;
    private List<GraphLinkDto> links;
}
