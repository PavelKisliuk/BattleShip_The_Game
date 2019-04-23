package com.github.PavelKisliuk.util.saveload;

import com.github.PavelKisliuk.model.data.Area;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author dzmitryplatonov on 2019-04-14.
 * @version 0.0.1
 */
public class GameLoader {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(GameLoader.class);
    }

    private Area[] loadGame(String path) {
       Area[] areas = new Area[2];
       try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
           for (int i = 0; i < areas.length; i++) {
               areas[i] = (Area) ois.readObject();
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       return areas;
    }

    public Area loadPlayerArea(String path) {
        logger.debug("loading player area");
        return loadGame(path)[0];
    }

    public Area loadOpponentArea(String path) {
        logger.debug("loading opponent area");
        return loadGame(path)[1];
    }
}
