package com.shpp.p2p.cs.nzubyk.assignment8;


import acm.graphics.GOval;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Animation program. Simple snowfall.
 */

public class Assignment8Part1 extends WindowProgram {
    //global time of pause
    private final int GLOBAL_PAUSE_TIME = 50;
    //limit for time
    private final int GLOBAL_TIME_TO_RESET = 100;
    //added wing gust speed
    private final int ADDED_WIND_GUST_SPEED  = 150;

    public void run() {
        //check your screen size and set a size window
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        //timer for synchronizing all object
        int timer = 0;

        //start snowfall
        while (true) {
            //global timer need for each thread
            int finalTimer = timer;
            Thread thread = new Thread() {
                public void run() {
                    //start value of timer for each thread is unique
                    createSnowflake(finalTimer);
                }

            };
            //timer reset
            if (timer > GLOBAL_TIME_TO_RESET) {
                timer = 0;
            }
            timer++;

            thread.start();

            pause(GLOBAL_PAUSE_TIME);
        }

    }

    /**
     * randomizer
     *
     * @return int value from 30 to 129
     */
    private int randomSizer100() {
        return 30 + (int) (Math.random() * 100);
    }

    /**
     * color randomizer
     *
     * @return random color
     */
    private Color getRandomColor() {
        return RandomGenerator.getInstance().nextColor();
    }

    /**
     * @param timer need for synchronized all object in time
     */
    private void createSnowflake(int timer) {
        //parameters for Snowflake
        int xStartPoint = (int) ((getWidth() * 1.3) * Math.random() - getWidth() / 4);
        int size = randomSizer100();
        Color color = getRandomColor();

        //create Snowflake with specific value
        GOval gOval = new GOval(xStartPoint, -10, size, size);


        //add colors
        gOval.setFillColor(color);
        gOval.setFilled(true);
        gOval.setColor(color);

        //add snowflake on canvas
        add(gOval);

        //create moving for snowflake
        sinMove(gOval, timer);
        //delete snowflake after finish path
        remove(gOval);
    }

    /**
     * This method add move for object
     *
     * @param gOval object will be mov
     * @param time  synchronized timer
     */
    private void sinMove(GOval gOval, int time) {
        //amplitude of sinusoid
        int amplitude = (int) (Math.random() * getWidth()) / 3;
        //speeds
        int speedY = randomSpeed();
        int speedX = randomSpeed() * 2;
        //amplitude limiter
        int buffer = 0;
        //change direction of vector Y
        while (gOval.getY() < getHeight()) {
            if (buffer > amplitude) {
                speedY = -speedY;
            } else if (buffer < 0) {
                speedY = -speedY;
            }
            buffer = buffer + speedY;
            //moving
            gOval.move(speedY, speedX);
            //synchronized local time with global time
            time++;
            pause(GLOBAL_PAUSE_TIME);

            //gust of wind
            if (time / GLOBAL_TIME_TO_RESET > 0) {
                for (int i = 0; i < 10; i++) {
                    gOval.move(speedY + ADDED_WIND_GUST_SPEED, speedX);
                    pause(GLOBAL_PAUSE_TIME);
                }
                //reset time like global time
                time = 0;
            }

        }
    }

    /**
     * Create unique speed
     *
     * @return int value from 20 to 29
     */
    private int randomSpeed() {
        return (int) (20 + Math.random() * 10);
    }
}
