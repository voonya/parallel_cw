package Core;

public class FloydResult {
    public double[][] dist;
    public int[][] prev;

    public FloydResult(double[][] dist, int[][] prev){
        this.dist = dist;
        this.prev = prev;
    }
}
