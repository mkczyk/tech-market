package pl.marcinkowalczyk.techmarket.technologylink.graph;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class WeightedGraphEdge {

    private String source;
    private String target;
    private Long weight;

    public static WeightedGraphEdge of(String source, String target) {
        return of(source, target, 1L);
    }

    public static WeightedGraphEdge of(String source, String target, Long weight) {
        return new WeightedGraphEdge(source, target, weight);
    }

    public boolean equalsEdgeWithoutWeight(WeightedGraphEdge rawEdge) {
        return (source.equals(rawEdge.source) && target.equals(rawEdge.target)) ||
                (source.equals(rawEdge.target) && target.equals(rawEdge.source));
    }

    public String toTextEdge() {
        return String.format("[%s, %s]", source, target);
    }

    public static String toTextEdge(List<WeightedGraphEdge> edges) {
        String elementsText = edges.stream()
                .map(edge -> edge.toTextEdge())
                .collect(Collectors.joining(", "));
        return String.format("[%s]", elementsText);
    }
}
