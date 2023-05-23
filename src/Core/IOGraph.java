package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
public class IOGraph {
    private static String INF_SIGN = "INF";

    public static Graph readWeightsMatrixFromFile(String filepath) {
        List<String> lines = readLines(filepath);

        int countVertex = Integer.parseInt(lines.get(0));

        Graph graph = new Graph(countVertex);

        for(int i = 1; i < countVertex + 1; i++){
            String line = lines.get(i);
            List<String> weights = Arrays.asList(line.split(" "));

            for(int j = 0; j < countVertex; j++){
                if(!weights.get(j).equals(INF_SIGN)){
                    graph.addWeight(i-1, j, Integer.parseInt(weights.get(j)));
                }
            }
        }

        return graph;
    }

    public static void writeWeightsMatrixToFile(String filepath, Graph graph) {
        try {
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write(graph.getCountVertex() + "\n");

            int[][] matrix = graph.getWeightsMatrix();
            for(int i = 0; i < matrix.length; i++){
                for(int j = 0; j < matrix[i].length; j++){
                    if(matrix[i][j] != Integer.MAX_VALUE){
                        myWriter.write(Integer.toString(matrix[i][j]));
                    } else {
                        myWriter.write(INF_SIGN);
                    }

                    if(j!= matrix[i].length - 1){
                        myWriter.write(" ");
                    }
                }

                if(i != matrix.length - 1){
                    myWriter.write("\n");
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Graph readEdgesFromFile(String filepath) {
        List<String> lines = readLines(filepath);

        int countVertex = Integer.parseInt(lines.get(0));

        Graph graph = new Graph(countVertex);

        for(int i = 1; i < lines.size(); i++){
            String line = lines.get(i);
            List<String> edgeParts = Arrays.asList(line.split(" "));

            int from = Integer.parseInt(edgeParts.get(0));
            int to = Integer.parseInt(edgeParts.get(1));
            int weight = Integer.MAX_VALUE;

            if(!edgeParts.get(2).equals("INF")){
                weight = Integer.parseInt(edgeParts.get(2));
            }

            graph.addWeight(from, to, weight);
        }

        return graph;
    }

    public static void outputMatrix(int[][] matrix) {
        System.out.printf("%11s", "");

        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%11s", "#" + i);
        }
        System.out.println();

        System.out.printf("%11s", "-");
        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%11s", "-----------");
        }
        System.out.println();

        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%11s", "#" + i + "|");
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] == Integer.MAX_VALUE){
                    System.out.printf("%11s", INF_SIGN);
                }else {
                    System.out.printf("%11d", matrix[i][j]);
                }

            }
            System.out.println();
        }
    }

    private static List<String> readLines(String filepath) {
        File file = new File(filepath);

        if(!file.exists() || file.isDirectory()) {
            System.out.println("FILE DOES NOT EXIST OR IT IS A DIRECTORY!");
            System.exit(1);
        }

        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e){
            System.out.println("ERROR WITH READING FILE!");
            e.printStackTrace();
            System.exit(1);
        }

        return lines;
    }
}
