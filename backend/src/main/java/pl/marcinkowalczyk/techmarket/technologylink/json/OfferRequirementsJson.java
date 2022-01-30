package pl.marcinkowalczyk.techmarket.technologylink.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OfferRequirementsJson {

    private List<OfferRequirement> musts;
    private List<OfferRequirement> nices;
}
