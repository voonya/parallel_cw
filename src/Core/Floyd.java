package Core;

import java.util.Arrays;
import java.util.HashMap;

public class Floyd {


    static public FloydResult getMinDistanceMatrix(Graph graph) {
        int countVertex = graph.getCountVertex();

        double[][] dist = new double[countVertex][countVertex];
        int[][] prev = new int[countVertex][countVertex];

        for (int i = 0; i < countVertex; i++){
            for (int j = 0; j < countVertex; j++){
                if(i == j){
                    dist[i][j] = 0;
                    prev[i][j] = j;
                } else {
                    double weight = graph.getWeight(i, j);
                    dist[i][j] = graph.getWeight(i, j);

                    if(weight != Double.POSITIVE_INFINITY)
                        prev[i][j] = i;
                    else
                        prev[i][j] = -1;
                }

            }
        }

        for(int k = 0; k < countVertex; k++){
            for(int i = 0; i < countVertex; i++){
                for(int j = 0; j < countVertex; j++){
                    if(dist[i][j] > dist[i][k] + dist[k][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }

        checkForNegativeCycle(dist);

        return new FloydResult(dist, prev);
    }

    private static void checkForNegativeCycle(double[][] distMatrix){
        for(int i = 0; i < distMatrix.length; i++){
            if(distMatrix[i][i] < 0){
                System.out.println("GRAPH HAS NEGATIVE CYCLE!");
                System.exit(1);
            }
        }
    }

    public static String getPathBetweenVertex(int from, int to, int[][] prevMatrix) {
        if(prevMatrix[from][to] == -1){
            return "None";
        }

        String path = "" + to;

        int currentV = to;
        while(currentV != from) {
            currentV = prevMatrix[from][currentV];
            path = currentV + "-->" + path;
        }

        return path;
    }

    public static HashMap<Integer, HashMap<Integer, String>> getAllPaths(int[][] prevMatrix) {
        HashMap<Integer, HashMap<Integer, String>> paths = new HashMap<>();

        for(int i = 0; i < prevMatrix.length; i++){
            HashMap<Integer, String> map = new HashMap<>();
            for(int j = 0; j < prevMatrix.length; j++){
                map.put(j, getPathBetweenVertex(i, j, prevMatrix));
            }

            paths.put(i, map);
        }

        return paths;
    }
}
