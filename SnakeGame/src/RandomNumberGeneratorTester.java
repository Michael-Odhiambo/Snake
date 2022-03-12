
public class RandomNumberGeneratorTester {

    public static void main( String[] args ) {
        for ( int i = 0; i < 100; i++ )
            System.out.println( RandomNumberGenerator.generateRandomInteger( 0, 10 ) );
    }
}
