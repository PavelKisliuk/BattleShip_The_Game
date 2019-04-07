package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import org.junit.jupiter.api.Test;


/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public class RandomAreaArrangerTest {

    private RandomAreaArranger randomAreaArranger = RandomAreaArranger.INSTANCE;

    @Test
    public void testArrangeRandomArea() {
        Area area = Creator.INSTANCE.createArea();

        randomAreaArranger.arrangeRandomArea(area);
    }
}
