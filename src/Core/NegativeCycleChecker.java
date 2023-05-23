package Core;

public class NegativeCycleChecker {
    public static boolean isNegativeCycle(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i][i] < 0){
                return true;
            }
        }

        return false;
    }
}
