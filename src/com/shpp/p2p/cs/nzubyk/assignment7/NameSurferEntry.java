package com.shpp.p2p.cs.nzubyk.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.ArrayList;
import java.util.Collections;

public class NameSurferEntry implements NameSurferConstants {

    private final String line;
    /* Constructor: NameSurferEntry(line) */

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        this.line = line;
    }

    /* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return (line.split(" "))[0];
    }

    /* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        String[] text = line.split(" ");
        return Integer.parseInt(text[decade]);
    }

    /* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        ArrayList list = new ArrayList();
        Collections.addAll(list, line.split(" "));
        return list.toString();
    }
}

