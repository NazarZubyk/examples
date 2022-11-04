package com.shpp.p2p.cs.nzubyk.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NameSurferDataBase implements NameSurferConstants {

    private final ArrayList<NameSurferEntry> lines = new ArrayList<>();
    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        try (FileReader file = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(file);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                NameSurferEntry nameSurferEntry = new NameSurferEntry(line);
                lines.add(nameSurferEntry);
            }

        } catch (IOException e) {
            System.out.println("File not found!!!");
        }

    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        //create 2 String object
        String nameFromDatabase;
        //This String contains name what need to find out
        String neededName = name.toLowerCase();
        for (NameSurferEntry nameSurferEntry : lines
        ) {
            //get name from dataBase
            nameFromDatabase = nameSurferEntry.getName().toLowerCase();
            //if needed name equals name from dataBase it returns object nameSurferEntry
            if (neededName.equals(nameFromDatabase)) {
                return nameSurferEntry;
            }
        }
        return null;
    }
}

