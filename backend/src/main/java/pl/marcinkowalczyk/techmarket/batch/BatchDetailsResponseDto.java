package pl.marcinkowalczyk.techmarket.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchDetailsResponseDto {

    private Long id;
    private String name;
    private LocalDateTime date;
    private int contentLength;
}
