package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.ShipInitializationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public class InitializerTest {

    @Test
    public void testInit() {
        Ship ship = Creator.INSTANCE.createShip();
        Initializer.INSTANCE.init(ship, 4, new int[]{1, 2, 3, 4}, new int[]{1, 1, 1, 1});
        Ship ship1 = Creator.INSTANCE.createShip();
        Initializer.INSTANCE.init(ship1, 1, new int[]{8}, new int[]{8});

        Area area = Creator.INSTANCE.createArea();
        AreaArranger.INSTANCE.arrangeShip(area,ship);
        AreaArranger.INSTANCE.arrangeShip(area,ship1);

        System.out.println(area);

        assertEquals(Area.CellsType.SHIP,area.getCell(1,1));
        assertEquals(Area.CellsType.SHIP,area.getCell(2,1));
        assertEquals(Area.CellsType.SHIP,area.getCell(3,1));
        assertEquals(Area.CellsType.SHIP,area.getCell(4,1));

        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 1, new int[]{1, 2, 3, 4}, new int[]{1, 1, 1, 1}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 2, new int[]{1, 2}, new int[]{1, 2}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 2, new int[]{1, 3}, new int[]{1, 1}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 2, new int[]{1, 1}, new int[]{1, 1}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 4, new int[]{1, 2, 4, 3}, new int[]{1, 1, 1, 1}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 5, new int[]{1, 2, 3, 4, 5}, new int[]{1, 1, 1, 1, 1}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(ship, 0, new int[]{}, new int[]{}));
        assertThrows(ShipInitializationException.class,
                () -> Initializer.INSTANCE.init(null, 2, new int[]{1, 2}, new int[]{1, 1}));



    }
}
