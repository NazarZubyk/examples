package com.shpp.p2p.cs.nzubyk.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {
    //create ArrayList for NameSurferEntries
    ArrayList<NameSurferEntry> localDataBaseNames = new ArrayList<>();


    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        // You fill in the rest //
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        removeAll();
        localDataBaseNames.clear();
        update();
    }


    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        try {
            localDataBaseNames.add(entry);
        } catch (NullPointerException e) {
            System.out.println("Not found name");
        }
        update();


    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        double weighSection = getWidth() / (double) NDECADES;
        //draw grid and years
        drawHorizontalLine();
        drawVerticalLine(weighSection);
        drawYears(weighSection);

        //if have added object in array this method draws graph
        if (!localDataBaseNames.isEmpty()) {
            drawGraphs(weighSection);
        }
    }

    /**
     * This method draw graph rating of TOP names of decade
     * Graph contains of lines and labels
     * Lines draw by points(coordinate's points is names rating's from database(arrayList))
     * labels contains of name and number of ranked
     *
     * @param sectionWidth width pixels of decade
     */
    private void drawGraphs(double sectionWidth) {
        //color count
        int count = 1;
        for (NameSurferEntry nameSurferEntry : localDataBaseNames
        ) {
            //get color by count from palette
            Color color = palette(count);
            //next color for next graph
            count++;
            for (int i = 1; i < NDECADES; i++) {
                //get start and end points rank
                int rank = nameSurferEntry.getRank(i);
                int rank2 = nameSurferEntry.getRank(i + 1);

                //change value of rank if name hasn't rate
                if (rank == 0) {
                    rank = 1000;
                }
                if (rank2 == 0) {
                    rank2 = 1000;
                }

                //create object's line with parameters
                GLine gLine = new GLine((i - 1) * sectionWidth, ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / 1000.0 * rank) + GRAPH_MARGIN_SIZE,
                        i * sectionWidth, ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / 1000.0 * rank2) + GRAPH_MARGIN_SIZE);

                //add color for line and add line on canvas
                gLine.setColor(color);
                add(gLine);

            }
            //add label to graph point
            for (int i = 1; i <= NDECADES; i++) {
                String name;

                //get start and end points rank
                int rank = nameSurferEntry.getRank(i);
                //change value of rank if name hasn't rate
                if (rank == 0) {
                    rank = 1000;
                }

                //if rank is not 0 add text with rank adn if rank is 0 add text with "*"
                if (nameSurferEntry.getRank(i) != 0) {
                    name = nameSurferEntry.getName() + " " + nameSurferEntry.getRank(i);
                } else {
                    name = nameSurferEntry.getName() + " *";
                }

                //create label with parameters, color and add it to canvas
                GLabel gLabel = new GLabel(name, (i - 1) * sectionWidth, ((getHeight() - 2 * GRAPH_MARGIN_SIZE) / 1000.0 * rank) + GRAPH_MARGIN_SIZE);
                gLabel.setColor(color);
                add(gLabel);
            }
        }
    }

    /**
     * This method change value counter of graphs to range 1-4
     *
     * @param i counter of graphs added to canvas
     * @return color by list
     */
    private Color palette(int i) {
        if (i < 5) {
            return listColor(i);
        } else {
            return listColor(i % 4);
        }
    }

    /**
     * This method find and return color from it list
     *
     * @param i number of color
     * @return color from list
     */
    private Color listColor(int i) {
        return switch (i) {
            case 1 -> Color.BLUE;
            case 2 -> Color.red;
            case 3 -> Color.MAGENTA;
            default -> Color.BLACK;
        };
    }

    /**
     * This method draw years on bottom side graph
     *
     * @param weighSection width pixels of decade
     */
    private void drawYears(double weighSection) {
        GLabel gLabel;
        for (int i = 0; i < NDECADES; i++) {
            String year;
            int integer = START_DECADE + 10 * i;
            year = Integer.toString(integer);
            gLabel = new GLabel(year, i * weighSection + 2, getHeight() - 2);
            add(gLabel);
        }
    }

    /**
     * Create graph's vertical line with specified step
     *
     * @param weighSection width pixels of decade
     */
    private void drawVerticalLine(double weighSection) {
        GLine gLine;
        for (int i = 0; i < NDECADES; i++) {
            gLine = new GLine(i * weighSection, 0, i * weighSection, getHeight());
            add(gLine);
        }
    }

    /**
     * Create graph's horizontal line with specified step
     */
    private void drawHorizontalLine() {
        GLine gLineTop, gLineBottom;
        gLineTop = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        gLineBottom = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        add(gLineTop);
        add(gLineBottom);
    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
