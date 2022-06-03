
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SnakeCanvas extends Canvas {
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private GraphicsContext drawingArea = getGraphicsContext2D();

    public SnakeCanvas( double canvasWidth, double canvasHeight ) {
        super( canvasWidth, canvasHeight );
        draw();
    }

    public void draw() {
        fillCanvasWithBackgroundColor();
    }

    private void fillCanvasWithBackgroundColor() {
        drawingArea.setFill( BACKGROUND_COLOR );
        drawingArea.fillRect( 0, 0, getWidth(), getHeight() );
    }

    public void erase( Apple apple ) {
        drawingArea.setFill( BACKGROUND_COLOR );
        drawingArea.fillRect( apple.getXPosition()*apple.getSize(), apple.getYPosition()*apple.getSize(),
                apple.getSize(), apple.getSize() );
    }

}
