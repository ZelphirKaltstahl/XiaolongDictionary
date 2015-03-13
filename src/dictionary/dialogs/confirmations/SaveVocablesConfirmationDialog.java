/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs.confirmations;

import dictionary.listeners.DecisionListener;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author xiaolong
 */
public class SaveVocablesConfirmationDialog extends Stage {
	
	private Scene scene;
	private VBox containingVBox;
	
	private Text text;
	private TextFlow textFlow;
	
	private HBox buttonHBox;
	
	private Button yesButton;
	private Button noButton;
	
	private CheckBox rememberMyDecisionCheckBox;
	
	private Decision response;
	
	private final List<DecisionListener> decisionListeners = new ArrayList<>();
	
	public SaveVocablesConfirmationDialog() {
		
	}
	
	public void initialize() {
		initializeControls();
		addControls();
		addEventHandlers();
	}
	
	private void initializeControls() {
		setTitle("Save Vocables Confirmation");
		
		containingVBox = new VBox();
		containingVBox.setSpacing(10);
		containingVBox.setPadding(new Insets(10));
		
		scene = new Scene(containingVBox);
		
		
		textFlow = new TextFlow();
		
		text = new Text("There are unsaved vocable changes. Do you want to save the changes?");
		text.setTextAlignment(TextAlignment.JUSTIFY);
		
		buttonHBox = new HBox();
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		
		yesButton = new Button("Yes");
		noButton = new Button("No");
		
		rememberMyDecisionCheckBox = new CheckBox("Remember my decision");
		
		setScene(scene);
	}
	
	private void addControls() {
		containingVBox.getChildren().add(textFlow);
		textFlow.getChildren().add(text);
		
		containingVBox.getChildren().add(rememberMyDecisionCheckBox);
		
		buttonHBox.getChildren().addAll(yesButton, noButton);
		
		containingVBox.getChildren().add(buttonHBox);
	}
	
	private void addEventHandlers() {
		yesButton.setOnAction((actionEvent) -> {
			if(rememberMyDecisionCheckBox.isSelected()) {
				response = Decision.YES_REMEMBER;
			} else {
				response = Decision.YES;
			}
			hide();
			notifyDecisionListeners();
		});
		
		noButton.setOnAction((actionEvent) -> {
			if(rememberMyDecisionCheckBox.isSelected()) {
				response = Decision.NO_REMEMBER;
			} else {
				response = Decision.NO;
			}
			hide();
			notifyDecisionListeners();
		});
		
		setOnCloseRequest((WindowEvent event) -> {
			event.consume();
			response = Decision.NO;
			hide();
			notifyDecisionListeners();
		});
	}
	
	
	public void registerDecisionListener(DecisionListener decisionListener) {
		decisionListeners.add(decisionListener);
	}
	
	public void unregisterListeners() {
		decisionListeners.clear();
	}
	
	private void notifyDecisionListeners() {
		decisionListeners.stream().forEach((decisionListener) -> {
			decisionListener.reactOnDecision(this, response);
		});
	}
}
