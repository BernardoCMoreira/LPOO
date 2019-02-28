import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;


public class Game {

    Screen screen;
    private int x = 10;
    private int y = 10;
    private Boolean runVar = true;

    Game() {
        try {

            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();// resize screen if necessary


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private void draw() throws IOException {
            screen.clear();
            screen.setCharacter(x, y, new TextCharacter('X'));
            screen.refresh();

    }

    public void run() {

            try {
                while (runVar) {
                    this.draw();
                    KeyStroke key = screen.readInput();
                    this.processKey(key);
                }
                screen.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    private void processKey(KeyStroke key) {
        System.out.println(key);

        if (key.getKeyType() == KeyType.ArrowUp) {
            if (this.y > 0) {
                this.y--;
            }

        } else if (key.getKeyType() == KeyType.ArrowDown) {
            this.y++;
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            if (this.x > 0) {
                this.x--;
            }
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            this.x++;
        }

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            runVar = false;
        }

        if (key.getKeyType() == KeyType.EOF ){
            runVar = false;
        }
    }

}

