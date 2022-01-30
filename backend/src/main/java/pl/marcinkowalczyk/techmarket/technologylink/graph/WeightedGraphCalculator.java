package pl.marcinkowalczyk.techmarket.technologylink.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeightedGraphCalculator {

    private final List<WeightedGraphEdge> rawEdges;
    private final List<WeightedGraphEdge> weightedEdges = new ArrayList<>();

    private WeightedGraphCalculator(List<WeightedGraphEdge> rawEdges) {
        this.rawEdges = rawEdges;
    }

    public static List<WeightedGraphEdge> calculateWeightedEdges(List<WeightedGraphEdge> edges) {
        WeightedGraphCalculator calculator = new WeightedGraphCalculator(edges);
        calculator.calculate();
        return calculator.weightedEdges;
    }

    private void calculate() {
        rawEdges.forEach(this::addEdgeOrAddWeight);
    }

    private void addEdgeOrAddWeight(WeightedGraphEdge rawEdge) {
        findEdge(rawEdge)
                .ifPresentOrElse(
                        foundEdge -> addWeight(foundEdge, rawEdge),
                        () -> addNewEdge(rawEdge));
    }

    private Optional<WeightedGraphEdge> findEdge(WeightedGraphEdge rawEdge) {
        return weightedEdges.stream()
                .filter(weightedEdge -> weightedEdge.equalsEdgeWithoutWeight(rawEdge))
                .findFirst();
    }

    private void addWeight(WeightedGraphEdge foundEdge, WeightedGraphEdge rawEdge) {
        foundEdge.setWeight(foundEdge.getWeight() + rawEdge.getWeight());
    }

    private void addNewEdge(WeightedGraphEdge rawEdge) {
        weightedEdges.add(WeightedGraphEdge.of(rawEdge.getSource(), rawEdge.getTarget()));
    }
}
