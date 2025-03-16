package mvh.world;

import mvh.Main;
import mvh.Menu;
import mvh.enums.Direction;
import mvh.enums.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A World is a 2D grid of entities, null Spots are floor spots
 * @author Jonathan Hudson, Hasan Salhi
 * @version 1.1b
 */
public class World {

    /**
     * World starts ACTIVE, but will turn INACTIVE after a simulation ends with only one type of Entity still ALIVE
     */
    private enum State {
        ACTIVE, INACTIVE
    }

    /**
     * The World starts ACTIVE
     */
    private State state;
    /**
     * The storage of entities in World, floor is null, Dead entities can be moved on top of (deleting them essentially from the map)
     */
    private final Entity[][] world;
    /**
     * We track the order that entities were added (this is used to determine order of actions each turn)
     * Entities remain in this list (Even if DEAD) ,unlike the world Entity[][] where they can be moved on top of causing deletion.
     */
    private final ArrayList<Entity> entities;
    /**
     * We use a HashMap to track entity location in world {row, column}
     * We will update this every time an Entity is shifted in the world Entity[][]
     */
    private final HashMap<Entity, Integer[]> locations;

    /**
     * The local view of world will be 3x3 grid for attacking
     */
    private static final int ATTACK_WORLD_SIZE = 3;
    /**
     * The local view of world will be 5x5 grid for moving
     */
    private static final int MOVE_WORLD_SIZE = 5;

    /**
     * A new world of ROWSxCOLUMNS in size
     *
     * @param rows    The 1D of the 2D world (rows)
     * @param columns The 2D of the 2D world (columns)
     */
    public World(int rows, int columns) {
        //Establishes all fields in World class to what they need to be
        this.world = new Entity[rows][columns];

        ArrayList<Entity> entities = new ArrayList<>();
        this.entities = entities;

        this.locations = new HashMap<>();

        this.state = State.ACTIVE;
    }

    /**
     * Is this simulation still considered ACTIVE
     *
     * @return True if the simulation still active, otherwise False
     */
    public boolean isActive() {
        return state == State.ACTIVE;
    }

    /**
     * End the simulation, (Set in INACTIVE)
     */
    public void endSimulation() {
        this.state = State.INACTIVE;
    }

    /**
     * Advance the simulation one step
     */
    public void advanceSimulation() {
        //Do not advance if simulation is done
        if (state == State.INACTIVE) {
            return;
        }
        //If not done go through all entities (this will be in order read and added from file)
        for (Entity entity : entities) {
            //If entity is something that is ALIVE, we want to give it a turn to ATTACK or MOVE
            if (entity.isAlive()) {
                //Get location of entity (only the world knows this, the entity does not itself)
                Integer[] location = locations.get(entity);
                //Pull out row,column
                int row = location[0];
                int column = location[1];
                //Determine if/where an entity wants to attack
                World attackWorld3X3 = getLocal(ATTACK_WORLD_SIZE, row, column);
                Direction attackWhere = entity.attackWhere(attackWorld3X3);
                //If I don't attack, then I must be moving
                if (attackWhere == null) {
                    //Figure out where entity wants to move
                    World moveWorld5x5 = getLocal(MOVE_WORLD_SIZE, row, column);
                    Direction moveWhere = entity.chooseMove(moveWorld5x5);
                    //Log moving
                    Menu.println(String.format("%s moving %s", entity.shortString(), moveWhere));
                    //If this move is valid, then move it
                    if (canMoveOnTopOf(row, column, moveWhere)) {
                        moveEntity(row, column, moveWhere);
                    } else {
                        //Otherwise, indicate an invalid attempt to move
                        Menu.println(String.format("%s  tried to move somewhere it could not!", entity.shortString()));
                    }
                } else {
                    //If we are here our earlier attack question was not null, and we are attacking a nearby entity
                    //Get the entity we are attacking
                    Entity attacked = getEntity(row, column, attackWhere);
                    Menu.println(String.format("%s attacking %s in direction %s", entity.shortString(), attackWhere, attacked.shortString()));
                    //Can we attack this entity
                    if (canBeAttacked(row, column, attackWhere)) {
                        //Determine damage using RNG
                        int damage = 1 + Main.random.nextInt(entity.weaponStrength());
                        int true_damage = Math.max(0, damage - attacked.armorStrength());
                        Menu.println(String.format("%s attacked %s for %d damage against %d defense for %d", entity.shortString(), attacked.shortString(), damage, attacked.armorStrength(), true_damage));
                        attacked.damage(true_damage);
                        if (!attacked.isAlive()) {
                            locations.remove(attacked);
                            Menu.println(String.format("%s died!", attacked.shortString()));
                        }
                    } else {
                        Menu.println(String.format("%s  tried to attack somewhere it could not!", entity.shortString()));
                    }
                }
            }
        }
        checkActive();
    }

    /**
     * Check if simulation has now ended (only one of two versus Entity types is alive
     */
    public void checkActive() {
        //Assumes all Dead until it finds one still alive
        boolean allMonstersDead = true;
        for (int row = 0; row < world.length; row++) {
            for (int column = 0; column < world[row].length; column++) {
                if (world[row][column] instanceof Monster) {
                    if (world[row][column].isAlive()) {
                        allMonstersDead = false;
                    }
                }
            }
        }

        //Assumes all dead until finds a living Hero
        boolean allHeroesDead = true;
        for (int row = 0; row < world.length; row++) {
            for (int column = 0; column < world[row].length; column++) {
                if (world[row][column] instanceof Hero) {
                    if (world[row][column].isAlive()) {
                        allHeroesDead = false;
                    }
                }
            }
        }

        if (allMonstersDead || allHeroesDead) {
            this.state = State.INACTIVE;
        }

    }

    /**
     * Move an existing entity
     *
     * @param row    The  row location of existing entity
     * @param column The  column location of existing entity
     * @param d      The direction to move the entity in
     */
    public void moveEntity(int row, int column, Direction d) {
        Entity entity = getEntity(row, column);
        int moveRow = row + d.getRowChange();
        int moveColumn = column + d.getColumnChange();
        this.world[moveRow][moveColumn] = entity;
        this.world[row][column] = null;
        this.locations.put(entity, new Integer[]{moveRow, moveColumn});
    }

    /**
     * Add a new entity
     *
     * @param row    The  row location of new entity
     * @param column The  column location of new entity
     * @param entity The entity to add
     */
    public void addEntity(int row, int column, Entity entity) {
        this.world[row][column] = entity;
        this.entities.add(entity);
        locations.put(entity, new Integer[]{row, column});
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column) {
        return this.world[row][column];
    }

    /**
     * Get entity at a location
     *
     * @param row    The row of the entity
     * @param column The column of the entity
     * @param d      The direction adjust look up towards
     * @return The Entity at the given row, column
     */
    public Entity getEntity(int row, int column, Direction d) {
        return getEntity(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return true;
        }
        return entity.canMoveOnTopOf();
    }

    /**
     * See if we can move to location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can move to that location
     */
    public boolean canMoveOnTopOf(int row, int column, Direction d) {
        return canMoveOnTopOf(row + d.getRowChange(), column + d.getColumnChange());
    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity.canBeAttacked();

    }

    /**
     * See if we can attack entity at a location
     *
     * @param row    The row to check
     * @param column The column to check
     * @param d      The direction adjust look up towards
     * @return True if we can attack entity at that location
     */
    public boolean canBeAttacked(int row, int column, Direction d) {
        return canBeAttacked(row + d.getRowChange(), column + d.getColumnChange());

    }

    /**
     * See if entity is hero at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a hero at that location
     */
    public boolean isHero(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Hero;
    }


    /**
     * See if entity is monster at this location
     *
     * @param row    The row to check
     * @param column The column to check
     * @return True if entity is a monster at that location
     */
    public boolean isMonster(int row, int column) {
        Entity entity = getEntity(row, column);
        if (entity == null) {
            return false;
        }
        return entity instanceof Monster;
    }

    /**
     * Return a nxn mini-view of the world
     *
     * @param size  Size of local world view we want (Must be odd)
     * @param row   Desired row to be centered on
     * @param column    Desired column to be centered on
     *
     * @return  world of size 3 centered at desired location
     */
    public World getLocal(int size, int row, int column) {
        World localView = new World(size,size);

        Entity center = world[row][column];
        int newRow = 0;
        for (int i = row - (size-1)/2; i <= row + (size-1)/2; i++) {
            int newCol = 0;
            for (int j = column - (size-1)/2; j <= column + (size-1)/2; j++) {
                if (i > world.length-1 || i < 0 || j > world[0].length-1 || j < 0) {
                    localView.addEntity(newRow, newCol, Wall.getWall());
                } else {
                    localView.addEntity(newRow, newCol, world[i][j]);
                }
                newCol++;
            }
            newRow++;
        }

        return localView;
    }

    /**
     * Writes a view of the map into a string
     *
     * @return view of the map using symbols to represent everything in one big string
     */
    public String worldString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < world[0].length+2; i++) {
            output.append(Symbol.WALL.getSymbol());
        }
        output.append("\n");

        for (int row = 0; row < world.length; row++) {
            output.append(Symbol.WALL.getSymbol());
            for (int column = 0; column < world[0].length; column++) {
                if (world[row][column] instanceof Wall) {
                    output.append(Symbol.WALL.getSymbol());
                } else if (world[row][column] instanceof Monster) {
                    if (world[row][column].isAlive()) {
                        output.append(world[row][column].getSymbol());
                    } else if (world[row][column].isDead()) {
                        output.append(Symbol.DEAD.getSymbol());
                    }
                } else if (world[row][column] instanceof Hero) {
                    if (world[row][column].isAlive()) {
                        output.append(world[row][column].getSymbol());
                    } else if (world[row][column].isDead()) {
                        output.append(Symbol.DEAD.getSymbol());
                    }
                } else {
                    output.append(Symbol.FLOOR.getSymbol());
                }
            }
            output.append(Symbol.WALL.getSymbol()+ "\n");
        }

        for (int i = 0; i < world[0].length+2; i++) {
            output.append(Symbol.WALL.getSymbol());
        }
        output.append("\n");

        return output.toString();
    }

    /**
     * Takes worldString and adds onto it, including data for each hero and monster
     *
     * @return worldString with all data beneath it in one big string
     */
    public String gameString() {
        StringBuilder output = new StringBuilder();
        output.append(worldString());
        //Adds world view to the StringBuilder
        output.append("NAME   \tS\tH\tSTATE\tINFO\n");
        for (int row = 0; row < world.length; row++) {
            for (int column = 0; column < world[0].length; column++) {
                if (world[row][column] instanceof Monster) {
                    String data = world[row][column].toString();

                    output.append(data + "\n");
                } else if (world[row][column] instanceof Hero) {
                    String data = world[row][column].toString();

                    output.append(data + "\n");
                }
            }
        }
        return output.toString();
    }

    @Override
    public String toString() {
        return gameString();
    }

    /**
     * The rows of the world
     * @return The rows of the world
     */
    public int getRows(){
        return world.length;
    }

    /**
     * The columns of the world
     * @return The columns of the world
     */
    public int getColumns(){
        return world[0].length;
    }

}
