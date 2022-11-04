package com.shpp.p2p.cs.nzubyk.assignment6.tm;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        boolean test = false;

        for (int row = 0; row<toneMatrix.length;row++){
            if(toneMatrix[row][column]==true){

                test = true;
                for (int i = 0;i<result.length;i++){
                    result[i] = result[i] + samples[row][i];
                }
            }
        }

        //This code needed for normalizing of sample when it has not only false
        if(test){
            double maxValue = maxValueOfMassive(result);
            for(int i = 0; i<result.length;i++){
                result[i] = result[i]/maxValue;
            }
        }
        else {
            for(int i = 0; i<result.length;i++){
                result[i] = 0;
            }
        }
        /* TODO: Fill this in! */

        return result;
    }


    /**
     * TThis method find out and return max modulus of numbers from massive
     * @param result massive of double values
     * @return max modulus of numbers
     */
    private static double maxValueOfMassive(double[] result) {
        double maxValue = 0;
        for (double ms:result
        ) {
            if(Math.abs(maxValue)<Math.abs(ms)){
                maxValue = ms;
            }
        }
        return Math.abs(maxValue);
    }
}

