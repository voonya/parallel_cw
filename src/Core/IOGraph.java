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

        System.out.printf("%11s", "" + getAscii(217));
        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%11s", "-----------");
        }
        System.out.println();

        for(int i = 0; i < matrix.length; i++){
            System.out.printf("%11s", "#" + i + getAscii(178));
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


    public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };
    public static final char getAscii(int code) {
        if (code >= 0x80 && code <= 0xFF) {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }
}
