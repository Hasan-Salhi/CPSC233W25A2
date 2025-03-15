package mvh.util;
import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.Wall;
import mvh.world.World;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class to assist reading in world file
 * @author Jonathan Hudson, Hasan Salhi
 * @version 1.1
 */
public final class Reader {
    /**
     * Function to read the lines of the world.txt and load them into a World class
     *
     * @return World loaded with all data from the text file
     */
    public static World loadWorld(File fileWorld) {
        World world = null;
        try {
            Scanner fr = new Scanner(fileWorld);
            int row = fr.nextInt();
            int col = fr.nextInt();
            world = new World(row, col);
            String line = null;

            do {
                line = fr.next();
                if (line.length() > 3) {
                    String[] split = line.split(",");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    String type = split[2];
                    if (type.equals("MONSTER")) {
                        char symbol = split[3].charAt(0);
                        int health = Integer.parseInt(split[4]);
                        String weaponSymbol = Character.toString(split[5].charAt(0));
                        WeaponType weapon = null;
                        if (weaponSymbol.equals("C")) {
                            weapon = WeaponType.SWORD;
                        } else if (weaponSymbol.equals("A")) {
                            weapon = WeaponType.AXE;
                        } else if (weaponSymbol.equals("S")) {
                            weapon = WeaponType.SWORD;
                        }

                        Monster newMons = new Monster(health,symbol,weapon);

                        world.addEntity(x,y,newMons);

                    } else if (type.equals("HERO")) {
                        char symbol = split[3].charAt(0);
                        int health = Integer.parseInt(split[4]);
                        int weaponStrength = Integer.parseInt(split[5]);
                        int armorStrength = Integer.parseInt(split[6]);

                        Hero newHero = new Hero(health,symbol,weaponStrength,armorStrength);

                        world.addEntity(x,y,newHero);

                    } else if (type.equals("WALL")) {
                        char symbol = split[3].charAt(0);

                        world.addEntity(x,y,Wall.getWall());
                    }
                }

                line = fr.next();

            } while (fr.hasNext());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return world;
    }

}
