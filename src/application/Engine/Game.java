package application.Engine;

import javafx.scene.image.Image;

public class Game {
	Image location = null; 
	int solution = -1;
	
	/**
	 * Location of the game and what is the solution to the game.
	 * @param location
	 * @param solution
	 */
	public Game(Image location, int solution) {
		super();
		this.location = location;
		this.solution = solution;
	}
	/**
	 * The location of the game. 
	 * @return the location of the game.
	 */
	public Image getLocation() {
		return location;
	}

	/**
	 * @return The solution of the game.
	 */
	public int getSolution() {
		return solution;
	}
	
	
	
	

}
