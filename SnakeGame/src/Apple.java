
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Apple {
    private int appleSize;
    private static int xPosition;
    private static int yPosition;

    public Apple( int xPosition, int yPosition, int appleSize ) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.appleSize = appleSize;
    }

    public void draw( GraphicsContext drawingArea ) {
        drawingArea.setFill( Color.RED );
        drawingArea.fillRect( xPosition*appleSize, yPosition*appleSize, appleSize - 1,
                appleSize - 1 );
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getSize() {
        return appleSize;
    }
}
