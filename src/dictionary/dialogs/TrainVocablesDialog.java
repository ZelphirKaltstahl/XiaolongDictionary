/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import dictionary.customcontrols.XLDBigCharacterBox;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.listeners.SettingsPropertyChangeListener;
import dictionary.manager.CustomControlsInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * TODO: change disabled textfield background color
 * TODO: Find a better way to react on Settings, maybe using a HashMap<String, Action>, with Action having a method taking a String, the new settingsValue.
 * TODO: Complete IF statements reacting on settings changes
 * TODO: Add a Label, TextField and Button for going to a specific vocable, or only a button that opens a dialog where the user can enter a number
 * TODO: Should the settings be SimpleStringProperty? That would save a lot of trouble in other classes.
 * TODO: define Stops for clicks on the slider, doesn't work so far, only works with keyboard, it should be the other way around, so that one can go one vocable further by using the keyboard and many by using mouse
 * @author xiaolong
 */
public class TrainVocablesDialog extends XLDDialog implements SettingsPropertyChangeListener {
	private static final String HIDDEN_TEXT = "Hidden ...";

	private List<Vocable> listOfTrainingVocables;

	private Scene scene;
	
	// containers
	//private VBox containingVBox;
	private GridPane root;
	
	private GridPane translationsGridPane;
	
	private HBox xldBigCharacterBoxWrapperHBox;
	
	private VBox descriptionVBox;
	
	private GridPane newLevelsGridPane;
	
	private VBox statisticsVBox;
	
	private GridPane actionButtonGridPane;
	
	// controls
	private Label firstLanguageTranslationLabel;
	private TextField firstLanguageTranslationTextField;
	private Button showHideFirstLanguageButton;
	
	private Label secondLanguageTranslationLabel;
	private TextField secondLanguageTranslationTextField;
	private Button showHideSecondLanguageButton;
	
	private Label firstLanguagePhoneticScriptLabel;
	private TextField firstLanguagePhoneticScriptTextField;
	private Button showHideFirstLanguagePhoneticScriptButton;
	
	private Label secondLanguagePhoneticScriptLabel;
	private TextField secondLanguagePhoneticScriptTextField;
	private Button showHideSecondLanguagePhoneticScriptButton;
	
	private XLDBigCharacterBox xldBigCharacterBox;
	
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
	private Button showHideDescriptionButton;
	
	private Label newLearnLevelLabel;
	private ToggleGroup newLearnLevelRadioButtonsToggleGroup;
	private RadioButton customNewLearnLevelRadioButton;
	private RadioButton predefinedNewLearnLevelRadioButton;
	private TextField newLearnLevelTextField;
	private ComboBox<String> newLearnLevelComboBox;
	
	private Label newRelevanceLevelLabel;
	private ToggleGroup newRelevanceLevelRadioButtonsToggleGroup;
	private RadioButton customNewRelevanceLevelRadioButton;
	private RadioButton predefinedNewRelevanceLevelRadioButton;
	private TextField newRelevanceLevelTextField;
	private ComboBox<String> newRelevanceLevelComboBox;
	
	private final SimpleStringProperty vocableNumbleLabelPrefixString = new SimpleStringProperty("Vocable Number: ");
	private Label vocableNumberLabel;
	private Slider vocableNumberSlider;
	private Label learnedVocablesLabel;
	private ProgressBar learnedVocablesProgressBar;
		
	private Button previousVocableButton;
	private Button nextVocableButton;
	private Button restartVocableTrainingButton;
	private Button stopVocableTrainingButton;
	
	// other
	private List<Vocable> trainingVocables;
	private final SimpleIntegerProperty currentVocableNumber = new SimpleIntegerProperty(0);
	private final SimpleDoubleProperty learnedVocablesProgress = new SimpleDoubleProperty(0);
	
	private final HashMap<Vocable, Boolean> learnedVocablesHashMap = new HashMap<>(); // which vocables are already learned?
	
	private boolean firstLanguageTranslationShown;
	private boolean firstLanguagePhoneticScriptShown;
	private boolean secondLanguageTranslationShown;
	private boolean secondLanguagePhoneticScriptShown;
	private boolean descriptionShown;
	
	
	public TrainVocablesDialog(Modality modality) {
		super(modality);
	}
	
	public void setTrainingVocables(List<Vocable> listOfVocables) {
		this.trainingVocables = listOfVocables;
		
		currentVocableNumber.set(0);
		learnedVocablesProgress.set(0);
		
		// no vocable is initially learned - TODO: change so that vocables which have a level that indicates that they are "learned" already count as learned vocables
		listOfVocables.forEach((vocable) -> learnedVocablesHashMap.put(vocable, Boolean.FALSE));
	}
	
	private void goToVocable(int vocableNumber) {
		setVocable(trainingVocables.get(0));
		setVocableNumber(vocableNumber);
	}
	
	private void setVocableNumber(int vocableNumber) {
		currentVocableNumber.set(vocableNumber);
	}
	
	private void setVocable(Vocable vocable) {
		if(firstLanguageTranslationShown) {
			firstLanguageTranslationTextField.setText(vocable.getFirstLanguageTranslationsAsString());
		} else {
			firstLanguageTranslationTextField.setText(HIDDEN_TEXT);
		}
		
		if(firstLanguagePhoneticScriptShown) {
			firstLanguagePhoneticScriptTextField.setText(vocable.getFirstLanguagePhoneticScriptsAsString());
		} else {
			firstLanguagePhoneticScriptTextField.setText(HIDDEN_TEXT);
		}
		
		if(secondLanguageTranslationShown) {
			secondLanguageTranslationTextField.setText(vocable.getSecondLanguageTranslationsAsString());
		} else {
			secondLanguageTranslationTextField.setText(HIDDEN_TEXT);
		}
		
		if(secondLanguagePhoneticScriptShown) {
			secondLanguagePhoneticScriptTextField.setText(vocable.getSecondLanguagePhoneticScriptsAsString());
		} else {
			secondLanguagePhoneticScriptTextField.setText(HIDDEN_TEXT);
		}
		
		if(secondLanguageTranslationShown) {
			xldBigCharacterBox.setCharacters(vocable.getSecondLanguageTranslationsAsString());
		} else {
			xldBigCharacterBox.setCharacters(vocable.getSecondLanguageTranslationsAsString());
			xldBigCharacterBox.hideCharacters();
		}
		
		if(descriptionShown) {
			descriptionTextArea.setText(vocable.getDescription());
		} else {
			descriptionTextArea.setText(HIDDEN_TEXT);
		}
		
		if(newLearnLevelComboBox.getItems().contains(vocable.getLearnLevel())) {
			predefinedNewLearnLevelRadioButton.setSelected(true);
			customNewLearnLevelRadioButton.setSelected(false);
			newLearnLevelTextField.setEditable(false);
			newLearnLevelComboBox.setEditable(true);
		} else {
			predefinedNewLearnLevelRadioButton.setSelected(false);
			customNewLearnLevelRadioButton.setSelected(true);
			newLearnLevelTextField.setText(vocable.getLearnLevel());
			newLearnLevelTextField.setEditable(true);
			newLearnLevelComboBox.setEditable(false);
		}
		
		if(newRelevanceLevelComboBox.getItems().contains(vocable.getRelevanceLevel())) {
			predefinedNewRelevanceLevelRadioButton.setSelected(true);
			customNewRelevanceLevelRadioButton.setSelected(false);
			newRelevanceLevelTextField.setEditable(false);
			newRelevanceLevelComboBox.setEditable(true);
		} else {
			predefinedNewRelevanceLevelRadioButton.setSelected(false);
			customNewRelevanceLevelRadioButton.setSelected(true);
			newRelevanceLevelTextField.setText(vocable.getRelevanceLevel());
			newRelevanceLevelTextField.setEditable(true);
			newRelevanceLevelComboBox.setEditable(false);
		}
		
		
		//input fields falls nicht hidden
		//werte aus der vokabel fuer die verschiedenen Level
		//System.out.println("Settings Vocable Number ");
	}
	
	private void setVocable(int vocableNumber) {
		System.out.println("setting vocable number " + vocableNumber);
		setVocable(trainingVocables.get(vocableNumber));
	}
	
	private void nextVocable() {
		
	}
	
	private void previousVocable() {
		
	}
	
	private void restartTraining() {
		goToVocable(0);
	}
	
	private void stopTraining() {
		hide();
	}
	
	private void startVocableTraining() {
		setVocable(0);
	}
	
	private void loadSettings() {
		try {
			firstLanguageTranslationShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME
			).equals(Boolean.TRUE.toString());
			
			firstLanguagePhoneticScriptShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME
			).equals(Boolean.TRUE.toString());
			
			secondLanguageTranslationShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME
			).equals(Boolean.TRUE.toString());
			
			secondLanguagePhoneticScriptShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME
			).equals(Boolean.TRUE.toString());
			
			descriptionShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME
			).equals(Boolean.TRUE.toString());
			
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(TrainVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * This method takes the previously loaded settings and changes the controls of the {@link TrainVocablesDialog} in a way that they reflect the settings.
	 */
	private void applySettings() {
		if(!firstLanguageTranslationShown) {
			hideFirstLanguage();
		} else {
			showFirstLanguage();
		}
		if(!firstLanguagePhoneticScriptShown) {
			hideFirstLanguagePhoneticScript();
		} else {
			showFirstLanguagePhoneticScript();
		}
		if(!secondLanguageTranslationShown) {
			hideSecondLanguage();
		} else {
			showSecondLanguage();
		}
		if(!secondLanguagePhoneticScriptShown) {
			hideSecondLanguagePhoneticScript();
		} else {
			showSecondLanguagePhoneticScript();
		}
		if(!descriptionShown) {
			hideDescription();
		} else {
			showDescription();
		}
	}
	
	private void addCSSClasses() {
		//scene.getStylesheets().add("../../resources/css/style.css");
		firstLanguageTranslationTextField.getStyleClass().add("disablable_text_field");
		firstLanguagePhoneticScriptTextField.getStyleClass().add("disablable_text_field");
		secondLanguageTranslationTextField.getStyleClass().add("disablable_text_field");
		secondLanguagePhoneticScriptTextField.getStyleClass().add("disablable_text_field");
		descriptionTextArea.getStyleClass().add("disablable_text_field");
	}
	
	public void initialize(List<Vocable> listOfVocables) {
		setTrainingVocables(listOfVocables);
		loadSettings();
		
		initializeControls();
		addCSSClasses();
		addControls();
		
		setMinimumSizes();
		
		applySettings();
		
		addActionListeners();
		addFocusChangeListeners();
		System.out.println("In Initialize");
		addPropertyChangeListeners();
		
		registerAsListener();
		
		startVocableTraining();
	}
	
	@Override
	protected void initializeControls() {
		this.setTitle("Vocable Training");
		this.setResizable(false);
		
		// containers
		root = new GridPane();
		root.setPadding(new Insets(10));
		root.setHgap(20);
		root.setVgap(10);
		root.setAlignment(Pos.CENTER);
		
		translationsGridPane = new GridPane();
		translationsGridPane.setHgap(10);
		translationsGridPane.setVgap(4);
		
		xldBigCharacterBoxWrapperHBox = new HBox();
		xldBigCharacterBoxWrapperHBox.setFillHeight(true);
		xldBigCharacterBoxWrapperHBox.setAlignment(Pos.TOP_RIGHT);
		
		descriptionVBox = new VBox(10);
		descriptionVBox.setPrefWidth(200);
		descriptionVBox.setPrefHeight(150);
		
		newLevelsGridPane = new GridPane();
		newLevelsGridPane.setHgap(5);
		newLevelsGridPane.setVgap(5);
		
		statisticsVBox = new VBox(10);
		actionButtonGridPane = new GridPane();
		actionButtonGridPane.setMaxWidth(200);
		
		actionButtonGridPane.getColumnConstraints().add(new ColumnConstraints());
        actionButtonGridPane.getColumnConstraints().get(0).setPercentWidth(50);
		
		actionButtonGridPane.getColumnConstraints().add(new ColumnConstraints());
        actionButtonGridPane.getColumnConstraints().get(1).setPercentWidth(50);
		
		try {
			// controls
			firstLanguageTranslationLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME) + ":");
			firstLanguageTranslationTextField = new TextField();
			firstLanguageTranslationTextField.setMinWidth(200);
			firstLanguageTranslationTextField.setPrefWidth(200);
			showHideFirstLanguageButton = new Button("Show");
			
			secondLanguageTranslationLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			secondLanguageTranslationTextField = new TextField();
			showHideSecondLanguageButton = new Button("Show");
			
			firstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			firstLanguagePhoneticScriptTextField = new TextField();
			showHideFirstLanguagePhoneticScriptButton = new Button("Show");
			
			secondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			secondLanguagePhoneticScriptTextField = new TextField();
			showHideSecondLanguagePhoneticScriptButton = new Button("Show");
			
			xldBigCharacterBox = CustomControlsInstanceManager.createXLDBigCharacterBoxInstance(trainingVocables.get(0).getSecondLanguageTranslationsAsString(), "Characters");
			xldBigCharacterBox.setAlignment(Pos.TOP_RIGHT);
			
			descriptionLabel = new Label("Description" + ":");
			descriptionTextArea = new TextArea();
			descriptionTextArea.setWrapText(true);
			showHideDescriptionButton = new Button("Show");
			
			newLearnLevelLabel = new Label("New Learn Level" + ":");
			newLearnLevelRadioButtonsToggleGroup = new ToggleGroup();
			customNewLearnLevelRadioButton = new RadioButton("Custom");
			predefinedNewLearnLevelRadioButton = new RadioButton("Predefined");
			newLearnLevelRadioButtonsToggleGroup.getToggles().add(customNewLearnLevelRadioButton);
			newLearnLevelRadioButtonsToggleGroup.getToggles().add(predefinedNewLearnLevelRadioButton);
			newLearnLevelTextField = new TextField();
			
			String[] predefinedNewLearnLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVELS_SETTING_NAME).split(",", -1);
			ObservableList newLearnLevelChoices = FXCollections.observableArrayList(predefinedNewLearnLevelChoices);
			newLearnLevelComboBox = new ComboBox<>(newLearnLevelChoices);
			
			newRelevanceLevelLabel = new Label("New Relevance Level" + ":");
			newRelevanceLevelRadioButtonsToggleGroup = new ToggleGroup();
			customNewRelevanceLevelRadioButton = new RadioButton("Custom");
			predefinedNewRelevanceLevelRadioButton = new RadioButton("Predefined");
			newRelevanceLevelRadioButtonsToggleGroup.getToggles().add(customNewRelevanceLevelRadioButton);
			newRelevanceLevelRadioButtonsToggleGroup.getToggles().add(predefinedNewRelevanceLevelRadioButton);
			newRelevanceLevelTextField = new TextField();
			
			String[] predefinedNewRelevanceLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVELS_SETTING_NAME).split(",", -1);
			ObservableList newRelevanceLevelChoices = FXCollections.observableArrayList(predefinedNewRelevanceLevelChoices);
			newRelevanceLevelComboBox = new ComboBox<>(newRelevanceLevelChoices);
			
			vocableNumberLabel = new Label("Vocable Number" + ":");
			
			vocableNumberLabel.textProperty().bind(
				vocableNumbleLabelPrefixString.concat(
					currentVocableNumber.asString().concat(
						" of " + (trainingVocables.size()-1)
					)
				)
			);
			
			vocableNumberSlider = new Slider(0, trainingVocables.size()-1, currentVocableNumber.get());
			vocableNumberSlider.setOrientation(Orientation.HORIZONTAL);
			
			vocableNumberSlider.setBlockIncrement(1);
			vocableNumberSlider.setMajorTickUnit(trainingVocables.size()/10);
			vocableNumberSlider.setMinorTickCount(5);
			vocableNumberSlider.setShowTickLabels(true);
			vocableNumberSlider.setShowTickMarks(true);
			//vocableNumberSlider.setSnapToTicks(true);
			
			vocableNumberSlider.valueProperty().bindBidirectional(currentVocableNumber);
			
			learnedVocablesLabel = new Label("Learned Vocables" + ":");
			learnedVocablesProgressBar = new ProgressBar(0);
			learnedVocablesProgressBar.setMaxWidth(Double.MAX_VALUE);
			learnedVocablesProgressBar.setMaxHeight(14);
			learnedVocablesProgressBar.progressProperty().bind(learnedVocablesProgress); // bind progress property of the progressbar to the counter of learned vocables
			
			previousVocableButton = new Button("<--");
			previousVocableButton.setMaxWidth(Double.MAX_VALUE);
			nextVocableButton = new Button("-->");
			nextVocableButton.setMaxWidth(Double.MAX_VALUE);
			restartVocableTrainingButton = new Button("Restart");
			restartVocableTrainingButton.setMaxWidth(Double.MAX_VALUE);
			stopVocableTrainingButton = new Button("Stop");
			stopVocableTrainingButton.setMaxWidth(Double.MAX_VALUE);
			
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(TrainVocablesDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		scene = new Scene(root);
	}

	@Override
	protected void addControls() {
		
		//containingVBox.getChildren().addAll(translationsAndBigCharacterBoxHBox, descriptionAndLevelsHBox, statisticsAndActionButtonsHBox);
		root.add(translationsGridPane, 0, 0);
		root.add(xldBigCharacterBoxWrapperHBox, 1, 0);
		root.add(descriptionVBox, 0, 1);
		root.add(newLevelsGridPane, 0, 2);
		root.add(statisticsVBox, 0, 3);
		root.add(actionButtonGridPane, 1, 3);
		
		//translationsAndBigCharacterBoxHBox.getChildren().addAll(translationsGridPane, xldBigCharacterBox);
		
		// first hbox
		translationsGridPane.add(firstLanguageTranslationLabel, 0, 0);
		translationsGridPane.add(firstLanguageTranslationTextField, 0, 1);
		translationsGridPane.add(showHideFirstLanguageButton, 1, 1);
		
		translationsGridPane.add(secondLanguageTranslationLabel, 0, 2);
		translationsGridPane.add(secondLanguageTranslationTextField, 0, 3);
		translationsGridPane.add(showHideSecondLanguageButton, 1, 3);
		
		translationsGridPane.add(firstLanguagePhoneticScriptLabel, 2, 0);
		translationsGridPane.add(firstLanguagePhoneticScriptTextField, 2, 1);
		translationsGridPane.add(showHideFirstLanguagePhoneticScriptButton, 3, 1);
		
		translationsGridPane.add(secondLanguagePhoneticScriptLabel, 2, 2);
		translationsGridPane.add(secondLanguagePhoneticScriptTextField, 2, 3);
		translationsGridPane.add(showHideSecondLanguagePhoneticScriptButton, 3, 3);
		
		//xldBigCharacterBoxWrapperHBox.getChildren().add(xldBigCharacterBoxLabel);
		xldBigCharacterBoxWrapperHBox.getChildren().add(xldBigCharacterBox);
		
		
		// second hbox
		//descriptionAndLevelsHBox.getChildren().addAll(descriptionVBox, newLevelsGridPane);
		
		descriptionVBox.getChildren().addAll(descriptionLabel, descriptionTextArea, showHideDescriptionButton);
		
		newLevelsGridPane.add(newLearnLevelLabel, 0, 0);
		newLevelsGridPane.add(customNewLearnLevelRadioButton, 0, 1);
		newLevelsGridPane.add(newLearnLevelTextField, 1, 1);
		newLevelsGridPane.add(predefinedNewLearnLevelRadioButton, 0, 2);
		newLevelsGridPane.add(newLearnLevelComboBox, 1, 2);
		
		newLevelsGridPane.add(newRelevanceLevelLabel, 2, 0);
		newLevelsGridPane.add(customNewRelevanceLevelRadioButton, 2, 1);
		newLevelsGridPane.add(newRelevanceLevelTextField, 3, 1);
		newLevelsGridPane.add(predefinedNewRelevanceLevelRadioButton, 2, 2);
		newLevelsGridPane.add(newRelevanceLevelComboBox, 3, 2);
		
		// third row
		statisticsVBox.getChildren().addAll(vocableNumberLabel, vocableNumberSlider, learnedVocablesLabel, learnedVocablesProgressBar);
		
		actionButtonGridPane.add(previousVocableButton, 0, 0);
		actionButtonGridPane.add(nextVocableButton, 1, 0);
		actionButtonGridPane.add(restartVocableTrainingButton, 0, 1);
		actionButtonGridPane.add(stopVocableTrainingButton, 1, 1);
		actionButtonGridPane.setAlignment(Pos.BOTTOM_RIGHT);
		
		setScene(scene);
	}
	
	private void setMinimumSizes() {
		setMinWidth(getWidth());
		setMinHeight(getHeight());
		
		showHideFirstLanguageButton.setMinWidth(showHideFirstLanguageButton.getWidth());
		showHideFirstLanguagePhoneticScriptButton.setMinWidth(showHideFirstLanguagePhoneticScriptButton.getWidth());
		showHideSecondLanguageButton.setMinWidth(showHideSecondLanguageButton.getWidth());
		showHideSecondLanguagePhoneticScriptButton.setMinWidth(showHideSecondLanguagePhoneticScriptButton.getWidth());
		showHideDescriptionButton.setMinWidth(showHideDescriptionButton.getWidth());
	}

	@Override
	protected void addActionListeners() {
		System.out.println("Adding Action Listeners");
		
		showHideFirstLanguageButton.setOnAction((actionEvent) -> {
			toggleFirstLanguageVisibility();
			firstLanguageTranslationShown = !firstLanguageTranslationShown;
		});
		
		showHideFirstLanguagePhoneticScriptButton.setOnAction((actionEvent) -> {
			toggleFirstLanguagePhoneticScriptVisibility();
			firstLanguagePhoneticScriptShown = !firstLanguagePhoneticScriptShown;
		});
		
		showHideSecondLanguageButton.setOnAction((actionEvent) -> {
			toggleSecondLanguageVisibility();
			toggleXLDBigCharacterBoxVisibility();
			secondLanguageTranslationShown = !secondLanguageTranslationShown;
		});
		
		showHideSecondLanguagePhoneticScriptButton.setOnAction((actionEvent) -> {
			toggleSecondLanguagePhoneticScriptVisibility();
			secondLanguagePhoneticScriptShown = !secondLanguagePhoneticScriptShown;
		});
		
		showHideDescriptionButton.setOnAction((actionEvent) -> {
			toggleDescriptionVisibility();
			descriptionShown = !descriptionShown;
		});
		
		previousVocableButton.setOnAction((actionEvent) -> {
			if(currentVocableNumber.get() > 0) {
				currentVocableNumber.set(currentVocableNumber.get() - 1);
			} else {
				currentVocableNumber.set(trainingVocables.size()-1);
			}
		});
		
		nextVocableButton.setOnAction((actionEvent) -> {
			currentVocableNumber.set((currentVocableNumber.getValue() + 1) % trainingVocables.size());
		});
		
		stopVocableTrainingButton.setOnAction((actionEvent) -> {
			hide();
		});
		
		restartVocableTrainingButton.setOnAction((actionEvent) -> {
			setVocable(0);
			learnedVocablesHashMap.forEach((vocable, learned) -> learnedVocablesHashMap.put(vocable, false));
			learnedVocablesProgress.set(0.0);
		});
	}
	
	private void addPropertyChangeListeners() {
		System.out.println("Adding change listener");
		currentVocableNumber.addListener(
			(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
				if(!oldValue.equals(newValue)) {
					setVocable(newValue.intValue());
				}
			}
		);
	}
	
	private void registerAsListener() {
		/* Don't subscribe to these settings, they are only changed in this dialog but not reacted to
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, this);
		*/
		
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME, this);
		
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVELS_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVELS_SETTING_NAME, this);
		
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_DIRECTION_SETTING_NAME, this);
		
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
	}
	
	@Override
	protected void addFocusChangeListeners() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private void toggleFirstLanguageVisibility() {
		if(firstLanguageTranslationShown) {
			hideFirstLanguage();
		} else {
			showFirstLanguage();
		}
	}
	
	private void toggleFirstLanguagePhoneticScriptVisibility() {
		if(firstLanguagePhoneticScriptShown) {
			hideFirstLanguagePhoneticScript();
		} else {
			showFirstLanguagePhoneticScript();
		}
	}
	
	private void toggleSecondLanguageVisibility() {
		if(secondLanguageTranslationShown) {
			hideSecondLanguage();
		} else {
			showSecondLanguage();
		}
	}
	
	private void toggleXLDBigCharacterBoxVisibility() {
		if(secondLanguageTranslationShown) {
			xldBigCharacterBox.hideCharacters();
		} else {
			xldBigCharacterBox.showCharacters();
		}
	}
	
	private void toggleSecondLanguagePhoneticScriptVisibility() {
		if(secondLanguagePhoneticScriptShown) {
			hideSecondLanguagePhoneticScript();
		} else {
			showSecondLanguagePhoneticScript();
		}
	}
	
	private void toggleDescriptionVisibility() {
		if(descriptionShown) {
			hideDescription();
		} else {
			showDescription();
		}
	}
	
	private void hideFirstLanguage() {
		firstLanguageTranslationTextField.setText("Hidden ...");
		firstLanguageTranslationTextField.setEditable(false);
		showHideFirstLanguageButton.setText("Show");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showFirstLanguage() {
		firstLanguageTranslationTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getFirstLanguageTranslationsAsString());
		firstLanguageTranslationTextField.setEditable(true);
		showHideFirstLanguageButton.setText("Hide");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME, Boolean.toString(true));
	}
	
	private void hideFirstLanguagePhoneticScript() {
		firstLanguagePhoneticScriptTextField.setText("Hidden ...");
		firstLanguagePhoneticScriptTextField.setEditable(false);
		showHideFirstLanguagePhoneticScriptButton.setText("Show");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showFirstLanguagePhoneticScript() {
		firstLanguagePhoneticScriptTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getFirstLanguagePhoneticScriptsAsString());
		firstLanguagePhoneticScriptTextField.setEditable(true);
		showHideFirstLanguagePhoneticScriptButton.setText("Hide");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(true));
	}
	
	private void hideSecondLanguage() {
		secondLanguageTranslationTextField.setText("Hidden ...");
		secondLanguageTranslationTextField.setEditable(false);
		xldBigCharacterBox.hideCharacters();
		showHideSecondLanguageButton.setText("Show");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showSecondLanguage() {
		secondLanguageTranslationTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getSecondLanguageTranslationsAsString());
		secondLanguageTranslationTextField.setEditable(true); //TODO: Hintergrundfarbe fuer Textfelder die nicht editierbar sind im css festlegen
		xldBigCharacterBox.showCharacters();
		showHideSecondLanguageButton.setText("Hide");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME, Boolean.toString(true));
	}
	
	private void hideSecondLanguagePhoneticScript() {
		secondLanguagePhoneticScriptTextField.setText("Hidden ...");
		secondLanguagePhoneticScriptTextField.setEditable(false);
		showHideSecondLanguagePhoneticScriptButton.setText("Show");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showSecondLanguagePhoneticScript() {
		secondLanguagePhoneticScriptTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getSecondLanguagePhoneticScriptsAsString());
		secondLanguagePhoneticScriptTextField.setEditable(true);
		showHideSecondLanguagePhoneticScriptButton.setText("Hide");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(true));
	}
	
	private void hideDescription() {
		descriptionTextArea.setText("Hidden ...");
		descriptionTextArea.setEditable(false);
		showHideDescriptionButton.setText("Show");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showDescription() {
		descriptionTextArea.setText(trainingVocables.get(currentVocableNumber.intValue()).getDescription());
		descriptionTextArea.setEditable(true);
		showHideDescriptionButton.setText("Hide");
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, Boolean.toString(true));
	}

	@Override
	public void update(String settingName, String settingValue) {
		
		if(settingName.equals(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME)) {
			updateFirstLanguageName(settingValue);
			
		} else if (settingName.equals(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME)) {
			updateFirstLanguagePhoneticScriptName(settingValue);
			
		} else if (settingName.equals(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME)) {
			updateSecondLanguageName(settingValue);
			
		} else if (settingName.equals(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME)) {
			updateSecondLanguagePhoneticScriptName(settingValue);
			
		}
		//TODO: complete these if clauses for all subscribed settings values
	}
	
	private void updateFirstLanguageName(String firstLanguageName) {
		firstLanguageTranslationLabel.setText(firstLanguageName + ":");
	}
	
	private void updateFirstLanguagePhoneticScriptName(String firstLanguagePhoneticScriptName) {
		firstLanguagePhoneticScriptLabel.setText(firstLanguagePhoneticScriptName + ":");
	}
	
	private void updateSecondLanguageName(String secondLanguageName) {
		secondLanguageTranslationLabel.setText(secondLanguageName + ":");
	}
	
	private void updateSecondLanguagePhoneticScriptName(String secondLanguagePhoneticScriptName) {
		secondLanguagePhoneticScriptLabel.setText(secondLanguagePhoneticScriptName + ":");
	}
}
