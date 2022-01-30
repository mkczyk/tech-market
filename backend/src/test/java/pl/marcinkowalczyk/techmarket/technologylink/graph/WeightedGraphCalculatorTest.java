package pl.marcinkowalczyk.techmarket.technologylink.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightedGraphCalculatorTest {

    @Test
    void calculateWeightedEdges() {
        // given
        List<WeightedGraphEdge> edges = List.of(
                WeightedGraphEdge.of("a", "b"),
                WeightedGraphEdge.of("b", "c"),
                WeightedGraphEdge.of("c", "d"),
                WeightedGraphEdge.of("a", "b"),
                WeightedGraphEdge.of("b", "c"),
                WeightedGraphEdge.of("b", "a")
        );

        // when
        List<WeightedGraphEdge> weightedGraphEdges = WeightedGraphCalculator.calculateWeightedEdges(edges);

        // then
        List<WeightedGraphEdge> expectedWeightedGraphEdges = List.of(
                WeightedGraphEdge.of("a", "b", 3L),
                WeightedGraphEdge.of("b", "c", 2L),
                WeightedGraphEdge.of("c", "d", 1L)
        );
        assertEquals(expectedWeightedGraphEdges, weightedGraphEdges);
    }
}
