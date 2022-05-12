package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML private TextField txtAvtname;
    @FXML private PasswordField txtPassword;
    @FXML private RadioButton rdbDontHaveAnAccount;
    @FXML private RadioButton rdbIHaveAnAccount;
    @FXML private Label lblErrorMessage;
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    @FXML private AnchorPane anpLogin;
    
	
    /**
	 * Initialises the login controller.
	 */
    @Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		lblErrorMessage.setText("");
		txtPassword.setDisable(true);
		btnLogin.setDisable(true);
		btnRegister.setDisable(true);	
	}
    
    
    /**
	 * Selects either the option to login or register with radio button.
	 */
    public void radioSelect(ActionEvent event) 
    {
    	if (rdbDontHaveAnAccount.isSelected()) 
    	{
    		txtPassword.setDisable(false);
    		btnLogin.setDisable(true);
    		btnRegister.setDisable(false);	
    	}
    	
    	if (rdbIHaveAnAccount.isSelected()) 
    	{
    		txtPassword.setDisable(false);
    		btnLogin.setDisable(false);
    		btnRegister.setDisable(true);
    	}
	}
    
    
    private Database db = new Database();
    
    /**
	 * Login to start playing game
     * @throws SQLException 
	 */
    public void Login(ActionEvent event) throws SQLException 
    {
    	String avtName = txtAvtname.getText();
		String password = txtPassword.getText();
		
		// trims white spaces
		avtName = avtName.trim();
		
		//Open Database
		db.DBConnection();
		
		try 
		{
			// SQL select statement
			String query = String.format("SELECT * \r\n" + 
					"FROM player  \r\n" + 
					"WHERE avatar_name = '%s' AND pa55w0rd = '%s'", avtName, password);
			
			// Executes SQL query
			db.resultSet = db.statement.executeQuery(query);
			
			// Gets the query result from the player table		 
			if(db.resultSet.next())
			{
				Database.THIS_PLAYER = db.resultSet.getString("avatar_name");
				Database.THIS_PLAYER_HIGHEST_SCORE = db.resultSet.getInt("highscore");
				
				// Open Game Play Page
				openGamePlay(event);
			}
			else
			{
				lblErrorMessage.setText("* Avatar Name or Password Incorrect.");
				return;
			}
			
		} catch (Exception e) {
			System.out.println(e);}
		finally {
			db.connection.close();
			db.statement.close();
			db.resultSet.close();}
		
	}
    
    
    /**
	 * Register new player
     * @throws SQLException 
	 */
    public void Register(ActionEvent event) throws SQLException 
    {
    	//Open Database
		db.DBConnection();
		
		String avtName = txtAvtname.getText();
		String password = txtPassword.getText();
		
		//Trims white space
		avtName = avtName.trim();
		
		if(!avtName.isEmpty()) 
		{
			if (!password.isEmpty()) 
			{
				if(isAvataNameExist(avtName) == false)
				{
					try {
						
						// SQL insert statement
						String query = String.format("INSERT INTO player (avatar_name, pa55w0rd, highscore)\r\n" + 
								"VALUES ('%s','%s','%s')", avtName, password, 0);
						
						// Executes SQL query
						db.statement.execute(query);
						
						Database.THIS_PLAYER = avtName;
						Database.THIS_PLAYER_HIGHEST_SCORE = 0;
						
						// Open Game Play Page
						openGamePlay(event);
					} 
					catch (Exception e){
						System.out.println(e);}
					finally {
						db.statement.close();
						db.connection.close();}
				}
				else {lblErrorMessage.setText("* Avatar name is already in use!");}
			} 
			else {lblErrorMessage.setText("* All fields are required!");}
		}
		else {lblErrorMessage.setText("* All fields are required!");}		
	}
    
    
    
    /**
	 * Checks if avatar name is already being used or already exists
	 */
 	public boolean isAvataNameExist(String avtName) 
 	{
 		boolean result = false;
 		
 		try {
 			// SQL select query
 			String query = String.format("SELECT avatar_name\r\n" + 
 					"FROM player\r\n" + 
 					"WHERE avatar_name = '%s'", avtName);
 			
 			// Executes SQL query
 			db.resultSet = db.statement.executeQuery(query);

 			// Gets the query result from player table 
 			if(db.resultSet.next())
 			{result = true;}
 		}
 		catch(Exception e)
 		{System.out.println(e);}
 		
 		return result;
 	}
    
 	
    
    /**
	 * Play as guest
     * @throws IOException 
	 */
    public void GuestPlay(ActionEvent event) throws IOException 
    {
    	// Open Game Play
		openGamePlay(event);
	}
    
    
    /**
	 * Open game play view
     * @throws IOException 
	 */
    private void openGamePlay(ActionEvent event) throws IOException 
    {
		((Node)event.getSource()).getScene().getWindow().hide();
		
		Stage primaryStage = new Stage();		
		Parent root = FXMLLoader.load(getClass().getResource("../View/GamePlay.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Six Equation G2");
		primaryStage.getIcons().add(new Image(RunGUI.class.getResourceAsStream("../Images/se_logo.png")));
		primaryStage.show();
    }
    
    
    
    
}
