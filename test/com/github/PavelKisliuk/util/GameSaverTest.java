package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.util.saveload.GameSaver;
import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-04-14.
 * @version 0.0.1
 */
public class GameSaverTest {

    GameSaver gameSaver = new GameSaver();
    RandomAreaArranger randomAreaArranger = RandomAreaArranger.INSTANCE;
    Creator creator = Creator.INSTANCE;

    @Test
    void testSaveGame() {
        Area playerArea = creator.createArea();
        Area opponentArea = creator.createArea();
        randomAreaArranger.arrangeRandomArea(playerArea);
        randomAreaArranger.arrangeRandomArea(opponentArea);

        gameSaver.saveGame("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame.txt", playerArea, opponentArea);
    }
}
