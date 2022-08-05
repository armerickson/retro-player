package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;

public class Controller implements Initializable{

	@FXML
	private Pane pane;

	@FXML
	private Label songLabel, artistLabel;

	@FXML
	private Button playButton, pauseButton, previousButton, resetButton, nextButton;
	
	@FXML
	public Button closeButton, minButton;

	@FXML
	private Slider volumeSlider;

	@FXML
	private ProgressBar songProgressBar;
	
	/*Opens choose theme page*/
	@FXML
	private Button changeThemeButton;

	private Media media;
	private MediaPlayer mediaPlayer;
	
	/*functions as a playlist*/
	private ArrayList<File> songs;

	private int songNumber;
	
	/*Used for song progression*/
	private Timer timer;
	private TimerTask task;

	/*if player is currently playing a song*/
	private boolean running;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Get songs ArrayList from intoScreenController
		IntroScreenController songsFromInput = new IntroScreenController();
		songs = songsFromInput.getSongs();
        
		System.out.println(songsFromInput.getSongs());
		
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		/*sets songLabel to be the currenly playing song
		 * or first song in media folder
		 * */
		songLabel.setText(songs.get(songNumber).getName());
		
		/*changed method is called when volume slider is adjusted*/
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);			
			}
		});
		songProgressBar.setStyle("-fx-accent: #00FF00;");
	}
	
	/* chooseThemeButton opens selectTheme.fxml
	 * which is a menu of themes/skins to choose from
	 * */
	public void chooseTheme(javafx.event.ActionEvent event) throws IOException {
		
		mediaPlayer.stop();
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("selectTheme.fxml"));
	    Parent parent = loader.load();
	    	    	    
	    Scene scene = new Scene(parent);
	    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
	    
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

	/*Minimize window*/
	@FXML
	public void handleMinButtonAction(ActionEvent event) {
	    Stage stage = (Stage) closeButton.getScene().getWindow();
	    stage.setIconified(true);
	}
	
	/* Start playing an MP3*/
	public void playMedia() {
		beginTimer();
		mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
		mediaPlayer.play();
	}
	
	/*Pauses currently playing media */
	public void pauseMedia() {
		cancelTimer();
		mediaPlayer.pause();
	}
	
	/*Stops currently playing media*/
	public void resetMedia() {
		mediaPlayer.stop();
	}
	
	/* Starts playing the media file previous to currently playing file
	 * in media folder. If playing first song, will loop back and play the 
	 * last song in the media folder.
	 */
	public void previousMedia() {

		//if not at first song, move to previous song
		if(songNumber > 0) {
			songNumber--;
		}
		//if at first song, move to end of playlisti
		else {
			songNumber = songs.size() - 1;
		}
		
		mediaPlayer.stop();

		if(running) {
			cancelTimer();
		}
		
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		songLabel.setText(songs.get(songNumber).getName());

		playMedia();
	}
	/* Starts playing the next media file to currently playing file
	 * in media folder. If playing last song, will loop back and play the 
	 * first song in the media folder.
	 */
	public void nextMedia() {

		//if not at last song, move to next song
		if(songNumber < songs.size() - 1) {
			songNumber++;
		}
		//if at last song, move back to beginning of playlist
		else {
			songNumber = 0;
		}
	
		mediaPlayer.stop();

		if(running) {
			cancelTimer();
		}
		
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		songLabel.setText(songs.get(songNumber).getName());
		playMedia();
	}

	/* Used to track song progression and duration */
	public void beginTimer() {

		timer = new Timer();
		
		task = new TimerTask() {

			public void run() {
				
				running = true;
	
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				songProgressBar.setProgress(current/end);

				if(current/end == 1) {
					cancelTimer();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	/* cancels timer tracking song progression */
	public void cancelTimer() {
		running = false;
		timer.cancel();
	}
}