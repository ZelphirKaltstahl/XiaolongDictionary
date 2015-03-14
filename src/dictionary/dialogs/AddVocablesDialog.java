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
import dictionary.listeners.SettingsPropertyChangeListener;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Action;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

/**
 *
 * @author XiaoLong
 */
public class AddVocablesDialog extends XLDDialog implements SettingsPropertyChangeListener {

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
	private TextArea descriptionTextArea;
	
	private GridPane learnLevelGridPane;
	private ToggleGroup learnLevelRadioButtonsToggleGroup;
	private RadioButton customLearnLevelRadioButton;
	private RadioButton predefinedLearnLevelRadioButton;
	private ComboBox<String> learnLevelComboBox;
	private TextField learnLevelTextField;
	
	private GridPane relevanceLevelGridPane;
	private ToggleGroup relevanceLevelRadioButtonsToggleGroup;
	private RadioButton customRelevanceLevelRadioButton;
	private RadioButton predefinedRelevanceLevelRadioButton;
	private ComboBox<String> relevanceLevelComboBox;
	private TextField relevanceLevelTextField;

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
	
	private final HashMap<String, Action<String>> actionsForObservedSettingsChanges = new HashMap<>();

	public AddVocablesDialog(Modality modality) {
		super(modality);
	}
	
	
	public void initialize() {
		loadSettings();
		initializeControls();
		addCSSClasses();
		addControls();
		addActionListeners();
		addFocusChangeListeners();
		applySettings();
		registerAsListener();
		addPropertyChangeListeners();
		setActionsForNotifications();
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
			firstLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME) + ":");
			firstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			secondLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			secondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		topicLabel = new Label("Topic" + ":");
		chapterLabel = new Label("Chapter" + ":");
		learnLevelLabel = new Label("Learn level" + ":");
		relevanceLevelLabel = new Label("Relevance level" + ":");
		descriptionLabel = new Label("Description" + ":");

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
		
		// ===========
		// learn level
		// ===========
		learnLevelGridPane = new GridPane();
		learnLevelGridPane.setHgap(2);
		learnLevelGridPane.setVgap(2);
		learnLevelGridPane.getColumnConstraints().add(new ColumnConstraints());
		learnLevelGridPane.getColumnConstraints().get(0).setPercentWidth(40);
		learnLevelGridPane.getColumnConstraints().add(new ColumnConstraints());
		learnLevelGridPane.getColumnConstraints().get(1).setPercentWidth(60);
		learnLevelGridPane.setMaxWidth(300);
		
		learnLevelRadioButtonsToggleGroup = new ToggleGroup();
		customLearnLevelRadioButton = new RadioButton("Custom");
		predefinedLearnLevelRadioButton = new RadioButton("Predefined");			
		learnLevelRadioButtonsToggleGroup.getToggles().add(customLearnLevelRadioButton);
		learnLevelRadioButtonsToggleGroup.getToggles().add(predefinedLearnLevelRadioButton);

		learnLevelTextField = new TextField();
		learnLevelTextField.setPrefColumnCount(20);
		learnLevelTextField.disableProperty().bindBidirectional(predefinedLearnLevelRadioButton.selectedProperty());

		String[] changedPredefinedLearnLevelChoices = null;
		try {
			changedPredefinedLearnLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_PREDEFINED_LEARN_LEVELS_SETTING_NAME).split(",", -1);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		ObservableList<String> changedLearnLevelChoices = FXCollections.observableArrayList(changedPredefinedLearnLevelChoices);
		learnLevelComboBox = new ComboBox<>(changedLearnLevelChoices);
		learnLevelComboBox.disableProperty().bindBidirectional(customLearnLevelRadioButton.selectedProperty());

		predefinedLearnLevelRadioButton.setSelected(false);
		customLearnLevelRadioButton.setSelected(true);
		learnLevelComboBox.getSelectionModel().select(0);
		
		// =========
		// RELEVANCE
		// =========
		relevanceLevelGridPane = new GridPane();
		relevanceLevelGridPane.setHgap(2);
		relevanceLevelGridPane.setVgap(2);
		relevanceLevelGridPane.getColumnConstraints().add(new ColumnConstraints());
		relevanceLevelGridPane.getColumnConstraints().get(0).setPercentWidth(40);
		relevanceLevelGridPane.getColumnConstraints().add(new ColumnConstraints());
		relevanceLevelGridPane.getColumnConstraints().get(1).setPercentWidth(60);
		relevanceLevelGridPane.setMaxWidth(300);
		
		relevanceLevelRadioButtonsToggleGroup = new ToggleGroup();
		customRelevanceLevelRadioButton = new RadioButton("Custom");
		predefinedRelevanceLevelRadioButton = new RadioButton("Predefined");			
		relevanceLevelRadioButtonsToggleGroup.getToggles().add(customRelevanceLevelRadioButton);
		relevanceLevelRadioButtonsToggleGroup.getToggles().add(predefinedRelevanceLevelRadioButton);

		relevanceLevelTextField = new TextField();
		relevanceLevelTextField.setPrefColumnCount(20);
		relevanceLevelTextField.disableProperty().bindBidirectional(predefinedRelevanceLevelRadioButton.selectedProperty());

		String[] changedPredefinedRelevanceLevelChoices = null;
		try {
			changedPredefinedRelevanceLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_PREDEFINED_LEARN_LEVELS_SETTING_NAME).split(",", -1);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(AddVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		ObservableList<String> changedRelevanceLevelChoices = FXCollections.observableArrayList(changedPredefinedRelevanceLevelChoices);
		relevanceLevelComboBox = new ComboBox<>(changedRelevanceLevelChoices);
		relevanceLevelComboBox.disableProperty().bindBidirectional(customRelevanceLevelRadioButton.selectedProperty());

		predefinedRelevanceLevelRadioButton.setSelected(false);
		customRelevanceLevelRadioButton.setSelected(true);
		relevanceLevelComboBox.getSelectionModel().select(0);
		
		

		descriptionTextArea = new TextArea();
		descriptionTextArea.setPrefColumnCount(20);
		descriptionTextArea.setPrefRowCount(4);
		descriptionTextArea.setWrapText(true);

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
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_SELECTED_SETTING_NAME))
			);
			preserveFirstLanguagePhoneticScriptCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME))
			);
			preserveSecondLanguageCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_SELECTED_SETTING_NAME))
			);
			preserveSecondLanguagePhoneticScriptCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME))
			);
			preserveTopicCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_TOPIC_SELECTED_SETTING_NAME))
			);
			preserveChapterCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_CHAPTER_SELECTED_SETTING_NAME))
			);
			preserveLearnLevelCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_LEARN_LEVEL_SELECTED_SETTING_NAME))
			);
			preserveRelevanceCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_RELEVANCE_LEVEL_SELECTED_SETTING_NAME))
			);
			preserveDescriptionCheckBox.setSelected(
				Boolean.getBoolean(Settings.getInstance().getSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_DESCRIPTION_SELECTED_SETTING_NAME))
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
		
		learnLevelGridPane.add(customLearnLevelRadioButton, 0, 0);
		learnLevelGridPane.add(learnLevelTextField, 1, 0);
		learnLevelGridPane.add(predefinedLearnLevelRadioButton, 0, 1);
		learnLevelGridPane.add(learnLevelComboBox, 1, 1);
		
		inputGridPane.add(learnLevelGridPane, 2, 6);

		inputGridPane.add(preserveRelevanceCheckBox, 0, 7);
		inputGridPane.add(relevanceLevelLabel, 1, 7);
		
		relevanceLevelGridPane.add(customRelevanceLevelRadioButton, 0, 0);
		relevanceLevelGridPane.add(relevanceLevelTextField, 1, 0);
		relevanceLevelGridPane.add(predefinedRelevanceLevelRadioButton, 0, 1);
		relevanceLevelGridPane.add(relevanceLevelComboBox, 1, 1);
		
		inputGridPane.add(relevanceLevelGridPane, 2, 7);

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
	
	private void applySettings() {
		
	}
	
	@Override
	protected void addFocusChangeListeners() {
		firstLanguageTranslationsTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = firstLanguageTranslationsTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		firstLanguagePhoneticScriptTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = firstLanguagePhoneticScriptTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		secondLanguageTranslationsTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = secondLanguageTranslationsTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		secondLanguagePhoneticScriptTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = secondLanguagePhoneticScriptTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		topicTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = topicTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		chapterTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = chapterTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		learnLevelTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = learnLevelTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		relevanceLevelTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = relevanceLevelTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});

		descriptionTextArea.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = descriptionTextArea;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
	}

	private void addVocableButtonActionPerformed() {
		if (isInputValid()) {
			
			Vocable additionalVocable = null;
			
			
			String learnLevel = null;
			String relevanceLevel = null;
			
			if(customLearnLevelRadioButton.isSelected()) {
				learnLevel = learnLevelTextField.getText();
			} else if(predefinedLearnLevelRadioButton.isSelected()) {
				learnLevel = learnLevelComboBox.getSelectionModel().getSelectedItem();
			}
			
			if(customRelevanceLevelRadioButton.isSelected()) {
				relevanceLevel = relevanceLevelTextField.getText();
			} else if(predefinedRelevanceLevelRadioButton.isSelected()) {
				relevanceLevel = relevanceLevelComboBox.getSelectionModel().getSelectedItem();
			}
			
			try {
				additionalVocable = new Vocable(
					Arrays.asList(firstLanguageTranslationsTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(firstLanguagePhoneticScriptTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(secondLanguageTranslationsTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(secondLanguagePhoneticScriptTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(topicTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					Arrays.asList(chapterTextField.getText().split(Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME), -1)),
					learnLevel,
					relevanceLevel,
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
				//ControlFXDialogDisplayer.showAddingDuplicateVocableDialog(this);
				DialogInstanceManager.getAddingDuplicateVocableDialogInstance(this).show();
			}
			
		} else {
			ControlFXDialogDisplayer.showEmptyInputFieldsDialog(this);
		}
	}

	private void clearButtonActionPerformed() {
		firstLanguageTranslationsTextField.clear();
		firstLanguagePhoneticScriptTextField.clear();
		secondLanguageTranslationsTextField.clear();
		secondLanguagePhoneticScriptTextField.clear();
		topicTextField.clear();
		chapterTextField.clear();
		learnLevelTextField.clear();
		learnLevelComboBox.getSelectionModel().clearSelection();
		relevanceLevelTextField.clear();
		relevanceLevelComboBox.getSelectionModel().clearSelection();
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
			learnLevelComboBox.getSelectionModel().clearSelection();
			predefinedLearnLevelRadioButton.setSelected(true);
			customLearnLevelRadioButton.setSelected(false);
		} else {
			if(predefinedLearnLevelRadioButton.isSelected()) {
				learnLevelTextField.clear();
			} else if (customLearnLevelRadioButton.isSelected()) {
				learnLevelComboBox.getSelectionModel().clearSelection();
				predefinedLearnLevelRadioButton.setSelected(false);
				customLearnLevelRadioButton.setSelected(true);
			}
		}

		if (!preserveRelevanceCheckBox.isSelected()) {
			relevanceLevelTextField.clear();
			relevanceLevelComboBox.getSelectionModel().clearSelection();
			predefinedRelevanceLevelRadioButton.setSelected(true);
			customRelevanceLevelRadioButton.setSelected(false);
		} else {
			if(predefinedRelevanceLevelRadioButton.isSelected()) {
				relevanceLevelTextField.clear();
			} else if(customRelevanceLevelRadioButton.isSelected()) {
				relevanceLevelComboBox.getSelectionModel().clearSelection();
				predefinedRelevanceLevelRadioButton.setSelected(false);
				customRelevanceLevelRadioButton.setSelected(true);
			}
		}

		if (!preserveDescriptionCheckBox.isSelected()) {
			descriptionTextArea.clear();
		}
	}

	private boolean isInputValid() {
		return (
			!firstLanguageTranslationsTextField.getText().isEmpty() &&
			!firstLanguagePhoneticScriptTextField.getText().isEmpty() &&
			!secondLanguageTranslationsTextField.getText().isEmpty() &&
			!secondLanguagePhoneticScriptTextField.getText().isEmpty() &&
			!topicTextField.getText().isEmpty() &&
			!chapterTextField.getText().isEmpty() &&
			!(learnLevelTextField.getText().isEmpty() && customLearnLevelRadioButton.isSelected()) &&
			!(learnLevelComboBox.getSelectionModel().isEmpty() && predefinedLearnLevelRadioButton.isSelected()) &&
			!(relevanceLevelTextField.getText().isEmpty() && customRelevanceLevelRadioButton.isSelected()) &&
			!(relevanceLevelComboBox.getSelectionModel().isEmpty() && predefinedRelevanceLevelRadioButton.isSelected()) &&
			!descriptionTextArea.getText().isEmpty()
		);
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
	
	private void registerAsListener() {
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
	}
	
	@Override
	public void update(String settingName, String settingValue) {
		actionsForObservedSettingsChanges.get(settingName).execute(settingValue);
	}

	private void loadSettings() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void addCSSClasses() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void addPropertyChangeListeners() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void setActionsForNotifications() {
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> firstLanguageLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> firstLanguagePhoneticScriptLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> secondLanguageLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> secondLanguagePhoneticScriptLabel.setText(value + ":")
		);
	}
}
