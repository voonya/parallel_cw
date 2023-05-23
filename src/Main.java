import Core.*;
import Solvers.*;
import Solvers.Blocked.FloydParallelBlocked;
import Solvers.Row.FloydParallelRow;

public class Main {
    public static void main(String[] args) {
        String graphsPath = "D:\\Study\\KPI\\year-3\\part-2\\cw\\src\\graphs/";
        String fileName = "test2000.txt";

        Graph graph = IOGraph.readWeightsMatrixFromFile(graphsPath + fileName);

        IFloydSolver seqSolver = new FloydSequential();
        testSolverNTimes(seqSolver, "SEQUENTIAL", graph, 5);

        IFloydSolver blockedSolver = new FloydParallelBlocked(8, 50);
        testSolverNTimes(blockedSolver, "BLOCKED", graph, 5);

        IFloydSolver rowSolver = new FloydParallelRow(8, 50);
        testSolverNTimes(rowSolver, "ROW", graph, 5);
    }

    public static void testSolverNTimes(IFloydSolver solver, String solverName, Graph graph, int n) {
        double sum = 0;
        System.out.println("SOLVER: " + solverName + " started");
        for (int i = 0; i < n; i++) {
            System.out.println("\nITERATION: " + i);
            sum += testSolver(solver, solverName, graph, false);
        }
        double avg = Math.floor(sum * 100 / n) / 100;
        System.out.println("\nAVG TIME: " + avg);
    }

    public static long testSolver(IFloydSolver solver, String solverName, Graph graph, boolean checkCorrectness) {
        System.out.println("SOLVER: " + solverName + " started");

        long start = System.currentTimeMillis();
        FloydResult res = solver.getMinDistances(graph);
        long end = System.currentTimeMillis();

        System.out.println("Execution time: " + (end - start) + "ms");

        if (checkCorrectness) {
            System.out.println("SOLVER: " + solverName + " checking for correctness...");
            IFloydSolver defSolver = new FloydSequential();
            FloydResult defRes = defSolver.getMinDistances(graph);
            boolean isEqual = defRes.isEqual(res);
            System.out.printf("SOLVER: %s %s\n", solverName, isEqual ? "CORRECT" : "INCORRECT");
        }

        return end - start;
    }

    public static void generateGraphAndSaveToFile(String filepath, int countVertex, int avgEdges, int disp, int negBound, int avgNeg, int dispNeg, int avgPos, int dispPos) {
        Graph generatedGraph = Graph.generateGraph(countVertex, avgEdges, disp, negBound, avgNeg, dispNeg, avgPos, dispPos);
        IOGraph.writeWeightsMatrixToFile(filepath, generatedGraph);
    }
}