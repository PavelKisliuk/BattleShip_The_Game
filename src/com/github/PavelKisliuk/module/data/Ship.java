package com.github.PavelKisliuk.module.data;

public class Ship {
	private int health;
	private int[] row;
	private int[] column;

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
