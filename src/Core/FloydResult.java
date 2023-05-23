package Core;

public class FloydResult {
    public int[][] dist;
    public int[][] prev;

    public boolean isNegativeCycle;

    public FloydResult(int[][] dist, int[][] prev, boolean isNegativeCycle){
        this.dist = dist;
        this.prev = prev;
        this.isNegativeCycle = isNegativeCycle;
    }

    public boolean isEqual(FloydResult res) {
        if(res.dist.length != dist.length) {
            System.out.println("Size of graphs are different!");
            System.exit(1);
        }

        // compare distances
        for(int i = 0; i < dist.length; i++){
            for(int j = 0; j < dist.length; j++){
                if(res.dist[i][j] != dist[i][j]){
                    System.out.println("DISTANCE UNEQUAL");
                    return false;
                }
            }
        }

        // compare paths
        for(int i = 0; i < prev.length; i++){
            for(int j = 0; j < prev.length; j++){
                if(res.prev[i][j] != prev[i][j]){
                    // check if another path with the same dist
                    if(res.dist[i][j] != PathFinder.getDistBetweenVertex(i, j, res)) {
                        System.out.println("PATHS UNEQUAL");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
