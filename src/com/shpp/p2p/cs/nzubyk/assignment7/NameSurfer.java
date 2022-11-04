package com.shpp.p2p.cs.nzubyk.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {
    //create local dataBase from text file
    private final NameSurferDataBase nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
    //create object's NameSurferGraph
    private NameSurferGraph graph;
    //create object's JButton
    private JButton jButtonGraph;
    //create object's JButton
    private JButton jButtonClear;
    //create object's JTextField
    private JTextField jTextField;
    /* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {

        //Create interactions object
        jButtonGraph = new JButton("Graph");
        jButtonClear = new JButton("Clear");
        //create object's JLabel
        JLabel jLabel = new JLabel("Name:");
        jTextField = new JTextField(GRAPH_MARGIN_SIZE);
        graph = new NameSurferGraph();

        //add interactions
        add(jLabel, "North");
        add(jTextField, "North");
        add(jButtonGraph, "North");
        add(jButtonClear, "North");
        add(graph);

        //add listeners to objects
        jButtonGraph.addActionListener(this);
        jButtonClear.addActionListener(this);
        jTextField.addActionListener(this);

    }

    /* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(jButtonGraph.getActionCommand())) {
            //create class' object NameSufferEntry using nameSurferDataBase and information's jTextField
            NameSurferEntry nameSurferEntry = nameSurferDataBase.findEntry(jTextField.getText());
            //if nameSurferEntry not null, add information for graph for drawing
            if (nameSurferEntry != null) {
                graph.addEntry(nameSurferEntry);
            }
        }
        //clear all nameSurferEntry from canvas and only the grid will remain
        else if (e.getActionCommand().equals(jButtonClear.getActionCommand())) {
            graph.clear();
        }
    }
}
