package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class OverviewSelectionPane extends GridPane{

	private TextArea profileDisplay, selectedDisplay, reservedDisplay;
	private Button saveOverview;

	public OverviewSelectionPane() {

		this.setStyle("-fx-background-color: #EBF6FF;");
		this.setVgap(10);
		this.setHgap(20);
		this.setPadding(new Insets(30));
		this.setAlignment(Pos.CENTER);

		profileDisplay = new TextArea("Profile will appear here");
		profileDisplay.setPrefSize(750, 150);
		profileDisplay.setEditable(false);
		
		selectedDisplay = new TextArea("Selected modules will appear here");
		selectedDisplay.setPrefSize(500, 300);
		selectedDisplay.setEditable(false);
		
		reservedDisplay = new TextArea("Reserved Modules will appear here");
		reservedDisplay.setPrefSize(500, 300);
		reservedDisplay.setEditable(false);
		
		saveOverview = new Button("Save Overview");
		saveOverview.setPrefSize(100, 35);
		saveOverview.setDisable(true);
		
		VBox vbox1 = new VBox();
		vbox1.getChildren().add(profileDisplay);
		vbox1.setAlignment(Pos.TOP_CENTER);
		vbox1.setPadding(new Insets(1, 30, 30, 30));

		this.add(vbox1, 0, 0);

		VBox vbox2 = new VBox(50);
		vbox2.setAlignment(Pos.CENTER);
		vbox2.setPadding(new Insets(30));

		HBox hbox1 = new HBox(20, selectedDisplay, reservedDisplay);
		HBox hbox2 = new HBox(10, saveOverview);
		hbox2.setAlignment(Pos.BOTTOM_CENTER);

		vbox2.getChildren().addAll(hbox1, hbox2);

		this.add(vbox2, 0, 1);

	}

	public void AddSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		saveOverview.setOnAction(handler);
	}

	public void populateSelected(String m) {
		selectedDisplay.setText(m);
	}

	public void populateReserved(String m) {
		reservedDisplay.setText(m);
	}

	public void populateStudentProfile(String m) {
		profileDisplay.setText(m);
	}
	
	public void clearOverview() {
		profileDisplay.clear();
		selectedDisplay.clear();
		reservedDisplay.clear();
	}
	
	public void enableSaveBtn() {
		saveOverview.setDisable(false);
	}
	
	public void disableSaveBtn() {
		saveOverview.setDisable(true);
	}
	
}
