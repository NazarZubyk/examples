package com.shpp.p2p.cs.nzubyk.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Game Breakout
 * create a canvas. It has bricks, a circle and a paddle. You can try to destroy all
 * bricks reflected circle using a paddle and win or you lose(when ball touch bottom)
 * when you's heals will be 0;
 */
public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 90;
    private static final int PADDLE_HEIGHT = 20;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 1;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 1;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;
    /**
     * Framerate per second
     */
    private static final double FPS = 1000 / 120.0;

    /**
     * Speed y axis
     */
    private static final double SPEED = 3.0;
    /**
     * Global speed values
     */
    private double vx, vy;
    /**
     * Global value for paddle
     */
    GObject paddle;

    int health;
    double pointTouchX, pointTouchY;

    /**
     * The entering point
     */
    public void run() {
        addMouseListeners();
        createNewGame();
    }

    /**
     * Start and settings each game
     */
    private void createNewGame() {

        vy = SPEED;
        while (true) {
            GOval gOval = createBall();
            health = NTURNS;

            //Number of bricks that player must brake for win
            int counterBricks = NBRICKS_PER_ROW * NBRICK_ROWS;

            //create and add paddle
            paddle = createPaddle();
            add(paddle);

            //create start field of bricks
            drawBricks();

            //random generate stars speed of X axis
            vx = vxGenerator();

            waitForClick();
            //entering point for each game with settings
            startGame(gOval, counterBricks);
        }
    }

    /**
     * create ball for our Game
     *
     * @return created ball with parameters
     */
    private GOval createBall() {
        GOval gOval;
        gOval = new GOval(WIDTH / 2.0 - BALL_RADIUS, HEIGHT / 2.0 - BALL_RADIUS,
                BALL_RADIUS * 2, BALL_RADIUS * 2);
        gOval.setFillColor(Color.BLACK);
        gOval.setFilled(true);
        add(gOval);
        return gOval;
    }

    /**
     * Start new game with specified parameters
     *
     * @param gOval         ball for game
     * @param counterBricks number of bricks which left
     */
    private void startGame(GOval gOval, int counterBricks) {
        while (true) {
            //reflect from walls and decrement heals if touches bottom
            smartMove(gOval);
            //check touches circle
            GObject collider = getCollidingObject(gOval);
            //if touches puddle
            if (collider == paddle) {
                reflectBollFromPaddle();
            }
            //if touches brick
            else if (collider != null) {
                brickBroke(collider);
                remove(collider);
                counterBricks--;
            }
            //pause time or speed program

            //check: Are you lose or win?
            if (health == 0) {
                removeAll();
                addEndGame();
                waitForClick();
                removeAll();
                break;
            }
            if (counterBricks == 0) {
                removeAll();
                addVictoryLabel();
                waitForClick();
                removeAll();
                break;
            }
            pause(FPS);
        }
    }

    /**
     * Delete brick which was hit by ball, and
     * change speed
     *
     * @param collider object what under check-point
     */
    private void brickBroke(GObject collider) {
        Integer difY;

        if (pointTouchY - collider.getY() > (BRICK_HEIGHT - (pointTouchY - collider.getY()))) {
            difY = (int) (BRICK_HEIGHT - (pointTouchY - collider.getY()));
        } else {
            difY = (int) (pointTouchY - collider.getY());
        }

        if (difY == 1 || difY == 0) {
            this.vy = -vy;
        } else {
            this.vx = -vx;
        }
        System.out.println(difY);

    }

    /**
     * Writes what you Win, and suggest trying again
     */
    private void addVictoryLabel() {
        String str = "You  Win. If you want to play again click the mouse button";
        GLabel gLabel = new GLabel(str);
        gLabel.setFont("ITALIC-10");
        gLabel.setLocation(WIDTH / 2.0 - gLabel.getWidth() / 2,
                HEIGHT / 2.0 - gLabel.getHeight() / 2 - BALL_RADIUS);
        add(gLabel);
    }

    /**
     * Create massive colors, and build bricks for the written pattern
     */
    private void drawBricks() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.RED);
        colors.add(Color.ORANGE);
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.CYAN);
        int colored = 0;
        for (int i = 0; i < NBRICK_ROWS; i++) {
            if (colored > colors.size() - 1) {
                colored = 0;
            }
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                brickCreate(getWidth() / 2.0 - (double) NBRICKS_PER_ROW / 2 * BRICK_WIDTH - (BRICK_SEP) *
                                (double) (NBRICKS_PER_ROW - 1) / 2 + (BRICK_WIDTH + BRICK_SEP) * j,
                        BRICK_Y_OFFSET + (BRICK_SEP + BRICK_HEIGHT) * i, colors.get(colored));

            }
            colored++;
        }
    }

    /**
     * add possibility reflect from paddle
     */
    private void reflectBollFromPaddle() {
        double difX, difY;
        difX = Math.min(pointTouchX - paddle.getX(), (PADDLE_WIDTH - (pointTouchX - paddle.getX())));
        difY = pointTouchY - paddle.getY();

        if (difX > difY && vy > 0) {
            this.vy = -vy;
        } else if (difY > difX && vy > 0) {
            this.vy = -vy;
            this.vx = -vx;

        } else if (vy > 0) {
            this.vy = -vy;
            this.vx = -vx;
        }

    }

    /**
     * Add moves to the circle. Add reflection from walls,
     * add unique gest if ball touch bottom
     *
     * @param gOval correcting move of specified ball
     */
    private void smartMove(GOval gOval) {
        gOval.move(vx, vy);
        if (gOval.getY() + BALL_RADIUS * 2 > HEIGHT) {
            if (this.health > 0) {
                addLoseLabel(gOval);
                this.health--;
            }

        }
        if (gOval.getY() < 0) {
            this.vy = -vy;
        }
        if (gOval.getX() < 0) {
            this.vx = -vx;
        }
        if (gOval.getX() + BALL_RADIUS * 2 > WIDTH) {
            this.vx = -vx;
        }
    }

    /**
     * Writes what you lose, and suggest try again
     */
    private void addEndGame() {
        String str = "You  Loosed. If you want to play again click the mouse button";
        GLabel gLabel = new GLabel(str);
        gLabel.setFont("ITALIC-10");
        gLabel.setLocation(WIDTH / 2.0 - gLabel.getWidth() / 2, HEIGHT / 2.0 - gLabel.getHeight() / 2.0 - BALL_RADIUS);
        add(gLabel);
    }

    /**
     * Add a label if you lose the ball and write "How many trays you have"
     * Also, decrement to 1 health
     * If health equals 0  do nothing
     *
     * @param gOval needed for centered after lose the ball
     */
    private void addLoseLabel(GOval gOval) {
        if (health > 1) {
            GLabel gLabel = new GLabel("You lose Health, you have " + (health - 1) + " try",
                    getWidth() / 2.0,
                    getHeight() / 2.0);
            gLabel.setFont("ITALIC-15");
            gLabel.setLocation(WIDTH / 2.0 - gLabel.getWidth() / 2, HEIGHT / 2.0 - gLabel.getHeight() / 2 - BALL_RADIUS);
            add(gLabel);
            gOval.setLocation(WIDTH / 2.0 - BALL_RADIUS, HEIGHT / 2.0 - BALL_RADIUS);
            waitForClick();
            remove(gLabel);
        }

    }

    /**
     * Check four-point on described around the circle of the square corners,
     * and returns object from the point if it has
     *
     * @param gOval check object around of the this Object
     * @return Object what was under point
     */
    private GObject getCollidingObject(GOval gOval) {
        GObject buffer;
        double X, Y;
        X = gOval.getX();
        Y = gOval.getY();
        if ((buffer = getElementAt(X, Y)) != null) {
            this.pointTouchX = X;
            this.pointTouchY = Y;
            return buffer;
        } else if ((buffer = getElementAt(X + BALL_RADIUS * 2, Y)) != null) {
            this.pointTouchX = X + BALL_RADIUS * 2;
            this.pointTouchY = Y;
            return buffer;
        } else if ((buffer = getElementAt(X, Y + BALL_RADIUS * 2)) != null) {
            this.pointTouchX = X;
            this.pointTouchY = Y + BALL_RADIUS * 2;
            return buffer;
        } else if ((buffer = getElementAt(X + BALL_RADIUS * 2, Y + BALL_RADIUS * 2)) != null) {
            this.pointTouchX = X + BALL_RADIUS * 2;
            this.pointTouchY = Y + BALL_RADIUS * 2;
            return buffer;
        }
        return null;
    }

    /**
     * Generate random speed for X
     *
     * @return random value between 1 and 3
     */
    private double vxGenerator() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5))
            vx = -vx;
        return vx;
    }

    /**
     * Read and give coordinate from mouse to paddle
     *
     * @param m all manipulation over mouse
     */
    public void mouseMoved(MouseEvent m) {
        int y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - getMenuBar().getHeight();
        if (!(m.getX() > APPLICATION_WIDTH - PADDLE_WIDTH / 2 || m.getX() < PADDLE_WIDTH / 2)) {
            paddle.setLocation(m.getX() - PADDLE_WIDTH / 2.0, y);
        }
    }

    /**
     * Create paddle in specific starts coordinate and return object of a paddle(gRect)
     *
     * @return object of paddle
     */
    private GObject createPaddle() {
        return createSection((WIDTH - PADDLE_WIDTH) / 2.0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT - getMenuBar().getHeight());
    }

    /**
     * Create object the gRect, in coordinate
     *
     * @param x start x coordinate for brick
     * @param y start y coordinate for brick
     * @return created brick
     */
    private GObject createSection(double x, double y) {
        GRect gRect = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        gRect.setFilled(true);
        add(gRect);
        return gRect;
    }

    /**
     * Draw one brick in specific coordinate and color
     *
     * @param x     x start x coordinate for brick
     * @param y     y start y coordinate for brick
     * @param color for fulling bricks
     */
    private void brickCreate(double x, double y, Color color) {
        //create brick left-height corner in the specified coordinates
        GRect gRect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        //painting brick
        gRect.setFilled(true);
        gRect.setFillColor(color);
        gRect.setColor(color);
        //add brick in canvas
        add(gRect);
    }
}
