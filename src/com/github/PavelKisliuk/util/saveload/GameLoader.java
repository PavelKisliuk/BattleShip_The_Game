package com.github.PavelKisliuk.util.saveload;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.Creator;
import com.github.PavelKisliuk.util.Initializer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-04-14.
 * @version 0.0.1
 */
public class GameLoader {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(GameLoader.class);
    }

    private static final int PLAYER_AREA_STARTING_ROW_IN_FILE = 0;
    private static final int PLAYER_AREA_FINISHING_ROW_IN_FILE = 10;
    private static final int OPPONENT_AREA_STARTING_ROW_IN_FILE = 13;
    private static final int OPPONENT_AREA_FINISHING_ROW_IN_FILE = 23;
    private static final int PLAYER_AREA_SHIPS_ROW_INDEX = 12;
    private static final int OPPONENT_AREA_SHIPS_ROW_INDEX = 25;
    private static final int ASCII_SHIFT = 48;

    Creator creator = Creator.INSTANCE;
    Initializer initializer = Initializer.INSTANCE;


    public void loadGame(String path) {

    }

    public Area loadPlayerArea(String path) {
        List<String> stringsFromFile = readLines(path);
        logger.debug("Loading player area");
        Area area = serializeArea(stringsFromFile.subList(PLAYER_AREA_STARTING_ROW_IN_FILE, PLAYER_AREA_FINISHING_ROW_IN_FILE));
        area.setShips(serializeShips(stringsFromFile.get(PLAYER_AREA_SHIPS_ROW_INDEX)));
        return area;
    }

    public Area loadOpponentArea(String path) {
        List<String> stringsFromFile = readLines(path);
        logger.debug("Loading opponent Area");
        Area area = serializeArea(stringsFromFile.subList(OPPONENT_AREA_STARTING_ROW_IN_FILE, OPPONENT_AREA_FINISHING_ROW_IN_FILE));
        area.setShips(serializeShips(stringsFromFile.get(OPPONENT_AREA_SHIPS_ROW_INDEX)));
        return area;
    }

    private List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();

        try {
            logger.debug("Creating list of lines from file " + path);
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException ex) {
            logger.info("IOException " + ex.getMessage());
        }
        return lines;
    }


    private Area serializeArea(List<String> list) {
        Area resultArea = new Area();

        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replaceAll(" ", ""));
            char[] chars = list.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                resultArea.setCell(i, j, serializeChar(chars[j]));
            }
        }

        logger.debug("\n" + resultArea + " was successfully serialized from sublist");

        return resultArea;
    }

    private Ship[] serializeShips(String string) {

        Ship[] ships = new Ship[10];
        int shipsCounter = 0;
        List<String> strings = Arrays.asList(string.split(", "));

        for (String s : strings) {
            s = s.replaceAll("\\D+","");

            Ship ship = creator.createShip();
            if (s.length() == 8) {

                initializer.init(ship,4, new int[]{s.charAt(0) - ASCII_SHIFT, s.charAt(2) - ASCII_SHIFT,
                                s.charAt(4) - ASCII_SHIFT, s.charAt(6) - ASCII_SHIFT},
                        new int[]{s.charAt(1) - ASCII_SHIFT, s.charAt(3) - ASCII_SHIFT, s.charAt(5) - ASCII_SHIFT,
                                s.charAt(7) - ASCII_SHIFT});
            }
            if (s.length() == 6) {

                initializer.init(ship, 3, new int[]{s.charAt(0) - ASCII_SHIFT, s.charAt(2) - ASCII_SHIFT,
                                s.charAt(4) - ASCII_SHIFT},
                        new int[]{s.charAt(1) - ASCII_SHIFT, s.charAt(3) - ASCII_SHIFT, s.charAt(5) - ASCII_SHIFT});
            }
            if (s.length() == 4) {
                initializer.init(ship, 2, new int[]{s.charAt(0) - ASCII_SHIFT, s.charAt(2) - ASCII_SHIFT},
                        new int[]{s.charAt(1) - ASCII_SHIFT, s.charAt(3) - ASCII_SHIFT});
            }
            if (s.length() == 2) {
                initializer.init(ship, new int[]{s.charAt(0) - ASCII_SHIFT}, new int[]{s.charAt(1) - ASCII_SHIFT});
            }
            ships[shipsCounter++] = ship;
        }

        return ships;
    }


    private Area.CellsType serializeChar(Character c) {
        Area.CellsType cellsType = Area.CellsType.EMPTY;

        switch (c) {
            case '.':
                cellsType = Area.CellsType.EMPTY;
                break;
            case '*':
                cellsType = Area.CellsType.SHIP;
                break;
            case '/':
                cellsType = Area.CellsType.NEIGHBOR;
                break;
            case 'b':
                cellsType = Area.CellsType.BEATEN;
                break;
            case 'k':
                cellsType = Area.CellsType.KILLED;
                break;
            case 'L':
                cellsType = Area.CellsType.LAST;
                break;
            case '@':
                cellsType = Area.CellsType.MISS;
                break;
        }

        return cellsType;
    }
}
