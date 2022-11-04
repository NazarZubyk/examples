package com.shpp.p2p.cs.nzybyk.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Program do count Syllables in enter word from user
 * English only
 */
public class SyllableCounting extends TextProgram {
    /**
     * Enter pint of program
     */
    public void run() {
        //startTests();
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesInWord(word));
        }
    }

    /**
     * Run all tests
     */
    private void startTests() {
        check("Unity",3);
        check("Unite",2);
        check("Growth",1);
        check("Possibilities",5);
        check("Nimble",1);
        check("Me",1);
        check("Beautiful",3);
        check("Manatee",3);
    }

    /**
     * Test for syllablesInWord() method
     * @param s word for test
     * @param b expected answer
     */
    private void check(String s, int b) {
        int syllables = syllablesInWord(s) ;
        if (syllables == b){
            println("Pass: "+syllables+" syllables in word "+s);
        }
        else {
            println("Fail: "+syllables+" syllables in word "+s+". True is - "+b);
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        String wordLower = word.toLowerCase();
        //if user don't enter the word
        if (wordLower.equals("")){
            return 0;
        }
        //if user enter the word
        else {
            //create char massive from word
            char [] mas = wordLower.toCharArray();
            //counter for syllables
            int counter = 0;
            for (int i = 0; i< mas.length;i++) {
                //if first letter is vowel
                if (letterIsVowel(mas[i]) && i == 0) {
                    counter++;
                }
                else if (mas.length == (i + 1)) {
                    if (counter == 0) {
                        return 1;
                    }
                    else if (!letterIsVowel(mas[i - 1]) && mas[i] != 'e' && letterIsVowel(mas[i])) {
                        counter++;
                    }

                }
                else if (letterIsVowel(mas[i])){
                    if(!letterIsVowel(mas[i-1])){
                        counter++;
                    }
                }
            }
            return counter;
        }
    }

    /**
     * This method say you letter is vowel, or not
     * @param ch letter for check
     * @return it is Vowel letter
     */
    private boolean letterIsVowel(char ch) {
        switch (ch){
            case 'a':
            case 'o':
            case 'i':
            case 'e':
            case 'u':
            case 'y':
                return true;
        }
        return false;
    }
}
