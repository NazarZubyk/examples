package com.shpp.p2p.cs.nzubyk.assignment6.sg;

import acm.graphics.*;

public class SteganographyLogic  {
    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int [][] imageArray= source.getPixelArray();

        boolean [][]booleans = new boolean[(int) source.getHeight()][(int) source.getWidth()];
        int red;
        for (int i = 0;i< imageArray.length;i++){
            for (int j = 0;j< imageArray[i].length;j++){

                red = GImage.getRed(imageArray[i][j]);
                if(red%2 == 0){
                    booleans[i][j]= false;
                }
                else {booleans[i][j]= true;}
            }
        }
        /* TODO: Implement this! */
        return booleans;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int [][] imageArray= source.getPixelArray();
        int redValue,greenValue,blueValue;
        /* TODO: Implement this! */
        for (int i = 0;i< imageArray.length;i++){
            for (int j = 0;j< imageArray[i].length;j++){
                redValue = GImage.getRed(imageArray[i][j]);
                greenValue = GImage.getGreen(imageArray[i][j]);
                blueValue = GImage.getBlue(imageArray[i][j]);
                if(message[i][j]){
                    if(redValue%2 == 0){
                        imageArray[i][j] = GImage.createRGBPixel(changeParityOfNumber(redValue),greenValue,blueValue);
                    }
                }
                else if (!message[i][j]){
                    if(redValue%2 != 0){
                        imageArray[i][j] = GImage.createRGBPixel(changeParityOfNumber(redValue),greenValue,blueValue);
                    }
                }
                else {imageArray[i][j] = GImage.createRGBPixel(redValue,greenValue,blueValue);}
            }
        }
        GImage gImage = new GImage(imageArray);
        return gImage;
    }

    /**
     * This method changes parity of number in 0-255 diapason
     * @param colorValue value of color
     * @return value of color with change parity
     */
    private static int changeParityOfNumber(int colorValue) {
        if(colorValue>255/2){
            return colorValue-1;
        }
        else return colorValue+1;
    }
}