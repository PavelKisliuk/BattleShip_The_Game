package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;

public abstract class AbstractGame {
	public enum Way {
		NORTH, EAST, SOUTH, WEST
	}

	public abstract Area getOpponentArea();
	public abstract boolean playerGoFirst();
	public abstract boolean opponentGo(Area area);

	public void playerGo(Area area, int row, int column) {

	}
}
