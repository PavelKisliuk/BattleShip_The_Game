package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameVsPlayer extends AbstractGame {
	private Socket client;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public void connect(){
		String hostName = "127.0.0.1";
		int port = 2101;

		try {
			this.client = new Socket(hostName, port);
			this.output = new ObjectOutputStream(this.client.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Area getOpponentArea() {
		Area area = new Area();
		try {
			area = (Area)input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return area;
	}

	@Override
	public boolean playerGoFirst() {
		try {
			return input.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean playerGo(Area area, int row, int column) {
		try {
			output.write(row);
			output.write(column);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.playerGo(area, row, column);
	}

	@Override
	public boolean opponentGo(Area area) {
		int row = 0;
		int column = 0;
		try {
			row = input.readInt();
			column = input.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.playerGo(area, row, column);
	}
}
