/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs.options;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.helpers.FontOperations;
import dictionary.model.Settings;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author xiaolong
 */
public class BigCharacterBoxOptionsDialog extends Stage {
	
	private Scene root;
	
	private GridPane containingGridPane;
	
	private Label fontLabel;
	private Text fontPreviewText;
	private TextFlow fontPreviewTextFlow;
	
	private Button chooseFontButton;
	private Button saveSettingsButton;
	private Button okButton;
	private Button cancelButton;
	
	private String fontFamily;
	private String fontSize;
	private String fontStyle;
	
	public BigCharacterBoxOptionsDialog() {
		
	}
	
	public void initialize() {
		loadSettings();
		initializeControls();
		addControls();
		addActionListeners();
	}
	
	private void loadSettings() {
		try {
			fontFamily = Settings.getInstance().getSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME);
			fontSize = Settings.getInstance().getSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME);
			fontStyle = Settings.getInstance().getSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_STYLE_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(BigCharacterBoxOptionsDialog.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void initializeControls() {
		setTitle("Big Character Box Options");
		
		containingGridPane = new GridPane();
		containingGridPane.setPadding(new Insets(10));
		containingGridPane.setHgap(5);
		containingGridPane.setVgap(5);
		
		root = new Scene(containingGridPane);
		
		fontLabel = new Label("Font:");
		fontPreviewText = new Text("Sample");
		fontPreviewTextFlow = new TextFlow(fontPreviewText);
		
		
		chooseFontButton = new Button("Choose font ...");
		
		saveSettingsButton = new Button("Save");
		okButton = new Button("OK");
		cancelButton = new Button("Cancel");
		
	}
	
	private void addControls() {
		containingGridPane.add(fontLabel, 0, 0);
		containingGridPane.add(fontPreviewTextFlow, 1, 0);
		containingGridPane.add(chooseFontButton, 2, 0);
		
		containingGridPane.add(saveSettingsButton, 0, 1);
		containingGridPane.add(okButton, 1, 1);
		containingGridPane.add(cancelButton, 2, 1);
		
		setScene(root);
	}
	
	private void addActionListeners() {
		chooseFontButton.setOnAction((actionEvent) -> {
			Optional<Font> response = Dialogs.create()
				.title("Big Character Box Options")
				.owner(this)
				.masthead("Choose a font for the big character box.")
				.showFontSelector(Font.font(fontFamily, FontOperations.getFontWeightFromFontStyle(fontStyle), FontOperations.getFontPostureFromFontStyle(fontStyle), Double.parseDouble(fontSize)));
			
			if (response != null) {
				fontFamily = response.get().getFamily();
				fontSize = Double.toString(response.get().getSize());
				fontStyle = response.get().getStyle();
				
				fontPreviewText.setFont(Font.font(fontFamily, FontOperations.getFontWeightFromFontStyle(fontStyle), FontOperations.getFontPostureFromFontStyle(fontStyle), Double.parseDouble(fontSize)));
			}
		});
		
		saveSettingsButton.setOnAction((actionEvent) -> {
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME, fontFamily);
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME, fontSize);
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_STYLE_SETTING_NAME, fontStyle);
			hide();
		});
	}
	
}
