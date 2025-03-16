package mvh.world;

import mvh.enums.Direction;

/**
 * A Monster is an Entity with a user provide WEAPON STRENGTH and ARMOR STRENGTH
 * @author Jonathan Hudson, Hasan Salhi
 * @version 1.1b
 */
public final class Hero extends Entity{

    /**
     * The user provided weapon strength
     */
    private final int weaponStrength;

    /**
     * The user provided armor strength
     */
    private final int armorStrength;

    /**
     * A Hero has regular health and symbol as well as a weapon strength and armor strength
     * @param health Health of hero
     * @param symbol Symbol for map to show hero
     * @param weaponStrength The weapon strength of the hero
     * @param armorStrength The armor strength of the hero
     */
    public Hero(int health, char symbol, int weaponStrength, int armorStrength) {
        //Inherits symbol and health fields from Entity
        super(symbol,health);

        this.weaponStrength = weaponStrength;
        this.armorStrength = armorStrength;
    }

    /**
     * The weapon strength of monster is from user value
     * @return The weapon strength of monster is from user value
     */
    @Override
    public int weaponStrength() {
        return weaponStrength;
    }

    /**
     * The armor strength of monster is from user value
     * @return The armor strength of monster is from user value
     */
    @Override
    public int armorStrength() {
        return armorStrength;
    }

    /**
     * Can only be moved on top of if dad
     * @return isDead()
     */
    @Override
    public boolean canMoveOnTopOf() {
        return isDead();
    }

    /**
     * Can only be attacked if alive
     * @return isAlive()
     */
    @Override
    public boolean canBeAttacked() {
        return isAlive();
    }

    @Override
    public String toString(){
        return super.toString()+"\t"+weaponStrength+"\t"+armorStrength;
    }

    //TODO: attackWhere
    /**
     * Moves to monster location to attack if there is a monster nearby
     *
     * @param local takes world to construct local view centered around hero
     *
     * @return direction to reach monster or null if there are none nearby
     */
    public Direction attackWhere(World local) {

        //This is under the assumption that the parameter local is already local to the hero
        for (int i = 0; i < local.getRows(); i++) {
            for (int j = 0; j < local.getColumns(); j++) {
                if (i != 1 || j != 1) {
                    if (local.isMonster(i, j)) {
                        Entity monster = local.getEntity(i, j);
                        if (monster.isAlive()) {
                            if (i == 0 && j == 0) {
                                return Direction.NORTHWEST;
                            } else if (i == 0 && j == 1) {
                                return Direction.NORTH;
                            } else if (i == 0 && j == 2) {
                                return Direction.NORTHEAST;
                            } else if (i == 1 && j == 0) {
                                return Direction.WEST;
                            } else if (i == 1 && j == 2) {
                                return Direction.EAST;
                            } else if (i == 2 && j == 0) {
                                return Direction.SOUTHWEST;
                            } else if (i == 2 && j == 1) {
                                return Direction.SOUTH;
                            } else if (i == 2 && j == 2) {
                                return Direction.SOUTHEAST;
                            }
                        }
                    }
                }
            }
        }

        return Direction.STAY;
    }


    //TODO: chooseMove
    /**
     * Decides where to move based on locations of monsters
     *
     * @param local, world view
     *
     * @return direction to move in
     */
    public Direction chooseMove(World local) {
        //This is under the assumption that the parameter local is already local to the hero
        for (int i = 0; i < local.getRows(); i++) {
            for (int j = 0; j < local.getColumns(); j++) {
                if (i != 2 || j != 2) {
                    if (local.isMonster(i, j) && local.getEntity(i, j).isAlive()) {
                        if (i == 0) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(-2, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 1) {
                                Direction[] directions = Direction.getDirections(-2, -1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 2) {
                                Direction[] directions = Direction.getDirections(-2, 0);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 3) {
                                Direction[] directions = Direction.getDirections(-2, 1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(-2, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 1) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(-1, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(-1, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 2) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(0, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(0, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 3) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(1, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(1, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 4) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(2, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 1) {
                                Direction[] directions = Direction.getDirections(2, -1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 2) {
                                Direction[] directions = Direction.getDirections(2, 0);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 3) {
                                Direction[] directions = Direction.getDirections(2, 1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(2, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows()-1)/2 + direction.getRowChange(), (local.getColumns()-1)/2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Attempt to move northwest if no nearby monsters
        if (local.canMoveOnTopOf(local.getRows()/2 - 1, local.getColumns()/2 - 1)) {
            return Direction.NORTHWEST;
        } else {
            //Attempt to move random direction if unable to go northwest
            int rowChange = Direction.getRandomDirection().getRowChange();
            int columnChange = Direction.getRandomDirection().getColumnChange();
            if (local.canMoveOnTopOf(rowChange, columnChange)) {
                return Direction.getDirection(rowChange, columnChange);
            }
        }

        //Stay in place if random direction doesn't work
        return Direction.STAY;
    }
}
