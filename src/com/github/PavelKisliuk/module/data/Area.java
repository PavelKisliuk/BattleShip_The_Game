package com.github.PavelKisliuk.module.data;

import static com.github.PavelKisliuk.module.data.Area.CellsType.*;

public class Area {
	public static final int SHIPS_AMOUNT = 10;
	public static  int AREA_SIZE = 10;
	private static final CellsType DEFAULT_CELLS_TYPE = EMPTY;

	private CellsType[][] area;
	private Ship[] ships;


	public enum CellsType {
		EMPTY, SHIP, NEIGHBOR, BEATEN, MISS
	}

	/**
	 * Default constructor for test
	 */
	public Area() {
		area = new CellsType[AREA_SIZE][AREA_SIZE];
		for (int i = 0; i < area.length; i++) {
			for (int j = 0; j < area[0].length; j++) {
				area[i][j] = DEFAULT_CELLS_TYPE;
			}
		}
		ships = new Ship[]{new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
								new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
								new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
								new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
								new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
								new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
								new Ship(1, new int[]{0}, new int[]{1}),
								new Ship(1, new int[]{2}, new int[]{4}),
								new Ship(1, new int[]{4}, new int[]{8}),
								new Ship(1, new int[]{8}, new int[]{8})};
	}

	public Area(int length, int width) {
		area = new CellsType[length][width];
		for (int i = 0; i < area.length; i++) {
			for (int j = 0; j < area[0].length; j++) {
				area[i][j] = DEFAULT_CELLS_TYPE;
			}
		}
	}

	public int length() {
		return area.length;
	}

	public int width() {
		if (area.length == 0) {
			return 0;
		}
		return area[0].length;
	}

	public CellsType getCell(int i, int j) {
		return area[i][j];
	}

	public void setCell(int i, int j, CellsType cellsType) {
		area[i][j] = cellsType;
	}

	public CellsType[][] getArea() {
		return area;
	}

	public void setArea(CellsType[][] area) {
		this.area = area;
	}

	public void setArea(Ship[] ships) {
		for (Ship ship : ships) {

			for (int j = 0; j < ship.getHealth(); j++) {
				this.area[ship.getRow()[j]][ship.getColumn()[j]] = SHIP;
				//setCell(ship.getRow()[j], ship.getColumn()[j], SHIP);
			}
		}
	}

	public Ship[] getShips() {
		return ships;
	}

	public void setShips(Ship[] ships) {
		this.ships = ships;
	}

	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < area.length; i++) {
			for(int j = 0; j < area[0].length; j++) {
				if(area[i][j] == CellsType.EMPTY) {
					str.append(". ");
				} else {
					str.append("* ");
				}
			}
			str.append("\n");
		}
		str.append("\n\n");
		return str.toString();
	}
}