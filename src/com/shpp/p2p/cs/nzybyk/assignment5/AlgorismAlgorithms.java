package com.shpp.p2p.cs.nzybyk.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program sum 2 positive number in String format different long
 */

public class AlgorismAlgorithms extends TextProgram {
    /**
     * Enter point of program;
     */
    public void run() {
        //startTest();

        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Start each written test
     */
    private void startTest() {
        check("1","2","3");
        check("11","2","13");
        check("19","9","28");
        check("1111111111111111","2","1111111111111113");
        check("2","1111111111111111","1111111111111113");
        check("2","11","13");
        check("2","1","3");
        check("000000001","32","33");
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        // TODO: Replace this comment with your implementation of this method!

        char [] num1Char = n1.toCharArray();
        char [] num2Char = n2.toCharArray();
        int [] num1Int = charToIntMass(num1Char,num1Char.length);
        int [] num2Int = charToIntMass(num2Char,num2Char.length);
        int[] sumIntMass;
        if(num1Int.length>num2Int.length){
            sumIntMass = new int[num1Int.length];
            sumIntMass = sumTwoIntMass(num1Int,num2Int);
        }
        else {
            sumIntMass = new int[num2Int.length];
            sumIntMass = sumTwoIntMass(num2Int,num1Int);
        }
        String str = IntToString(sumIntMass);

        return str;
    }

    /**
     * This method convert massive of int value to String
     * @param sumIntMass massive of Integer value. Massive with final summa.
     * @return converted massive of int value to String
     */
    private String IntToString(int[] sumIntMass) {
        String str = "";
        for (int i = 0; i < sumIntMass.length; i++) {
            if (i == 0 && sumIntMass[0] == 0) {
                continue;
            }
            str = str + sumIntMass[i];
        }
        return str;
    }

    /**
     * Method sum two value written of massive
     * @param num1Int first number
     * @param num2Int second number
     * @return sum of first and second numbers.
     */
    private int[] sumTwoIntMass(int[] num1Int, int[] num2Int) {
        //create massive for final sum
        int [] sumMas = new int[num1Int.length+1];
        int buffer = 0 ;
        int num;
        //sum value of short massive
;        for(int i = 0; i<num2Int.length;i++){
            num = num1Int[num1Int.length-i-1] + num2Int[num2Int.length-i-1]+buffer;
            if(num/10!=0){
                buffer = 1;
            }
            else {
                buffer = 0;
                }
            sumMas[sumMas.length-i-1] = num%10;
        }
        // if have remaindered after sum
        if (buffer==1){

            for (int i = 0;i<num1Int.length-num2Int.length;i++){
                num = num1Int[num1Int.length-num2Int.length-i-1]+buffer;
                if(num/10!=0){
                    buffer = 1;
                }
                else {
                    buffer = 0;
                }
                sumMas[sumMas.length-i-1-num2Int.length] = num%10;
            }
        }
        //copied value from more long massive if not have remainder
        else {
            for (int i = 0;i<num1Int.length-num2Int.length;i++) {
                sumMas[sumMas.length - i - 1 - num2Int.length] = num1Int[num1Int.length - i - 1 - num2Int.length];
            }
        }
        //if have remaindered after copies longer massive
        if (buffer == 1){
            sumMas[0]=1;
        }
        return sumMas;
    }

    /**
     * Converter type of massive, char to int
     * @param num1Char mass of char
     * @param length length of massive
     * @return  massive of int
     */
    private int[] charToIntMass(char[] num1Char, int length) {
        int[]mas = new int[length];
        for (int i = 0; i<length;i++){
            mas[i] = num1Char[i] - '0';
        }
        return mas;
    }

    /**
     * Test for addNumericStrings() method
     * @param num1 test parameter
     * @param num2  second test parameter
     * @param trueValue expected answer
     */
    private void check(String num1, String num2,String trueValue) {
        String check = addNumericStrings(num1,num2);
        if (check.equals(trueValue)){
            println("Pass: "+num1+" + "+num2+" = "+trueValue);
        }
        else {
            println("Fail: True is - "+ trueValue +". We are get - " + check);
        }
    }
}
