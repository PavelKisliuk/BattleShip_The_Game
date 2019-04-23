package com.github.PavelKisliuk.model.data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * class of playing field containing certain types of cells and ships {@link Ship}
 */


public class Area implements Serializable {

    public static final int SHIPS_AMOUNT = 10;
    public static final int AREA_SIZE = 10;
    private static final CellsType DEFAULT_CELLS_TYPE = CellsType.EMPTY;

    private CellsType[][] cellsTypes;
    private Ship[] ships;

    /**
     * enum of cell types
     */
    public enum CellsType implements Serializable{
        EMPTY, SHIP, NEIGHBOR, BEATEN, MISS, KILLED, LAST
    }

    /**
     * default constructor to create an Area with empty cells and without any ships
     */
    public Area() {
        cellsTypes = new CellsType[AREA_SIZE][AREA_SIZE];
        for (int i = 0; i < cellsTypes.length; i++) {
            for (int j = 0; j < cellsTypes[0].length; j++) {
                cellsTypes[i][j] = DEFAULT_CELLS_TYPE;
            }
        }
        ships = new Ship[0];
    }

    public Area(int length, int width) {
        cellsTypes = new CellsType[length][width];
        for (int i = 0; i < cellsTypes.length; i++) {
            for (int j = 0; j < cellsTypes[0].length; j++) {
                cellsTypes[i][j] = DEFAULT_CELLS_TYPE;
            }
        }
    }

    public Area(Ship[] ships) {
        cellsTypes = new CellsType[AREA_SIZE][AREA_SIZE];
        for (int i = 0; i < cellsTypes.length; i++) {
            for (int j = 0; j < cellsTypes[0].length; j++) {
                cellsTypes[i][j] = DEFAULT_CELLS_TYPE;
            }
        }
        this.ships = ships;
        for (Ship ship : ships) {

            for (int j = 0; j < ship.getHealth(); j++) {
                this.cellsTypes[ship.getRow()[j]][ship.getColumn()[j]] = CellsType.SHIP;
            }
        }
    }

    public int length() {
        return cellsTypes.length;
    }

    public int width() {
        if (cellsTypes.length == 0) {
            return 0;
        }
        return cellsTypes[0].length;
    }

    public CellsType getCell(int i, int j) {
        return cellsTypes[i][j];
    }

    public void setCell(int i, int j, CellsType cellsType) {
        cellsTypes[i][j] = cellsType;
    }

    public CellsType[][] getCellsTypes() {
        return cellsTypes;
    }

    public void setCellsTypes(CellsType[][] cellsTypes) {
        this.cellsTypes = cellsTypes;
    }

    /**
     * method to set up an area
     * @param cellsTypesArea this is a two dim array with cells
     * @param ships this is an array of ships
     */
    public void setArea(CellsType[][] cellsTypesArea, Ship[] ships) {
        this.cellsTypes = cellsTypesArea;
        this.ships = ships;
    }

    /**
     * method to set up an area according to array with ships
     * @param ships
     */
    public void setArea(Ship[] ships) {
        for (Ship ship : ships) {

            for (int j = 0; j < ship.getHealth(); j++) {
                this.cellsTypes[ship.getRow()[j]][ship.getColumn()[j]] = CellsType.SHIP;
            }
        }
    }

    public Ship[] getShips() {
        return ships;
    }

    /**
     * method to set up ships from array on area field
     * @param ships array of ships to set
     */
    public void setShips(Ship[] ships) {
        this.ships = ships;
        for (Ship ship : ships) {

            for (int j = 0; j < ship.getHealth(); j++) {
                if(this.cellsTypes[ship.getRow()[j]][ship.getColumn()[j]] == CellsType.EMPTY) {
                    this.cellsTypes[ship.getRow()[j]][ship.getColumn()[j]] = CellsType.SHIP;
                }
            }
        }
    }

    /**
     * method to add one ship to playing field
     * @param ship
     */
    public void addShip(Ship ship) {
        for (int i = 0; i < ship.getHealth(); i++) {
            this.cellsTypes[ship.getRow()[i]][ship.getColumn()[i]] = CellsType.SHIP;
        }
        ships = Arrays.copyOf(ships, ships.length + 1);
        ships[ships.length - 1] = ship;
    }

    public void copy(Area area) {
        this.cellsTypes = area.cellsTypes;
        this.ships = area.ships;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < cellsTypes.length; i++) {
            for (int j = 0; j < cellsTypes[0].length; j++) {
                if (cellsTypes[i][j] == CellsType.EMPTY) {
                    str.append(". ");
                } else if (cellsTypes[i][j] == CellsType.SHIP) {
                    str.append("* ");
                } else if (cellsTypes[i][j] == CellsType.NEIGHBOR) {
                    str.append("/ ");
                } else if (cellsTypes[i][j] == CellsType.BEATEN){
                    str.append("b ");
                } else if (cellsTypes[i][j] == CellsType.KILLED) {
                    str.append("k ");
                } else if (cellsTypes[i][j] == CellsType.LAST) {
                    str.append("L ");
                } else {
                    str.append("@ ");
                }
            }
            str.append("\n");
        }
        str.append("\n\n");
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Arrays.equals(cellsTypes, area.cellsTypes) &&
                Arrays.equals(ships, area.ships);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(cellsTypes);
        result = 31 * result + Arrays.hashCode(ships);
        return result;
    }
}