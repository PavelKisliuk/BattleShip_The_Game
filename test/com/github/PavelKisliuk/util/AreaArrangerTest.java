package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.AreaArrangementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-04-05.
 * @version 0.0.1
 */
public class AreaArrangerTest {


    @Test
    public void testArrangeShips() {
        AreaArranger arranger = AreaArranger.INSTANCE;
        Area actualArea = new Area();

        arranger.arrangeShip(actualArea, new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}));
        arranger.arrangeFewShips(actualArea, new Ship[]{
                new Ship(3, new int[]{1,1,1}, new int[]{2,3,4}),
                new Ship(1, new int[]{9}, new int[]{9})
        });
        arranger.arrangeShip(actualArea, new Ship(1,new int[]{9}, new int[]{0}));
        arranger.arrangeShip(actualArea, new Ship(1,new int[]{9}, new int[]{0}));
        arranger.arrangeShip(actualArea, new Ship(1,new int[]{8}, new int[]{0}));
        arranger.arrangeShip(actualArea, new Ship(4,new int[]{9,9,9,9}, new int[]{5,6,7,8}));
        arranger.arrangeShip(actualArea, new Ship(4,new int[]{7,7,7,7}, new int[]{5,6,7,8}));


        System.out.println(actualArea);



        assertThrows(AreaArrangementException.class, () -> arranger.arrangeShip(null, null));
        assertThrows(AreaArrangementException.class, () -> arranger.arrangeFewShips(null, null));

        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(0,0));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(0,1));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(0,2));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(0,3));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(0,4));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(0,5));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(0,6));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(0,7));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(0,8));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(0,9));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(1,0));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(1,1));
        assertEquals(Area.CellsType.SHIP, actualArea.getCell(1,2));
        assertEquals(Area.CellsType.SHIP, actualArea.getCell(1,3));
        assertEquals(Area.CellsType.SHIP, actualArea.getCell(1,4));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(1,5));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(1,6));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(1,7));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(1,8));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(1,9));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(2,0));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(2,1));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(2,2));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(2,3));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(2,4));
        assertEquals(Area.CellsType.NEIGHBOR, actualArea.getCell(2,5));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(2,6));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(2,7));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(2,8));
        assertEquals(Area.CellsType.EMPTY, actualArea.getCell(2,9));




    }
}
