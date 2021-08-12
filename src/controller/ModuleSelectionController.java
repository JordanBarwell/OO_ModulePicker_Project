package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateProfilePane;
import view.ModuleSelectionMenuBar;

public class ModuleSelectionController {

	//fields to be used throughout class
	private StudentProfile model;
	private ModuleSelectionRootPane view;

	private CreateProfilePane cpp;
	private ModuleSelectionMenuBar msmb;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;

	public ModuleSelectionController(StudentProfile model, ModuleSelectionRootPane view) {
		//initialise model and view fields
		this.model = model;
		this.view = view;

		//initialise view subcontainer fields
		cpp = view.getCreateProfilePane();
		msmb = view.getModuleSelectionMenuBar();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		osp = view.getOverviewSelectionPane();


		//populate combobox in create profile pane with courses using the setupAndGetCourses method below
		cpp.populateCourseComboBox(setupAndGetCourses());
		//populate list view


		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}


	//a helper method used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create profile pane
		cpp.addCreateProfileHandler(new CreateProfileHandler());

		//attaching handlers to select module pane buttons
		smp.AddTerm1AddHandler(new term1AddBtnHandler());
		smp.AddTerm1RemoveHandler(new term1RemoveBtnHandler());
		smp.AddTerm2AddHandler(new term2AddBtnHandler());
		smp.AddTerm2RemoveHandler(new term2RemoveBtnHandler());
		smp.AddResetBtnHandler(new resetHandler());
		smp.AddSubmitBtnHandler(new submitHandler());

		//attaching handlers to reserve module pane buttons
		rmp.AddTerm1AddHandler(new term1AddHandler());
		rmp.AddTerm1RemoveHandler(new term1RemoveHandler());
		rmp.AddTerm1ConfirmHandler(new term1ConfirmHandler());
		rmp.AddTerm2AddHandler(new term2AddHandler());
		rmp.AddTerm2RemoveHandler(new term2RemoveHandler());
		rmp.AddTerm2ConfirmHandler(new term2ConfirmHandler());

		//attaching the handler for overview selection pane
		osp.AddSaveOverviewHandler(new saveOverviewHandler());

		//attach an event handler to the menu bar that closes the application
		msmb.addExitHandler(e -> System.exit(0));
		msmb.addAboutHandler(new aboutHandler());
		msmb.addSaveHandler(new saveHandler());
		msmb.addLoadHandler(new loadHandler());
	}

	//empty event handler, which can be used for creating a profile
	private class CreateProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			if(cpp.getSelectedCourse() != null && cpp.getPnumberInput() != null && 
					cpp.getName() != null && cpp.getName() != null && cpp.getEmail() != null && cpp.getDate() != null) {

				model.setCourse(cpp.getSelectedCourse());
				model.setpNumber(cpp.getPnumberInput());
				model.setStudentName(cpp.getName());
				model.setEmail(cpp.getEmail());
				model.setSubmissionDate(cpp.getDate());

				cpp.disableCreateBtn();

				smp.disableBtnT1Remove();
				smp.disableBtnT2Remove();

				view.changeTab(1);

				for (Module m: model.getCourse().getAllModulesOnCourse()) {
					if (m.getRunPlan().equals(Delivery.TERM_1)) {
						if(m.isMandatory()){
							smp.populateSelectedTerm1ListView(m);
							smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits());
						} else {
							smp.populateUnselectedTerm1ListView(m);
						}
					} else if (m.getRunPlan().equals(Delivery.TERM_2)){ 
						if(m.isMandatory()){
							smp.populateSelectedTerm2ListView(m);
							smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits());
						} else {
							smp.populateUnselectedTerm2ListView(m);
						}
					} else if (m.getRunPlan().equals(Delivery.YEAR_LONG)) {
						smp.populateYrLongListView(m);
						smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits() / 2);
						smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits() / 2);
						smp.disableBtnSubmit();
					}
				}
			}
		}
	}

	//handlers for Select Module Pane

	//event handler used for the term 1 add button 
	private class term1AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			smp.enableBtnT1Remove();

			Module m = smp.getUnselectedT1Module();

			if (smp.getCreditCountT1() < 60) {

				if (m != null) {
					smp.populateSelectedTerm1ListView(m);
					smp.removeUnSelectedT1Module(m);
					smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits());
				}
			} else if(smp.getCreditCountT1() == 60 && smp.getCreditCountT2() == 60){
				smp.enableBtnSubmit();
			} else {
				smp.disableBtnT1();
			}
		}
	}

	//event handler used for the term 1 remove button
	private class term1RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			Module m = smp.getSelectedT1Module();

			if (m != null && m.isMandatory() == false) {
				smp.removeSelectedT1Module(m);
				smp.populateUnselectedTerm1ListView(m);
				smp.setCreditCountT1(smp.getCreditCountT1() - m.getCredits());

				if (smp.getCreditCountT1() < 60) {
					smp.enableBtnT1();
					smp.disableBtnSubmit();

				}
			} else if (m != null && m.isMandatory() == true && smp.getCreditCountT1() < 30) {				
				smp.removeSelectedT1Module(m);
				smp.populateUnselectedTerm1ListView(m);
				smp.setCreditCountT1(smp.getCreditCountT1() - m.getCredits());				
				smp.enableBtnT1();
				smp.disableBtnT1Remove();
				smp.disableBtnSubmit();
			}
		}
	}


	//event handler used for the term 2 add button 
	private class term2AddBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			smp.enableBtnT2Remove();

			Module m = smp.getUnselectedT2Module();

			if (smp.getCreditCountT2() < 60) {

				if (m != null) {
					smp.populateSelectedTerm2ListView(m);
					smp.removeUnSelectedT2Module(m);
					smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits());					
				} 
			} else if (smp.getCreditCountT2() == 60 && smp.getCreditCountT1() == 60){
				smp.enableBtnSubmit();
			} else {
				smp.disableBtnT2();
			}
		}
	}

	//event handler term 2 remove button
	private class term2RemoveBtnHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			Module m = smp.getSelectedT2Module();

			if (m != null && m.isMandatory() == false) {
				smp.removeSelectedT2Module(m);
				smp.populateUnselectedTerm2ListView(m);
				smp.setCreditCountT2(smp.getCreditCountT2() - m.getCredits());

				if (smp.getCreditCountT2() < 60) {
					smp.enableBtnT2();
					smp.disableBtnSubmit();

				}
			} else if (m != null && m.isMandatory() == true && smp.getCreditCountT2() < 30) {				
				smp.removeSelectedT2Module(m);
				smp.populateUnselectedTerm2ListView(m);
				smp.setCreditCountT2(smp.getCreditCountT2() - m.getCredits());				
				smp.enableBtnT2();
				smp.disableBtnT2Remove();
				smp.disableBtnSubmit();
			}
		}
	}



	private class resetHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			model.clearSelectedModules();
			model.clearReservedModules();

			smp.reset();
			smp.disableBtnSubmit();
			smp.enableAllBtns();
			smp.disableBtnT1Remove();
			smp.disableBtnT2Remove();
			smp.clearCount();

			rmp.reset();
			rmp.enableAllReserveBtn();

			osp.clearOverview();
			osp.disableSaveBtn();


			for (Module m: model.getCourse().getAllModulesOnCourse()) {
				if (m.getRunPlan().equals(Delivery.TERM_1)) {
					if(m.isMandatory()){
						smp.populateSelectedTerm1ListView(m);
						smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits());
					} else {
						smp.populateUnselectedTerm1ListView(m);
					}
				} else if (m.getRunPlan().equals(Delivery.TERM_2)){ 
					if(m.isMandatory()){
						smp.populateSelectedTerm2ListView(m);
						smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits());
					} else {
						smp.populateUnselectedTerm2ListView(m);
					}
				} else if (m.getRunPlan().equals(Delivery.YEAR_LONG)) {
					smp.populateYrLongListView(m);
					smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits() / 2);
					smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits() / 2);
				}
			}
		}
	}

	private class submitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			for(Module x : smp.getAllSelectedT1()) {
				model.addSelectedModule(x);
			}

			for(Module v : smp.getAllSelectedT2()) {
				model.addSelectedModule(v);
			}

			model.addSelectedModule(smp.getYearLongModule());

			view.changeTab(2);
			smp.disableAllBtns();

			int m = smp.getCreditCountT1();
			int c = smp.getCreditCountT2(); 

			if (m == 60 && c == 60) {				
				rmp.populateUnselectedT1ListView(smp.getAllUnselectedT1());
				rmp.populateUnselectedT2ListView(smp.getAllUnselectedT2());

			}
		}
	}

	//Handlers for the Reserve Module Pane

	private class term1AddHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			Module m = rmp.getUnselectedT1Module();

			if (rmp.getCreditCountT1() < 30) {

				if (m != null) {
					rmp.populateReservedT1ListView(m);
					rmp.removeUnselectedT1Module(m);			
					rmp.setCreditCountT1(rmp.getCreditCountT1() + m.getCredits());
				}
			} else {
				rmp.disableBtnT1();
			}
		}
	}

	private class term1RemoveHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			Module m = rmp.getReservedT1Module();

			if (m != null) {
				rmp.removeReservedT1Module(m);
				rmp.addUnselectedT1Module(m);	
				rmp.setCreditCountT1(rmp.getCreditCountT1() - m.getCredits());

				if (rmp.getCreditCountT1() < 30) {
					rmp.enableBtnT1();
				}
			}
		}
	}

	private class term1ConfirmHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			if (rmp.getCreditCountT1() == 30) {
				rmp.disableAllT1btns();
				rmp.nextAccordion();

				for (Module x : rmp.getAllReservedT1()) {
					model.addReservedModule(x);
				}	
			}
		}
	}

	private class term2AddHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			Module m = rmp.getUnselectedT2Module();

			if (rmp.getCreditCountT2() < 30) {

				if (m != null) {
					rmp.populateReservedT2ListView(m);
					rmp.removeUnselectedT2Module(m);
					rmp.setCreditCountT2(rmp.getCreditCountT2() + m.getCredits());
				}			
			} else {
				rmp.disableBtnT2();
			}
		}
	}

	private class term2RemoveHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			Module m = rmp.getReservedT2Module();

			if (m != null) {
				rmp.removeReservedT2Module(m);
				rmp.addUnselectedT2Module(m);	
				rmp.setCreditCountT2(rmp.getCreditCountT2() - m.getCredits());

				if (rmp.getCreditCountT2() < 30) {
					rmp.enableBtnT2();
				}
			}
		}
	}

	private class term2ConfirmHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			if (rmp.getCreditCountT1() == 30 && rmp.getCreditCountT2() == 30 ) {
				rmp.disableAllT2btns();
				osp.enableSaveBtn();
				view.changeTab(3);

				for (Module c : rmp.getAllReservedT2()) {
					model.addReservedModule(c);
				}

				String Reserved = "Reserved Modules\n===============\n";
				String Selected = "Selected Modules\n===============\n";
				String Student = "Student Profile\n===============\n";

				Student += "Name: "+ model.getStudentName().getFullName() + "\nPNo: " + model.getpNumber() + "\nEmail: "
						+ model.getEmail() + "\nDate: " + model.getSubmissionDate() + "\nCourse: " + model.getCourse();


				for (Module v: model.getAllSelectedModules()) {
					Selected += "Module Code: " + v.getModuleCode().toString() + ", Module Name: " + v.getModuleName().toString() + "\n" +
							"Credits: "+ v.getCredits() + ", Mandatory? " + v.isMandatory() + ", Delivery Term: " + v.getRunPlan() + "\n\n";			
				}

				for (Module m: model.getAllReservedModules()) {
					Reserved += "Module Code: " + m.getModuleCode().toString() + ", Module Name: " + m.getModuleName().toString() + "\n" +
							"Credits: "+ m.getCredits() + ", Mandatory? " + m.isMandatory() + ", Delivery Term: " + m.getRunPlan() + "\n\n";
				}

				osp.populateSelected(Selected);
				osp.populateReserved(Reserved);			
				osp.populateStudentProfile(Student);

			}
		}
	}

	//Handlers for the overview selection pane 
	private class saveOverviewHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			String fileName = "Profile.txt";
			String Profile = "Student Profile\n";
			String Reserved = "Reserved Modules\n";
			String Selected = "Selected Modules\n";

			PrintWriter writer = null;
			try {
				writer = new PrintWriter(fileName);
			} catch (FileNotFoundException x) {
				x.printStackTrace();
			}

			Profile += "Name: "+ model.getStudentName().getFullName() + "\nPNo: " + model.getpNumber() + "\nEmail: "
					+ model.getEmail() + "\nDate: " + model.getSubmissionDate() + "\nCourse: " + model.getCourse() + "\n";

			for (Module v: model.getAllSelectedModules()) {
				Selected += "Module Code: " + v.getModuleCode().toString() + ", Module Name: " + v.getModuleName().toString() + "\n" +
						"Credits: "+ v.getCredits() + ", Mandatory? " + v.isMandatory() + ", Delivery Term: " + v.getRunPlan() + "\n\n";			
			}

			for (Module m: model.getAllReservedModules()) {
				Reserved += "Module Code: " + m.getModuleCode().toString() + ", Module Name: " + m.getModuleName().toString() + "\n" +
						"Credits: "+ m.getCredits() + ", Mandatory? " + m.isMandatory() + ", Delivery Term: " + m.getRunPlan() + "\n\n";				
			}

			writer.println(Profile);
			writer.println(Selected);
			writer.println(Reserved);			
			writer.close();

		}
	}

	private class aboutHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			String about = "This is an application which allows you to select and reserve modules for the third year of university. Created by Jordan Barwell."
					+ "\n\n(When in 'SelectModules' click either add buttons once more when selected module lists are filled and it will enable the submit button)";	

			Alert popUp = new Alert(AlertType.INFORMATION);
			popUp.setContentText(about);
			popUp.setTitle("About this Program!");
			popUp.setHeaderText(null);
			popUp.showAndWait();

		}
	}

	private class saveHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			String fileName = ("Student.txt");

			if (model.getCourse() != null) {

				try {
					ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
					os.writeObject(model);
					System.out.println("Save Done");
					model.getAllReservedModules().forEach(System.out::println);
					model.getAllSelectedModules().forEach(System.out::println);
					os.close();
				} catch (IOException x) {
					x.printStackTrace();
				}
			}
		}
	}

	private class loadHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {

			model.clearReservedModules();
			model.clearSelectedModules();

			smp.reset();
			smp.clearCount();
			smp.enableAllBtns();

			rmp.reset();
			rmp.enableAllReserveBtn();

			String fileName = ("Student.txt");

			try {

				ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
				model = (StudentProfile) is.readObject();	

				if(model.getAllSelectedModules() != null) {

					for (Module v : model.getCourse().getAllModulesOnCourse()) {
						if(! model.getAllSelectedModules().contains(v)) {
							if(v.getRunPlan().equals(Delivery.TERM_1)) {
								smp.populateUnselectedTerm1ListView(v);
							} else if(v.getRunPlan().equals(Delivery.TERM_2)) {
								smp.populateUnselectedTerm2ListView(v);
							} else if (v.getRunPlan().equals(Delivery.YEAR_LONG)) {
								smp.populateYrLongListView(v);
								smp.setCreditCountT1(smp.getCreditCountT1() + v.getCredits() / 2);
								smp.setCreditCountT2(smp.getCreditCountT2() + v.getCredits() / 2);
							}
						}
					}

					for (Module m : model.getAllSelectedModules()) {

						if (m.getRunPlan().equals(Delivery.TERM_1)) {
							smp.populateSelectedTerm1ListView(m);
							smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits());

						} else if (m.getRunPlan().equals(Delivery.TERM_2)) {
							smp.populateSelectedTerm2ListView(m);
							smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits());


						} else if (m.getRunPlan().equals(Delivery.YEAR_LONG)) {
							smp.populateYrLongListView(m);
							smp.setCreditCountT1(smp.getCreditCountT1() + m.getCredits() / 2);
							smp.setCreditCountT2(smp.getCreditCountT2() + m.getCredits() / 2);
						}
					}
				}

				if (model.getAllReservedModules() != null) {

					for(Module v : smp.getAllUnselectedT1()) {
						if(! model.getAllReservedModules().contains(v))
							if(v.getRunPlan().equals(Delivery.TERM_1)) {
								rmp.loadUnselectedT1ListView(v);
							} 
					}

					for(Module x : smp.getAllUnselectedT2()) {
						if(! model.getAllReservedModules().contains(x))
							if(x.getRunPlan().equals(Delivery.TERM_2)) {
								rmp.loadUnselectedT2ListView(x);
							}
					}

					for(Module m : model.getAllReservedModules()) {
						if(m.getRunPlan().equals(Delivery.TERM_1)) {
							rmp.populateReservedT1ListView(m);
							rmp.getCreditCountT1();
							rmp.setCreditCountT1(m.getCredits() + rmp.getCreditCountT1());	

						} else if(m.getRunPlan().equals(Delivery.TERM_2)) {
							rmp.populateReservedT2ListView(m);
							rmp.getCreditCountT2();
							rmp.setCreditCountT2(m.getCredits() + rmp.getCreditCountT2());

						} 
					}
				}

				is.close();				
			} catch (FileNotFoundException x) {
				x.printStackTrace();
			} catch (IOException x) {
				x.printStackTrace();
			} catch (ClassNotFoundException x) {
				x.printStackTrace();
			}

		}
	}


	//generates all module and course data and returns courses within an array
	private Course[] setupAndGetCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Delivery.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Delivery.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Delivery.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Delivery.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Delivery.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Delivery.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Delivery.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModule(imat3423);
		compSci.addModule(ctec3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3605);
		compSci.addModule(ctec3606);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(ctec3906);
		compSci.addModule(imat3410);
		compSci.addModule(imat3406);
		compSci.addModule(imat3611);
		compSci.addModule(imat3613);
		compSci.addModule(imat3614);
		compSci.addModule(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(imat3423);
		softEng.addModule(ctec3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3605);
		softEng.addModule(ctec3606);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(ctec3906);
		softEng.addModule(imat3410);
		softEng.addModule(imat3406);
		softEng.addModule(imat3611);
		softEng.addModule(imat3613);
		softEng.addModule(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}

}
