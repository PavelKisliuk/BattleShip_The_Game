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

        randomAreaArranger.arrangeRandomArea(area);

        while (!gameVsComputer.isWin(area)) {
            gameVsComputer.opponentGo(area);
            System.out.println(area);
            counterGoes++;
        }
        System.out.println(counterGoes);

    }

    @Test
    void testSaveAndLoad() {
        Area area = creator.createArea();
        Area area1 = creator.createArea();
        randomAreaArranger.arrangeRandomArea(area);
        System.out.println(area);
        gameVsComputer.saveGame("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame", area, area1);

        gameVsComputer.loadGame("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame", area, area1);
        System.out.println(area);
    }
}
