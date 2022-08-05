package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class IntroScreenController{

	private File directory;
	private File[] files;

	/*functions as a playlist*/
	private static ArrayList<File> songs;
	
	@FXML
	private Button browseButton, closeButton, minButton;
	
	public void handleSourceBrowse(ActionEvent event) throws IOException{
	
		songs = new ArrayList<File>();
		
		//Demo Music button pressed
		String text = ((Button)event.getSource()).getText();
		System.out.println(text);
		
		if(text.equals("Demo")) { //user pressed Demo button, set directory to music folder in src/Music 
			directory = new File("music");
			
		} else {//User browsed for music folder
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Open music folder");
			
		    Node source = (Node) event.getSource();
		    Window stage = source.getScene().getWindow();
			directory = directoryChooser.showDialog(stage);
		}

		/*stores all mp3 files until there are no more files*/
		if(directory!=null) {
			files = directory.listFiles();
		}

		/*adds song files into song ArrayList*/
		if(files != null) {
			for(File file : files) {
				if(Files.probeContentType(file.toPath())!=null) {
					switch(Files.probeContentType(file.toPath())) {
			            case "audio/x-wav": // wav file
			            case "audio/mpeg":  // mp3
			            case "audio/mp4": //mp4
			            case "audio/ogg": //ogg
							System.out.println(file);
			                songs.add(file);
			                break;
			            default: 
							System.out.println("not a song");
							break;
					}
				}
			}
		}
		
		if(!songs.isEmpty()) { 
			//Go to choose theme page
			chooseTheme(event);
			
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "No audio files detected. Please try a different folder.", ButtonType.OK);
			alert.showAndWait();
		}
		
	}
	
	/* chooseThemeButton opens selectTheme.fxml
	 * which is a menu of themes/skins to choose from
	 * */
	public void chooseTheme(javafx.event.ActionEvent event) throws IOException {
		/*Go to Select Theme window (controller = Controller.java)*/
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("selectTheme.fxml"));
	    Parent parent = loader.load();
	    
	    Scene scene = new Scene(parent);
	    		
	    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	    
	    //Make window transparent. Widget looking!
		scene.setFill(Color.TRANSPARENT);
		
	    window.setScene(scene);  
        
	    window.show();  
	}

	/*Allows other classes to get songs chosen from directory*/
	public ArrayList<File> getSongs(){
		return songs;
	}
	
	/*Close window when press X button*/
	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.close();
	}

	/*Minimize window*/
	@FXML
	public void handleMinButtonAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.setIconified(true);
	}
}