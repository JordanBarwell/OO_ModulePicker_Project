package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class SelectModulesPane extends GridPane {

	private ListView<Module> unTerm1, unTerm2, selTerm1, selTerm2, yrLongList; 
	private Button t1BtnAdd, t1BtnRemove, t2BtnAdd, t2BtnRemove, reset, submit;
	private TextField term1Cred, term2Cred;

	public SelectModulesPane() {

		this.setStyle("-fx-background-color: #EBF6FF;");
		this.setVgap(10);
		this.setHgap(20);
		this.setPadding(new Insets(30));
		this.setAlignment(Pos.CENTER);

		//Labels for the term 1 listViews, buttons and TextFields
		Label unselT1 = new Label("Unselected Term 1 Modules"); 
		Label unselT1Btn = new Label("Term 1");
		Label selT1 = new Label("Selected Term 1 Modules");
		Label t1Cred = new Label("Current Term 1 Credits: ");

		//yearLong modules Label
		Label lblYrLong = new Label("Selected Year Long Modules");

		//Labels for the term 2 listViews, buttons and TextFields
		Label unselT2 = new Label("Unselected Term 2 Modules");
		Label unselT2Btn = new Label("Term 2"); 
		Label selT2 = new Label("Selected Term 2 Modules");
		Label t2Cred = new Label("Current Term 2 Credits: ");

		//sets up ListViews 
		unTerm1 = new ListView<Module>();
		unTerm1.setPrefSize(450, 200);
		
		unTerm2 = new ListView<Module>();
		unTerm2.setPrefSize(450, 200);

		selTerm1 = new ListView<Module>();
		selTerm1.setPrefSize(450, 200);

		selTerm2 = new ListView<Module>();
		selTerm2.setPrefSize(450, 200);

		yrLongList = new ListView<Module>();
		yrLongList.setPrefSize(450, 125);


		//Initialising term one add and remove buttons
		t1BtnAdd = new Button("Add");
		t1BtnAdd.setPrefSize(75, 25);
		t1BtnRemove = new Button("Remove");
		t1BtnRemove.setPrefSize(75, 25);

		//Initialising term two add and remove buttons
		t2BtnAdd = new Button("Add");
		t2BtnAdd.setPrefSize(75, 25);
		t2BtnRemove = new Button("Remove");
		t2BtnRemove.setPrefSize(75, 25);

		//Initialising reset and submit buttons
		reset = new Button("Reset"); 
		reset.setPrefSize(80, 30);
		submit = new Button("Submit");
		submit.setPrefSize(80, 30);

		//set up TextFields for the credit counters
		term1Cred = new TextField("0");
		term1Cred.setEditable(false);
		term1Cred.setPrefSize(40, 20);
		term2Cred = new TextField("0");
		term2Cred.setEditable(false);
		term2Cred.setPrefSize(40, 20);

		//First VBox for the left side of the GridPane
		VBox vbox1 = new VBox();
		vbox1.setAlignment(Pos.CENTER_LEFT);
		vbox1.getChildren().addAll(unselT1, unTerm1);

		//HBox for the unselected term 1 buttons 
		HBox hbox1 = new HBox(10, unselT1Btn, t1BtnAdd, t1BtnRemove);
		hbox1.setPadding(new Insets(20, 20, 20, 50));
		vbox1.getChildren().add(hbox1);

		vbox1.getChildren().addAll(unselT2, unTerm2);

		//HBox for unselected term 2 buttons
		HBox hbox2 = new HBox(10, unselT2Btn, t2BtnAdd, t2BtnRemove);
		hbox2.setPadding(new Insets(20, 20, 20, 50));
		vbox1.getChildren().add(hbox2);

		HBox creditCount1 = new HBox(10, t1Cred, term1Cred);
		creditCount1.setPadding(new Insets(20, 20, 20, 50));
		vbox1.getChildren().add(creditCount1);

		HBox resetBtn = new HBox(reset);
		resetBtn.setPadding(new Insets(40, 1, 1, 1));
		resetBtn.setAlignment(Pos.CENTER_RIGHT);
		vbox1.getChildren().add(resetBtn);

		this.add(vbox1, 0, 0);

		//Second VBox for the right side of the GridPane 
		VBox vbox2 = new VBox();
		vbox2.setAlignment(Pos.CENTER_RIGHT);

		HBox hbox3 = new HBox(lblYrLong);
		HBox hbox4 = new HBox(yrLongList);
		vbox2.getChildren().addAll(hbox3, hbox4);

		HBox hbox5 = new HBox(selT1);
		HBox hbox6 = new HBox(selTerm1);
		hbox5.setPadding(new Insets(30, 1, 1, 1));
		vbox2.getChildren().addAll(hbox5, hbox6);

		HBox hbox7 = new HBox(selT2);
		HBox hbox8 = new HBox(selTerm2);
		hbox7.setPadding(new Insets(30, 1, 1, 1));
		vbox2.getChildren().addAll(hbox7, hbox8);

		HBox creditCount2 = new HBox(10, t2Cred, term2Cred);
		creditCount2.setPadding(new Insets(20, 20, 20, 50));
		vbox2.getChildren().add(creditCount2);

		HBox submitBtn = new HBox(submit);
		submitBtn.setPadding(new Insets(40, 1, 1, 1));
		submitBtn.setAlignment(Pos.CENTER_LEFT);
		vbox2.getChildren().add(submitBtn);

		this.add(vbox2, 1, 0);
		
	}

	public void populateUnselectedTerm1ListView(Module m) {
		unTerm1.getSelectionModel().select(m);
		unTerm1.getItems().add(m);
	}

	public void populateUnselectedTerm2ListView(Module m) {
		unTerm2.getSelectionModel().select(m);
		unTerm2.getItems().add(m);
	}

	public void populateYrLongListView(Module m) {
		yrLongList.getSelectionModel().select(m);
		yrLongList.getItems().add(m);
	}

	public void populateSelectedTerm1ListView(Module m) {
		selTerm1.getSelectionModel().select(m);
		selTerm1.getItems().add(m);
	}

	public void populateSelectedTerm2ListView(Module m) {
		selTerm2.getSelectionModel().select(m);
		selTerm2.getItems().add(m);
	}

	public void removeSelectedT1Module(Module m) {
		selTerm1.getSelectionModel().select(m);
		selTerm1.getItems().remove(m);
	}

	public void removeUnSelectedT1Module(Module m) {
		unTerm1.getSelectionModel().select(m);
		unTerm1.getItems().remove(m);
	}

	public void removeSelectedT2Module(Module m) {
		selTerm2.getSelectionModel().select(m);
		selTerm2.getItems().remove(m);
	}

	public void removeUnSelectedT2Module(Module m) {
		unTerm2.getSelectionModel().select(m);
		unTerm2.getItems().remove(m);
	}

	public void AddTerm1AddHandler(EventHandler<ActionEvent> handler) {
		t1BtnAdd.setOnAction(handler);
	}

	public void AddTerm1RemoveHandler(EventHandler<ActionEvent> handler) {
		t1BtnRemove.setOnAction(handler);
	}

	public void AddTerm2AddHandler(EventHandler<ActionEvent> handler) {
		t2BtnAdd.setOnAction(handler);
	}

	public void AddTerm2RemoveHandler(EventHandler<ActionEvent> handler) {
		t2BtnRemove.setOnAction(handler);
	}

	public void AddResetBtnHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}

	public void AddSubmitBtnHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}

	public Module getUnselectedT1Module() {
		return unTerm1.getSelectionModel().getSelectedItem();
	}

	public Module getUnselectedT2Module() {
		return unTerm2.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedT1Module() {
		return selTerm1.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedT2Module() {
		return selTerm2.getSelectionModel().getSelectedItem();
	}
	
	public Module getYearLongModule() {
		return yrLongList.getSelectionModel().getSelectedItem();
	}

	public void setCreditCountT1 (int c) {
		term1Cred.setText(String.valueOf(c));
	}

	public void setCreditCountT2 (int c) {		
		term2Cred.setText(String.valueOf(c));
	}

	public void clearCount() {
		term1Cred.setText(String.valueOf("0"));
		term2Cred.setText(String.valueOf("0"));
	}

	public int getCreditCountT1() {
		return Integer.parseInt(term1Cred.getText());
	}

	public int getCreditCountT2() {
		return Integer.parseInt(term2Cred.getText());
	}

	public void disableBtnT1() {
		t1BtnAdd.setDisable(true);
	}

	public void disableBtnT2() {
		t2BtnAdd.setDisable(true);
	}

	public void enableBtnT1() {
		t1BtnAdd.setDisable(false);
	}

	public void enableBtnT2() {
		t2BtnAdd.setDisable(false);
	}

	public void disableBtnT1Remove() {
		t1BtnRemove.setDisable(true);
	}	

	public void enableBtnT1Remove() {
		t1BtnRemove.setDisable(false);
	}

	public void disableBtnT2Remove() {
		t2BtnRemove.setDisable(true);
	}	

	public void enableBtnT2Remove() {
		t2BtnRemove.setDisable(false);
	}

	public void reset() {
		unTerm1.getItems().clear();
		unTerm2.getItems().clear();
		selTerm1.getItems().clear();
		selTerm2.getItems().clear();
		yrLongList.getItems().clear();
	}	

	public ObservableList<Module> getAllUnselectedT1() {
		return unTerm1.getItems();
	}


	public  ObservableList<Module> getAllUnselectedT2() {
		return unTerm2.getItems();
	}


	public ObservableList<Module> getAllSelectedT1() {
		return selTerm1.getItems();
	}


	public  ObservableList<Module> getAllSelectedT2() {
		return selTerm2.getItems();
	}

	public String term1ModulesToString() {
		return selTerm1.toString();
	}

	public String term2ModulesToString() {
		return selTerm2.toString();
	}

	public void disableBtnSubmit() {
		submit.setDisable(true);	
	}

	public void enableBtnSubmit() {
		submit.setDisable(false);	
	}

	public void disableAllBtns() {
		submit.setDisable(true);
		t1BtnAdd.setDisable(true);
		t2BtnAdd.setDisable(true);
		t1BtnRemove.setDisable(true);
		t2BtnRemove.setDisable(true);
	}

	public void enableAllBtns() {
		t1BtnAdd.setDisable(false);
		t2BtnAdd.setDisable(false);
		t1BtnRemove.setDisable(false);
		t2BtnRemove.setDisable(false);
	}

}
