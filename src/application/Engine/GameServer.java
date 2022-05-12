package application.Engine;

import java.io.File;
import java.util.Random;
import javafx.scene.image.Image;


public class GameServer {
	
	/**
	 * Retrieves a game for any positive number less than 1000.
	 * 
	 * @param i
	 * @return a random game.
	 */
	public Game getRandomGame()
	{	
		int i =  new Random().nextInt(1000);
		
		File file = new File("src/application/Location/sixeqgame_" + i + ".png");
		Image location = new Image(file.toURI().toString());
		
		int solution  = i % 10;
		 
		return new Game(location, solution);
	}


}
