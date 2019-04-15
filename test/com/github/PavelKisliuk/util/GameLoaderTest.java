package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.util.saveload.GameLoader;
import org.junit.jupiter.api.Test;

/**
 * @author dzmitryplatonov on 2019-04-14.
 * @version 0.0.1
 */
public class GameLoaderTest {

    GameLoader gameLoader = new GameLoader();

    @Test
    void testReadLines() {

        gameLoader.loadPlayerArea("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame.txt");
        gameLoader.loadOpponentArea("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame.txt");
//        List<String> list;
//        list = gameLoader.readLines("/Users/dzmitryplatonov/Documents/GitHub/BattleShip_The_Game/savegame.txt");
//        for (String s: list) {
//            System.out.println(s);
//        }
//
//        Area area = gameLoader.serializeArea(list.subList(0,10));
//
//        Area opArea = gameLoader.serializeArea(list.subList(13, 23));
//
//        gameLoader.serializeShips(list.get(12));
//        gameLoader.serializeShips(list.get(25));
    }
}
