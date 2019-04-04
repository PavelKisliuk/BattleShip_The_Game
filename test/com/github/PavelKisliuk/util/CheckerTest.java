package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.module.data.Area;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

	@Test
	void isRightArrangement() {
		Area area = new Area();
		System.out.println(area);
		assertTrue(Checker.isRightArrangement(area));
	}
}