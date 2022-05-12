package application.Engine;

import javafx.scene.image.Image;

/**
 * Main class where the games are coming from. 
 * Basic functionality
 */
public class GameEngine {
	String thePlayer = null;
	public String sessionCookies = null;

	/**
	 * Each player has their own game engine.
	 * 
	 * @param player
	 */
	public GameEngine(String player) {
		thePlayer = player;
	}

	int counter = 0;
	int score = 0; 
	GameServer theGames = new GameServer(); 
	Game current = null;
	
	/*
	 * Retrieves a game. This basic version only has two games that alternate.
	 */
	public Image nextGame() {		
		current = theGames.getRandomGame();
		return current.getLocation();
	}

	/**
	 * Checks if the parameter i is a solution to the game Image. If so, score is increased by one. 
	 * @param game
	 * @param i
	 * @return
	 */
	public boolean checkSolution(Image game, int i) {
		if (i == current.getSolution()) {
			score++; 
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Retrieves the score. 
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}
	
	
	/**
	 * Implementation of cookies method. 
	 */
	public void setSessionCookies()
	{
		sessionCookies = "player-"+Math.random();
	}

}
