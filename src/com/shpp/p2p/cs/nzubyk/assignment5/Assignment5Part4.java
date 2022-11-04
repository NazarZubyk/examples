package com.shpp.p2p.cs.nzubyk.assignment5;

import com.shpp.cs.a.console.TextProgram;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is CSV parser
 * Pars your CSV file to ArrayList by column index
 */

public class Assignment5Part4 extends TextProgram {
    /**
     * Number column of .csv file
     */
    private final int COLUMNINDEX = 8;
    /**
     * Path to .csv file/
     */
    private final String PATH = "D:/java/untitled135/assets/annual-enterprise-survey-2021-financial-year-provisional-csv.csv";
    @Override
    /**
     * Enter point of program
     */
    public void run() {
        //create massive of String
        List<String> mass ;
        //use created method. Reed of comment extractColumn();
        try {
            mass = extractColumn(PATH,COLUMNINDEX);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Print created massive
        System.out.println(mass);

    }

    /**
     * This method import .csv file from PATH, and getting value from column by index
     * @param filename path of file
     * @param columnIndex   index of column
     * @return  massive of column value
     * @throws IOException
     */
    private  ArrayList<String> extractColumn(String filename, int columnIndex) throws IOException {
        String line = "";
        File file = new File(filename);
        BufferedReader bufferedReader;
        ArrayList<String> strings = new ArrayList<String>();
        //reed file
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }
        //redd each line of file and use fieldsIn()
        while (line!=null){
            line = bufferedReader.readLine();
            if(line!=null){
                strings.add(deleteNeedlessQuotes(fieldsIn(line).get(columnIndex)));
            }
        }

        return strings;
    }

    /**
     *  This method delete needless quotes from section in .csv
     *  when you convert section to line
     * @param s delete needless quotes in this line
     * @return  line without needless quotes
     */
    private String deleteNeedlessQuotes(String s) {
        String str;
        if(s.charAt(0) == '"'){
            str = s.substring(1,s.length()-1);
            for (int i = 0;i<str.length();i++){
                if(i<str.length()-1){
                    if(str.charAt(i)=='"'&& str.charAt(i+1)=='"'){
                        str = deleteCharAt(str,i+1);
                    }
                }
            }
            return str;
        } else {
            return s;
        }
    }

    private String deleteCharAt(String str, int i) {
        String line = str.substring(0,i)+str.substring(i+1);
        return line;
    }

    /**
     * This method convert line from .csv to String massive
     * @param line line from .csv file
     * @return  massive of value
     */
    private ArrayList<String> fieldsIn(String line) {

        ArrayList<String> words = new ArrayList<>();

        String word;
        int buffer = 0, countOfQuotes = 0, step = 0;

        while (line.contains(",")) {
                    buffer = line.indexOf(",", step);

            if (buffer == -1) {
                words.add(line);
                break;
            }

            if (quotesIsTwin(line, buffer)) {
                word = line.substring(0, buffer);
                line = line.substring(buffer + 1);
                words.add(word);
                buffer = 0;
            }
            step = buffer + 1;
        }
        if (line.length() != 0) {
            words.add(line);
        }
        return words;
    }

    /**
     * This method counts quotes in part of line, and return the information
     * quotes is double or not
     * @param line line for check
     * @param limit part size of line where counting quotes
     * @return quotes is double or not
     */
    private boolean quotesIsTwin(String line, int limit) {
        if (limit == -1) {
            return false;
        }
        int counter = 0;
        char symbol;
        for (int i = 0; i < limit; i++) {
            symbol = line.charAt(i);
            if (symbol == '"') {
                counter++;
            }
        }

        if (counter % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
}
