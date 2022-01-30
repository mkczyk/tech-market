package pl.marcinkowalczyk.techmarket.technologylink.graph;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphHelper {

    public static List<WeightedGraphEdge> generatePairLinks(Set<String> elements) {
        return generatePairLinks(new ArrayList<>(elements));
    }

    private static List<WeightedGraphEdge> generatePairLinks(List<String> elements) {
        List<WeightedGraphEdge> pairs = new ArrayList<>();
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                pairs.add(WeightedGraphEdge.of(elements.get(i), elements.get(j)));
            }
        }
        return pairs;
    }
}
