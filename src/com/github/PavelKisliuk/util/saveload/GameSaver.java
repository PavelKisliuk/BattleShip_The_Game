package com.github.PavelKisliuk.util.saveload;

import com.github.PavelKisliuk.model.data.Area;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * @author dzmitryplatonov on 2019-04-14.
 * @version 0.0.1
 */
public class GameSaver {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(GameSaver.class);
    }


    public void saveGame(String path, Area playerArea, Area opponentArea) {
        try {
            Files.write(Paths.get(path), playerArea.toString().getBytes());
            Files.write(Paths.get(path), Arrays.toString(playerArea.getShips()).getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(path), "\n".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(path), opponentArea.toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(path), Arrays.toString(opponentArea.getShips()).getBytes(), StandardOpenOption.APPEND);
            logger.debug(playerArea + " and " + opponentArea + " saved successfully");
        } catch (IOException ex) {
            logger.info(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
