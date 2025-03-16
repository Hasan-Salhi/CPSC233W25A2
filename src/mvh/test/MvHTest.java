package mvh.test;

import mvh.enums.Direction;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MvHTest {

    /**
     * This test does not count for credit for A2
     */
    @Test
    void gameStringMH() {
        //This test mimics the example world.txt input
        //Should be good example of common output format to match for your gameString/worldString attempts for A2

        //Since IDs are a static class variable, for each unit test to act like World is new and entities are the first we've seen
        //We need to reset the ID counter to 1 so Monster is 1 and Hero is 2
        //If we didn't do this each gameString test our IDs would continue to increase through-out all the tests instead
        //of starting at 1 each time
        Entity.resetIDCounter();
        //Do a regular test
        //Make OO test framework
        Monster monster = new Monster(10, 'M', WeaponType.SWORD);
        Hero hero = new Hero(10, 'H', 3, 1);
        World world = new World(3, 3);
        world.addEntity(0, 0, monster);
        world.addEntity(2, 2, hero);
        //Expected is this String
        String expected = """
                #####
                #M..#
                #...#
                #..H#
                #####
                NAME   \tS\tH\tSTATE\tINFO
                Mons(1)\tM\t10\tALIVE\tSWORD
                Hero(2)\tH\t10\tALIVE\t3\t1
                """;
        //Get actual from world.gameString()
        String actual = world.gameString();
        //Now call function
        assertEquals(expected, actual);
    }

    //TODO: Unit tests for credit (15 marks of 50)

    /**
     * Test gameString with extra complexities
     */
    @Test
    void gameStringMH75() {
        Entity.resetIDCounter();

        World world = new World(7, 5);

        //Create new entities for World
        Hero hero = new Hero(10, 'H', 3, 2);
        Hero secondHero = new Hero(15, 'F', 5, 1);
        Monster monster = new Monster(12,'M',WeaponType.SWORD);
        Monster secondMonster = new Monster(10,'M',WeaponType.CLUB);
        Monster thirdMonster = new Monster(8,'S',WeaponType.AXE);

        //Add entities to World
        world.addEntity(0, 2, secondHero);
        world.addEntity(2, 4, hero);
        world.addEntity(1, 0, monster);
        world.addEntity(2, 1, secondMonster);
        world.addEntity(5, 3, thirdMonster);

        world.addEntity(1,3, Wall.getWall());
        world.addEntity(4,0, Wall.getWall());
        world.addEntity(4,1, Wall.getWall());

        //Expected output
        String expected = """
                #######
                #..F..#
                #M..#.#
                #.M..H#
                #.....#
                ###...#
                #...S.#
                #.....#
                #######
                NAME   	S	H	STATE	INFO
                Hero(2)	F	15	ALIVE	5	1
                Mons(3)	M	12	ALIVE	SWORD
                Mons(4)	M	10	ALIVE	CLUB
                Hero(1)	H	10	ALIVE	3	2
                Mons(5)	S	8	ALIVE	AXE
                """;
        //Call function and store in actual
        String actual = world.gameString();

        //Assert
        assertEquals(expected, actual);
    }

    /*
     * Test worldString
     */
    @Test
    void worldStringMH() {
        Entity.resetIDCounter();

        World world = new World(3, 3);

        //Create new entities for World
        Hero hero = new Hero(10, 'H', 3, 1);
        Monster monster = new Monster(10,'M',WeaponType.AXE);
        Monster otherMonster = new Monster(10,'M',WeaponType.CLUB);

        //Add entities to World
        world.addEntity(0, 2, hero);
        world.addEntity(1, 0, monster);
        world.addEntity(2, 1, otherMonster);

        world.addEntity(1,2, Wall.getWall());

        //Expected output
        String expected = """
                #####
                #..H#
                #M.##
                #.M.#
                #####
                """;
        //Call function and store in actual
        String actual = world.worldString();

        //Assert
        assertEquals(expected, actual);
    }

    /*
     * Test worldString with extra complexities
     */
    @Test
    void worldStringMH75() {
        Entity.resetIDCounter();

        World world = new World(7, 5);

        //Create new entities for World
        Hero hero = new Hero(10, 'H', 3, 2);
        Hero secondHero = new Hero(15, 'F', 5, 1);
        Monster monster = new Monster(12,'M',WeaponType.SWORD);
        Monster secondMonster = new Monster(10,'M',WeaponType.CLUB);
        Monster thirdMonster = new Monster(8,'S',WeaponType.AXE);

        //Add entities to World
        world.addEntity(0, 2, secondHero);
        world.addEntity(2, 4, hero);
        world.addEntity(1, 0, monster);
        world.addEntity(2, 1, secondMonster);
        world.addEntity(5, 3, thirdMonster);

        world.addEntity(1,3, Wall.getWall());
        world.addEntity(4,0, Wall.getWall());
        world.addEntity(4,1, Wall.getWall());

        //Expected output
        String expected = """
                #######
                #..F..#
                #M..#.#
                #.M..H#
                #.....#
                ###...#
                #...S.#
                #.....#
                #######
                """;
        //Call function and store in actual
        String actual = world.worldString();

        //Assert
        assertEquals(expected, actual);
    }

    /*
     * Test loadWorld
     */
    @Test
    void loadWorldMH() {
        Entity.resetIDCounter();

        //Create world to compare result too
        World expected = new World(3, 3);

        //Create entities to put in world
        Monster expectMonster = new Monster(10,'M',WeaponType.SWORD);
        Hero expectHero = new Hero(10,'H',3,1);
        //Add entities to world
        expected.addEntity(0,0,expectMonster);
        expected.addEntity(2,2,expectHero);

        File worldFile = new File("world.txt");

        //Reset necessary, otherwise entity IDs in actual will not match expected values
        Entity.resetIDCounter();
        World actual = Reader.loadWorld(worldFile);

        assertTrue(expected.gameString().equals(actual.gameString()));
    }

    /*
     * Test loadWorld with example worldBig.txt
     */
    @Test
    void loadWorldMH54() {
        Entity.resetIDCounter();
        World expected = new World(5, 4);

        //Create monsters for expected
        Monster monster = new Monster(10,'A',WeaponType.AXE);
        Monster monster1 = new Monster(9,'B',WeaponType.SWORD);
        Monster monster2 = new Monster(8,'C',WeaponType.CLUB);

        //Create heroes for expected
        Hero hero = new Hero(8,'D',5,2);
        Hero hero1 = new Hero(7,'E',4,1);
        Hero hero2 = new Hero(6,'F',3,1);

        //Add entities to expected
        expected.addEntity(0,0,monster);
        expected.addEntity(0,2,monster1);
        expected.addEntity(1,2,monster2);
        expected.addEntity(4,1,hero);
        expected.addEntity(4,2,hero1);
        expected.addEntity(4,3,hero2);

        //Call loadWorld() to make actual
        Entity.resetIDCounter();
        File worldFile = new File("worldbig.txt");
        World actual = Reader.loadWorld(worldFile);

        //I don't know why, but the test fails when I compare the objects directly
        assertEquals(expected.gameString(), actual.gameString());
    }

    /*
     * Testing getLocal
     */

    @Test
    void getLocalMH5() {
        Entity.resetIDCounter();
        //Testing with the provided world here
        World testWorld = Reader.loadWorld(new File("world.txt"));

        //Creating a world to match what the local view is supposed to look like
        World expected = new World(3,3);

        expected.addEntity(1,1, testWorld.getEntity(2,2));
        expected.addEntity(0,2,Wall.getWall());
        expected.addEntity(1,2,Wall.getWall());
        expected.addEntity(2,0,Wall.getWall());
        expected.addEntity(2,1,Wall.getWall());
        expected.addEntity(2,2,Wall.getWall());

        World actual = testWorld.getLocal(3,2,2);

        assertEquals(expected.gameString(), actual.gameString());
    }

    /*
     * Testing getLocal with bigger world sizes and bigger local sizes
     */
    @Test
    void getLocalMH88() {
        Entity.resetIDCounter();
        World testWorld = new World(8,8);

        //Setting up testWorld
        testWorld.addEntity(1,7,new Hero(10,'U',4,2));
        testWorld.addEntity(2,0,Wall.getWall());
        testWorld.addEntity(2,1,new Monster(12,'M',WeaponType.AXE));
        testWorld.addEntity(3,0,Wall.getWall());
        testWorld.addEntity(3,5,new Monster(0,'$',WeaponType.SWORD)); //Centre
        testWorld.addEntity(5,2,new Hero(6,'H',8,1));
        testWorld.addEntity(6,6,new Monster(7,'M',WeaponType.CLUB));
        testWorld.addEntity(7,2,new Monster(14,'R',WeaponType.CLUB));

        /**
         * ##########
         * #........#
         * #.......U#
         * ##M......#
         * ##....$..#
         * #........#
         * #..H.....#
         * #......M.#
         * #..R.....#
         * ##########
         */

        World expected = new World(5,5);

        //Replicating what the output is supposed to be
        expected.addEntity(0,4,testWorld.getEntity(1,7));
        expected.addEntity(2,2,testWorld.getEntity(3,5));

        /**
         * ....U
         * .....
         * ..$..
         * .....
         * .....
         */

        //Calling function
        World actual = testWorld.getLocal(5,3,5);

        assertEquals(expected.gameString(), actual.gameString());
    }

    /*
     * This test is to make sure no errors occur when getLocal goes far out of bounds
     */
    @Test
    void getLocalMH55() {
        Entity.resetIDCounter();

        //Creating testWorld
        World testWorld = new World(5,5);
        testWorld.addEntity(1,1,new Monster(10,'M',WeaponType.AXE));
        testWorld.addEntity(3,2,new Hero(12,'H',4,2));
        testWorld.addEntity(4,2,new Monster(10,'R',WeaponType.AXE));

        /*
         * #######
         * #.....#
         * #.M...#
         * #.....#
         * #..H..#
         * #..R..#
         * #######
         */

        //Centre will be 1,0
        World expected = new World(5,5);
        expected.addEntity(2,3,testWorld.getEntity(1,1));
        expected.addEntity(4,4,testWorld.getEntity(3,2));
        for (int i = 0; i < 5; i++) {
            expected.addEntity(0,i,Wall.getWall());
        }
        for (int i = 1; i < 5; i++) {
            expected.addEntity(i,0,Wall.getWall());
            expected.addEntity(i,1,Wall.getWall());
        }

        /*
         * //Expected result
         * #####
         * ##...
         * ##.M.
         * ##...
         * ##..H
         */

        //Call function
        World actual = testWorld.getLocal(5,1,0);

        System.out.println(expected.gameString());
        System.out.println(actual.gameString());

        assertEquals(expected.gameString(), actual.gameString());

    }

    //Any functions beyond here, expect them to be very buggy

    /*
     * Testing attackWhere for Heroes
     */
    @Test
    void attackWhereMH() {
        Entity.resetIDCounter();

        World testWorld = Reader.loadWorld(new File("world.txt"));

        //Adding a new monster within the hero's getLocal radius
        testWorld.addEntity(1,2,new Monster(10,'M',WeaponType.CLUB));

        //For attackWhere, we have to compare the direction the hero needs to go
        Direction expected = Direction.NORTH;
        Direction actual = testWorld.getEntity(2,2).attackWhere(testWorld.getLocal(3,2,2));

        assertEquals(expected, actual);
    }

    @Test
    void attackWhereMHNull() {
        Entity.resetIDCounter();

        World testWorld = Reader.loadWorld(new File("worldbig2.txt"));

        //Hero should not move, because there are no monsters nearby
        Direction expected = Direction.STAY;
        Direction actual = testWorld.getEntity(4,1).attackWhere(testWorld.getLocal(3,4,1));

        assertEquals(expected, actual);
    }

    /*
     * Testing attackWhere for monsters
     */
    @Test
    void attackWhereMHMonster() {
        Entity.resetIDCounter();

        World testWorld = Reader.loadWorld(new File("worldbig2.txt"));

        //Monster must aim south to reach hero
        Direction expected = Direction.SOUTH;
        Direction actual = testWorld.getEntity(3,3).attackWhere(testWorld.getLocal(3,3,3));

        assertEquals(expected, actual);
    }

    @Test
    void attackWhereMHMonsterNull() {
        Entity.resetIDCounter();

        World testWorld = Reader.loadWorld(new File("worldbig2.txt"));

        //Monster should not move as there is no hero within getLocal radius
        Direction expected = Direction.STAY;
        Direction actual = testWorld.getEntity(0,2).attackWhere(testWorld.getLocal(3,0,2));

        assertEquals(expected, actual);
    }

    //Testing chooseMove

        //Starting with Hero
    @Test
    void chooseMoveMHNull() {
        Entity.resetIDCounter();

        World worldTest = Reader.loadWorld(new File("worldbig.txt"));

        //Hero should default northwest as there are no monsters nearby
        Direction expected = Direction.NORTHWEST;
        Direction actual = worldTest.getEntity(4,2).chooseMove(worldTest.getLocal(5,4,2));

        assertEquals(expected, actual);
    }

    @Test
    void chooseMoveMH2() {
        Entity.resetIDCounter();

        World worldTest = Reader.loadWorld(new File("world.txt"));
        worldTest.addEntity(0,2,new Monster(10,'R',WeaponType.SWORD));
        Entity.resetIDCounter(); //This is to turn this ID back into 1
        worldTest.addEntity(0,0,new Monster(0,'$',WeaponType.SWORD));

        //Hero should ignore dead monster in first index and go north to the living one
        Direction expected = Direction.NORTH;
        Direction actual = worldTest.getEntity(2,2).chooseMove(worldTest.getLocal(5,2,2));

        assertEquals(expected, actual);
    }

        //Testing for monsters now

    @Test
    void chooseMoveMHMonsterNull() {
        Entity.resetIDCounter();

        World worldTest = Reader.loadWorld(new File("worldbig.txt"));

        //Monster defaults southeast when no heroes nearby
        Direction expected = Direction.SOUTHEAST;
        Direction actual = worldTest.getEntity(1,2).chooseMove(worldTest.getLocal(5,1,2));

        assertEquals(expected, actual);
    }

    @Test
    void chooseMoveMHMonster() {
        Entity.resetIDCounter();

        World worldTest = new World(5,6);

        worldTest.addEntity(3,2,new Monster(10,'M',WeaponType.CLUB));
        worldTest.addEntity(1,3,new Hero(10,'H',4,2));

        //Monster should head northeast towards hero
        Direction expected = Direction.NORTHEAST;
        Direction actual = worldTest.getEntity(3,2).chooseMove(worldTest.getLocal(5,3,2));

        assertEquals(expected, actual);
    }

}