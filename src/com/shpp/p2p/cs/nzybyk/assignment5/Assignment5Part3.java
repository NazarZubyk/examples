package com.shpp.p2p.cs.nzybyk.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.*;
import java.util.Scanner;

/**
 * This program is parser of words;
 * You can input 3 latter after phrase "Input 3 letter from car number"
 * And you get the word from the PATH file that matches the pattern
 */
public class Assignment5Part3 extends TextProgram {
    /**
     * Path to .txt file
     */
    public static final String PATH = "en-dictionary(1).txt";
    /**
     * Buffer of file
     */
    private BufferedReader bf;
    /**
     * analog of Buffer
     */
    private Scanner sc;
    /**
     * Massive of words
     */
    private String [] words;

    /**
     * Import file with words, and convert them in massive.
     */
    @Override
    public void init() {
        try {
            File file = new File(PATH);
            bf = new BufferedReader(new FileReader(file));
            sc = new Scanner(file);
            //convert File to massive
            words = createMassStringFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enter point of program
     */
    @Override
    public void run() {

        while (true) {
            megaLogicMethod(readLineMod());
        }
    }
    /**
     * Ask 3 letter from user
     * If user input something wrong, asks again.
     * @return user's input 3 letter
     */
    private String readLineMod() {
        String str;
        str = readLine("Input 3 letter from car number");
        while (str.length() != 3) {
            str = readLine("It is a bad idea. Input word must contain for 3 letters");
        }
        str = str.toLowerCase();
        return str;

    }

    /**
     * Find of words.
     * Words from file;
     * Words have letters from str line in similar sequence
     * @param str line of letters
     */
    private void megaLogicMethod(String str) {

        //Checker. If we haven't needed words
        boolean bool = true;
        String buffer, line;

        for (String word : words
        ) {
            int counter = 0;
            line = word;
            if (word.length() >= str.length()) {
                for (int i = 0; i < str.length(); i++) {
                    buffer = str.substring(i, i + 1);
                    if (line.contains(buffer)) {
                        line = line.substring(line.indexOf(buffer) + 1);
                        counter++;
                    } else {
                        break;
                    }
                    if (counter == str.length()) {
                        System.out.println(word);
                        bool = false;
                        break;
                    }
                }

            }
        }
        if (bool){
            System.out.println("Not found words");
        }
        System.out.println("You enter "+str);
    }


    /**
     * Create and fill massive of words from file
     * @return Massive of words from file
     * @throws IOException
     */
    private String[] createMassStringFromFile() throws IOException {
        String buffer;
        int counter = 0;
        //Counter word from file
        while (sc.hasNext()) {
            sc.next();
            counter++;
        }
        //Created massive the required length
        String[] mas = new String[counter];
        //Filled massive of words
        for (int i = 0; i < mas.length; i++) {
            buffer = bf.readLine();
            mas[i] = buffer;
        }
        return mas;
    }

}

