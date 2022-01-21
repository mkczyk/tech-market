package pl.marcinkowalczyk.techmarket.batch;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "batches")
public class BatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    @Column(unique = true)
    private String name;

    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String content;
}
