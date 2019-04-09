package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;

/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public enum  Creator {

    INSTANCE;

    public Ship createShip() {
        return new Ship();
    }

    public Area createArea() {
        return new Area();
    }

    public int[] getIntArray(Integer[] ar) {
        int[] newAr = new int[ar.length];
        for(int i = 0; i < ar.length; i++) {
            newAr[i] = ar[i];
        }
        return newAr;
    }
}
