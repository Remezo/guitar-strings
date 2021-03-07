import java.util.*;

public class GuitarStringArrayList extends GuitarString {
    ArrayList<Double> RingBuffer;

    GuitarStringArrayList(double frequency) {

        RingBuffer = new ArrayList<>();
        int SAMPLE_RATE = 44100;
        int bufferLength = (int) ((StdAudio.SAMPLE_RATE / frequency) + 0.5);

        if (bufferLength < 2) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < bufferLength; i++) {
            RingBuffer.add(0.0);
        }

    }

    GuitarStringArrayList(double[] init) {
        if (init.length < 2) {
            throw new IllegalArgumentException();
        }
        RingBuffer = new ArrayList<>();
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
        Double decayedValue = ((RingBuffer.get(0) + RingBuffer.get(1)) / 2) * ENERGY_DECAY;
        RingBuffer.remove(0);
        RingBuffer.add(decayedValue);
        myNumTics++;
    }

    /**
     * create the code for method pluck which will, for i in length of ring buffer,
     * replace all the indexes with random values using the random module
     */

    @Override
    public void pluck() {
        // TODO Auto-generated method stub

        double MIN = -0.5;
        double MAX = 0.5; // although our max is actually 0.5, we are stating
        // the max as 0.6 because the random module is (inclusive, exclusive)
        for (int i = 0; i < RingBuffer.size(); i++) {
            RingBuffer.set(i, Math.random() * (MAX - MIN) + MIN);
        }

    }

    @Override
    public double sample() {
        /**
         * Returns the number of steps that have elapsed in this string's history.
         * 
         * @return the number of Karplus-Strong updates that have been made to this
         *         string.
         */

        return RingBuffer.get(0);
    }

    @Override
    public int time() {
        return myNumTics;
    }

}
