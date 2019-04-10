package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.AreaArrangementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.PavelKisliuk.model.data.Area.CellsType.*;

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

        assertThrows(AreaArrangementException.class, () -> arranger.arrangeShip(null, null));
        assertThrows(AreaArrangementException.class, () -> arranger.arrangeFewShips(null, null));

        Area area = Creator.INSTANCE.createArea();
        Ship[] ships = new Ship[]{
                new Ship(1, new int[]{0}, new int[]{0}),
                new Ship(1, new int[]{2}, new int[]{0}),
                new Ship(1, new int[]{4}, new int[]{0}),
                new Ship(1, new int[]{6}, new int[]{0}),
                new Ship(2, new int[]{9,8}, new int[]{9,9}),
                new Ship(2, new int[]{0,1}, new int[]{9,9}),
                new Ship(2, new int[]{3,4}, new int[]{9,9}),
                new Ship(3, new int[]{0,1,2}, new int[]{5,5,5}),
                new Ship(3, new int[]{4,5,6}, new int[]{5,5,5}),
                new Ship(4, new int[]{0,1,2,3}, new int[]{3,3,3,3}),
        };
        arranger.arrangeFewShips(area, ships);
        arranger.changeCelltype(area, NEIGHBOR, EMPTY);
        System.out.println(area);

        Area testArea = Creator.INSTANCE.createArea();
        Ship[] shipsBad = new Ship[]{
                new Ship(1, new int[]{0}, new int[]{0}),
                new Ship(1, new int[]{2}, new int[]{0}),
                new Ship(1, new int[]{4}, new int[]{0}),
                new Ship(1, new int[]{6}, new int[]{0}),
                new Ship(2, new int[]{9,8}, new int[]{9,9}),
                new Ship(2, new int[]{0,1}, new int[]{9,9}),
                new Ship(2, new int[]{3,4}, new int[]{9,9}),
                new Ship(3, new int[]{0,1,2}, new int[]{5,5,5}),
                new Ship(3, new int[]{4,5,6}, new int[]{5,5,5}),
                new Ship(4, new int[]{0,1,2,3}, new int[]{0,0,0,0}),
        };
        arranger.arrangeFewShips(testArea, shipsBad);
        System.out.println(testArea);

    }

    private static Stream<Arguments> addData() {
        return Stream.of(
                Arguments.of(0,0,EMPTY),
                Arguments.of(0,1,NEIGHBOR),
                Arguments.of(0,2,NEIGHBOR),
                Arguments.of(0,3,NEIGHBOR),
                Arguments.of(0,4,NEIGHBOR),
                Arguments.of(0,5,NEIGHBOR),
                Arguments.of(0,6,EMPTY),
                Arguments.of(0,7,EMPTY),
                Arguments.of(0,8,EMPTY),
                Arguments.of(0,9,EMPTY),
                Arguments.of(1,0,EMPTY),
                Arguments.of(1,1,NEIGHBOR),
                Arguments.of(1,2,SHIP),
                Arguments.of(1,3,SHIP),
                Arguments.of(1,4,SHIP),
                Arguments.of(1,5,NEIGHBOR),
                Arguments.of(1,6,EMPTY),
                Arguments.of(1,7,EMPTY),
                Arguments.of(1,8,EMPTY),
                Arguments.of(1,9,EMPTY),
                Arguments.of(2,0,EMPTY),
                Arguments.of(2,1,NEIGHBOR),
                Arguments.of(2,2,NEIGHBOR),
                Arguments.of(2,3,NEIGHBOR),
                Arguments.of(2,4,NEIGHBOR),
                Arguments.of(2,5,NEIGHBOR),
                Arguments.of(2,6,EMPTY),
                Arguments.of(2,7,EMPTY),
                Arguments.of(2,8,EMPTY),
                Arguments.of(2,9,EMPTY),
                Arguments.of(3,0,EMPTY),
                Arguments.of(3,1,EMPTY),
                Arguments.of(3,2,EMPTY),
                Arguments.of(3,3,EMPTY),
                Arguments.of(3,4,EMPTY),
                Arguments.of(3,5,EMPTY),
                Arguments.of(3,6,EMPTY),
                Arguments.of(3,7,EMPTY),
                Arguments.of(3,8,EMPTY),
                Arguments.of(3,9,EMPTY),
                Arguments.of(4,0,EMPTY),
                Arguments.of(4,1,EMPTY),
                Arguments.of(4,2,EMPTY),
                Arguments.of(4,3,EMPTY),
                Arguments.of(4,4,EMPTY),
                Arguments.of(4,5,EMPTY),
                Arguments.of(4,6,EMPTY),
                Arguments.of(4,7,EMPTY),
                Arguments.of(4,8,EMPTY),
                Arguments.of(4,9,EMPTY),
                Arguments.of(5,0,EMPTY),
                Arguments.of(5,1,NEIGHBOR),
                Arguments.of(5,2,NEIGHBOR),
                Arguments.of(5,3,NEIGHBOR),
                Arguments.of(5,4,EMPTY),
                Arguments.of(5,5,EMPTY),
                Arguments.of(5,6,EMPTY),
                Arguments.of(5,7,EMPTY),
                Arguments.of(5,8,EMPTY),
                Arguments.of(5,9,EMPTY),
                Arguments.of(6,0,EMPTY),
                Arguments.of(6,1,NEIGHBOR),
                Arguments.of(6,2,SHIP),
                Arguments.of(6,3,NEIGHBOR),
                Arguments.of(6,4,NEIGHBOR),
                Arguments.of(6,5,NEIGHBOR),
                Arguments.of(6,6,NEIGHBOR),
                Arguments.of(6,7,NEIGHBOR),
                Arguments.of(6,8,NEIGHBOR),
                Arguments.of(6,9,NEIGHBOR),
                Arguments.of(7,0,EMPTY),
                Arguments.of(7,1,NEIGHBOR),
                Arguments.of(7,2,SHIP),
                Arguments.of(7,3,NEIGHBOR),
                Arguments.of(7,4,NEIGHBOR),
                Arguments.of(7,5,SHIP),
                Arguments.of(7,6,SHIP),
                Arguments.of(7,7,SHIP),
                Arguments.of(7,8,SHIP),
                Arguments.of(7,9,NEIGHBOR),
                Arguments.of(8,0,NEIGHBOR),
                Arguments.of(8,1,NEIGHBOR),
                Arguments.of(8,2,SHIP),
                Arguments.of(8,3,NEIGHBOR),
                Arguments.of(8,4,NEIGHBOR),
                Arguments.of(8,5,NEIGHBOR),
                Arguments.of(8,6,NEIGHBOR),
                Arguments.of(8,7,NEIGHBOR),
                Arguments.of(8,8,NEIGHBOR),
                Arguments.of(8,9,NEIGHBOR),
                Arguments.of(9,0,SHIP),
                Arguments.of(9,1,NEIGHBOR),
                Arguments.of(9,2,SHIP),
                Arguments.of(9,3,NEIGHBOR),
                Arguments.of(9,4,EMPTY),
                Arguments.of(9,5,EMPTY),
                Arguments.of(9,6,EMPTY),
                Arguments.of(9,7,EMPTY),
                Arguments.of(9,8,NEIGHBOR),
                Arguments.of(9,9,SHIP));
    }

    @ParameterizedTest
    @MethodSource("addData")
    void testArrangeShip(int i, int j, Area.CellsType cellsType) {
        AreaArranger arranger = AreaArranger.INSTANCE;
        Area area = Creator.INSTANCE.createArea();
        arranger.arrangeShip(area, new Ship(4, new int[]{6, 7, 8, 9}, new int[]{2, 2, 2, 2}));
        arranger.arrangeFewShips(area, new Ship[]{
                new Ship(3, new int[]{1,1,1}, new int[]{2,3,4}),
                new Ship(1, new int[]{9}, new int[]{9})
        });
        arranger.arrangeShip(area, new Ship(1,new int[]{9}, new int[]{0}));
        arranger.arrangeShip(area, new Ship(4,new int[]{7,7,7,7}, new int[]{5,6,7,8}));

        assertEquals(cellsType, area.getCell(i, j));
    }
}
