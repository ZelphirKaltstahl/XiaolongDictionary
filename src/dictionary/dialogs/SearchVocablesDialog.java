/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import dictionary.manager.DialogInstanceManager;
import dictionary.buttons.InsertSpecialCharacterButton;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.listeners.SettingsPropertyChangeListener;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Action;
import dictionary.model.Settings;
import dictionary.model.VocableSearchData;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author XiaoLong
 */
public class SearchVocablesDialog extends XLDDialog implements SettingsPropertyChangeListener {

	private Scene scene;
	private VBox containingVBox;
	private GridPane searchOptionsGridPane;
	private HBox insertSpecialCharacterButtonHBox;
	private HBox buttonHBox;
	private GridPane gridPane;

	private Button searchButton;
	private Button cancelButton;
	private Button clearButton;
	private InsertSpecialCharacterButton insertSpecialCharacterButton;

	private CheckBox wholeWordCheckBox;
	private CheckBox caseSensitiveCheckBox;
	private RadioButton standardSearchRadioButton;
	private RadioButton andSearchRadioButton;
	private RadioButton orSearchRadioButton;
	private CheckBox negateSearchCheckBox;

	private ToggleGroup radioButtonGroup;

	private CheckBox searchFirstLanguageTranslationsCheckBox;
	private CheckBox searchFirstLanguagePhoneticScriptCheckBox;
	private CheckBox searchSecondLanguageTranslationsCheckBox;
	private CheckBox searchSecondLanguagePhoneticScriptCheckBox;
	private CheckBox searchTopicCheckBox;
	private CheckBox searchChapterCheckBox;
	private CheckBox searchLearnLevelCheckBox;
	private CheckBox searchRelevanceLevelCheckBox;
	private CheckBox searchDescriptionCheckBox;

	private CheckBox negateFirstLanguageTranslationsCheckBox;
	private CheckBox negateFirstLanguagePhoneticScriptCheckBox;
	private CheckBox negateSecondLanguageTranslationsCheckBox;
	private CheckBox negateSecondLanguagePhoneticScriptCheckBox;
	private CheckBox negateTopicCheckBox;
	private CheckBox negateChapterCheckBox;
	private CheckBox negateLearnLevelCheckBox;
	private CheckBox negateRelevanceLevelCheckBox;
	private CheckBox negateDescriptionCheckBox;

	private Label firstLanguageTranslationsLabel;
	private Label firstLanguagePhoneticScriptLabel;
	private Label secondLanguageTranslationsLabel;
	private Label secondLanguagePhoneticScriptLabel;
	private Label topicLabel;
	private Label chapterLabel;
	private Label learnLevelLabel;
	private Label relevanceLevelLabel;
	private Label descriptionLabel;
	

	private TextField firstLanguageTranslationsTextField;
	private TextField firstLanguagePhoneticScriptTextField;
	private TextField secondLanguageTranslationsTextField;
	private TextField secondLanguagePhoneticScriptTextField;
	private TextField topicTextField;
	private TextField chapterTextField;
	private TextField descriptionTextField;

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
	
	
	private final HashMap<String, Action<String>> actionsForObservedSettingsChanges = new HashMap<>();
	
	public SearchVocablesDialog(Modality modality) {
		super(modality);
	}
	
	public void initialize() {
		initializeControls();
		addControls();
		addActionListeners();
		addFocusChangeListeners();
		setActionsForNotifications();
		registerAsListener();
	}
	
	@Override
	protected void initializeControls() {
		this.setTitle("Search vocables");
		this.setResizable(false);

		containingVBox = new VBox();
		containingVBox.setPadding(new Insets(10, 10, 10, 10));

		gridPane = new GridPane();
		gridPane.setHgap(12);
		gridPane.setVgap(5);
		gridPane.setAlignment(Pos.CENTER_LEFT);

		searchOptionsGridPane = new GridPane();
		searchOptionsGridPane.setPadding(new Insets(20, 0, 0, 0));
		searchOptionsGridPane.setHgap(7);
		searchOptionsGridPane.setVgap(7);
		searchOptionsGridPane.setAlignment(Pos.CENTER_LEFT);

		buttonHBox = new HBox();
		buttonHBox.setPadding(new Insets(20, 0, 0, 0));
		buttonHBox.setSpacing(5);
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		
		scene = new Scene(containingVBox);

		searchButton = new Button("Search");
		clearButton = new Button("Clear");
		cancelButton = new Button("Cancel");
		
		insertSpecialCharacterButtonHBox = new HBox();
		insertSpecialCharacterButtonHBox.setAlignment(Pos.CENTER_RIGHT);
		insertSpecialCharacterButton = new InsertSpecialCharacterButton();

		wholeWordCheckBox = new CheckBox("whole word");
		caseSensitiveCheckBox = new CheckBox("case sensitive");
		andSearchRadioButton = new RadioButton("AND search");
		orSearchRadioButton = new RadioButton("OR search");
		standardSearchRadioButton = new RadioButton("standard search");
		standardSearchRadioButton.setSelected(true);

		negateSearchCheckBox = new CheckBox("NOT search");

		radioButtonGroup = new ToggleGroup();

		standardSearchRadioButton.setToggleGroup(radioButtonGroup);
		andSearchRadioButton.setToggleGroup(radioButtonGroup);
		orSearchRadioButton.setToggleGroup(radioButtonGroup);

		searchFirstLanguageTranslationsCheckBox = new CheckBox("use");
		searchFirstLanguagePhoneticScriptCheckBox = new CheckBox("use");
		searchSecondLanguageTranslationsCheckBox = new CheckBox("use");
		searchSecondLanguagePhoneticScriptCheckBox = new CheckBox("use");
		searchTopicCheckBox = new CheckBox("use");
		searchChapterCheckBox = new CheckBox("use");
		searchLearnLevelCheckBox = new CheckBox("use");
		searchRelevanceLevelCheckBox = new CheckBox("use");
		searchDescriptionCheckBox = new CheckBox("use");

		negateFirstLanguageTranslationsCheckBox = new CheckBox("not");
		negateFirstLanguagePhoneticScriptCheckBox = new CheckBox("not");
		negateSecondLanguageTranslationsCheckBox = new CheckBox("not");
		negateSecondLanguagePhoneticScriptCheckBox = new CheckBox("not");
		negateTopicCheckBox = new CheckBox("not");
		negateChapterCheckBox = new CheckBox("not");
		negateLearnLevelCheckBox = new CheckBox("not");
		negateRelevanceLevelCheckBox = new CheckBox("not");
		negateDescriptionCheckBox = new CheckBox("not");

		try {
			firstLanguageTranslationsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME));
			firstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
			secondLanguageTranslationsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME));
			secondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME));
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(SearchVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		topicLabel = new Label("Topic");
		chapterLabel = new Label("Chapter");
		learnLevelLabel = new Label("Learn level");
		relevanceLevelLabel = new Label("Relevance level");
		descriptionLabel = new Label("Description");

		firstLanguageTranslationsTextField = new TextField();
		firstLanguagePhoneticScriptTextField = new TextField();
		secondLanguageTranslationsTextField = new TextField();
		secondLanguagePhoneticScriptTextField = new TextField();
		topicTextField = new TextField();
		chapterTextField = new TextField();
		
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
		
		descriptionTextField = new TextField();
		
		lastActiveTextInputControl = firstLanguageTranslationsTextField;
	}

	@Override
	protected void addControls() {
		gridPane.add(searchFirstLanguageTranslationsCheckBox, 0, 0);
		gridPane.add(negateFirstLanguageTranslationsCheckBox, 1, 0);
		gridPane.add(firstLanguageTranslationsLabel, 2, 0);
		gridPane.add(firstLanguageTranslationsTextField, 3, 0);

		gridPane.add(searchFirstLanguagePhoneticScriptCheckBox, 0, 1);
		gridPane.add(negateFirstLanguagePhoneticScriptCheckBox, 1, 1);
		gridPane.add(firstLanguagePhoneticScriptLabel, 2, 1);
		gridPane.add(firstLanguagePhoneticScriptTextField, 3, 1);

		gridPane.add(searchSecondLanguageTranslationsCheckBox, 0, 2);
		gridPane.add(negateSecondLanguageTranslationsCheckBox, 1, 2);
		gridPane.add(secondLanguageTranslationsLabel, 2, 2);
		gridPane.add(secondLanguageTranslationsTextField, 3, 2);

		gridPane.add(searchSecondLanguagePhoneticScriptCheckBox, 0, 3);
		gridPane.add(negateSecondLanguagePhoneticScriptCheckBox, 1, 3);
		gridPane.add(secondLanguagePhoneticScriptLabel, 2, 3);
		gridPane.add(secondLanguagePhoneticScriptTextField, 3, 3);

		gridPane.add(searchTopicCheckBox, 0, 4);
		gridPane.add(negateTopicCheckBox, 1, 4);
		gridPane.add(topicLabel, 2, 4);
		gridPane.add(topicTextField, 3, 4);

		gridPane.add(searchChapterCheckBox, 0, 5);
		gridPane.add(negateChapterCheckBox, 1, 5);
		gridPane.add(chapterLabel, 2, 5);
		gridPane.add(chapterTextField, 3, 5);

		gridPane.add(searchLearnLevelCheckBox, 0, 6);
		gridPane.add(negateLearnLevelCheckBox, 1, 6);
		gridPane.add(learnLevelLabel, 2, 6);
		gridPane.add(learnLevelGridPane, 3, 6);
		
		learnLevelGridPane.add(customLearnLevelRadioButton, 0, 0);
		learnLevelGridPane.add(learnLevelTextField, 1, 0);
		learnLevelGridPane.add(predefinedLearnLevelRadioButton, 0, 1);
		learnLevelGridPane.add(learnLevelComboBox, 1, 1);
		
		gridPane.add(searchRelevanceLevelCheckBox, 0, 7);
		gridPane.add(negateRelevanceLevelCheckBox, 1, 7);
		gridPane.add(relevanceLevelLabel, 2, 7);
		gridPane.add(relevanceLevelGridPane, 3, 7);
		
		relevanceLevelGridPane.add(customRelevanceLevelRadioButton, 0, 0);
		relevanceLevelGridPane.add(relevanceLevelTextField, 1, 0);
		relevanceLevelGridPane.add(predefinedRelevanceLevelRadioButton, 0, 1);
		relevanceLevelGridPane.add(relevanceLevelComboBox, 1, 1);
		
		gridPane.add(searchDescriptionCheckBox, 0, 8);
		gridPane.add(negateDescriptionCheckBox, 1, 8);
		gridPane.add(descriptionLabel, 2, 8);
		gridPane.add(descriptionTextField, 3, 8);
		
		gridPane.add(insertSpecialCharacterButtonHBox, 3, 9);
		insertSpecialCharacterButtonHBox.getChildren().add(insertSpecialCharacterButton);

		containingVBox.getChildren().add(gridPane);

		searchOptionsGridPane.add(wholeWordCheckBox, 0, 0);
		searchOptionsGridPane.add(caseSensitiveCheckBox, 1, 0);

		searchOptionsGridPane.add(standardSearchRadioButton, 0, 1);
		searchOptionsGridPane.add(andSearchRadioButton, 1, 1);
		searchOptionsGridPane.add(orSearchRadioButton, 2, 1);

		searchOptionsGridPane.add(negateSearchCheckBox, 0, 2);
		containingVBox.getChildren().add(searchOptionsGridPane);

		buttonHBox.getChildren().add(cancelButton);
		buttonHBox.getChildren().add(clearButton);
		buttonHBox.getChildren().add(searchButton);
		containingVBox.getChildren().add(buttonHBox);

		//Scene
		setScene(scene);
	}

	@Override
	protected void addActionListeners() {
		cancelButton.setOnAction((ActionEvent event) -> {
			cancelButtonActionPerformed();
		});
		
		clearButton.setOnAction((ActionEvent event) -> {
			clearButtonActionPerformed();
		});

		searchButton.setOnAction((ActionEvent event) -> {
			searchButtonActionPerformed();
		});
		
		insertSpecialCharacterButton.setOnAction((ActionEvent event) -> {
			insertSpecialCharacterButtonActionPerformed();
		});
	}

	@Override
	protected void addFocusChangeListeners() {
		firstLanguageTranslationsTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = firstLanguageTranslationsTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		firstLanguagePhoneticScriptTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = firstLanguagePhoneticScriptTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		secondLanguageTranslationsTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = secondLanguageTranslationsTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		secondLanguagePhoneticScriptTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = secondLanguagePhoneticScriptTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		topicTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = topicTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		chapterTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = chapterTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		topicTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = topicTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		learnLevelTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = learnLevelTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		relevanceLevelTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = relevanceLevelTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
		
		descriptionTextField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (newValue) {
				lastActiveTextInputControl = descriptionTextField;
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(DialogInstanceManager.getSearchVocablesDialogInstance());
				DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			} else {
				caretPosition = lastActiveTextInputControl.getCaretPosition();
			}
		});
	}

	private void cancelButtonActionPerformed() {
		hide();
	}

	private void clearButtonActionPerformed() {
		firstLanguageTranslationsTextField.clear();
		firstLanguagePhoneticScriptTextField.clear();
		secondLanguageTranslationsTextField.clear();
		secondLanguagePhoneticScriptTextField.clear();
		topicTextField.clear();
		chapterTextField.clear();
		learnLevelTextField.clear();
		relevanceLevelTextField.clear();
		descriptionTextField.clear();
		
		lastActiveTextInputControl = firstLanguageTranslationsTextField;
	}
	
	private void searchButtonActionPerformed() {
		if (isAtLeastOneSearchAttributeUsed()) {
			String learnLevel = null;
			String relevanceLevel = null;
			
			if (customLearnLevelRadioButton.isSelected()) {
				learnLevel = learnLevelTextField.getText();
			} else if (predefinedLearnLevelRadioButton.isSelected()) {
				learnLevel = learnLevelComboBox.getSelectionModel().getSelectedItem();
			}
			
			if (customRelevanceLevelRadioButton.isSelected()) {
				relevanceLevel = relevanceLevelTextField.getText();
			} else if (predefinedRelevanceLevelRadioButton.isSelected()) {
				relevanceLevel = learnLevelComboBox.getSelectionModel().getSelectedItem();
			}
			
			VocableSearchData vocableSearchData = new VocableSearchData(
				searchFirstLanguageTranslationsCheckBox.isSelected(), 
				searchFirstLanguagePhoneticScriptCheckBox.isSelected(), 
				searchSecondLanguageTranslationsCheckBox.isSelected(), 
				searchSecondLanguagePhoneticScriptCheckBox.isSelected(), 
				searchTopicCheckBox.isSelected(), 
				searchChapterCheckBox.isSelected(), 
				searchLearnLevelCheckBox.isSelected(), 
				searchRelevanceLevelCheckBox.isSelected(), 
				searchDescriptionCheckBox.isSelected(), 

				negateFirstLanguageTranslationsCheckBox.isSelected(),
				negateFirstLanguagePhoneticScriptCheckBox.isSelected(),
				negateSecondLanguageTranslationsCheckBox.isSelected(),
				negateSecondLanguagePhoneticScriptCheckBox.isSelected(),
				negateTopicCheckBox.isSelected(),
				negateChapterCheckBox.isSelected(),
				negateLearnLevelCheckBox.isSelected(),
				negateRelevanceLevelCheckBox.isSelected(),
				negateDescriptionCheckBox.isSelected(),

				firstLanguageTranslationsTextField.getText(), 
				firstLanguagePhoneticScriptTextField.getText(), 
				secondLanguageTranslationsTextField.getText(), 
				secondLanguagePhoneticScriptTextField.getText(), 
				topicTextField.getText(), 
				chapterTextField.getText(), 
				learnLevel,
				relevanceLevel,
				descriptionTextField.getText(), 

				negateSearchCheckBox.isSelected(), 
				andSearchRadioButton.isSelected(), 
				orSearchRadioButton.isSelected(), 
				standardSearchRadioButton.isSelected(),
				caseSensitiveCheckBox.isSelected(), 
				wholeWordCheckBox.isSelected()
			);
			//vocableSearchData.print();
			ManagerInstanceManager.getVocableManagerInstance().searchVocables(vocableSearchData);
		} else {
			ControlFXDialogDisplayer.showNoVocableAttributesSelectedForSearch(this);
		}
	}

	private boolean isAtLeastOneSearchAttributeUsed() {
		return searchFirstLanguageTranslationsCheckBox.isSelected()
			|| searchFirstLanguagePhoneticScriptCheckBox.isSelected()
			|| searchSecondLanguageTranslationsCheckBox.isSelected()
			|| searchSecondLanguagePhoneticScriptCheckBox.isSelected()
			|| searchTopicCheckBox.isSelected()
			|| searchChapterCheckBox.isSelected()
			|| searchLearnLevelCheckBox.isSelected()
			|| searchRelevanceLevelCheckBox.isSelected()
			|| searchDescriptionCheckBox.isSelected();
	}

	@Override
	public void update(String settingName, String settingValue) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private void registerAsListener() {
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
	}
	
	private void setActionsForNotifications() {
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> firstLanguageTranslationsLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> firstLanguagePhoneticScriptLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> secondLanguageTranslationsLabel.setText(value + ":")
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> secondLanguagePhoneticScriptLabel.setText(value + ":")
		);
	}
}
