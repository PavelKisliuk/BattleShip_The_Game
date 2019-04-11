package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.util.RandomAreaArranger;

import java.security.SecureRandom;

public class GameVsComputer extends AbstractGame {
	@Override
	public Area getOpponentArea() {
		Area area = new Area();
		boolean isRightArea = RandomAreaArranger.INSTANCE.arrangeRandomArea(area);
		while(!isRightArea) {
			isRightArea = RandomAreaArranger.INSTANCE.arrangeRandomArea(area);
		}
		return area;
	}

	@Override
	public boolean playerGoFirst() {
		SecureRandom r = new SecureRandom();
		return r.nextBoolean();
	}

	@Override
	public boolean opponentGo(Area area) {
		return false;
	}
}
