/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customcontrols;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.listeners.VocableChangeListener;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author xiaolong
 */
public class XLDVocableDetailsBox extends GridPane implements VocableChangeListener {
	// LABELS
	private Label firstLanguageTranslationsLabel;
	private Label firstLanguagePhoneticScriptsLabel;
	private Label secondLanguageTranslationsLabel;
	private Label secondLanguagePhoneticScriptsLabel;
	
	private Label topicsLabel;
	private Label chaptersLabel;
	
	private Label learnLevelLabel;
	private Label relevanceLevelLabel;
	
	private Label descriptionLabel;
	
	// TEXTFLOWS
	private TextFlow firstLanguageTranslationsTextFlow;
	private TextFlow firstLanguagePhoneticScriptsTextFlow;
	private TextFlow secondLanguageTranslationsTextFlow;
	private TextFlow secondLanguagePhoneticScriptsTextFlow;
	
	
	private TextFlow topicsTextFlow;
	private TextFlow chaptersTextFlow;
	
	private TextFlow learnLevelTextFlow;
	private TextFlow relevanceLevelTextFlow;
	
	private TextFlow descriptionTextFlow;
	
	private Vocable detailsVocable;
	
	
	// TEXTS
	private Text firstLanguageTranslationsText;
	private Text firstLanguagePhoneticScriptsText;
	private Text secondLanguageTranslationsText;
	private Text secondLanguagePhoneticScriptsText;
	
	
	private Text topicsText;
	private Text chaptersText;
	
	private Text learnLevelText;
	private Text relevanceLevelText;
	
	private Text descriptionText;
	
	
	public XLDVocableDetailsBox() {
		
	}
	
	public void init() {
		initializeControls();
		addControls();
		registerAsListener();
		setActionsForNotifications();
	}

	private void initializeControls() {
		
		setPadding(new Insets(5));
		
		setHgap(5);
		setVgap(10);
		
		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(0).setPercentWidth(25);
		//getColumnConstraints().get(0).setHgrow(Priority.ALWAYS);
		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(1).setPercentWidth(25);
		//getColumnConstraints().get(1).setHgrow(Priority.ALWAYS);
		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(2).setPercentWidth(25);
		//getColumnConstraints().get(2).setHgrow(Priority.ALWAYS);
		getColumnConstraints().add(new ColumnConstraints());
		getColumnConstraints().get(3).setPercentWidth(25);
		//getColumnConstraints().get(3).setHgrow(Priority.ALWAYS);
		
		setMaxWidth(Double.MAX_VALUE);
		
		//setAlignment(Pos.TOP_LEFT);
		//setGridLinesVisible(true);
		
		try {
			// LABELS
			firstLanguageTranslationsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME) + ":");
			firstLanguagePhoneticScriptsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			secondLanguageTranslationsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME) + ":");
			secondLanguagePhoneticScriptsLabel = new Label(Settings.getInstance().getSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME) + ":");
			
			topicsLabel = new Label("Topics:");
			chaptersLabel = new Label("Chapters:");
			
			learnLevelLabel = new Label("Learn level:");
			relevanceLevelLabel = new Label("Relevance level:");
			
			descriptionLabel = new Label("Description:");
			
			// ALIGNMENT OF LABELS
			GridPane.setValignment(firstLanguageTranslationsLabel, VPos.TOP);
			GridPane.setValignment(firstLanguagePhoneticScriptsLabel, VPos.TOP);
			GridPane.setValignment(secondLanguageTranslationsLabel, VPos.TOP);
			GridPane.setValignment(secondLanguagePhoneticScriptsLabel, VPos.TOP);
			GridPane.setValignment(topicsLabel, VPos.TOP);
			GridPane.setValignment(chaptersLabel, VPos.TOP);
			GridPane.setValignment(learnLevelLabel, VPos.TOP);
			GridPane.setValignment(relevanceLevelLabel, VPos.TOP);
			GridPane.setValignment(descriptionLabel, VPos.TOP);
			
			// TEXTS
			firstLanguageTranslationsText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			firstLanguagePhoneticScriptsText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			secondLanguageTranslationsText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			secondLanguagePhoneticScriptsText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			
			topicsText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			chaptersText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			
			learnLevelText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			relevanceLevelText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			
			descriptionText = new Text(Settings.getInstance().getSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME));
			
			
			
			// TEXTFLOWS
			firstLanguageTranslationsTextFlow = new TextFlow();
			firstLanguagePhoneticScriptsTextFlow = new TextFlow();
			secondLanguageTranslationsTextFlow = new TextFlow();
			secondLanguagePhoneticScriptsTextFlow = new TextFlow();
			
			topicsTextFlow = new TextFlow();
			chaptersTextFlow = new TextFlow();
			
			learnLevelTextFlow = new TextFlow();
			relevanceLevelTextFlow = new TextFlow();
			
			descriptionTextFlow = new TextFlow();
			
			//firstLanguageTranslationsTextFlow.get
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(XLDVocableDetailsBox.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void addControls() {
		
		firstLanguageTranslationsTextFlow.getChildren().add(firstLanguageTranslationsText);
		firstLanguagePhoneticScriptsTextFlow.getChildren().add(firstLanguagePhoneticScriptsText);
		secondLanguageTranslationsTextFlow.getChildren().add(secondLanguageTranslationsText);
		secondLanguagePhoneticScriptsTextFlow.getChildren().add(secondLanguagePhoneticScriptsText);
		topicsTextFlow.getChildren().add(topicsText);
		chaptersTextFlow.getChildren().add(chaptersText);
		learnLevelTextFlow.getChildren().add(learnLevelText);
		relevanceLevelTextFlow.getChildren().add(relevanceLevelText);
		descriptionTextFlow.getChildren().add(descriptionText);
		
		add(firstLanguageTranslationsLabel, 0, 0);
		add(firstLanguageTranslationsTextFlow, 1, 0);
		add(firstLanguagePhoneticScriptsLabel, 2, 0);
		add(firstLanguagePhoneticScriptsTextFlow, 3, 0);
		
		add(secondLanguageTranslationsLabel, 0, 1);
		add(secondLanguageTranslationsTextFlow, 1, 1);
		add(secondLanguagePhoneticScriptsLabel, 2, 1);
		add(secondLanguagePhoneticScriptsTextFlow, 3, 1);
		
		add(topicsLabel, 0, 2);
		add(topicsTextFlow, 1, 2);
		add(chaptersLabel, 2, 2);
		add(chaptersTextFlow, 3, 2);
		
		add(learnLevelLabel, 0, 3);
		add(learnLevelTextFlow, 1, 3);
		add(relevanceLevelLabel, 2, 3);
		add(relevanceLevelTextFlow, 3, 3);
		
		add(descriptionLabel, 0, 4);
		add(descriptionTextFlow, 1, 4, 3, 2);
	}
	
	private void registerAsListener() {
		ManagerInstanceManager.getVocableManagerInstance().registerVocableChangeListener(this);
	}

	private void setActionsForNotifications() {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public void setVocable(Vocable vocable) {
		this.detailsVocable = vocable;
		
		firstLanguageTranslationsText.setText(detailsVocable.getFirstLanguageTranslationsAsString());
		firstLanguagePhoneticScriptsText.setText(detailsVocable.getFirstLanguagePhoneticScriptsAsString());
		secondLanguageTranslationsText.setText(detailsVocable.getSecondLanguageTranslationsAsString());
		secondLanguagePhoneticScriptsText.setText(detailsVocable.getSecondLanguagePhoneticScriptsAsString());
		
		topicsText.setText(detailsVocable.getTopicsAsString());
		chaptersText.setText(detailsVocable.getChaptersAsString());
		
		learnLevelText.setText(detailsVocable.getLearnLevel());
		relevanceLevelText.setText(detailsVocable.getRelevanceLevel());
		
		descriptionText.setText(detailsVocable.getDescription());
	}

	@Override
	public void updateOnVocableChange(Vocable oldVocable, Vocable changedVocable) {
		if(oldVocable == detailsVocable) {
			setVocable(changedVocable);
		}
	}
}
