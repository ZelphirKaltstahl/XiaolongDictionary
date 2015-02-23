/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import dictionary.buttons.InsertSpecialCharacterButton;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.listeners.SettingsPropertyChangeListener;
import dictionary.manager.DialogInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

/**
 *
 * @author xiaolong
 */
public class ChangeVocableDialog extends XLDDialog implements SettingsPropertyChangeListener {
	
	private Vocable changingVocable;
	
	private Scene scene;
	
	// CONTAINERS
	
	private GridPane root;
	private GridPane actionButtonGridPane;
	
	// CONTROLS
	
	private Label currentValuesHeadingLabel;
	
	private Label currentFirstLanguageLabel;
	private Label currentFirstLanguagePhoneticScriptLabel;
	private Label currentSecondLanguageLabel;
	private Label currentSecondLanguagePhoneticScriptLabel;
	private Label currentChapterLabel;
	private Label currentTopicLabel;
	private Label currentDescriptionLabel;
	private Label currentLearnLevelLabel;
	private Label currentRelevanceLabel;
	
	private TextField currentFirstLanguageTextField;
	private TextField currentFirstLanguagePhoneticScriptTextField;
	private TextField currentSecondLanguageTextField;
	private TextField currentSecondLanguagePhoneticScriptTextField;
	private TextField currentChapterTextField;
	private TextField currentTopicTextField;
	private TextArea currentDescriptionTextArea;
	private TextField currentLearnLevelTextField;
	private TextField currentRelevanceLevelTextField;
	
	private Button takeCurrentFirstLanguageButton;
	private Button takeCurrentFirstLanguagePhoneticScriptButton;
	private Button takeCurrentSecondLanguageButton;
	private Button takeCurrentSecondLanguagePhoneticScriptButton;
	private Button takeCurrentChapterButton;
	private Button takeCurrentTopicButton;
	private Button takeCurrentDescriptionButton;
	private Button takeCurrentLearnLevelButton;
	private Button takeCurrentRelevanceButton;
	private Button takeCurrentAllButton;
	
	private Label changedValuesHeadingLabel;
	
	private Label changedFirstLanguageLabel;
	private Label changedFirstLanguagePhoneticScriptLabel;
	private Label changedSecondLanguageLabel;
	private Label changedSecondLanguagePhoneticScriptLabel;
	private Label changedChapterLabel;
	private Label changedTopicLabel;
	private Label changedDescriptionLabel;
	private Label changedLearnLevelLabel;
	private Label changedRelevanceLabel;
	
	private TextField changedFirstLanguageTranslationTextField;
	private TextField changedFirstLanguagePhoneticScriptTextField;
	private TextField changedSecondLanguageTranslationTextField;
	private TextField changedSecondLanguagePhoneticScriptTextField;
	private TextField changedChapterTextField;
	private TextField changedTopicTextField;
	private TextArea changedDescriptionTextArea;
	private TextField changedLearnLevelTextField;
	private TextField changedRelevanceLevelTextField;
	
	private Button clearChangedValuesButton;
	
	private Button changeButton;
	private Button cancelButton;
	
	private InsertSpecialCharacterButton insertSpecialCharacterButton;
	private HBox insertSpecialCharacterButtonHBox;
	
	public ChangeVocableDialog(Modality modality) {
		super(modality);
	}
	
	public void initialize(Vocable vocable) {
		this.changingVocable = vocable;
		
		initializeControls();
		//addCSSClasses();
		addControls();
		
		//setMinimumSizes();
		
		//applySettings();
		
		addActionListeners();
		addFocusChangeListeners();
		
		registerAsListener();
		setActionsForNotifications();
	}
	
	@Override
	protected void initializeControls() {
		try {
			setTitle("Change Vocable");
			
			//CONTAINES
			
			root = new GridPane();
			root.setPadding(new Insets(10));
			root.setHgap(20);
			root.setVgap(10);
			root.setAlignment(Pos.CENTER);
			
			actionButtonGridPane = new GridPane();
			actionButtonGridPane.setHgap(2);
			actionButtonGridPane.setVgap(2);
			actionButtonGridPane.setMaxWidth(200);
			actionButtonGridPane.getColumnConstraints().add(new ColumnConstraints());
			actionButtonGridPane.getColumnConstraints().get(0).setPercentWidth(50);
			actionButtonGridPane.getColumnConstraints().add(new ColumnConstraints());
			actionButtonGridPane.getColumnConstraints().get(1).setPercentWidth(50);
			
			// CONTROLS
			currentValuesHeadingLabel = new Label("Current Values");
			
			currentValuesHeadingLabel.setMaxHeight(Double.MAX_VALUE);
			currentValuesHeadingLabel.setAlignment(Pos.CENTER);
			currentValuesHeadingLabel.setTextAlignment(TextAlignment.CENTER);
			currentValuesHeadingLabel.setFont(new Font("System Regular", 20));
			
			currentFirstLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME) + ":");
			currentFirstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			currentSecondLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			currentSecondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			currentChapterLabel = new Label("Chapter" + ":");
			currentTopicLabel = new Label("Topic" + ":");
			currentDescriptionLabel = new Label("Description" + ":");
			currentLearnLevelLabel = new Label("Learn Level" + ":");
			currentRelevanceLabel = new Label("Relevance Level" + ":");
			
			currentFirstLanguageTextField = new TextField(changingVocable.getFirstLanguageTranslationsAsString());
			currentFirstLanguagePhoneticScriptTextField = new TextField(changingVocable.getFirstLanguagePhoneticScriptsAsString());
			currentSecondLanguageTextField = new TextField(changingVocable.getSecondLanguageTranslationsAsString());
			currentSecondLanguagePhoneticScriptTextField = new TextField(changingVocable.getSecondLanguagePhoneticScriptsAsString());
			currentChapterTextField = new TextField(changingVocable.getChaptersAsString());
			currentTopicTextField = new TextField(changingVocable.getTopicsAsString());
			currentDescriptionTextArea = new TextArea(changingVocable.getDescription());
			currentLearnLevelTextField = new TextField(changingVocable.getLearnLevel());
			currentRelevanceLevelTextField = new TextField(changingVocable.getRelevanceLevel());
			
			// ALIGNMENT
			currentFirstLanguageLabel.setMaxWidth(Double.MAX_VALUE);
			currentFirstLanguageLabel.setAlignment(Pos.TOP_RIGHT);
			currentFirstLanguageLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentFirstLanguagePhoneticScriptLabel.setMaxWidth(Double.MAX_VALUE);
			currentFirstLanguagePhoneticScriptLabel.setAlignment(Pos.TOP_RIGHT);
			currentFirstLanguagePhoneticScriptLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentSecondLanguageLabel.setMaxWidth(Double.MAX_VALUE);
			currentSecondLanguageLabel.setAlignment(Pos.TOP_RIGHT);
			currentSecondLanguageLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentSecondLanguagePhoneticScriptLabel.setMaxWidth(Double.MAX_VALUE);
			currentSecondLanguagePhoneticScriptLabel.setAlignment(Pos.TOP_RIGHT);
			currentSecondLanguagePhoneticScriptLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentTopicLabel.setMaxWidth(Double.MAX_VALUE);
			currentTopicLabel.setAlignment(Pos.TOP_RIGHT);
			currentTopicLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentChapterLabel.setMaxWidth(Double.MAX_VALUE);
			currentChapterLabel.setAlignment(Pos.TOP_RIGHT);
			currentChapterLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentDescriptionLabel.setMaxWidth(Double.MAX_VALUE);
			currentDescriptionLabel.setAlignment(Pos.TOP_RIGHT);
			currentDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentLearnLevelLabel.setMaxWidth(Double.MAX_VALUE);
			currentLearnLevelLabel.setAlignment(Pos.TOP_RIGHT);
			currentLearnLevelLabel.setTextAlignment(TextAlignment.RIGHT);
			
			currentRelevanceLabel.setMaxWidth(Double.MAX_VALUE);
			currentRelevanceLabel.setAlignment(Pos.TOP_RIGHT);
			currentRelevanceLabel.setTextAlignment(TextAlignment.RIGHT);
			
			// EDITABILITY
			currentFirstLanguageTextField.setEditable(false);
			currentFirstLanguagePhoneticScriptTextField.setEditable(false);
			currentSecondLanguageTextField.setEditable(false);
			currentSecondLanguagePhoneticScriptTextField.setEditable(false);
			currentChapterTextField.setEditable(false);
			currentTopicTextField.setEditable(false);
			currentDescriptionTextArea.setEditable(false);
			currentLearnLevelTextField.setEditable(false);
			currentRelevanceLevelTextField.setEditable(false);
			
			currentDescriptionTextArea.setMaxWidth(200);
			currentDescriptionTextArea.setWrapText(true);
			
			takeCurrentFirstLanguageButton = new Button("-->");
			takeCurrentFirstLanguagePhoneticScriptButton = new Button("-->");
			takeCurrentSecondLanguageButton = new Button("-->");
			takeCurrentSecondLanguagePhoneticScriptButton = new Button("-->");
			takeCurrentChapterButton = new Button("-->");
			takeCurrentTopicButton = new Button("-->");
			takeCurrentDescriptionButton = new Button("-->");
			takeCurrentLearnLevelButton = new Button("-->");
			takeCurrentRelevanceButton = new Button("-->");
			takeCurrentAllButton = new Button("-->");

			changedValuesHeadingLabel = new Label("Changed Values");
			
			changedValuesHeadingLabel.setMaxHeight(Double.MAX_VALUE);
			changedValuesHeadingLabel.setAlignment(Pos.CENTER);
			changedValuesHeadingLabel.setTextAlignment(TextAlignment.CENTER);
			changedValuesHeadingLabel.setFont(new Font("System Regular",20));

			changedFirstLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME) + ":");
			changedFirstLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			changedSecondLanguageLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			changedSecondLanguagePhoneticScriptLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			changedChapterLabel = new Label("Chapter" + ":");
			changedTopicLabel = new Label("Topic" + ":");
			changedDescriptionLabel = new Label("Description" + ":");
			changedLearnLevelLabel = new Label("Learn Level" + ":");
			changedRelevanceLabel = new Label("Relevance Level" + ":");
			
			changedFirstLanguageTranslationTextField = new TextField(changingVocable.getFirstLanguageTranslationsAsString());
			changedFirstLanguagePhoneticScriptTextField = new TextField(changingVocable.getFirstLanguagePhoneticScriptsAsString());
			changedSecondLanguageTranslationTextField = new TextField(changingVocable.getSecondLanguageTranslationsAsString());
			changedSecondLanguagePhoneticScriptTextField = new TextField(changingVocable.getSecondLanguagePhoneticScriptsAsString());
			changedChapterTextField = new TextField(changingVocable.getChaptersAsString());
			changedTopicTextField = new TextField(changingVocable.getTopicsAsString());
			changedDescriptionTextArea = new TextArea(changingVocable.getDescription());
			changedLearnLevelTextField = new TextField(changingVocable.getLearnLevel());
			changedRelevanceLevelTextField = new TextField(changingVocable.getRelevanceLevel());
			
			// ALIGNMENT
			changedFirstLanguageLabel.setMaxWidth(Double.MAX_VALUE);
			changedFirstLanguageLabel.setAlignment(Pos.TOP_RIGHT);
			changedFirstLanguageLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedFirstLanguagePhoneticScriptLabel.setMaxWidth(Double.MAX_VALUE);
			changedFirstLanguagePhoneticScriptLabel.setAlignment(Pos.TOP_RIGHT);
			changedFirstLanguagePhoneticScriptLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedSecondLanguageLabel.setMaxWidth(Double.MAX_VALUE);
			changedSecondLanguageLabel.setAlignment(Pos.TOP_RIGHT);
			changedSecondLanguageLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedSecondLanguagePhoneticScriptLabel.setMaxWidth(Double.MAX_VALUE);
			changedSecondLanguagePhoneticScriptLabel.setAlignment(Pos.TOP_RIGHT);
			changedSecondLanguagePhoneticScriptLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedTopicLabel.setMaxWidth(Double.MAX_VALUE);
			changedTopicLabel.setAlignment(Pos.TOP_RIGHT);
			changedTopicLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedChapterLabel.setMaxWidth(Double.MAX_VALUE);
			changedChapterLabel.setAlignment(Pos.TOP_RIGHT);
			changedChapterLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedDescriptionLabel.setMaxWidth(Double.MAX_VALUE);
			changedDescriptionLabel.setAlignment(Pos.TOP_RIGHT);
			changedDescriptionLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedLearnLevelLabel.setMaxWidth(Double.MAX_VALUE);
			changedLearnLevelLabel.setAlignment(Pos.TOP_RIGHT);
			changedLearnLevelLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedRelevanceLabel.setMaxWidth(Double.MAX_VALUE);
			changedRelevanceLabel.setAlignment(Pos.TOP_RIGHT);
			changedRelevanceLabel.setTextAlignment(TextAlignment.RIGHT);
			
			changedDescriptionTextArea.setMaxWidth(200);
			changedDescriptionTextArea.setWrapText(true);

			clearChangedValuesButton = new Button("Clear");
			clearChangedValuesButton.setMaxWidth(Double.MAX_VALUE);

			changeButton = new Button("Change");
			changeButton.setMaxWidth(Double.MAX_VALUE);
			
			cancelButton = new Button("Cancel");
			cancelButton.setMaxWidth(Double.MAX_VALUE);
			
			insertSpecialCharacterButtonHBox = new HBox();
			insertSpecialCharacterButtonHBox.setMaxWidth(Double.MAX_VALUE);
			insertSpecialCharacterButtonHBox.setAlignment(Pos.TOP_RIGHT);
			
			insertSpecialCharacterButton = new InsertSpecialCharacterButton();
			insertSpecialCharacterButton.setAlignment(Pos.TOP_RIGHT);
			insertSpecialCharacterButton.setTextAlignment(TextAlignment.RIGHT);
			
			lastActiveTextInputControl = changedFirstLanguageTranslationTextField;
			
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(ChangeVocableDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		scene = new Scene(root);
	}

	@Override
	protected void addControls() {
		root.add(currentValuesHeadingLabel, 0, 0, 2, 1);
		root.add(changedValuesHeadingLabel, 3, 0, 2, 1);
		
		root.add(currentFirstLanguageLabel, 0, 1);
		root.add(currentFirstLanguageTextField, 1, 1);
		root.add(takeCurrentFirstLanguageButton, 2, 1);
		root.add(changedFirstLanguageLabel, 3, 1);
		root.add(changedFirstLanguageTranslationTextField, 4, 1);
		
		root.add(currentFirstLanguagePhoneticScriptLabel, 0, 2);
		root.add(currentFirstLanguagePhoneticScriptTextField, 1, 2);
		root.add(takeCurrentFirstLanguagePhoneticScriptButton, 2, 2);
		root.add(changedFirstLanguagePhoneticScriptLabel, 3, 2);
		root.add(changedFirstLanguagePhoneticScriptTextField, 4, 2);
		
		root.add(currentSecondLanguageLabel, 0, 3);
		root.add(currentSecondLanguageTextField, 1, 3);
		root.add(takeCurrentSecondLanguageButton, 2, 3);
		root.add(changedSecondLanguageLabel, 3, 3);
		root.add(changedSecondLanguageTranslationTextField, 4, 3);
		
		root.add(currentSecondLanguagePhoneticScriptLabel, 0, 4);
		root.add(currentSecondLanguagePhoneticScriptTextField, 1, 4);
		root.add(takeCurrentSecondLanguagePhoneticScriptButton, 2, 4);
		root.add(changedSecondLanguagePhoneticScriptLabel, 3, 4);
		root.add(changedSecondLanguagePhoneticScriptTextField, 4, 4);
		
		root.add(currentChapterLabel, 0, 5);
		root.add(currentChapterTextField, 1, 5);
		root.add(takeCurrentChapterButton, 2, 5);
		root.add(changedChapterLabel, 3, 5);
		root.add(changedChapterTextField, 4, 5);
		
		root.add(currentTopicLabel, 0, 6);
		root.add(currentTopicTextField, 1, 6);
		root.add(takeCurrentTopicButton, 2, 6);
		root.add(changedTopicLabel, 3, 6);
		root.add(changedTopicTextField, 4, 6);
		
		root.add(currentDescriptionLabel, 0, 7);
		root.add(currentDescriptionTextArea, 1, 7);
		root.add(takeCurrentDescriptionButton, 2, 7);
		root.add(changedDescriptionLabel, 3, 7);
		root.add(changedDescriptionTextArea, 4, 7);
		
		root.add(currentLearnLevelLabel, 0, 8);
		root.add(currentLearnLevelTextField, 1, 8);
		root.add(takeCurrentLearnLevelButton, 2, 8);
		root.add(changedLearnLevelLabel, 3, 8);
		root.add(changedLearnLevelTextField, 4, 8);
		
		root.add(currentRelevanceLabel, 0, 9);
		root.add(currentRelevanceLevelTextField, 1, 9);
		root.add(takeCurrentRelevanceButton, 2, 9);
		root.add(changedRelevanceLabel, 3, 9);
		root.add(changedRelevanceLevelTextField, 4, 9);
		
		root.add(takeCurrentAllButton, 2, 10);
		insertSpecialCharacterButtonHBox.getChildren().add(insertSpecialCharacterButton);
		root.add(insertSpecialCharacterButtonHBox, 4, 10);
		
		actionButtonGridPane.add(clearChangedValuesButton, 1, 0);
		actionButtonGridPane.add(changeButton, 0, 1);
		actionButtonGridPane.add(cancelButton, 1, 1);
		
		root.add(actionButtonGridPane, 4, 11, 1, 2);
		
		setScene(scene);
	}

	@Override
	protected void addActionListeners() {
		changeButton.setOnAction(
			(ActionEvent actionEvent) -> changeVocableButtonActionPerformed()
		);
		
		clearChangedValuesButton.setOnAction(
			(ActionEvent actionEvent) -> clearButtonActionPerformed()
		);
		
		cancelButton.setOnAction(
			(ActionEvent actionEvent) -> cancelButtonActionPerformed()
		);
		
		takeCurrentFirstLanguageButton.setOnAction(
			(ActionEvent event) -> changedFirstLanguageTranslationTextField.setText(changingVocable.getFirstLanguageTranslationsAsString())
		);
		
		takeCurrentFirstLanguagePhoneticScriptButton.setOnAction(
			(ActionEvent event) -> changedFirstLanguagePhoneticScriptTextField.setText(changingVocable.getFirstLanguagePhoneticScriptsAsString())
		);
		
		takeCurrentSecondLanguageButton.setOnAction(
			(ActionEvent event) -> changedSecondLanguageTranslationTextField.setText(changingVocable.getSecondLanguageTranslationsAsString())
		);
		
		takeCurrentSecondLanguagePhoneticScriptButton.setOnAction(
			(ActionEvent event) -> changedSecondLanguagePhoneticScriptTextField.setText(changingVocable.getSecondLanguagePhoneticScriptsAsString())
		);
		
		takeCurrentTopicButton.setOnAction(
			(ActionEvent event) -> changedTopicTextField.setText(changingVocable.getTopicsAsString())
		);
		
		takeCurrentChapterButton.setOnAction(
			(ActionEvent event) -> changedChapterTextField.setText(changingVocable.getChaptersAsString())
		);
		
		takeCurrentDescriptionButton.setOnAction(
			(ActionEvent event) -> changedDescriptionTextArea.setText(changingVocable.getDescription())
		);
		
		takeCurrentLearnLevelButton.setOnAction(
			(ActionEvent event) -> changedLearnLevelTextField.setText(changingVocable.getLearnLevel())
		);
		
		takeCurrentRelevanceButton.setOnAction(
			(ActionEvent event) -> changedRelevanceLevelTextField.setText(changingVocable.getRelevanceLevel())
		);
		
		takeCurrentAllButton.setOnAction(
			(ActionEvent event) -> {
				changedFirstLanguageTranslationTextField.setText(changingVocable.getFirstLanguageTranslationsAsString());
				changedFirstLanguagePhoneticScriptTextField.setText(changingVocable.getFirstLanguagePhoneticScriptsAsString());
				changedSecondLanguageTranslationTextField.setText(changingVocable.getSecondLanguageTranslationsAsString());
				changedSecondLanguagePhoneticScriptTextField.setText(changingVocable.getSecondLanguagePhoneticScriptsAsString());
				changedTopicTextField.setText(changingVocable.getTopicsAsString());
				changedChapterTextField.setText(changingVocable.getChaptersAsString());
				changedDescriptionTextArea.setText(changingVocable.getDescription());
				changedLearnLevelTextField.setText(changingVocable.getLearnLevel());
				changedRelevanceLevelTextField.setText(changingVocable.getRelevanceLevel());
			}
		);
		
		insertSpecialCharacterButton.setOnAction((ActionEvent event) -> {
			insertSpecialCharacterButtonActionPerformed();
		});
	}

	@Override
	protected void addFocusChangeListeners() {
		List<TextInputControl> editableTextInputControls = new ArrayList<>();
		editableTextInputControls.add(changedFirstLanguageTranslationTextField);
		editableTextInputControls.add(changedFirstLanguagePhoneticScriptTextField);
		editableTextInputControls.add(changedSecondLanguageTranslationTextField);
		editableTextInputControls.add(changedSecondLanguagePhoneticScriptTextField);
		editableTextInputControls.add(changedTopicTextField);
		editableTextInputControls.add(changedChapterTextField);
		editableTextInputControls.add(changedDescriptionTextArea);
		editableTextInputControls.add(changedLearnLevelTextField);
		editableTextInputControls.add(changedRelevanceLevelTextField);
		
		editableTextInputControls.forEach((textInputControl) -> {
			textInputControl.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
				if (newValue) {
					lastActiveTextInputControl = textInputControl;
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
					DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
				} else {
					caretPosition = lastActiveTextInputControl.getCaretPosition();
				}
			});
		});
	}

	@Override
	public void update(String settingName, String settingValue) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void registerAsListener() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void setActionsForNotifications() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private void changeVocableButtonActionPerformed() {
		String separatorRegularExpression = null;
		try {
			separatorRegularExpression = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(ChangeVocableDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		String[] firstLanguageTranslations = changedFirstLanguageTranslationTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedFirstLanguageTranslations = new ArrayList<>();
		for(String firstLanguageTranslation : firstLanguageTranslations) {
			changedFirstLanguageTranslations.add(firstLanguageTranslation.trim());
		}
		changingVocable.setFirstLanugageTranslations(changedFirstLanguageTranslations);
		
		String[] firstLanguagePhoneticScripts = changedFirstLanguagePhoneticScriptTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedFirstLanguagePhoneticScripts = new ArrayList<>();
		for(String firstLanguagePhoneticScript : firstLanguagePhoneticScripts) {
			changedFirstLanguagePhoneticScripts.add(firstLanguagePhoneticScript.trim());
		}
		changingVocable.setFirstLanguagePhoneticScripts(changedFirstLanguagePhoneticScripts);
		
		String[] secondLanguageTranslations = changedSecondLanguageTranslationTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedSecondLanguageTranslations = new ArrayList<>();
		for(String secondLanguageTranslation : secondLanguageTranslations) {
			changedSecondLanguageTranslations.add(secondLanguageTranslation.trim());
		}
		changingVocable.setSecondLanguageTranslations(changedSecondLanguageTranslations);
		
		String[] secondLanguagePhoneticScripts = changedSecondLanguagePhoneticScriptTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedSecondLanguagePhoneticScripts = new ArrayList<>();
		for(String secondLanguagePhoneticScript : secondLanguagePhoneticScripts) {
			changedSecondLanguagePhoneticScripts.add(secondLanguagePhoneticScript.trim());
		}
		changingVocable.setSecondLanguagePhoneticScripts(changedSecondLanguagePhoneticScripts);
		
		String[] chapters = changedChapterTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedChapters = new ArrayList<>();
		for(String chapter : chapters) {
			changedChapters.add(chapter.trim());
		}
		changingVocable.setChapters(changedChapters);
		
		String[] topics = changedTopicTextField.getText().split(separatorRegularExpression, -1);
		List<String> changedTopics = new ArrayList<>();
		for(String topic : topics) {
			changedTopics.add(topic.trim());
		}
		changingVocable.setTopic(changedTopics);
		
		changingVocable.setDescription(changedDescriptionTextArea.getText());
		changingVocable.setLearnLevel(changedLearnLevelTextField.getText());
		changingVocable.setRelevanceLevel(changedRelevanceLevelTextField.getText());
		
		hide();
	}
	
	private void cancelButtonActionPerformed() {
		hide();
	}
	
	private void clearButtonActionPerformed() {
		changedFirstLanguageTranslationTextField.clear();
		changedFirstLanguagePhoneticScriptTextField.clear();
		changedSecondLanguageTranslationTextField.clear();
		changedSecondLanguagePhoneticScriptTextField.clear();
		changedChapterTextField.clear();
		changedTopicTextField.clear();
		changedDescriptionTextArea.clear();
		changedLearnLevelTextField.clear();
		changedRelevanceLevelTextField.clear();
	}
	
	public Vocable getChangingVocable() {
		return this.changingVocable;
	}
	
}
