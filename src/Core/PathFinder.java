package Core;

import java.util.HashMap;

public class PathFinder {
    public static String getPathBetweenVertex(int from, int to, FloydResult result) {
        if(result.prev[from][to] == -1){
            return "None";
        }

        String path = "" + to;

        int currentV = to;
        while(currentV != from) {
            currentV = result.prev[from][currentV];
            path = currentV + "-->" + path;
        }

        return path;
    }

    public static int getDistBetweenVertex(int from, int to, FloydResult result) {
        if(result.prev[from][to] == -1){
            return Integer.MAX_VALUE;
        }

        int sum = 0;
        int currentV1 = to;
        int currentV2 = -1;

        while(currentV2 != from) {
            currentV2 = currentV1;
            currentV1 = result.prev[from][currentV1];

            if(currentV1 == Integer.MAX_VALUE)
                break;

            sum += result.dist[currentV1][currentV2];
        }

        return sum;
    }


    public static HashMap<Integer, HashMap<Integer, String>> getAllPaths(FloydResult result) {
        HashMap<Integer, HashMap<Integer, String>> paths = new HashMap<>();

        for(int i = 0; i < result.prev.length; i++){
            HashMap<Integer, String> map = new HashMap<>();
            for(int j = 0; j < result.prev.length; j++){
                map.put(j, getPathBetweenVertex(i, j, result));
            }

            paths.put(i, map);
        }

        return paths;
    }
}
