package application.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.Engine.GameEngine;
import application.Peripherals.CountDownTimer;
import application.Peripherals.Database;
import application.StartGUI.RunGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GamePlayController implements Initializable {

	@FXML private AnchorPane anpGamePlay;
    @FXML private ImageView imvGameView;
    @FXML private Label lblAvtName;
    @FXML private Button btnProfile;
    @FXML private Label lblLivesRemaining;
    @FXML private Label lblLiveScore;
    @FXML private Label lblHighestScore;
    @FXML private Label lblCountdown;
    
    Database db = new Database();
    
    CountDownTimer cd_timer = new CountDownTimer();
	GameEngine myGame = null;
	Image currentGame = null;
	int noOfLives = 3;
	public static boolean timerGameOver = false;
	
    /**
	 * Initialises the login controller.
	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
    {
    	// Welcome player
    	if (Database.THIS_PLAYER != null) {
    		lblAvtName.setText("Welcome " + Database.THIS_PLAYER);
		}
    	else {
    		lblAvtName.setText("Welcome Guest");
    		btnProfile.setVisible(false);
    	}
    
    	
    	// load game engine and pick a game location
    	myGame = new GameEngine(Database.THIS_PLAYER);
		currentGame = myGame.nextGame();
		imvGameView.setImage(currentGame);
    	
    	// load highest score
    	if (Database.THIS_PLAYER_HIGHEST_SCORE < 0) {
    		lblHighestScore.setText("0");
    	}
    	else {
    		lblHighestScore.setText(""+Database.THIS_PLAYER_HIGHEST_SCORE);
    	}
    	
    	// set score
    	lblLiveScore.setText("0");
    	
    	// set lives
    	lblLivesRemaining.setText(""+noOfLives);
    	
    	// start count down timer
    	cd_timer.start(lblCountdown, imvGameView);
		
	}
    
    
    /**
	 * Action is performed when button is click.
     * @throws SQLException 
	 */
    public void GameActionPerformed(ActionEvent event) throws SQLException
    {
    	if (noOfLives > 0 && timerGameOver == false) {
    	
	    	Button source = (Button)event.getSource();
	    	String source_text = source.getText();
	    	int playerSolution = Integer.parseInt(source_text);
	    	
	    	// setting session cookies
	    	myGame.setSessionCookies();
	    	
	    	boolean isCorrect = myGame.checkSolution(currentGame, playerSolution);
			int score = myGame.getScore();
			
			if (isCorrect)
			{
				// update score
				lblLiveScore.setText(""+score);
				
				if (score > Database.THIS_PLAYER_HIGHEST_SCORE) 
				{
					//update highest score
					lblHighestScore.setText(""+score);
					Database.THIS_PLAYER_HIGHEST_SCORE = score;
					
					//database update
					if (Database.THIS_PLAYER != null) {
						
						//Open Database
						db.DBConnection();
								
						try {
							//  SQL update statement
							String query = String.format("UPDATE player\r\n" + 
									"SET highscore = '%s'\r\n" + 
									"WHERE avatar_name = '%s';", score, Database.THIS_PLAYER);
							
							// Executes SQL query
							db.statement.execute(query);
						
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
				
				// next game
				currentGame = myGame.nextGame();
				imvGameView.setImage(currentGame);
			} 
			else 
			{ 
				// reduce number of lives remaining
				noOfLives = noOfLives - 1;
				lblLivesRemaining.setText(""+noOfLives);
			}
    	}
    	else {
    		// stop timer
    		cd_timer.stop();
    		
    		// game over
    		File file = new File("src/application/Images/gameover.png");
    		Image gameover = new Image(file.toURI().toString());
    		imvGameView.setImage(gameover);
    	}
    			
	}
    
    
    
    /**
	 * Opens player profile page
	 * @param event
	 * @throws IOException
	 */
    public void Profile(ActionEvent event) throws IOException 
    {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/Profile.fxml"));
    	anpGamePlay.getChildren().setAll(pane);
    }
   
    
    
    /**
	 * Logs player out and redirects to login window
	 * @param event
	 * @throws IOException
	 */
	public void Logout(ActionEvent event) throws IOException
	{
		Database.THIS_PLAYER = null;
		Database.THIS_PLAYER_HIGHEST_SCORE = -1;
		
		((Node)event.getSource()).getScene().getWindow().hide();
		
		Stage primaryStage = new Stage();		
		Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Six Equation G2");
		primaryStage.getIcons().add(new Image(RunGUI.class.getResourceAsStream("../Images/se_logo.png")));
		primaryStage.show();
	}
    
    
    
}
