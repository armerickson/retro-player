package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	@FXML
	public Button closeButton, minButton;
	
    private double xOffset = 0, yOffset =0; //used to compute window location and then reposition when dragged
    
	@Override
	public void start(Stage stage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("introScreen.fxml"));
		Scene scene = new Scene(root);

		scene.setFill(Color.TRANSPARENT);
						
		stage.setScene(scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		
		/*Click and drag window to reposition*/
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
		
		stage.show();
				
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);	
			}		
		});
	}	

	public static void main(String[] args) {

		launch(args);
	}
}