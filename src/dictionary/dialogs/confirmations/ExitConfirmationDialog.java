/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs.confirmations;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

/**
 *
 * @author xiaolong
 */
public class ExitConfirmationDialog {

	private TextField txUserName;
	private PasswordField txPassword;
	private Action actionLogin;
	private GridPane content;
	private Dialog dialog;

	public void initialize() {
		initializeControls();
		addControls();
		addChangeListeners();
		makeReady();
	}

	private void initializeControls() {

		actionLogin = new AbstractAction("Login") {

			{
				ButtonBar.setType(this, ButtonType.OK_DONE);
			}

			// This dialog will consist of two input fields (username and password),
			// and have two buttons: Login and Cancel.
			// This method is called when the login button is clicked...
			public void execute(ActionEvent ae) {
				Dialog dlg = (Dialog) ae.getSource();
				// real login code here
				dlg.hide();
			}

			@Override
			public void handle(ActionEvent event) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
		};

		dialog = new Dialog(null, "Exit Dialog");

		// layout a custom GridPane containing the input fields and labels
		content = new GridPane();
		content.setHgap(10);
		content.setVgap(10);
		
		txUserName = new TextField();
		txPassword = new PasswordField();
		
		GridPane.setHgrow(txUserName, Priority.ALWAYS);
		GridPane.setHgrow(txPassword, Priority.ALWAYS);
		
		// create the dialog with a custom graphic and the gridpane above as the
		// main content region
		dialog.setResizable(false);
		dialog.setIconifiable(false);
	}
	
	private void addControls() {
		content.add(new Label("User name"), 0, 0);
		content.add(txUserName, 1, 0);
		content.add(new Label("Password"), 0, 1);
		content.add(txPassword, 1, 1);
		
		dialog.setGraphic(new ImageView(getClass().getResource("login.png").toString()));
		dialog.setContent(content);
		dialog.getActions().addAll(actionLogin, Dialog.Actions.CANCEL);
		
		
	}
	
	private void makeReady() {
		validate();
		
		// request focus on the username field by default (so the user can
		// type immediately without having to click first)
		Platform.runLater(() -> txUserName.requestFocus());
	}
	
	private void addChangeListeners() {
		// listen to user input on dialog (to enable / disable the login button)
		ChangeListener<String> changeListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			validate();
		};

		txUserName.textProperty().addListener(changeListener);
		txPassword.textProperty().addListener(changeListener);
	}

	// This method is called when the user types into the username / password fields
	private void validate() {
		actionLogin.disabledProperty().set(
			txUserName.getText().trim().isEmpty() || txPassword.getText().trim().isEmpty()
		);
	}
}
