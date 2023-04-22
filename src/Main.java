import Core.Floyd;
import Core.FloydResult;
import Core.Graph;
import Core.IOGraph;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String graphEdgesPath = "D:\\Study\\KPI\\year-3\\part-2\\cw\\src\\graphs/test1.txt";

        Graph graphEdges = IOGraph.readEdgesFromFile(graphEdgesPath);

        IOGraph.outputMatrix(graphEdges.getWeightsMatrix());

        FloydResult result = Floyd.getMinDistanceMatrix(graphEdges);

        HashMap<Integer, HashMap<Integer, String>> res = Floyd.getAllPaths(result.prev);

        System.out.println(Floyd.getPathBetweenVertex(3, 2, result.prev));

        System.out.println();

    }
}