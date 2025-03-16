package mvh.world;

import mvh.enums.Direction;
import mvh.enums.WeaponType;

/**
 * A Monster is an Entity with a set ARMOR STRENGTH and a user provided WEAPON TYPE
 * @author Jonathan Hudson, Hasan Salhi
 * @version 1.1b
 */
public final class Monster extends Entity {

    /**
     * The set armor strength of a Monster
     */
    private static final int MONSTER_ARMOR_STRENGTH = 2;

    /**
     * The user provided weapon type
     */
    private final WeaponType weaponType;

    /**
     * A Monster has regular health and symbol as well as a weapon type
     *
     * @param health     Health of Monster
     * @param symbol     Symbol for map to show Monster
     * @param weaponType The weapon type of the Monster
     */
    public Monster(int health, char symbol, WeaponType weaponType) {
        //Used super to access health and symbol fields from Entity class
        super(symbol,health);
        this.weaponType = weaponType;
    }

    /**
     * Gets Monster's weapon type
     * @return The Monster's weapon type
     */
    public WeaponType getWeaponType(){
        return this.weaponType;
    }

    /**
     * The weapon strength of monster is from their weapon type
     * @return The weapon strength of monster is from their weapon type
     */
    @Override
    public int weaponStrength() {
        return weaponType.getWeaponStrength();
    }

    /**
     * The armor strength of monster is from the stored constant
     * @return The armor strength of monster is from the stored constant
     */
    @Override
    public int armorStrength() {
        return MONSTER_ARMOR_STRENGTH;
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
    public String toString() {
        return super.toString() + "\t" + weaponType;
    }

    /**
     * Moves to hero location to attack if there is a hero nearby
     *
     * @param local takes world to construct local view centered around hero
     *
     * @return direction to reach monster or null if there are none nearby
     */
    public Direction attackWhere(World local) {
        //This is under the assumption that the parameter local is already local to the hero
        for (int i = local.getRows()-1; i >= 0; i--) {
            for (int j = local.getColumns()-1; j >= 0; j--) {
                if (local.isHero(i,j)) {
                    Entity hero = local.getEntity(i,j);
                    if (hero.isAlive()) {
                        if (i == 0 && j == 0) {
                            return Direction.getDirection(-1,-1);
                        } else if (i == 0 && j == 1) {
                            return Direction.getDirection(-1,0);
                        } else if (i == 0 && j == 2) {
                            return Direction.getDirection(-1,1);
                        } else if (i == 1 && j == 0) {
                            return Direction.getDirection(0,-1);
                        } else if (i == 1 && j == 2) {
                            return Direction.getDirection(0,1);
                        } else if (i == 2 && j == 0) {
                            return Direction.getDirection(1,-1);
                        } else if (i == 2 && j == 1) {
                            return Direction.getDirection(1,0);
                        } else if (i == 2 && j == 2) {
                            return Direction.getDirection(1,1);
                        }
                    }
                }
            }
        }

        return Direction.getDirection(0,0);
    }

    //TODO: chooseMove
    /**
     * Decides where to move based on locations of heroes
     *
     * @param local, world view
     *
     * @return direction to move in
     */
    public Direction chooseMove(World local) {
        //This is under the assumption that the parameter local is already local to the monster
        for (int i = local.getRows()-1; i >= 0; i--) {
            for (int j = local.getColumns()-1; j >= 0; j--) {
                if (i != 2 || j != 2) {
                    if (local.isHero(i, j) && local.getEntity(i, j).isAlive()) {
                        if (i == 0) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(-2, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 1) {
                                Direction[] directions = Direction.getDirections(-2, -1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 2) {
                                Direction[] directions = Direction.getDirections(-2, 0);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 3) {
                                Direction[] directions = Direction.getDirections(-2, 1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(-2, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 1) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(-1, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(-1, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 2) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(0, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(0, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 3) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(1, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(1, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        } else if (i == 4) {
                            if (j == 0) {
                                Direction[] directions = Direction.getDirections(2, -2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 1) {
                                Direction[] directions = Direction.getDirections(2, -1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 2) {
                                Direction[] directions = Direction.getDirections(2, 0);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 3) {
                                Direction[] directions = Direction.getDirections(2, 1);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            } else if (j == 4) {
                                Direction[] directions = Direction.getDirections(2, 2);
                                for (Direction direction : directions) {
                                    if (local.canMoveOnTopOf((local.getRows() - 1) / 2 + direction.getRowChange(), (local.getColumns() - 1) / 2 + direction.getColumnChange())) {
                                        return direction;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //Attempt to move southeast if no nearby monsters
        if (local.canMoveOnTopOf(local.getRows()/2 - 1, local.getColumns()/2 - 1)) {
            return Direction.SOUTHEAST;
        } else {
            //Attempt to move random direction if unable to go southeast
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
