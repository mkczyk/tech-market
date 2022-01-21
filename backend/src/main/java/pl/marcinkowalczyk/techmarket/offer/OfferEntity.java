package pl.marcinkowalczyk.techmarket.offer;

import lombok.*;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private BatchEntity batch;

    private String identifier;
    private String name;
    private String title;
    private String category;

}
