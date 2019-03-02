import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Arena {
    private int height;
    private int width;
    Hero hero;


    public Arena(int height, int width){
        hero = new Hero( 10, 10);
        this.height = height;
        this.width = width;
    }

    public void draw (TextGraphics graphics){
      hero.draw(graphics);
    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    private Boolean canHeroMove(Position position){
        if (position.getX() < 0){
            return false;
        }
        if (position.getY() < 0){
            return false;
        }
        if (position.getX() >=width){
            return false;
        }
        if (position.getY() >= height){
            return false;
        }
        return true;
    }

    public void processKey(KeyStroke key){
        if (key.getKeyType() == KeyType.ArrowUp) {
            moveHero(hero.moveUp());
        }
        else if (key.getKeyType() == KeyType.ArrowDown) {
            moveHero(hero.moveDown());
        }
        else if (key.getKeyType() == KeyType.ArrowLeft) {
            moveHero(hero.moveLeft());
        }
        else if (key.getKeyType() == KeyType.ArrowRight) {
            moveHero(hero.moveRight());
        }
    }
}
