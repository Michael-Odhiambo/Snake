
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

public class Snake extends Application {
    private final int CANVAS_WIDTH = 800;
    private final int CANVAS_HEIGHT = 600;
    private final int CELL_SIZE = 10;
    private final int CELL_WIDTH = CANVAS_WIDTH/CELL_SIZE;
    private final int CELL_HEIGHT = CANVAS_HEIGHT/CELL_SIZE;
    private final int SEGMENT_SIZE = CELL_SIZE;
    private SnakeCanvas snakeCanvas;
    private GraphicsContext drawingArea;
    private Label score;
    private int currentScore;
    private SnakeBody snake;
    private DIRECTION snakesDirection;
    private Apple apple;

    private enum DIRECTION { LEFT, RIGHT, UP, DOWN };

    public void start( Stage stage ) {
        currentScore = 0;
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
        if ( event.getCode() == KeyCode.UP && snakesDirection != DIRECTION.DOWN )
            snakesDirection = DIRECTION.UP;
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
        score.setText(String.valueOf(currentScore));
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
        apple.draw( drawingArea );
    }

    private class AnimationThread extends Thread {

        AnimationThread() {
            setDaemon( true );
        }

        public void run() {
            while ( true ) {
                if ( gameIsOver() ) {
                    SnakeSoundEffectsPlayer.playGameOverTone();
                    break;
                }
                if ( !snake.hasEatenApple( apple ) ) {
                    snake.removeTailSegment();
                }
                else {
                    SnakeSoundEffectsPlayer.playEatingAppleSoundEffect();
                    updateScore();
                    eraseApple();
                    generateApple();
                }
                moveTheSnakeAccordingToTheDirection();
                Platform.runLater( () -> {
                    updateFrame();
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

    private void updateScore() {
        currentScore++;
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

    private void updateFrame() {
        score.setText( String.valueOf( currentScore ) );
        snakeCanvas.draw();
        snake.draw( drawingArea );
        apple.draw( drawingArea );
    }
}
