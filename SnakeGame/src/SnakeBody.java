
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class SnakeBody {

    private final int HEAD_POSITION = 0;
    private ArrayList<Segment> segments;
    private int xPositionOfHead;
    private int yPositionOfHead;
    private int segmentSize;

    public SnakeBody( int xPositionOfHead, int yPositionOfHead, int segmentSize ) {
        this.xPositionOfHead = xPositionOfHead;
        this.yPositionOfHead = yPositionOfHead;
        this.segmentSize = segmentSize;
        segments = new ArrayList<>();
        addThreeSegmentsToSnakeBody();
    }

    private void addThreeSegmentsToSnakeBody() {
        for ( int segment = 0; segment < 3; segment++ ) {
            segments.add( new Segment( xPositionOfHead - segment, yPositionOfHead, segmentSize ) );
        }
    }

    public void draw( GraphicsContext drawingArea ) {
        for ( Segment segment : segments )
            segment.draw( drawingArea );
    }

    public void moveUp() {
        yPositionOfHead--;
        segments.add( HEAD_POSITION, new Segment( xPositionOfHead, yPositionOfHead, segmentSize ) );
    }

    public void moveDown() {
        yPositionOfHead++;
        segments.add( HEAD_POSITION, new Segment( xPositionOfHead, yPositionOfHead, segmentSize ) );
    }

    public void moveLeft() {
        xPositionOfHead--;
        segments.add( HEAD_POSITION, new Segment( xPositionOfHead, yPositionOfHead, segmentSize ) );
    }

    public void moveRight() {
        xPositionOfHead++;
        segments.add( HEAD_POSITION, new Segment( xPositionOfHead, yPositionOfHead, segmentSize ) );
    }

    public boolean hasCollidedOnItself() {
        Segment head = getHead();
        for ( int i = 1; i < segments.size(); i++ ) {
            Segment currentSegment = segments.get(i);
            if ( currentSegment.getXPosition() == head.getXPosition() &&
                    currentSegment.getYPosition() == head.getYPosition() )
                return true;
        }
        return false;
    }

    public Segment getHead() {
        return segments.get( HEAD_POSITION );
    }

    public void removeTailSegment() {
        segments.remove( segments.size() - 1 );
    }

    public boolean hasEatenApple( Apple apple ) {
        Segment snakesHead = getHead();
        if ( snakesHead.getXPosition() == apple.getXPosition() && snakesHead.getYPosition() == apple.getYPosition() ) {
            System.out.println( "Apple eaten." );
            return true;
        }
        return false;
    }

    public boolean hasCollidedIntoTheWall( int cellWidth, int cellHeight ) {
        Segment snakesHead = getHead();
        if ( snakesHead.getXPosition() == -1 || snakesHead.getXPosition() == cellWidth ||
                snakesHead.getYPosition() == -1 || snakesHead.getYPosition() == cellHeight )
            return true;
        return false;
    }

}
