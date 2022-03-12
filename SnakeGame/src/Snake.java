
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import java.io.File;


public class Snake extends Application {
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 600;
    private final int CELL_WIDTH = 20;
    private final int CELL_HEIGHT = 20;
    private final int SEGMENT_SIZE = 10;
    private SnakeCanvas snakeCanvas;
    private GraphicsContext drawingArea;
    private Label score;
    private SnakeBody snake;
    private DIRECTION snakesDirection;
    private Apple apple;

    String path = "/home/michael/Desktop/succeeded-message-tone.mp3";

    private Media eatingSound = new Media( new File(path).toURI().toString() );
    private MediaPlayer player = new MediaPlayer( eatingSound );
    private AudioClip audioClip = new AudioClip( new File(path).toURI().toString() );

    private enum DIRECTION { LEFT, RIGHT, UP, DOWN };

    public void start( Stage stage ) {
        setupMainWindow( stage );
        showMainWindow( stage );
    }

    private void setupMainWindow( Stage stage ) {
        stage.setScene( setupScene() );
    }

    private Scene setupScene() {
        Scene scene = new Scene( setupPane() );
        scene.setOnKeyPressed( event -> keyPressed( event ) );
        return scene;
    }

    private void keyPressed( KeyEvent event ) {
        if ( event.getCode() == KeyCode.UP && snakesDirection != DIRECTION.DOWN ) {
            snakesDirection = DIRECTION.UP;
        }
        else if ( event.getCode() == KeyCode.DOWN && snakesDirection != DIRECTION.UP )
            snakesDirection = DIRECTION.DOWN;
        else if ( event.getCode() == KeyCode.LEFT && snakesDirection != DIRECTION.RIGHT )
            snakesDirection = DIRECTION.LEFT;
        else if ( event.getCode() == KeyCode.RIGHT && snakesDirection != DIRECTION.LEFT )
            snakesDirection = DIRECTION.RIGHT;
    }

    private Pane setupPane() {
        BorderPane root = new BorderPane( setupCanvas() );
        root.setTop( setupScore() );
        return root;
    }

    private Label setupScore() {
        score = new Label( "Your Score: " );
        return score;
    }

    private Canvas setupCanvas() {
        snakeCanvas = createCanvas();
        return snakeCanvas;
    }

    private SnakeCanvas createCanvas() {
        snakeCanvas = new SnakeCanvas( CANVAS_WIDTH, CANVAS_HEIGHT );
        drawingArea = snakeCanvas.getGraphicsContext2D();
        return snakeCanvas;
    }


    private void showMainWindow( Stage stage ) {
        stage.setResizable( false );
        stage.setTitle( "Snake" );
        stage.show();

        createSnake();
        generateApple();
        drawApple();
        new AnimationThread().start();
    }

    private void createSnake() {
        int xPosition = RandomNumberGenerator.generateRandomInteger( 5, CELL_WIDTH - 5 );
        int yPosition = RandomNumberGenerator.generateRandomInteger( 5, CELL_HEIGHT - 5 );
        snake = new SnakeBody( xPosition, yPosition, SEGMENT_SIZE );
        snakesDirection = DIRECTION.RIGHT;
        snake.draw( drawingArea );
    }

    private void generateApple() {
        int xPosition = RandomNumberGenerator.generateRandomInteger( 0, CELL_WIDTH );
        int yPosition = RandomNumberGenerator.generateRandomInteger( 0, CELL_HEIGHT );
        apple = new Apple( xPosition, yPosition, SEGMENT_SIZE );
    }

    private void drawApple() {
        apple.draw( drawingArea );
    }

    private class AnimationThread extends Thread {

        AnimationThread() {
            setDaemon( true );
        }

        public void run() {
            while ( true ) {
                if ( gameIsOver() )
                    break;
                if ( !snake.hasEatenApple( apple ) ) {
                    snake.removeTailSegment();
                }
                else {
                    audioClip.play();
                    eraseApple();
                    generateApple();
                }
                moveTheSnakeAccordingToTheDirection();
                Platform.runLater( () -> {
                    snakeCanvas.draw();
                    snake.draw( drawingArea );
                    apple.draw( drawingArea );
                } );
                try {
                    Thread.sleep( 100 );
                }
                catch ( InterruptedException e ) {}
            }
        }
    }

    private boolean gameIsOver() {
        if ( snake.hasCollidedOnItself() )
            return true;
        else if ( snake.hasCollidedIntoTheWall() )
            return true;
        return false;
    }

    private void eraseApple() {
        snakeCanvas.erase( apple );
    }

    private void moveTheSnakeAccordingToTheDirection() {
        if ( snakesDirection == DIRECTION.UP )
            snake.moveUp();
        else if ( snakesDirection == DIRECTION.DOWN )
            snake.moveDown();
        else if ( snakesDirection == DIRECTION.LEFT )
            snake.moveLeft();
        else
            snake.moveRight();
    }
}
