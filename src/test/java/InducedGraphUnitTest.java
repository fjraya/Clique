import model.InducedGraph;
import mothers.InducedGraphMother;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by franciscoraya on 10/7/17.
 */
public class InducedGraphUnitTest {
    @Test(groups = {"unit"})
    public void test_isClique_calledWithCliqueGraphGiven_returnTrue() {
        InducedGraph graph = InducedGraphMother.getCliqueGraph();
        boolean expected = true;
        exerciseAndVerifyIsClique(graph, expected);
    }

    @Test(groups = {"unit"})
    public void test_isClique_calledWithNoCliqueGraphGiven_returnFalse() {
        InducedGraph graph = InducedGraphMother.getNoCliqueGraph();
        boolean expected = false;
        exerciseAndVerifyIsClique(graph, expected);
    }


    @DataProvider(name = "getGraphDataProvider")
    public Object[][] getGraphDataProvider() {
        return new Object[][]{{InducedGraphMother.getTestInstance(), new ArrayList<>(Arrays.asList("s01", "s02", "s03", "s04", "s05"))},
                {InducedGraphMother.getOneNodeInstance(), new ArrayList<>(Arrays.asList("s01"))},
                {InducedGraphMother.getTestInstance(), new ArrayList<>(Arrays.asList("s01", "s02", "s03", "s04", "s05"))}
        };
    }


    @Test(groups = {"unit"}, dataProvider = "getGraphDataProvider")
    public void test_getMaximalCliqueFromInducedGraph_graphGiven_returnCorrectMaximalClique(InducedGraph sut, List<String> expected) {
        List<String> actual = sut.getMaximalCliqueFromInducedGraph();
        Assert.assertEquals(actual, expected);
    }


    private void exerciseAndVerifyIsClique(InducedGraph graph, boolean expected) {
        boolean actual = graph.isClique();
        Assert.assertEquals(actual, expected);
    }


}
