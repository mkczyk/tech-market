package pl.marcinkowalczyk.techmarket.technologylink.graph;

import org.assertj.core.util.Sets;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphHelperTest {

    @ParameterizedTest
    @MethodSource("provideElementsWithPairs")
    void pairTest(Set<String> elements, String expected) {
        List<WeightedGraphEdge> pairs = GraphHelper.generatePairLinks(elements);
        assertEquals(expected, WeightedGraphEdge.toTextEdge(pairs));
    }

    private static Stream<Arguments> provideElementsWithPairs() {
        return Stream.of(
                Arguments.of(
                        Sets.newLinkedHashSet("a", "b", "c", "d"),
                        "[[a, b], [a, c], [a, d], [b, c], [b, d], [c, d]]"),
                Arguments.of(Sets.newLinkedHashSet("a", "b"), "[[a, b]]"),
                Arguments.of(Sets.newLinkedHashSet(), "[]")
        );
    }
}
