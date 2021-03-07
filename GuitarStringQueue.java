import java.util.*;
import java.math.*;
import java.nio.Buffer;
import java.util.Random;

import jdk.jfr.Frequency;

public class GuitarStringQueue extends GuitarString {
    Queue<Double> RingBuffer;

    GuitarStringQueue(double data) {
        if (data < 0.0) {
            throw new IllegalArgumentException();
        }

        // first constuctor -- double
        // second contructor - double array
        // Only difference -- first double zeros
        // second -- fills queue with already inside double array

        // first frequcency
        // secdond array
        RingBuffer = new LinkedList<Double>();
        int SAMPLE_RATE = 44100;

        // we put in the frequency and this will divide a magic number SAMPLE RATE by
        // the frequency to give us a size
        // n of our ring buffer

        int bufferLength = (int)((StdAudio.SAMPLE_RATE/data)+0.5);

        // Math.floorDiv((int) data, SAMPLE_RATE);
        if (bufferLength < 2) {
            throw new IllegalArgumentException();
        }

        // Once you have determined the n or size of the buffer ring, use a for loop to
        // add the amount of zeros into the dynamic list
        // ring buffer

        for (int i = 0; i < bufferLength; i++) {

            RingBuffer.add(0.0);
        }

    }

    GuitarStringQueue(double[] init) {
        if (init.length < 2){
            throw new IllegalArgumentException();
        }

        RingBuffer = new LinkedList<Double>();
        int SAMPLE_RATE = 44100;

        for (int i = 0; i < init.length; i++) {

            RingBuffer.add(init[i]);
        }
    }

    /**
     * create the code for tic which will take the average of the first two indexes
     * multiplied by the decay value and add it to the end of the list. It will then
     * delete the first index
     */

    @Override
    public void tic() {
        // TODO Auto-generated method stub


        double firstValue = RingBuffer.remove();
        double average = ((firstValue + RingBuffer.peek()) / 2);
        double decayedValue = average * ENERGY_DECAY;
        RingBuffer.add(decayedValue);
        myNumTics++;

    }

    /**
     * create the code for method pluck which will, for i in length of ring buffer,
     * replace all the indexes with random values using the random module
     */

    @Override
    public void pluck() {

        double MIN = -0.5;
        double MAX = 0.5; // although our max is actually 0.5, we are stating
        // the max as 0.6 because the random module is (inclusive, exclusive)
        for (int i=0; i< RingBuffer.size(); i++) {

            RingBuffer.remove();
            RingBuffer.add(Math.random()*(MAX-MIN)+ MIN);

        }

    }

    @Override
    public double sample() {
        /** Returns the number of steps that have elapsed in this string's history.
      * 
      * @return the number of Karplus-Strong updates that have been made to 
      * this string.
      */
        return RingBuffer.peek();
    }

    @Override
    public int time() {
        return myNumTics;
    }
}