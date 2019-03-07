import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Game {

    Screen screen;
    private Boolean runVar = true;
    Arena arena;

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

        arena = new Arena(screen.getTerminalSize().getRows() , screen.getTerminalSize().getColumns());
    }

     private void draw() throws IOException {
            screen.clear();
            arena.draw(screen.newTextGraphics());
            screen.refresh();
    }

    public void run() {

            try {
                while (runVar) {
                    this.draw();
                    KeyStroke key = screen.readInput();
                    this.processKey(key);
                }
                this.draw();
                try
                {
                    Thread.sleep(2000);

                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                screen.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    private void processKey(KeyStroke key) {
        System.out.println(key);
        arena.processKey(key);

        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
            runVar = false;
        }
        if (key.getKeyType() == KeyType.EOF ){
            runVar = false;
        }
       if (arena.checkColisionMonsters() == true){
            System.out.println("You lost the Game! ");
            runVar = false;


       }
    }

}

