package pl.marcinkowalczyk.techmarket.technologylink;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphHelper {

    public static List<List<String>> generatePairs(Set<String> elements) {
        return generatePairs(new ArrayList<>(elements));
    }

    private static List<List<String>> generatePairs(List<String> elements) {
        List<List<String>> pairs = new ArrayList<>();
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                pairs.add(List.of(elements.get(i), elements.get(j)));
            }
        }
        return pairs;
    }
}
