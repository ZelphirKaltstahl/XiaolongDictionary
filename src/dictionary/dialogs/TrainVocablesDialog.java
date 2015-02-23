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
import dictionary.model.Action;
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
 * TODO: Count learned vocables somehow - maybe only if the highest predefined learn level is selected, or maybe a user can specify in the settings which learn levels are "learned"
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
	
	private Button changeVocableButton;
	private Button previousVocableButton;
	private Button nextVocableButton;
	private Button restartVocableTrainingButton;
	private Button stopVocableTrainingButton;
	
	// other
	private List<Vocable> trainingVocables;
	private final SimpleIntegerProperty currentVocableNumber = new SimpleIntegerProperty(0);
	private final SimpleDoubleProperty learnedVocablesProgress = new SimpleDoubleProperty(0);
	
	private final HashMap<Vocable, Boolean> learnedVocablesHashMap = new HashMap<>(); // which vocables are already learned?
	
	//private final SimpleBooleanProperty firstLanguageTranslationDisableProperty = new SimpleBooleanProperty(true);
	private boolean firstLanguageTranslationShown;
	private boolean firstLanguagePhoneticScriptShown;
	private boolean secondLanguageTranslationShown;
	private boolean secondLanguagePhoneticScriptShown;
	private boolean descriptionShown;
	
	private final HashMap<String, Action<String>> actionsForObservedSettingsChanges = new HashMap<>();
	
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
			newLearnLevelComboBox.getSelectionModel().select(vocable.getLearnLevel());
		} else {
			predefinedNewLearnLevelRadioButton.setSelected(false);
			customNewLearnLevelRadioButton.setSelected(true);
			newLearnLevelComboBox.getSelectionModel().clearSelection();
			newLearnLevelTextField.setText(vocable.getLearnLevel());
		}
		
		if(newRelevanceLevelComboBox.getItems().contains(vocable.getRelevanceLevel())) {
			predefinedNewRelevanceLevelRadioButton.setSelected(true);
			customNewRelevanceLevelRadioButton.setSelected(false);
			newRelevanceLevelComboBox.getSelectionModel().select(vocable.getRelevanceLevel());
		} else {
			predefinedNewRelevanceLevelRadioButton.setSelected(false);
			customNewRelevanceLevelRadioButton.setSelected(true);
			newRelevanceLevelComboBox.getSelectionModel().clearSelection();
			newRelevanceLevelTextField.setText(vocable.getRelevanceLevel());
		}
	}
	
	private void setVocable(int vocableNumber) {
		System.out.println("setting vocable number " + vocableNumber);
		setVocable(trainingVocables.get(vocableNumber));
	}
	
	private void nextVocable() {
		if(customNewLearnLevelRadioButton.isSelected()) {
			trainingVocables.get(currentVocableNumber.get()).setLearnLevel(newLearnLevelTextField.getText());
		} else {
			trainingVocables.get(currentVocableNumber.get()).setLearnLevel(newLearnLevelComboBox.getSelectionModel().getSelectedItem());
		}
		
		if(customNewRelevanceLevelRadioButton.isSelected()) {
			trainingVocables.get(currentVocableNumber.get()).setRelevanceLevel(newRelevanceLevelTextField.getText());
		} else {
			trainingVocables.get(currentVocableNumber.get()).setRelevanceLevel(newRelevanceLevelComboBox.getSelectionModel().getSelectedItem());
		}
		
		currentVocableNumber.set((currentVocableNumber.getValue() + 1) % trainingVocables.size());
		setVocable(currentVocableNumber.get());
	}
	
	private void previousVocable() {
		if(customNewLearnLevelRadioButton.isSelected()) {
			trainingVocables.get(currentVocableNumber.get()).setLearnLevel(newLearnLevelTextField.getText());
		} else {
			trainingVocables.get(currentVocableNumber.get()).setLearnLevel(newLearnLevelComboBox.getSelectionModel().getSelectedItem());
		}
		
		if(customNewRelevanceLevelRadioButton.isSelected()) {
			trainingVocables.get(currentVocableNumber.get()).setRelevanceLevel(newRelevanceLevelTextField.getText());
		} else {
			trainingVocables.get(currentVocableNumber.get()).setRelevanceLevel(newRelevanceLevelComboBox.getSelectionModel().getSelectedItem());
		}
		
		if(currentVocableNumber.get() > 0) {
			currentVocableNumber.set(currentVocableNumber.get() - 1);
		} else {
			currentVocableNumber.set(trainingVocables.size()-1);
		}
		setVocable(currentVocableNumber.get());
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
			firstLanguageTranslationShown =	Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME).equals(Boolean.TRUE.toString()
			);
			
			firstLanguagePhoneticScriptShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME).equals(Boolean.TRUE.toString()
			);
			
			secondLanguageTranslationShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME).equals(Boolean.TRUE.toString()
			);
			
			secondLanguagePhoneticScriptShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME).equals(Boolean.TRUE.toString()
			);
			
			descriptionShown = Settings.getInstance().getSettingsProperty(
				Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME).equals(Boolean.TRUE.toString()
			);
			
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
		/*
		firstLanguageTranslationTextField.getStyleClass().add("disablable_text_field");
		firstLanguagePhoneticScriptTextField.getStyleClass().add("disablable_text_field");
		secondLanguageTranslationTextField.getStyleClass().add("disablable_text_field");
		secondLanguagePhoneticScriptTextField.getStyleClass().add("disablable_text_field");
		descriptionTextArea.getStyleClass().add("disablable_text_field");
		*/
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
		setActionsForNotifications();
		
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
			firstLanguageTranslationTextField.setEditable(false);
			showHideFirstLanguageButton = new Button("Show");
			
			secondLanguageTranslationLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			secondLanguageTranslationTextField = new TextField();
			secondLanguageTranslationTextField.setEditable(false);
			showHideSecondLanguageButton = new Button("Show");
			
			firstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			firstLanguagePhoneticScriptTextField = new TextField();
			firstLanguagePhoneticScriptTextField.setEditable(false);
			showHideFirstLanguagePhoneticScriptButton = new Button("Show");
			
			secondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			secondLanguagePhoneticScriptTextField = new TextField();
			secondLanguagePhoneticScriptTextField.setEditable(false);
			showHideSecondLanguagePhoneticScriptButton = new Button("Show");
			
			xldBigCharacterBox = CustomControlsInstanceManager.createXLDBigCharacterBoxInstance(trainingVocables.get(0).getSecondLanguageTranslationsAsString(), "Characters");
			xldBigCharacterBox.setIgnoredCharacters(Settings.getInstance().getSettingsProperty(Settings.getInstance().IGNORED_CHARACTERS_SETTING_NAME));
			xldBigCharacterBox.setAlignment(Pos.TOP_RIGHT);
			
			descriptionLabel = new Label("Description" + ":");
			descriptionTextArea = new TextArea();
			descriptionTextArea.setWrapText(true);
			descriptionTextArea.setEditable(false);
			showHideDescriptionButton = new Button("Show");
			
			newLearnLevelLabel = new Label("New Learn Level" + ":");
			newLearnLevelRadioButtonsToggleGroup = new ToggleGroup();
			customNewLearnLevelRadioButton = new RadioButton("Custom");
			predefinedNewLearnLevelRadioButton = new RadioButton("Predefined");
			newLearnLevelRadioButtonsToggleGroup.getToggles().add(customNewLearnLevelRadioButton);
			newLearnLevelRadioButtonsToggleGroup.getToggles().add(predefinedNewLearnLevelRadioButton);
			newLearnLevelTextField = new TextField();
			newLearnLevelTextField.disableProperty().bindBidirectional(predefinedNewLearnLevelRadioButton.selectedProperty());
			
			String[] predefinedNewLearnLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVELS_SETTING_NAME).split(",", -1);
			ObservableList newLearnLevelChoices = FXCollections.observableArrayList(predefinedNewLearnLevelChoices);
			newLearnLevelComboBox = new ComboBox<>(newLearnLevelChoices);
			newLearnLevelComboBox.disableProperty().bindBidirectional(customNewLearnLevelRadioButton.selectedProperty());
			
			newRelevanceLevelLabel = new Label("New Relevance Level" + ":");
			newRelevanceLevelRadioButtonsToggleGroup = new ToggleGroup();
			customNewRelevanceLevelRadioButton = new RadioButton("Custom");
			predefinedNewRelevanceLevelRadioButton = new RadioButton("Predefined");
			newRelevanceLevelRadioButtonsToggleGroup.getToggles().add(customNewRelevanceLevelRadioButton);
			newRelevanceLevelRadioButtonsToggleGroup.getToggles().add(predefinedNewRelevanceLevelRadioButton);
			newRelevanceLevelTextField = new TextField();
			newRelevanceLevelTextField.disableProperty().bindBidirectional(predefinedNewRelevanceLevelRadioButton.selectedProperty());
			
			String[] predefinedNewRelevanceLevelChoices = Settings.getInstance().getSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVELS_SETTING_NAME).split(",", -1);
			ObservableList newRelevanceLevelChoices = FXCollections.observableArrayList(predefinedNewRelevanceLevelChoices);
			newRelevanceLevelComboBox = new ComboBox<>(newRelevanceLevelChoices);
			newRelevanceLevelComboBox.disableProperty().bindBidirectional(customNewRelevanceLevelRadioButton.selectedProperty());
			
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
			vocableNumberSlider.setMajorTickUnit(Math.floor(Math.max(((double) trainingVocables.size())/10 , 1)));
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
			
			changeVocableButton = new Button("Change");
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
		
		actionButtonGridPane.add(changeVocableButton, 1, 0);
		actionButtonGridPane.add(previousVocableButton, 0, 1);
		actionButtonGridPane.add(nextVocableButton, 1, 1);
		actionButtonGridPane.add(restartVocableTrainingButton, 0, 2);
		actionButtonGridPane.add(stopVocableTrainingButton, 1, 2);
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
		});
		
		showHideFirstLanguagePhoneticScriptButton.setOnAction((actionEvent) -> {
			toggleFirstLanguagePhoneticScriptVisibility();
		});
		
		showHideSecondLanguageButton.setOnAction((actionEvent) -> {
			toggleSecondLanguageVisibility();
		});
		
		showHideSecondLanguagePhoneticScriptButton.setOnAction((actionEvent) -> {
			toggleSecondLanguagePhoneticScriptVisibility();
		});
		
		showHideDescriptionButton.setOnAction((actionEvent) -> {
			toggleDescriptionVisibility();
		});
		
		previousVocableButton.setOnAction((actionEvent) -> {
			previousVocable();
		});
		
		nextVocableButton.setOnAction((actionEvent) -> {
			nextVocable();
		});
		
		stopVocableTrainingButton.setOnAction((actionEvent) -> {
			stopTraining();
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
	
	private void setFirstLanguageVisibility(boolean value) {
		if(value) {
			showFirstLanguage();
		} else {
			hideFirstLanguage();
		}
	}
	
	private void setFirstLanguagePhoneticScriptVisibility(boolean value) {
		if(value) {
			showFirstLanguagePhoneticScript();
		} else {
			hideFirstLanguagePhoneticScript();
		}
	}
	
	private void setSecondLanguageVisibility(boolean value) {
		if(value) {
			showSecondLanguage();
		} else {
			hideSecondLanguage();
		}
	}
	
	private void setSecondLanguagePhoneticScriptVisibility(boolean value) {
		if(value) {
			showSecondLanguagePhoneticScript();
		} else {
			hideSecondLanguagePhoneticScript();
		}
	}
	
	private void setDescriptionVisibility(boolean value) {
		if(value) {
			showDescription();
		} else {
			hideDescription();
		}
	}
	
	private void hideFirstLanguage() {
		firstLanguageTranslationTextField.setText("Hidden ...");
		showHideFirstLanguageButton.setText("Show");
		firstLanguageTranslationShown = false;
	}
	
	private void showFirstLanguage() {
		firstLanguageTranslationTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getFirstLanguageTranslationsAsString());
		showHideFirstLanguageButton.setText("Hide");
		firstLanguageTranslationShown = true;
	}
	
	private void hideFirstLanguagePhoneticScript() {
		firstLanguagePhoneticScriptTextField.setText("Hidden ...");
		showHideFirstLanguagePhoneticScriptButton.setText("Show");
		firstLanguagePhoneticScriptShown = false;
	}
	
	private void showFirstLanguagePhoneticScript() {
		firstLanguagePhoneticScriptTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getFirstLanguagePhoneticScriptsAsString());
		showHideFirstLanguagePhoneticScriptButton.setText("Hide");
		firstLanguagePhoneticScriptShown = true;
	}
	
	private void hideSecondLanguage() {
		secondLanguageTranslationTextField.setText("Hidden ...");
		xldBigCharacterBox.hideCharacters();
		showHideSecondLanguageButton.setText("Show");
		secondLanguageTranslationShown = false;
	}
	
	private void showSecondLanguage() {
		secondLanguageTranslationTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getSecondLanguageTranslationsAsString());
		xldBigCharacterBox.showCharacters();
		showHideSecondLanguageButton.setText("Hide");
		secondLanguageTranslationShown = true;
	}
	
	private void hideSecondLanguagePhoneticScript() {
		secondLanguagePhoneticScriptTextField.setText("Hidden ...");
		showHideSecondLanguagePhoneticScriptButton.setText("Show");
		secondLanguagePhoneticScriptShown = false;
	}
	
	private void showSecondLanguagePhoneticScript() {
		secondLanguagePhoneticScriptTextField.setText(trainingVocables.get(currentVocableNumber.intValue()).getSecondLanguagePhoneticScriptsAsString());
		showHideSecondLanguagePhoneticScriptButton.setText("Hide");
		secondLanguagePhoneticScriptShown = true;
	}
	
	private void hideDescription() {
		descriptionTextArea.setText("Hidden ...");
		showHideDescriptionButton.setText("Show");
		descriptionShown = false;
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, Boolean.toString(false));
	}
	
	private void showDescription() {
		descriptionTextArea.setText(trainingVocables.get(currentVocableNumber.intValue()).getDescription());
		showHideDescriptionButton.setText("Hide");
		descriptionShown = true;
		//Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, Boolean.toString(true));
	}

	private void registerAsListener() {
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, this);
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, this);
		
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
		
		Settings.getInstance().registerSettingsPropertyChangeListener(Settings.getInstance().IGNORED_CHARACTERS_SETTING_NAME, this);
	}
	
	private void setActionsForNotifications() {
		// ====================================================
		// actions for language an phonetic script name changes
		// ====================================================
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> updateFirstLanguageName(value)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> updateFirstLanguagePhoneticScriptName(value)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> updateSecondLanguageName(value)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> updateSecondLanguagePhoneticScriptName(value)
		);
		
		// ================================================
		// actions for showing or hiding vocable attributes
		// ================================================
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> setFirstLanguageVisibility(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> setFirstLanguagePhoneticScriptVisibility(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME,
			(Action<String>) (String value) -> setSecondLanguageVisibility(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
			(Action<String>) (String value) -> setSecondLanguagePhoneticScriptVisibility(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME,
			(Action<String>) (String value) -> setDescriptionVisibility(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		// ==================================
		// actions for level settings changes
		// ==================================
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME,
			(Action<String>) (String value) -> customNewLearnLevelRadioButton.setSelected(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME,
			(Action<String>) (String value) -> predefinedNewLearnLevelRadioButton.setSelected(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME,
			(Action<String>) (String value) -> customNewRelevanceLevelRadioButton.setSelected(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME,
			(Action<String>) (String value) -> predefinedNewRelevanceLevelRadioButton.setSelected(Boolean.getBoolean(value) == Boolean.TRUE)
		);
		
		// OTHER
		actionsForObservedSettingsChanges.put(
			Settings.getInstance().IGNORED_CHARACTERS_SETTING_NAME,
			(Action<String>) (String value) -> xldBigCharacterBox.setIgnoredCharacters(value)
		);
	}
	
	@Override
	public void update(String settingName, String settingValue) {
		actionsForObservedSettingsChanges.get(settingName).execute(settingValue);
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
