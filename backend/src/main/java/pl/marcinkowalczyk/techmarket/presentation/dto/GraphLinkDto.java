package pl.marcinkowalczyk.techmarket.presentation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GraphLinkDto {

    private String source;
    private String target;
    private Long value;
}
