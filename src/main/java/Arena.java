import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private int height;
    private int width;
    Hero hero;

    private List<Monster> monsters;
    private List<Wall> walls;
    private List<Coin> coins;
    Random rand = new Random();
    int scoreAdd = 0;

    public Arena(int height, int width){
        hero = new Hero( 10, 10);
        this.height = height;
        this.width = width;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonster();

    }

    public void draw (TextGraphics graphics){

        if(checkColisionMonsters() == false) {
            hero.draw(graphics);
            for (Wall wall : walls)
                wall.draw(graphics);
            for (Coin coin : coins)
                coin.draw(graphics);
            for (Monster monster : monsters)
                monster.draw(graphics);

          //Draw de score
          graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
          graphics.enableModifiers(SGR.BOLD);
          graphics.putString(new TerminalPosition(0, 0), "Score:");
          graphics.putString(new TerminalPosition(8, 0), String.valueOf(scoreAdd));
      }
    else {
          graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
          graphics.enableModifiers(SGR.BOLD);
          graphics.putString(new TerminalPosition(graphics.getSize().getColumns()/2-4, graphics.getSize().getRows()/2), "YOU DIED");

      }

    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();

        }
    }
    private void moveMonster(Position position, Monster monster){
        if (canHeroMove(position)){
            monster.setPosition(position);

        }
    }

    private Boolean canHeroMove(Position position){

        if (position.getX() <=0) return false;
        if (position.getY() <=1) return false;
        if (position.getX() >=width-1) return false;
        if (position.getY() >=height-1)return false;

        return true;
    }
    public void processKey(KeyStroke key) {
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

        for (Monster monster : monsters ) {
            int n = rand.nextInt(4);
            //n += 1;

            if (n == 0) {
                moveMonster(monster.moveUp(), monster);

            } else if (n == 1) {
                moveMonster((monster.moveDown()),monster);

            } else if (n == 2) {
                moveMonster((monster.moveLeft()), monster);

            } else if (n == 3) {
                moveMonster((monster.moveRight()), monster);

            }
        }

    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 1; c < width; c++) {
            walls.add(new Wall(c, 1));
            walls.add(new Wall(c, height -1));
        }

        for (int r = 1; r < height ; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 3) + 2));

        return coins;
    }


    private void retrieveCoins(){
     int coinsAux= -1;

        for (int i=0; i<coins.size(); i++){
            if(coins.get(i).getPosition().equals(hero.getPosition()))
                coinsAux = i;
        }

        if (coinsAux !=-1){
            coins.remove(coinsAux);
            scoreAdd++;
        }
    }

    private List<Monster> createMonster() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 3) + 2));

        return monsters;
    }

   public boolean checkColisionMonsters(){
        int monstersAux= -1;

        for (int i=0; i<monsters.size(); i++){
            if(monsters.get(i).getPosition().equals(hero.getPosition()))
                monstersAux = i;
        }

        if (monstersAux !=-1){
            return true;
        }
        return false;
    }


}
