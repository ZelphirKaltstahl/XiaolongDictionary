/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import dictionary.manager.DialogInstanceManager;
import dictionary.buttons.InsertSpecialCharacterButton;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.exceptions.VocableAlreadyExistsException;
import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

/**
 *
 * @author XiaoLong
 */
public class AddVocablesDialog extends XLDDialog {

	private Scene scene;

	private VBox containingVBox;
	private GridPane inputGridPane;
	private HBox insertSpecialCharacterButtonHBox;
	private HBox buttonHBox;

	private Label firstLanguageLabel;
	private Label firstLanguagePhoneticScriptLabel;
	private Label secondLanguageLabel;
	private Label secondLanguagePhoneticScriptLabel;
	private Label topicLabel;
	private Label chapterLabel;
	private Label learnLevelLabel;
	private Label relevanceLevelLabel;
	private Label descriptionLabel;

	private Label addedVocablesCounterLabel;

	private TextField firstLanguageTranslationsTextField;
	private TextField firstLanguagePhoneticScriptTextField;
	private TextField secondLanguageTranslationsTextField;
	private TextField secondLanguagePhoneticScriptTextField;
	private TextField topicTextField;
	private TextField chapterTextField;
	private TextField learnLevelTextField;
	private TextField relevanceLevelTextField;
	private TextArea descriptionTextArea;

	private CheckBox preserveFirstLanguageCheckBox;
	private CheckBox preserveFirstLanguagePhoneticScriptCheckBox;
	private CheckBox preserveSecondLanguageCheckBox;
	private CheckBox preserveSecondLanguagePhoneticScriptCheckBox;
	private CheckBox preserveTopicCheckBox;
	private CheckBox preserveChapterCheckBox;
	private CheckBox preserveLearnLevelCheckBox;
	private CheckBox preserveRelevanceCheckBox;
	private CheckBox preserveDescriptionCheckBox;

	private Button addVocableButton;
	private Button clearButton;
	private Button cancelButton;
	private InsertSpecialCharacterButton insertSpecialCharacterButton;

	private int numberOfAddedVocables = 0;

	public AddVocablesDialog(Modality modality) {
		super(modality);
	}

	@Override
	protected void initializeControls() {
		this.setTitle("Add Vocables");
		this.setResizable(false);
		
		containingVBox = new VBox();
		//containingVBox.setSpacing(5);

		scene = new Scene(containingVBox);

		inputGridPane = new GridPane();
		inputGridPane.setHgap(7);
		inputGridPane.setVgap(7);
		inputGridPane.setPadding(new Insets(10, 10, 10, 10));
		inputGridPane.setAlignment(Pos.CENTER_LEFT);

		insertSpecialCharacterButtonHBox = new HBox();
		insertSpecialCharacterButtonHBox.setAlignment(Pos.CENTER_RIGHT);
		insertSpecialCharacterButtonHBox.setPadding(new Insets(10, 10, 10, 10));
		
		buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(10, 10, 10, 10));
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		buttonHBox.setSpacing(5);
		
		try {
			firstLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME));
			firstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
			secondLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME));
			secondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		topicLabel = new Label("Topic");
		chapterLabel = new Label("Chapter");
		learnLevelLabel = new Label("Learn level");
		relevanceLevelLabel = new Label("Relevance level");
		descriptionLabel = new Label("Description");

		addedVocablesCounterLabel = new Label("Vocables added: " + numberOfAddedVocables);
		addedVocablesCounterLabel.setAlignment(Pos.BOTTOM_LEFT);
		addedVocablesCounterLabel.setTextAlignment(TextAlignment.LEFT);
		addedVocablesCounterLabel.setPadding(new Insets(10, 10, 10, 10));

		firstLanguageTranslationsTextField = new TextField();
		firstLanguageTranslationsTextField.setPrefColumnCount(20);
		firstLanguagePhoneticScriptTextField = new TextField();
		firstLanguagePhoneticScriptTextField.setPrefColumnCount(20);
		secondLanguageTranslationsTextField = new TextField();
		secondLanguageTranslationsTextField.setPrefColumnCount(20);
		secondLanguagePhoneticScriptTextField = new TextField();
		secondLanguagePhoneticScriptTextField.setPrefColumnCount(20);
		topicTextField = new TextField();
		topicTextField.setPrefColumnCount(20);
		chapterTextField = new TextField();
		chapterTextField.setPrefColumnCount(20);
		learnLevelTextField = new TextField();
		learnLevelTextField.setPrefColumnCount(20);
		relevanceLevelTextField = new TextField();
		relevanceLevelTextField.setPrefColumnCount(20);

		descriptionTextArea = new TextArea();
		descriptionTextArea.setPrefColumnCount(20);
		descriptionTextArea.setPrefRowCount(4);

		preserveFirstLanguageCheckBox = new CheckBox("preserve");
		preserveFirstLanguagePhoneticScriptCheckBox = new CheckBox("preserve");
		preserveSecondLanguageCheckBox = new CheckBox("preserve");
		preserveSecondLanguagePhoneticScriptCheckBox = new CheckBox("preserve");
		preserveTopicCheckBox = new CheckBox("preserve");
		preserveChapterCheckBox = new CheckBox("preserve");
		preserveLearnLevelCheckBox = new CheckBox("preserve");
		preserveRelevanceCheckBox = new CheckBox("preserve");
		preserveDescriptionCheckBox = new CheckBox("preserve");
		
		try {
			preserveFirstLanguageCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_FIRST_LANGUAGE_SELECTED_SETTING_NAME))
			);
			preserveFirstLanguagePhoneticScriptCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_FIRST_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME))
			);
			preserveSecondLanguageCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_SECOND_LANGUAGE_SELECTED_SETTING_NAME))
			);
			preserveSecondLanguagePhoneticScriptCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_SECOND_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME))
			);
			preserveTopicCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_TOPIC_SELECTED_SETTING_NAME))
			);
			preserveChapterCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_CHAPTER_SELECTED_SETTING_NAME))
			);
			preserveLearnLevelCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_LEARN_LEVEL_SELECTED_SETTING_NAME))
			);
			preserveRelevanceCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_RELEVANCE_LEVEL_SELECTED_SETTING_NAME))
			);
			preserveDescriptionCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_KEEP_DESCRIPTION_SELECTED_SETTING_NAME))
			);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}

		
		addVocableButton = new Button("Add");
		clearButton = new Button("Clear");
		cancelButton = new Button("Cancel");
		insertSpecialCharacterButton = new InsertSpecialCharacterButton();
		
		lastActiveTextInputControl = firstLanguageTranslationsTextField;
	}

	@Override
	protected void addControls() {

		containingVBox.getChildren().addAll(inputGridPane, insertSpecialCharacterButtonHBox ,buttonHBox, addedVocablesCounterLabel);

		inputGridPane.add(preserveFirstLanguageCheckBox, 0, 0);
		inputGridPane.add(firstLanguageLabel, 1, 0);
		inputGridPane.add(firstLanguageTranslationsTextField, 2, 0);

		inputGridPane.add(preserveFirstLanguagePhoneticScriptCheckBox, 0, 1);
		inputGridPane.add(firstLanguagePhoneticScriptLabel, 1, 1);
		inputGridPane.add(firstLanguagePhoneticScriptTextField, 2, 1);

		inputGridPane.add(preserveSecondLanguageCheckBox, 0, 2);
		inputGridPane.add(secondLanguageLabel, 1, 2);
		inputGridPane.add(secondLanguageTranslationsTextField, 2, 2);

		inputGridPane.add(preserveSecondLanguagePhoneticScriptCheckBox, 0, 3);
		inputGridPane.add(secondLanguagePhoneticScriptLabel, 1, 3);
		inputGridPane.add(secondLanguagePhoneticScriptTextField, 2, 3);

		inputGridPane.add(preserveTopicCheckBox, 0, 4);
		inputGridPane.add(topicLabel, 1, 4);
		inputGridPane.add(topicTextField, 2, 4);

		inputGridPane.add(preserveChapterCheckBox, 0, 5);
		inputGridPane.add(chapterLabel, 1, 5);
		inputGridPane.add(chapterTextField, 2, 5);

		inputGridPane.add(preserveLearnLevelCheckBox, 0, 6);
		inputGridPane.add(learnLevelLabel, 1, 6);
		inputGridPane.add(learnLevelTextField, 2, 6);

		inputGridPane.add(preserveRelevanceCheckBox, 0, 7);
		inputGridPane.add(relevanceLevelLabel, 1, 7);
		inputGridPane.add(relevanceLevelTextField, 2, 7);

		inputGridPane.add(preserveDescriptionCheckBox, 0, 8);
		inputGridPane.add(descriptionLabel, 1, 8);
		inputGridPane.add(descriptionTextArea, 2, 8);

		//inputGridPane.add(insertSpecialCharacterButtonHBox, 2, 9);
		insertSpecialCharacterButtonHBox.getChildren().add(insertSpecialCharacterButton);
		
		/*ObservableList<String> translations = FXCollections.observableArrayList();
		 translations.add("4tgsarawt");
		 translations.add("4tgsarawt");
		 ListView<String> listView = new ListView<>();
		 listView.setItems(translations);
		 listView.setOrientation(Orientation.HORIZONTAL);
		 listView.setMaxHeight(40);

		 inputGridPane.add(listView, 2, 10);*/
		//Button
		buttonHBox.getChildren().addAll(addVocableButton, clearButton, cancelButton);

		//Scene
		setScene(scene);
	}

	@Override
	protected void addActionListeners() {
		addVocableButton.setOnAction((ActionEvent event) -> {
			addVocableButtonActionPerformed();
		});

		clearButton.setOnAction((ActionEvent event) -> {
			clearButtonActionPerformed();
		});

		cancelButton.setOnAction((ActionEvent event) -> {
			cancelButtonActionPerformed();
		});

		insertSpecialCharacterButton.setOnAction((ActionEvent event) -> {
			insertSpecialCharacterButtonActionPerformed();
		});
	}

	@Override
	protected void addFocusChangeListeners() {
		firstLanguageTranslationsTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = firstLanguageTranslationsTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		firstLanguagePhoneticScriptTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = firstLanguagePhoneticScriptTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		secondLanguageTranslationsTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = secondLanguageTranslationsTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		secondLanguagePhoneticScriptTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = secondLanguagePhoneticScriptTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		topicTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = topicTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		chapterTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = chapterTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		learnLevelTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = learnLevelTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		relevanceLevelTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = relevanceLevelTextField;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});

		descriptionTextArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					lastActiveTextInputControl = descriptionTextArea;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getAddVocablesDialogInstance());
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			}
		});
	}

	private void addVocableButtonActionPerformed() {
		if (isInputValid()) {
			
			Vocable additionalVocable = null;
			
			try {
				additionalVocable = new Vocable(
					Arrays.asList(firstLanguageTranslationsTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(firstLanguagePhoneticScriptTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(secondLanguageTranslationsTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(secondLanguagePhoneticScriptTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(topicTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(chapterTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					learnLevelTextField.getText(),
					relevanceLevelTextField.getText(),
					descriptionTextArea.getText()
				);
			} catch (SettingNotFoundException ex) {
				Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			
			try {
				ManagerInstanceManager.getVocableManagerInstance().addVocable(additionalVocable);
				numberOfAddedVocables++;
				updateAddedVocablesCounterLabel();
				clearAllNotPreservedTexts();
			} catch (VocableAlreadyExistsException ex) {
				/*
				Dialogs.create()
					.owner(this)
					.title("Duplicate Vocable")
					.masthead("Information")
					.message("The vocable you want to add is already in the currently active dictionary.")
					.showInformation();
				*/
				ControlFXDialogDisplayer.showAddingDuplicateVocableDialog(this);
			}
			
		} else {
			ControlFXDialogDisplayer.showEmptyInputFieldsDialog(this);
		}
	}

	private void clearButtonActionPerformed() {
		clearAllTexts();
	}

	private void clearAllTexts() {
		firstLanguageTranslationsTextField.clear();
		firstLanguagePhoneticScriptTextField.clear();
		secondLanguageTranslationsTextField.clear();
		secondLanguagePhoneticScriptTextField.clear();
		topicTextField.clear();
		chapterTextField.clear();
		learnLevelTextField.clear();
		relevanceLevelTextField.clear();
		descriptionTextArea.clear();
		
		lastActiveTextInputControl = firstLanguageTranslationsTextField;
	}

	private void clearAllNotPreservedTexts() {
		if (!preserveFirstLanguageCheckBox.isSelected()) {
			firstLanguageTranslationsTextField.clear();
		}

		if (!preserveFirstLanguagePhoneticScriptCheckBox.isSelected()) {
			firstLanguagePhoneticScriptTextField.clear();
		}

		if (!preserveSecondLanguageCheckBox.isSelected()) {
			secondLanguageTranslationsTextField.clear();
		}

		if (!preserveSecondLanguagePhoneticScriptCheckBox.isSelected()) {
			secondLanguagePhoneticScriptTextField.clear();
		}

		if (!preserveTopicCheckBox.isSelected()) {
			topicTextField.clear();
		}

		if (!preserveChapterCheckBox.isSelected()) {
			chapterTextField.clear();
		}

		if (!preserveLearnLevelCheckBox.isSelected()) {
			learnLevelTextField.clear();
		}

		if (!preserveRelevanceCheckBox.isSelected()) {
			relevanceLevelTextField.clear();
		}

		if (!preserveDescriptionCheckBox.isSelected()) {
			descriptionTextArea.clear();
		}
	}

	private boolean isInputValid() {
		return !(firstLanguageTranslationsTextField.getText().isEmpty()
				|| firstLanguagePhoneticScriptTextField.getText().isEmpty()
				|| secondLanguageTranslationsTextField.getText().isEmpty()
				|| secondLanguagePhoneticScriptTextField.getText().isEmpty()
				|| topicTextField.getText().isEmpty()
				|| chapterTextField.getText().isEmpty()
				|| learnLevelTextField.getText().isEmpty()
				|| relevanceLevelTextField.getText().isEmpty()
				|| descriptionTextArea.getText().isEmpty());
	}

	private void cancelButtonActionPerformed() {
		hide();
	}

	/*
	public int getCaretPosition() {
		return this.caretPosition;
	}
	*/
	
	private void updateAddedVocablesCounterLabel() {
		addedVocablesCounterLabel.setText("Vocables added: " + numberOfAddedVocables);
	}
}
