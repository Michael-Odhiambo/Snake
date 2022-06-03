
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Segment {
    private int segmentSize;
    private int xPosition;
    private int yPosition;

    Segment( int xPosition, int yPosition, int segmentSize ) {
        this.segmentSize = segmentSize;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    void draw( GraphicsContext drawingArea ) {
        drawingArea.setFill( Color.GREEN );
        drawingArea.fillRect( ( xPosition*segmentSize ),yPosition*segmentSize, segmentSize-1,
                segmentSize-1 );
    }

    double getXPosition() {
        return xPosition;
    }

    double getYPosition() {
        return yPosition;
    }
}
