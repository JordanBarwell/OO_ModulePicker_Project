package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Module;

public class ReserveModulesPane extends GridPane{

	private Accordion termModules;
	private ListView<Module> unselT1, resT1, unselT2, resT2;
	private Button t1Add, t1Remove, t1Confirm, t2Add, t2Remove, t2Confirm;
	private TextField term1Cred, term2Cred;
	private TitledPane term1, term2;

	public ReserveModulesPane() {

		this.setStyle("-fx-background-color: #EBF6FF;");
		this.setVgap(10);
		this.setHgap(20);
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);

		//Accordion 1 Labels
		Label unselT1Mod = new Label("Unselected Term 1 Modules");
		Label resT1Mod = new Label("Reserved Term 1 Modules");
		Label messageLbl1 = new Label("Reserve 30 credits worth of term 1 modules");

		//Accordion 2 Labels
		Label unselT2Mod = new Label("Unselected Term 2 Modules");
		Label resT2Mod = new Label("Reserved Term 2 Modules");
		Label messageLbl2 = new Label("Reserve 30 credits worth of term 2 modules");

		VBox term1Vbox = new VBox();
		VBox term2Vbox = new VBox();

		//TitledPanes for the Accordion  
		term1 = new TitledPane("Term 1 Modules", term1Vbox);
		term2 = new TitledPane("Term 2 Modules", term2Vbox);

		//Accordion
		termModules = new Accordion(term1, term2);
		termModules.setExpandedPane(term1);		

		//Term 1 ListViews 
		unselT1 = new ListView<Module>();
		unselT1.setPrefSize(400, 200);
		resT1 = new ListView<Module>();
		resT1.setPrefSize(400, 200);

		//Term 2 ListViews
		unselT2 = new ListView<Module>();
		unselT2.setPrefSize(400, 200);
		resT2 = new ListView<Module>();
		resT2.setPrefSize(400, 200);

		//T1 Buttons
		t1Add = new Button("Add");
		t1Add.setPrefSize(75, 25);
		t1Remove = new Button("Remove");
		t1Remove.setPrefSize(75, 25);
		t1Confirm = new Button("Confirm");
		t1Confirm.setPrefSize(75, 25);

		//T2 Buttons
		t2Add = new Button("Add");
		t2Add.setPrefSize(75, 25);
		t2Remove = new Button("Remove");
		t2Remove.setPrefSize(75, 25);
		t2Confirm = new Button("Confirm");
		t2Confirm.setPrefSize(75, 25);

		//credit count (will not be visable) to help with handlers
		term1Cred = new TextField("0");
		term2Cred = new TextField("0");

		//Term 1 Module Accordion content
		VBox unselected = new VBox();
		HBox hbox1 = new HBox(unselT1Mod);
		HBox hbox2 = new HBox(unselT1);
		unselected.getChildren().addAll(hbox1, hbox2);

		VBox reserve = new VBox();
		HBox hbox3 = new HBox(resT1Mod);
		HBox hbox4 = new HBox(resT1);
		reserve.getChildren().addAll(hbox3, hbox4);

		HBox t1Buttons = new HBox(20, messageLbl1, t1Add, t1Remove, t1Confirm);
		t1Buttons.setPadding(new Insets(1,1,1,75));

		HBox positionBox = new HBox(30);
		positionBox.setPadding(new Insets(30));
		positionBox.getChildren().addAll(unselected, reserve);

		term1Vbox.getChildren().addAll(positionBox, t1Buttons);
		term1Vbox.setPadding(new Insets(10,10,10,10));

		//term 2 Module Accordion content
		VBox t2Unselected = new VBox();
		HBox hbox5 = new HBox(unselT2Mod);
		HBox hbox6 = new HBox(unselT2);
		t2Unselected.getChildren().addAll(hbox5, hbox6);

		VBox t2Reserve = new VBox();
		HBox hbox7 = new HBox(resT2Mod);
		HBox hbox8 = new HBox(resT2);
		t2Reserve.getChildren().addAll(hbox7, hbox8);

		HBox t2Buttons = new HBox(20, messageLbl2, t2Add, t2Remove, t2Confirm);
		t2Buttons.setPadding(new Insets(1,1,1,75));

		HBox t2PositionBox = new HBox(30);
		t2PositionBox.setPadding(new Insets(30));
		t2PositionBox.getChildren().addAll(t2Unselected, t2Reserve);

		term2Vbox.getChildren().addAll(t2PositionBox, t2Buttons);
		term2Vbox.setPadding(new Insets(10,10,10,10));

		this.getChildren().add(termModules);

	}

	public void populateUnselectedT1ListView(ObservableList<Module> m) {
		unselT1.getItems().addAll(m);
	}

	public void populateReservedT1ListView(Module m) {
		resT1.getSelectionModel().select(m);
		resT1.getItems().add(m);
	}

	public void populateUnselectedT2ListView(ObservableList<Module> m) {	
		unselT2.getItems().addAll(m);
	}

	public void populateReservedT2ListView(Module m) {
		resT2.getSelectionModel().select(m);
		resT2.getItems().add(m);
	}

	public void AddTerm1AddHandler(EventHandler<ActionEvent> handler) {
		t1Add.setOnAction(handler);
	}

	public void AddTerm1RemoveHandler(EventHandler<ActionEvent> handler) {
		t1Remove.setOnAction(handler);
	}

	public void AddTerm1ConfirmHandler(EventHandler<ActionEvent> handler) {
		t1Confirm.setOnAction(handler);
	}

	public void AddTerm2AddHandler(EventHandler<ActionEvent> handler) {
		t2Add.setOnAction(handler);
	}

	public void AddTerm2RemoveHandler(EventHandler<ActionEvent> handler) {
		t2Remove.setOnAction(handler);
	}

	public void AddTerm2ConfirmHandler(EventHandler<ActionEvent> handler) {
		t2Confirm.setOnAction(handler);
	}

	public void reset() {
		unselT1.getItems().clear();
		resT1.getItems().clear();
		unselT2.getItems().clear();
		resT2.getItems().clear();
		term1Cred.setText("0");
		term2Cred.setText("0");
		termModules.setExpandedPane(term1);
	}

	public Module getUnselectedT1Module() {
		return unselT1.getSelectionModel().getSelectedItem();
	}

	public Module getReservedT1Module() {
		return resT1.getSelectionModel().getSelectedItem();
	}

	public Module getUnselectedT2Module() {
		return unselT2.getSelectionModel().getSelectedItem();
	}

	public Module getReservedT2Module() {
		return resT2.getSelectionModel().getSelectedItem();
	}

	public void addUnselectedT1Module(Module m) {
		unselT1.getSelectionModel().select(m);
		unselT1.getItems().add(m);
	}

	public void addUnselectedT2Module(Module m) {
		unselT2.getSelectionModel().select(m);
		unselT2.getItems().add(m);
	}

	public void removeUnselectedT1Module(Module m) {
		unselT1.getSelectionModel().select(m);
		unselT1.getItems().remove(m);
	}

	public void removeReservedT1Module(Module m) {
		resT1.getSelectionModel().select(m);
		resT1.getItems().remove(m);
	}

	public void removeUnselectedT2Module(Module m) {
		unselT2.getSelectionModel().select(m);
		unselT2.getItems().remove(m);
	}

	public void removeReservedT2Module(Module m) {
		resT2.getSelectionModel().select(m);
		resT2.getItems().remove(m);
	}

	public void setCreditCountT1 (int c) {
		term1Cred.setText(String.valueOf(c));
	}

	public void setCreditCountT2 (int c) {		
		term2Cred.setText(String.valueOf(c));
	}

	public int getCreditCountT1() {
		return Integer.parseInt(term1Cred.getText());
	}

	public int getCreditCountT2() {
		return Integer.parseInt(term2Cred.getText());
	}

	public void disableBtnT1() {
		t1Add.setDisable(true);
	}	

	public void enableBtnT1() {
		t1Add.setDisable(false);
	}

	public void disableBtnT2() {
		t2Add.setDisable(true);
	}	

	public void enableBtnT2() {
		t2Add.setDisable(false);
	}

	public void disableAllT1btns() {
		t1Add.setDisable(true);
		t1Remove.setDisable(true);
	}	

	public void disableAllT2btns() {
		t2Add.setDisable(true);
		t2Remove.setDisable(true);
	}	

	public void enableAllReserveBtn() {
		t1Add.setDisable(false);
		t1Remove.setDisable(false);
		t2Add.setDisable(false);
		t2Remove.setDisable(false);
	}

	public ObservableList<Module> getAllReservedT1() {
		return resT1.getItems();
	}

	public ObservableList<Module> getAllReservedT2() {
		return resT2.getItems();
	}

	public void nextAccordion() {
		termModules.setExpandedPane(term2);
	}

	public void loadUnselectedT2ListView(Module v) {
		unselT2.getSelectionModel().select(v);
		unselT2.getItems().add(v);
		
	}
	

	public void loadUnselectedT1ListView(Module v) {
		unselT1.getSelectionModel().select(v);
		unselT1.getItems().add(v);
		
	}
	
}
