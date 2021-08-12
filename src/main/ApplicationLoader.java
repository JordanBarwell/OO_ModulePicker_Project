 
package main;

import controller.ModuleSelectionController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleSelectionRootPane;

public class ApplicationLoader extends Application {

	private ModuleSelectionRootPane view;
	
	
	@Override
	public void init() {
		//create view and model and pass their references to the controller
		view = new ModuleSelectionRootPane();
		StudentProfile model = new StudentProfile();
		new ModuleSelectionController(model, view);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//sets min width and height for the stage window
		stage.setMinWidth(650); 
		stage.setMinHeight(500);
		
		stage.setTitle("Final Year Module Selection Tool");
		stage.setScene(new Scene(view));
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
