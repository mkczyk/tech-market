package pl.marcinkowalczyk.techmarket.technologylink.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OfferJson {

    private OfferRequirementsJson requirements;

    public Set<String> getAllTechnologies() {
        Set<String> musts = requirements.getMusts().stream()
                .map(OfferRequirement::getValue)
                .collect(Collectors.toSet());
        Set<String> nices = requirements.getNices().stream()
                .map(OfferRequirement::getValue)
                .collect(Collectors.toSet());
        Set<String> all = new HashSet<>();
        all.addAll(musts);
        all.addAll(nices);
        return all;
    }
}
