import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class  Element {
    private Position position;

    Element (int x, int y){
        position = new Position(x,y);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    abstract void draw(TextGraphics graphics);

}
