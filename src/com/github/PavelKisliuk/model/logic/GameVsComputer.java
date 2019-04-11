package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.util.RandomAreaArranger;

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
}
