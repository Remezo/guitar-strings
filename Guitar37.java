
/**
 * An implementation of a 37 -string Guitar that implements the Guitar class
 * 
 * @author Mike Remezo
   @author Michael Adenew
 * 
 */

public class Guitar37 implements Guitar {
    // create 37 guitar strings, using GuitatStringQueue

    // keyboard layout
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private GuitarStringQueue[] keyStrings = new GuitarStringQueue[37];

    public Guitar37() {

        for (int i = 0; i < KEYBOARD.length(); i++) {
            double concertFrequency = 440 * Math.pow(2.0, (i - 24.0) / 12.0);
            keyStrings[i] = new GuitarStringQueue(concertFrequency);

        }
    }

    public boolean hasString(char key) {
    /** Returns whether there is a string that corresponds to this character. 
      *
      * @param key the key on the keyboard to look up
      * @return true iff the suppleid key maps to a string in this instrument
    */
        return (KEYBOARD.contains(key + ""));
    }

    public void pluck(char key) {
        /** Plucks the string corresponding to the key pressed by the user.
        * 
        * @param key the key pressed by the user
        */

        if (!KEYBOARD.contains(key + "")) {
            throw new IllegalArgumentException();
        }
        keyStrings[KEYBOARD.indexOf(key)].pluck();

    }

    public void play() {
        /** Plays the current sound (the sum of all strings) */
        double sum = 0;
        for (GuitarStringQueue keys : keyStrings) {
            sum += keys.sample();
        }

        StdAudio.play(sum);
    }

    public void tic() {
        /** Advances the string simulation by having each string tic forward */

        for (int i = 0; i < KEYBOARD.length(); i++) {

            keyStrings[i].tic();

        }

    }

}