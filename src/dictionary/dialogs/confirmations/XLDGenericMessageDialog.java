/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs.confirmations;

import dictionary.customcontrols.XLDGenericMessageDialogButton;
import dictionary.model.Decision;
import dictionary.model.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

/**
 *
 * @author xiaolong
 * @param <T> the type of the data carried by this dialog
 */
public class XLDGenericMessageDialog<T> extends Stage {
	
	public static final Button OK_BUTTON = new Button("OK");
	public static final Button YES_BUTTON = new Button("Yes");
	public static final Button NO_BUTTON = new Button("No");
	public static final Button SAVE_BUTTON = new Button("Save");
	public static final Button CANCEL_BUTTON = new Button("Cancel");
	public static final Button EXIT_BUTTON = new Button("Exit");
	
	private final HashMap<Decision, Action<Object>> actions = new HashMap<>();
	private final List<XLDGenericMessageDialogButton> buttons = new ArrayList<>();
	
	private Scene scene;
	
	private VBox containingVBox;
	private HBox buttonHBox;
	
	private CheckBox doNotShowAgainCheckBox;
	
	private TextFlow textFlow;
	private Text text;
	
	private boolean displayDoNotShowAgainCheckBox = false;
	private String message = "No message";
	private String title = "No title";
	private boolean hasData = false;
	private T data;
	
	/**
	 * 
	 * @param title the title of the dialog
	 * @param message the message displayed by the dialog
	 * @param displayDoNotShowAgainCheckBox whether or not the dialog shall show a checkbox for the user to decide whether or not the dialog shall be shown again in the future
	 * @param hasData whether or not this dialog carries any data, which is used in the actions set for the decisions connected to the buttons of this dialog
	 * @param buttons an arbitrary number ob buttons, which will be displayed in the dialog
	 */
	public XLDGenericMessageDialog(String title, String message, boolean displayDoNotShowAgainCheckBox, boolean hasData, XLDGenericMessageDialogButton... buttons) {
		this.buttons.addAll(Arrays.asList(buttons));
		
		this.displayDoNotShowAgainCheckBox = displayDoNotShowAgainCheckBox;
		this.hasData = hasData;
		this.message = message;
		this.title = title;
	}
	
	public void initialize() {
		initializeControls();
		addControls();
		addEventHandlers();
	}
	
	private void initializeControls() {
		setTitle(title);
		
		containingVBox = new VBox();
		containingVBox.setSpacing(10);
		containingVBox.setPadding(new Insets(10));
		
		scene = new Scene(containingVBox);
		
		textFlow = new TextFlow();
		
		text = new Text(message);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		
		buttonHBox = new HBox();
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		
		doNotShowAgainCheckBox = new CheckBox("Do not show this dialog again");
		
		setScene(scene);
	}
	
	private void addControls() {
		containingVBox.getChildren().add(textFlow);
		textFlow.getChildren().add(text);
		
		containingVBox.getChildren().add(doNotShowAgainCheckBox);
		
		buttonHBox.getChildren().addAll(buttons);
		
		containingVBox.getChildren().add(buttonHBox);
	}
	
	private void addEventHandlers() {
		buttons.forEach((button) -> {
			
			if (button.getDecision() == Decision.OK) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}
					
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.OK_REMEMBER, value);
					} else {
						executeActionForDecision(Decision.OK, value);
					}
				});
				
			} else if (button.getDecision() == Decision.YES) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}
					
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.YES_REMEMBER, value);
					} else {
						executeActionForDecision(Decision.YES, value);
					}
				});
				
			} else if (button.getDecision() == Decision.NO) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}
					

					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.NO_REMEMBER, value);
					} else {
						executeActionForDecision(Decision.NO, value);
					}
				});
				
			} else if (button.getDecision() == Decision.SAVE) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}

					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.SAVE_REMEMBER, value);
					} else {
						executeActionForDecision(Decision.SAVE, value);
					}
				});
				
			} else if (button.getDecision() == Decision.EXIT) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}

					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.EXIT_REMEMBER, value);
					} else {
						executeActionForDecision(Decision.EXIT, value);
					}
				});
				
			} else if (button.getDecision() == Decision.CANCEL) {
				button.setOnAction((actionEvent) -> {
					T value = null;
					if (hasData) {
						value = data;
					}
					
					executeActionForDecision(Decision.CANCEL, value);
				});
			}
		});
	}
	
	public void setActionForDecision(Decision decision, Action action) {
		actions.put(decision, action);
	}
	
	private void executeActionForDecision(Decision decision, T value) {
		actions.get(decision).execute(value);
	}
}
