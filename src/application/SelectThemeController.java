package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SelectThemeController {
	
	//buttons to close and minimize
	@FXML
	public Button closeButton, minButton;
	
	@FXML
	private Button theme1Button;
	
	public void showTheme(ActionEvent event) throws IOException {	
		
		FXMLLoader loader = new FXMLLoader();

		//Will load a theme depending on which theme was selected
		switch (((Button)event.getSource()).getText()){
			case "selectTheme1":  loader.setLocation(getClass().getResource("theme1.fxml"));
				break;
			case "selectTheme2":  loader.setLocation(getClass().getResource("theme2.fxml"));
				break;
			case "selectTheme3":  loader.setLocation(getClass().getResource("theme3.fxml"));
				break;
			case "selectTheme4":  loader.setLocation(getClass().getResource("theme4.fxml"));
				break;
			default:
				break;
		}
        
	    Parent parent = loader.load();
	    
	    Scene scene = new Scene(parent);
	    		
	    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	    
	    //Make window transparent. Widget looking!
		scene.setFill(Color.TRANSPARENT);
	   
	    window.setScene(scene);  
	    window.show();  
	}
	
	/*Close window when press X button*/
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

	/*minimize window*/
	@FXML
	public void handleMinButtonAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.setIconified(true);
	}
}

