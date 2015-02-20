/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.model.Settings;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author XiaoLong
 */
public class InsertSpecialCharacterDialog extends Stage {

	private Scene scene;

	private VBox containingVBox;
	private GridPane specialCharacterButtonsGridPane;
	private HBox buttonHBox;

	private Button closeButton;

	private XLDDialog parentStage;
	private TextInputControl activeTextInputControl;
	private Button switchCaseButton;

	private ArrayList<Button> specialCharacterButtons;

	public InsertSpecialCharacterDialog(Modality modality) {
		this.specialCharacterButtons = new ArrayList<>();
		initModality(modality);
	}

	public void init() {
		initializeControls();
		addControls();
		addActionListeners();
	}

	private void initializeControls() {
		this.setTitle("Special Characters");

		try {
			for (char chr : Settings.getInstance().getSettingsProperty(Settings.getInstance().SPECIAL_CHARACTERS_SETTING_NAME).toCharArray()) {
				specialCharacterButtons.add(new Button(String.valueOf(chr)));
			}
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(InsertSpecialCharacterDialog.class.getName()).log(Level.SEVERE, null, ex);
		}

		specialCharacterButtons.stream().forEach((item) -> {
			item.setMinWidth(36);
			item.setMaxWidth(36);
			item.setMinHeight(36);
			item.setMaxHeight(36);
			item.setFont(new Font(16));
			item.setTextAlignment(TextAlignment.CENTER);
		});

		switchCaseButton = new Button("Switch case");
		closeButton = new Button("Close");

		containingVBox = new VBox();

		scene = new Scene(containingVBox);

		specialCharacterButtonsGridPane = new GridPane();
		specialCharacterButtonsGridPane.setHgap(5);
		specialCharacterButtonsGridPane.setVgap(5);
		specialCharacterButtonsGridPane.setPadding(new Insets(10, 10, 10, 10));
		specialCharacterButtonsGridPane.setAlignment(Pos.CENTER_LEFT);

		buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(10, 10, 10, 10));
		buttonHBox.setAlignment(Pos.CENTER_LEFT);
		buttonHBox.setSpacing(5);
	}

	private void addControls() {
		containingVBox.getChildren().addAll(specialCharacterButtonsGridPane, buttonHBox);

		try {
			for (int i = 0; i < Settings.getInstance().getSettingsProperty(Settings.getInstance().SPECIAL_CHARACTERS_SETTING_NAME).length(); i += 4) {
				for (int k = 0; k < 4; k++) {
					specialCharacterButtonsGridPane.add(specialCharacterButtons.get(i + k), k, i / 4);
				}
			}
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(InsertSpecialCharacterDialog.class.getName()).log(Level.SEVERE, null, ex);
		}

		buttonHBox.getChildren().addAll(switchCaseButton, closeButton);

		//Scene
		setScene(scene);
	}

	private void addActionListeners() {
		closeButton.setOnAction((ActionEvent event) -> {
			closeButtonActionPerformed();
		});

		switchCaseButton.setOnAction((ActionEvent event) -> {
			toUpperLowerButtonActionPerformed();
		});

		specialCharacterButtons.stream().forEach((button) -> {
			button.setOnAction((ActionEvent event) -> {
				writeSpecialCharacterIntoTextInputControl(button.getText());
			});
		});
	}

	private void writeSpecialCharacterIntoTextInputControl(String specialCharacter) {
		if (activeTextInputControl != null) {

			if (activeTextInputControl.getSelection().getLength() == 0) {
				
				int oldCaretPosition = parentStage.getCaretPosition();
				activeTextInputControl.setText(
						activeTextInputControl.getText(0, oldCaretPosition)
						+ specialCharacter
						+ activeTextInputControl.getText(oldCaretPosition, activeTextInputControl.getText().length())
				);
				int newCaretPosition = oldCaretPosition + 1;
				activeTextInputControl.positionCaret(newCaretPosition);
				
				
			} else {
				int selectionStart = activeTextInputControl.getSelection().getStart();
				int selectionEnd = activeTextInputControl.getSelection().getEnd();
				activeTextInputControl.setText(
						activeTextInputControl.getText(0, selectionStart)
						+ specialCharacter
						+ activeTextInputControl.getText(selectionEnd, activeTextInputControl.getText().length())
				);
				int newCaretPosition = selectionStart + 1;
				activeTextInputControl.positionCaret(newCaretPosition);
			}
			
			parentStage.requestFocus();
		}
	}

	public void setActiveTextField(TextInputControl activeTextInputControl) {
		this.activeTextInputControl = activeTextInputControl;
	}
	
	public void setActiveParent(XLDDialog parentStage) {
		this.parentStage = parentStage;
	}

	private void toUpperLowerButtonActionPerformed() {
		specialCharacterButtons.stream().forEach((button) -> {
			if (button.getText().equals(button.getText().toUpperCase())) {
				button.setText(button.getText().toLowerCase());
			} else {
				button.setText(button.getText().toUpperCase());
			}
		});
	}

	private void closeButtonActionPerformed() {
		hide();
	}
}
