

public class RandomNumberGenerator {

    // Generates a random integer in the range minimum to maximum inclusive.
    public static int generateRandomInteger( int minimum, int maximum ) {
        int range = maximum - minimum + 1;
        return (int)( Math.random() * range ) + minimum;
    }
}
