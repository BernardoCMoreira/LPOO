import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private int height;
    private int width;
    Hero hero;
    Monster monsters;
    private List<Wall> walls;
    private List<Coin> coins;
    Random rand = new Random();

    public Arena(int height, int width){
        hero = new Hero( 10, 10);
        monsters = new Monster ( 50, 10);

        this.height = height;
        this.width = width;
        this.walls = createWalls();
        this.coins = createCoins();

    }

    public void draw (TextGraphics graphics){
      hero.draw(graphics);
      monsters.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);

    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
            retrieveCoins();

        }
    }
    private void moveMonster(Position position){
        if (canHeroMove(position)){
            monsters.setPosition(position);

        }
    }

    private Boolean canHeroMove(Position position){

        if (position.getX() <=0) return false;
        if (position.getY() <=0) return false;
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

        int n = rand.nextInt(4);
        n+=1;

        if (n==1){
            moveMonster(monsters.moveUp());

        }
        else if (n==2){
            moveMonster((monsters.moveDown()));

        }
        else if(n==3){
            moveMonster((monsters.moveLeft()));

        }
        else if (n==4){
            moveMonster((monsters.moveRight()));

        }

    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));

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
        }
    }

   /* private List<Monster> createMonster() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();

        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));

        return monsters;
    }

    private void checkColisionMonsters(){
        int monstersAux= -1;

        for (int i=0; i<monsters.size(); i++){
            if(monsters.get(i).getPosition().equals(hero.getPosition()))
                monstersAux = i;
        }

        if (monstersAux !=-1){
            coins.remove(monstersAux);
        }
    } */
       public boolean colision(){
           if (monsters.getPosition().getX() == hero.getPosition().getX() && monsters.getPosition().getY() == hero.getPosition().getY()) return true;

           return false;
        }


}
