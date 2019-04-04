package com.github.PavelKisliuk.module.data.area;

import com.github.PavelKisliuk.module.data.ships.Ship;

public class Area {
	public static final int SHIPS_AMOUNT = 10;

	private CellsType[][] area;
	private Ship[] ships;


	public enum CellsType {
		EMPTY, SHIP, NEIGHBOR, BEATEN, MISS
	}


	public Area() {

	}
}