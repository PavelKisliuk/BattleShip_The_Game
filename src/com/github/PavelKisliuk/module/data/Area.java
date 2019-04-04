package com.github.PavelKisliuk.module.data;

import com.github.PavelKisliuk.module.data.Ship;

public class Area {
	public static final int SHIPS_AMOUNT = 10;
	public static final int AREA_SIZE = 10;

	private CellsType[][] area;
	private Ship[] shipsGroup;


	public enum CellsType {
		EMPTY, SHIP, NEIGHBOR, BEATEN, MISS
	}

	/**
	 * Default constructor for test
	 */
	public Area() {
		area = new CellsType[][]{{CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.SHIP, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.SHIP, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.SHIP, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY},
								{CellsType.EMPTY, CellsType.EMPTY, CellsType.SHIP, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY, CellsType.EMPTY}};
		shipsGroup = new Ship[]{new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
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

	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < AREA_SIZE; i++) {
			for(int j = 0; j < AREA_SIZE; j++) {
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