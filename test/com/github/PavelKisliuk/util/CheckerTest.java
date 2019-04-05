package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.module.data.Area;
import com.github.PavelKisliuk.module.data.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

	@Test
	void isRightArrangement() {
		Area area = new Area(new Ship[]{new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
                new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
                new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
                new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
                new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
                new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
                new Ship(1, new int[]{0}, new int[]{1}),
                new Ship(1, new int[]{2}, new int[]{4}),
                new Ship(1, new int[]{4}, new int[]{8}),
                new Ship(1, new int[]{8}, new int[]{8})});
		assertTrue(Checker.isRightArrangement(area));

		assertFalse(Checker.isRightArrangement(new Area(10,11)));

		assertFalse(Checker.isRightArrangement(null));

		assertFalse(Checker.isRightArrangement(new Area(0, 100)));

		assertFalse(Checker.isRightArrangement(new Area(5,5)));

		assertFalse(Checker.isRightArrangement(new Area(10, 10)));

		Area testArea = new Area(10, 10);
		testArea.setShips(new Ship[]{new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
				new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
				new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
				new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
				new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
				new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
				new Ship(1, new int[]{0}, new int[]{1}),
				new Ship(1, new int[]{2}, new int[]{4}),
				new Ship(1, new int[]{8}, new int[]{8}),
				new Ship(1, new int[]{9}, new int[]{9})
		});
		assertFalse(Checker.isRightArrangement(testArea));

        Area.CellsType[][] matrix = area.getCellsTypes();
        Ship[] ships = area.getShips();
        Area matrixArea = new Area();
        matrixArea.setArea(matrix, ships);
        assertEquals(area, matrixArea);
	}

	@Test
	void testCheckNeighbor() {
		Area area = new Area();
		area.setShips(new Ship[]{new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
				new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
				new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
				new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
				new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
				new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
				new Ship(1, new int[]{0}, new int[]{1}),
				new Ship(1, new int[]{2}, new int[]{4}),
				new Ship(1, new int[]{4}, new int[]{8}),
				new Ship(1, new int[]{8}, new int[]{8})});
		assertTrue(Checker.checkNeighbor(area, area.getShips()[9]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[6]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[7]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[8]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[5]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[4]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[3]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[2]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[1]));
		assertTrue(Checker.checkNeighbor(area, area.getShips()[0]));
	}

	@Test
	void testCheckShips() {
		Area area = new Area();
        area.setShips(new Ship[]{
                new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
                new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
                new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
                new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
                new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
                new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
                new Ship(1, new int[]{0}, new int[]{1}),
                new Ship(1, new int[]{2}, new int[]{4}),
                new Ship(1, new int[]{4}, new int[]{8}),
                new Ship(1, new int[]{8}, new int[]{8})});
		assertTrue(Checker.checkShips(area));
		Area area1 = new Area(10,10);
		area1.setShips(new Ship[]{
				new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
				new Ship(3, new int[]{2, 3, 4}, new int[]{2, 2, 2}),
				new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
				new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
				new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
				new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
				new Ship(1, new int[]{0}, new int[]{1}),
				new Ship(1, new int[]{2}, new int[]{4}),
				new Ship(1, new int[]{8}, new int[]{8})});
		assertFalse(Checker.checkShips(area1));
		Area area2 = new Area(10,10);
		area2.setShips(new Ship[]{});
		assertFalse(Checker.checkShips(area2));
		Area area3 = new Area(10,10);
		area3.setShips(new Ship[]{
				new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}),
				new Ship(4, new int[]{2, 3, 4, 5}, new int[]{2, 2, 2, 2}),
				new Ship(3, new int[]{6, 7, 8}, new int[]{4, 4, 4}),
				new Ship(2, new int[]{0, 0}, new int[]{7, 8}),
				new Ship(2, new int[]{2, 2}, new int[]{6, 7}),
				new Ship(2, new int[]{6, 6}, new int[]{7, 8}),
				new Ship(1, new int[]{0}, new int[]{1}),
				new Ship(1, new int[]{2}, new int[]{4}),
				new Ship(1, new int[]{4}, new int[]{8}),
				new Ship(1, new int[]{8}, new int[]{8})});
		assertFalse(Checker.checkShips(area3));
	}
}