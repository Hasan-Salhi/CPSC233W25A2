package mvh.test;

import mvh.enums.WeaponType;
import mvh.world.*;
import org.junit.jupiter.api.Test;

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

}