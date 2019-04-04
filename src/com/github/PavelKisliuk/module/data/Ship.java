package com.github.PavelKisliuk.module.data;

public class Ship {
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
		this.health = health;
		this.row = new int[health];
		this.column = new  int[health];
		for(int i = 0; i < health; i++) {
			this.row[i] = row[i];
			this.column[i] = column[i];
		}
	}
}
