package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Peripherals.Database;
import application.Peripherals.WebAPI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ProfileController implements Initializable  {
	
	@FXML private AnchorPane anpProfile;
    @FXML private Label lblAvatarName;
    @FXML private Label lblHighestScore;
    @FXML private Label lblIPAddress;
    @FXML private Label lblLocation;
    @FXML private Label lblTimeZone;
    
    WebAPI api = new WebAPI();
    
    /**
	 * Initialises the login controller.
	 */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) 
    {
    	// set player name and score
    	lblAvatarName.setText(Database.THIS_PLAYER);
    	lblHighestScore.setText(""+Database.THIS_PLAYER_HIGHEST_SCORE);
    	
    	//set Web API information
    	lblIPAddress.setText(api.getIpAddress());
    	lblLocation.setText(api.getLocation());
    	lblTimeZone.setText(api.getTimezone());
    	
	}
    

    /**
	 * Go to previous page action.
	 */
    public void GoBack(ActionEvent event) throws IOException
    {
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/GamePlay.fxml"));
    	anpProfile.getChildren().setAll(pane);
    }




	

}
