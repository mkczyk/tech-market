package pl.marcinkowalczyk.techmarket.technologylink;

import lombok.*;
import pl.marcinkowalczyk.techmarket.batch.BatchEntity;
import pl.marcinkowalczyk.techmarket.offer.OfferEntity;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "technology_link")
public class TechnologyLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BatchEntity batch;

    @ManyToOne(fetch = FetchType.LAZY)
    private OfferEntity offer;

    private String source;
    private String target;
    private Long weight;

}
