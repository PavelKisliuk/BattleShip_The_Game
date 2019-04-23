package com.github.PavelKisliuk.util.saveload;

import com.github.PavelKisliuk.model.data.Area;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(playerArea);
            oos.writeObject(opponentArea);
        } catch (IOException e) {
            logger.debug(playerArea + " and " + opponentArea + " saved successfully");
            e.printStackTrace();
        }
    }
}
