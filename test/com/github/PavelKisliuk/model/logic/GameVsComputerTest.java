package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.util.AreaArranger;
import com.github.PavelKisliuk.util.Creator;
import com.github.PavelKisliuk.util.RandomAreaArranger;
import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-04-11.
 * @version 0.0.1
 */
public class GameVsComputerTest {

    Creator creator = Creator.INSTANCE;
    AreaArranger arranger = AreaArranger.INSTANCE;
    GameVsComputer gameVsComputer = new GameVsComputer();
    RandomAreaArranger randomAreaArranger = RandomAreaArranger.INSTANCE;

    @Test
    void testOpponentGo() {
        Area area = creator.createArea();
        int counterGoes = 1;
//        Ship[] ships = new Ship[]{
//                new Ship(1, new int[]{0}, new int[]{0}),
//                new Ship(1, new int[]{2}, new int[]{0}),
//                new Ship(1, new int[]{4}, new int[]{0}),
//                new Ship(1, new int[]{6}, new int[]{0}),
//                new Ship(2, new int[]{9, 8}, new int[]{9, 9}),
//                new Ship(2, new int[]{0, 1}, new int[]{9, 9}),
//                new Ship(2, new int[]{3, 4}, new int[]{9, 9}),
//                new Ship(3, new int[]{0, 1, 2}, new int[]{5, 5, 5}),
//                new Ship(3, new int[]{4, 5, 6}, new int[]{5, 5, 5}),
//                new Ship(4, new int[]{0, 1, 2, 3}, new int[]{3, 3, 3, 3}),
//        };
//        arranger.arrangeFewShips(area, ships);
//        arranger.changeCelltype(area, NEIGHBOR, EMPTY);

        randomAreaArranger.arrangeRandomArea(area);

        while (!gameVsComputer.isWin(area)) {
            gameVsComputer.opponentGo(area);
            System.out.println(area);
            counterGoes++;
        }
        System.out.println(counterGoes);

    }
}
