package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.AreaArrangementException;
import org.apache.log4j.Logger;

/**
 * class arranges playing objects on playing field
 * implements pattern singleton
 * @author dzmitryplatonov on 2019-04-05.
 * @version 0.0.1
 */
public enum AreaArranger {

	INSTANCE;

	private static final Logger logger;

	static {
		logger = Logger.getLogger(AreaArranger.class);
	}

	/**
	 * method to arrange array of ship's on playing area
	 * @param area on which the ships will be placed
	 * @param ships that need to be placed on the field
	 */
	public void arrangeFewShips(Area area, Ship[] ships) {
		if (area == null || ships == null) {
			throw new AreaArrangementException("Parameter is null");
		}
		for (Ship ship : ships) {
			if (isSuited(area, ship)) {
				arrangeShip(area, ship);
				arrangeNeighbors(area, Area.CellsType.SHIP);
			} else {
				logger.debug(ship + " not arranged");
			}
		}
		logger.debug(area.getShips().length + " ships arranged successfully on \n" + area);
	}

	/**
	 * method to arrange ship on playing area
	 * @param area on which the ship will be placed
	 * @param ship that need to be placed on the field
	 */
	public void arrangeShip(Area area, Ship ship) {
		if (area == null || ship == null) {
			throw new AreaArrangementException("Parameter is null");
		}
		if (isSuited(area, ship)) {
			area.addShip(ship);
			logger.debug(ship + " arranged successfully");
		}
		arrangeNeighbors(area, Area.CellsType.SHIP);
	}

	/**
	 * method to change some kind of cells on the field to another type
	 * @param area on which changes will be made
	 * @param cellsTypeOut type of cell that we need to remove
	 * @param cellsTypeIn type of cell that we need to insert
	 */
	public void changeCelltype(Area area, Area.CellsType cellsTypeOut, Area.CellsType cellsTypeIn) {
		for (int i = 0; i < area.length(); i++) {
			for (int j = 0; j < area.width(); j++) {
				if (area.getCell(i, j) == cellsTypeOut) {
					area.setCell(i, j, cellsTypeIn);
					//logger.debug("(" + i + ", " + j + ")" + " changed to " + cellsTypeIn);
				}
			}
		}
	}

	/**
	 * this method checks if it is possible to insert ship into the playing field according with game rules
	 * @param area on which we need to check possibility of insertion
	 * @param ship that we need to place
	 * @return true if it is possible to place ship, and false if the rules will be broken during placing
	 */
	private boolean isSuited(Area area, Ship ship) {
		boolean result = true;
		for (int i = 0; i < ship.getHealth(); i++) {
			if (area.getCell(ship.getRow()[i], ship.getColumn()[i]) == Area.CellsType.EMPTY) {
				result = true;
			} else {
				result = false;
				logger.debug(ship + " not arranged, cell [" + ship.getRow()[i] + ", " + ship.getColumn()[i]
						+ "] isn't empty (" + area.getCell(ship.getRow()[i], ship.getColumn()[i]).toString() + ")");
				break;
			}
		}
		return result;
	}

	/**
	 * this method places {@link Area.CellsType}NEIGHBOR's on field
	 * @param area on which changes should be placed
	 * @param contactCellsType cell type, in case of contact with which it is necessary to put NEIGHBOR
	 */
	public void arrangeNeighbors(Area area, Area.CellsType contactCellsType) {
		for (int i = 0; i < area.length(); i++) {
			for (int j = 0; j < area.width(); j++) {
				if (isCellContactWithCellType(area, i, j, contactCellsType) && area.getCell(i, j) != Area.CellsType.SHIP) {
					area.setCell(i, j, Area.CellsType.NEIGHBOR);
				}
			}
		}
	}

	/**
	 * method to check if cell is contacted with some kind of {@link Area.CellsType}
	 * @param area on which we should make a check
	 * @param i "X" coordinate of checking cell
	 * @param j "Y" coordinate of checking cell
	 * @param cellsType type of the cell contact with which you need to check
	 * @return true or false result of the checking
	 */
	public boolean isCellContactWithCellType(Area area, int i, int j, Area.CellsType cellsType) {
		boolean result = false;
		if (i > 0 && i < area.length() - 1 && j > 0 && j < area.width() - 1
				&& (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (i == 0 && j > 0 && j < area.width() - 1 && ((checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType)))) {
			result = true;
		}
		if (i == area.length() - 1 && j > 0 && j < area.width() - 1 && (checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i > 0 && i < area.length() - 1 && (checkSouth(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i > 0 && i < area.length() - 1 && (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i == 0 && (checkSouth(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i == area.length() - 1 && (checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i == 0 && (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i == area.length() - 1 && (checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType))) {
			result = true;
		}
		return result;
	}

	private boolean checkSouth(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j) == cellsType;
	}

	private boolean checkSouthWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j - 1) == cellsType;
	}

	private boolean checkWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i, j - 1) == cellsType;
	}

	private boolean checkNorthWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j - 1) == cellsType;
	}

	private boolean checkNorth(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j) == cellsType;
	}

	private boolean checkNorthEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j + 1) == cellsType;
	}

	private boolean checkEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i, j + 1) == cellsType;
	}

	private boolean checkSouthEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j + 1) == cellsType;
	}


}
