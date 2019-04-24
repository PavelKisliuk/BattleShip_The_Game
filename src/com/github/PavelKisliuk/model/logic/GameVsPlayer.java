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

	public boolean connect() {
		String hostName = "46.216.22.140";
		int port = 2101;

		try {
			client = new Socket(hostName, port);
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			input = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public void disconnect(){
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendArea(Area area) {
		try {
			output.writeObject(area);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Area getOpponentArea() {
		Area area = new Area();
		try {
			area = (Area) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return area;
	}

	@Override
	public boolean playerGoFirst() {
		boolean b = false;
		try {
			b = (Boolean) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public boolean playerGo(Area area, int row, int column) {
		try {
			output.writeObject(row);
			output.writeObject(column);
			if(super.playerGo(area, row, column)) {
				output.writeObject(Boolean.TRUE);
				if(super.isWin(area)) {
					output.writeObject(Boolean.TRUE);
				} else {
					output.writeObject(Boolean.FALSE);
				}
				return true;
			} else {
				output.writeObject(Boolean.FALSE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean opponentGo(Area area) {
		int row = 0;
		int column = 0;
		try {
			row = (Integer) input.readObject();
			column = (Integer) input.readObject();
			return super.playerGo(area, row, column);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
