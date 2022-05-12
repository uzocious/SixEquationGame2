package application.Peripherals;

import java.io.File;

import application.Controller.GamePlayController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *  This timer class is used to control the game count down timer
 *
 */
public class CountDownTimer
{
	
	private Timeline timer;
	private int minute = 10;
	private int second = 0;
	private String counter ="";
	
	/**
	 * Starts the timer event
	 * @param lbl
	 */
	public void start(Label lbl, ImageView img)
	{
		timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick(lbl, img)));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}
	
	public void stop() {
		timer.stop();
	}
	
	
	/**
	 * Start the thread for the timer tick
	 * @param lbl
	 */
	private void tick(Label lbl, ImageView img)
	{
		if (second > 0)
		{
			second--;
		}
		
		if (minute == 0 && second == 0) 
		{
			timer.stop();
			
			//game over
    		File file = new File("src/application/Images/gameover.png");
    		Image gameover = new Image(file.toURI().toString());
    		img.setImage(gameover);
			
    		GamePlayController.timerGameOver = true;
			
		}
		else if (second == 0)
		{
			minute--;
			second = 60;
		}
		
		if (second < 10)
		{
			counter = "0" + minute + ":0" + second;
		}
		else 
		{
			counter = "0" + minute + ":" + second;
		}
		
		lbl.setText(counter);
	}


}
