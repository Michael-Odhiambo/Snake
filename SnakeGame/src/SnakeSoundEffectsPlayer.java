
import javafx.scene.media.AudioClip;
import java.io.File;

public class SnakeSoundEffectsPlayer {

    private static String eatingAppleSoundEffect = "SnakeGame/src/succeeded-message-tone.mp3";
    private static String gameOverSoundEffect = "SnakeGame/src/glitch-in-the-matrix-600.mp3";
    private static AudioClip appleEatenTone = new AudioClip( new File( eatingAppleSoundEffect ).toURI().toString() );
    private static AudioClip gameOverTone = new AudioClip( new File( gameOverSoundEffect ).toURI().toString() );

    public static void playEatingAppleSoundEffect() {
        appleEatenTone.play();
    }

    public static void playGameOverTone() {
        gameOverTone.play();
    }
}
