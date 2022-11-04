package com.shpp.p2p.cs.nzubyk.assignment10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Reed equation from program parameters in "1 + a * 2" "a = 2" format
 * Program parse equation and solved it
 * Program can be extended for dynamic change variables
 */
public class Assignment10Part1 {
    //The values and variables from the example are collected in the array in the correct order
    private static final ArrayList<String> arrayListDouble = new ArrayList<>();
    //The key is the index where the variable is, the value is the name of the variable.
    private static final HashMap<Integer, String> variablesPosition = new HashMap<>();
    //The key is the name of the variable, the value is the value of the variable
    private static final HashMap<String, String> variables = new HashMap<>();
    //An array of mathematical operators in the appropriate order
    private static final ArrayList<Character> arrayListSigns = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //print equation
        arrToString(args);
        //start parsing equation
        parseEquation(args);
        //finds answer of equation
        double d = calculate(arrayListDouble, arrayListSigns);
        //print answer
        System.out.println(variables);
        System.out.println(args[0]+" = " + d);


    }

    /**
     * Start all parsers methods
     * @param argsEq Class input parameters
     */
    private static void parseEquation(String [] argsEq) {
        String eq = primaryProcessing(argsEq[0]);

        parseNumbers(eq);

        parseSigns(eq);

        parseVariables(argsEq);
        changeVariablesOnValue();
    }

    /**
     *Replaces in the arrayListDouble variables to values
     * If in the variable from the arrayList the double first sign '-'
     * replace the mathematical operator from the array variables
     * when there will be reassignment
     */
    private static void changeVariablesOnValue() {
        for (Map.Entry<Integer,String> entry: variablesPosition.entrySet()
             ) {
            if(variables.get(entry.getValue())==null){
                System.out.println(entry.getValue()+" - Not found this variable in list of the variables, delete this " +
                        "variable from equation or assign a value");
                System.exit(0);
            }
            else {
                char [] s = arrayListDouble.get(entry.getKey()).toCharArray();

                if(s[0]=='-'){
                    arrayListDouble.set(entry.getKey(), "-"+variables.get(entry.getValue()));
                }
                else {
                    arrayListDouble.set(entry.getKey(), variables.get(entry.getValue()));
                }
            }
        }
    }

    /**
     *The method forms a hashmap of the variable-value type
     * from arg parameters
     * @param args array of variable-value in format "name = value"
     */
    private static void parseVariables(String[] args) {
        for (int i = 1;i<args.length;i++){
            String[] line = args[i].replaceAll(" ", "").split("=");
            if (line.length!=2 || isDouble(line[0])){

                System.out.println("wrong format of variables ["+args[i]+"], Change it and restart the program");
                System.exit(0);
            }
            else {
                variables.put(line[0],line[1]);
            }
        }
    }

    /**
     * The method replaces double characters and blank spaces
     * @param formula for formatting
     * @return formatted formula
     */
    private static String primaryProcessing(String formula){
        String buffer;
        buffer = formula.replaceAll(" ", "" );
        buffer = buffer.replaceAll("--","+");
        buffer = buffer.replaceAll("[-+][+-]","-");
        buffer = buffer.replaceAll("//","/");
        buffer = buffer.replaceAll("[*]{2}","*");

        return buffer;
    }

    /**
     *  This method do all math operation in right sequence.
     *  Quantity Math operators must be equals quantity of numbers + 1
     *  Start position of Math operators must be after first value
     * @param value2 array of value
     * @param signs2 array of math operators
     * @return value of answer Equations
     */
    private static double calculate(ArrayList<String> value2, ArrayList<Character> signs2) {
        ArrayList<Double> value = new ArrayList<>();
        for (String s: value2
             ) {
            s = s.replaceAll("--","");
            value.add(Double.parseDouble(s));
        }

        ArrayList<Character> signs = new ArrayList<>(signs2);
        if (value.size() == signs.size() ) {

            if (!signs.isEmpty()){
                if (signs.get(0).equals('-')){
                    value.set(0,-value.get(0));
                    signs.remove(0);
                    }
                else {signs.remove(0);}
            }


            for (int i = signs.size()-1 ; i >= 0; i--) {
                if (!signs.isEmpty() && signs.get(i).equals('^')) {
                    value.set(i, Math.pow(value.get(i), value.get(i + 1)));
                    value.remove(i + 1);
                    signs.remove(i);
                }
            }
            for (int i = 0; i < signs.size(); i++) {
                if (!signs.isEmpty()) {
                    if (signs.get(i).equals('*')) {
                        value.set(i, value.get(i) * value.get(i + 1));
                        value.remove(i + 1);
                        signs.remove(i);
                        i--;
                    } else if (signs.get(i).equals('/')) {
                        value.set(i, value.get(i) / value.get(i + 1));
                        value.remove(i + 1);
                        signs.remove(i);
                        i--;
                    }
                }
            }

            for (int i = 0; i <= signs.size(); i++) {
                if (!signs.isEmpty()) {
                    if (signs.get(i).equals('+')) {
                        value.set(i, value.get(i) + value.get(i + 1));
                        value.remove(i + 1);
                        signs.remove(i);
                        i--;
                    } else if (signs.get(i).equals('-')) {
                        value.set(i, value.get(i) - value.get(i + 1));
                        value.remove(i+1);
                        signs.remove(i);
                        i--;
                    }
                }
            }
            return value.get(0);
        }
        else {
            System.out.println("Not found number or variable");
            System.exit(0);
            return 0;
        }
    }

    /**
     * The method forms an array of mathematical operators from a line
     *@param line line of  equation
     */
    private static void parseSigns(String line) {
        char [] masChar = line.toCharArray();
        //if first char is '-' add him to array else add '+'
        if (masChar[0] == '-'){arrayListSigns.add('-');}
        else {arrayListSigns.add('+');}

        for (int i = 1; i < masChar.length -1; i++) {
            //if not double math operators
            if (isSign(masChar[i]) && !isSign(masChar[i+1])) {

                arrayListSigns.add(masChar[i]);
            }
            else if(isSign(masChar[i]) && masChar[i+1] == '-') {

                arrayListSigns.add(masChar[i]);
                //if next operator is '-' swap math operator
                arrayListDouble.set(arrayListSigns.size()-1,"-"+arrayListDouble.get(arrayListSigns.size()-1));
                //skip next math operator
                i++;
            }
        }

    }

    /**
     * Looking for line equals pattern
     * if equals one of + - / ^
     * @param line line for parsing
     * @return is line equals of pattern
     */
    private static boolean isSign(Character line) {
        return Pattern.matches("[-+*/^]", line.toString());
    }

    /**
     *Splits a string by mathematical operators.
     *Add values to the corresponding arrays.
     *All values are added to the arrayListDouble.
     * Method adds variable names and variables` indices arrayListDouble to variablesPosition
     * @param line math formula
     */
    private static void parseNumbers(String line) {
        String formula = line;
        String splitLine [] = formula.split("[-+/*^]+");
        if (splitLine[0]==""||splitLine[0]==null){
            splitLine = massRemoveFirst(splitLine);
        }

        for (int i = 0; i < splitLine.length; i++) {
            if (isDouble(splitLine[i])) {
                arrayListDouble.add(splitLine[i]);
            } else {
                variablesPosition.put(i, splitLine[i]);
                arrayListDouble.add(splitLine[i]);
            }
        }

    }

    /**
     * The method removes the first element of the array and returns it
     * @param splitLine input array
     * @return array without first element
     */
    private static String[] massRemoveFirst(String[] splitLine) {
        String [] mas = new String[splitLine.length-1];
        for (int i = 1; i < splitLine.length;i++){
            mas[i-1] = splitLine[i];
        }
        return mas;
    }


    /**
     * Simple method
     * Print out array in console
     * @param s array for print
     */
    private static void arrToString(String  [] s) {
        for (int i = 0; i< s.length;i++){
            if(i == s.length-1){
                System.out.println(s[i]);
            continue;
            }
            else {
                System.out.print(s[i]+"; ");
            }
        }
    }

    /**
     * The method checks if the value is a number
     * @param line value to check
     * @return true if is line a number
     */
    private static boolean isDouble(String line) {
        try {
            Double.parseDouble(line);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
