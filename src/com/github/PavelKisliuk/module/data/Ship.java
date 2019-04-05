package com.github.PavelKisliuk.module.data;

import com.github.PavelKisliuk.util.Checker;
import com.github.PavelKisliuk.util.ShipHealthException;
import org.apache.log4j.Logger;

public class Ship {

	private static final Logger logger;

	static {
		logger = Logger.getLogger(Ship.class);
	}


	private int health;
	private int[] row;
	private int[] column;

	/**
	 * Parameterized constructor for creating {@param Ship}
	 * @param health is points of health of ship
	 *               if {@param health} == 0 Ship is dead
	 * @param row is array contain places in matrix by row where reside Ship
	 * @param column is array contain places in matrix by column where reside Ship
	 */
	public Ship(int health, int[] row, int[] column)
	{
		if (health <= 0 || row.length <= 0 || column.length <= 0) {
			throw new ShipInitializationException("Not correct input data");
		}
		this.health = health;
		this.row = new int[health];
		this.column = new  int[health];
		for(int i = 0; i < health; i++) {
			this.row[i] = row[i];
			this.column[i] = column[i];
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health <= 0 || health > Area.AREA_SIZE ) {
			throw new ShipInitializationException("Illegal health parameter");
		}
		this.health = health;
	}

	public int[] getRow() {
		return row;
	}

	public void setRow(int[] row) {
		if (row == null || row.length <= 0 || row.length > Area.AREA_SIZE) {
			throw new ShipInitializationException("Illegal row parameter");
		}
		this.row = row;
	}

	public int[] getColumn() {
		return column;
	}

	public void setColumn(int[] column) {
		if (column == null || column.length <= 0 || column.length > Area.AREA_SIZE) {
			throw new ShipInitializationException("Illegal column parameter");
		}
		this.column = column;
	}
}
