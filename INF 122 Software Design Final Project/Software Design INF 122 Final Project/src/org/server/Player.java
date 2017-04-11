package org.server;

public class Player implements java.io.Serializable {
	private static final long serialVersionUID = -5357273333179509355L;
	
	private String name;
	private int gamesPlayed;
	private int winCount, lossCount;
	
	public Player(String name) {
		this.name = name;
		this.gamesPlayed = 0;
		this.winCount = 0;
		this.lossCount = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public int getLossCount() {
		return lossCount;
	}

	public void setLossCount(int lossCount) {
		this.lossCount = lossCount;
	}
	
	public void incrementGamesPlayed() {
		gamesPlayed++;
	}
	
	public void incrementWinCount() {
		winCount++;
	}
	
	public void incrementLossCount() {
		lossCount++;
	}
	
	@Override
	public String toString()
	{
		String giveString = "";
		giveString = giveString + "Player name: " + this.name + "\n";
		giveString = giveString + "GamesPlayed: " + this.gamesPlayed + "\n";
		giveString = giveString + "Win Count: " + this.winCount + "\n";
		giveString = giveString + "Loss Count: " + this.lossCount + "\n";
		return giveString;
	}
}
