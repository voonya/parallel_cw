package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    private double[][] weightsMatrix;

    private List<Edge> edges = new ArrayList<>();

    public Graph(int countVertex){
        this.weightsMatrix = new double[countVertex][countVertex];
        for (double[] row: weightsMatrix)
            Arrays.fill(row, Double.POSITIVE_INFINITY);
    }

    public void addWeight(int from, int to, double weight){
        weightsMatrix[from][to] = weight;
        edges.add(new Edge(from, to, weight));
    }

    public double[][] getWeightsMatrix() {
        return weightsMatrix;
    }

    public double getWeight(int from, int to){
        return weightsMatrix[from][to];
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getCountVertex(){
        return weightsMatrix.length;
    }
}
