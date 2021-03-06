/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customcontrols;

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
 */
public class XLDGenericMessageDialog extends Stage {
	
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
	
	/**
	 * 
	 * @param title the title of the dialog
	 * @param message the message displayed by the dialog
	 * @param displayDoNotShowAgainCheckBox whether or not the dialog shall show a checkbox for the user to decide whether or not the dialog shall be shown again in the future
	 * @param buttons an arbitrary number ob buttons, which will be displayed in the dialog
	 */
	public XLDGenericMessageDialog(String title, String message, boolean displayDoNotShowAgainCheckBox, XLDGenericMessageDialogButton... buttons) {
		this.buttons.addAll(Arrays.asList(buttons));
		
		this.displayDoNotShowAgainCheckBox = displayDoNotShowAgainCheckBox;
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
		
		/*
		setMaxWidth(300);
		setMaxHeight(Double.MAX_VALUE);
		*/
		
		containingVBox = new VBox();
		containingVBox.setSpacing(10);
		containingVBox.setPadding(new Insets(10));
		
		scene = new Scene(containingVBox);
		
		textFlow = new TextFlow();
		textFlow.setMaxWidth(300);
		textFlow.setTextAlignment(TextAlignment.JUSTIFY);
		
		text = new Text(message);
		text.setTextAlignment(TextAlignment.JUSTIFY);
		
		buttonHBox = new HBox();
		buttonHBox.setSpacing(10);
		buttonHBox.setAlignment(Pos.CENTER_RIGHT);
		
		if (displayDoNotShowAgainCheckBox) {
			doNotShowAgainCheckBox = new CheckBox("Do not show this dialog again");
		}
		
		setScene(scene);
	}
	
	private void addControls() {
		containingVBox.getChildren().add(textFlow);
		textFlow.getChildren().add(text);
		
		if (displayDoNotShowAgainCheckBox) {
			containingVBox.getChildren().add(doNotShowAgainCheckBox);
		}
		
		buttonHBox.getChildren().addAll(buttons);
		
		containingVBox.getChildren().add(buttonHBox);
	}
	
	private void addEventHandlers() {
		buttons.forEach((button) -> {
			
			if (button.getDecision() == Decision.OK) {
				button.setOnAction((actionEvent) -> {
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.OK_REMEMBER);
					} else {
						executeActionForDecision(Decision.OK);
					}
				});
				
			} else if (button.getDecision() == Decision.YES) {
				button.setOnAction((actionEvent) -> {
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.YES_REMEMBER);
					} else {
						executeActionForDecision(Decision.YES);
					}
				});
				
			} else if (button.getDecision() == Decision.NO) {
				button.setOnAction((actionEvent) -> {
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.NO_REMEMBER);
					} else {
						executeActionForDecision(Decision.NO);
					}
				});
				
			} else if (button.getDecision() == Decision.SAVE) {
				button.setOnAction((actionEvent) -> {
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.SAVE_REMEMBER);
					} else {
						executeActionForDecision(Decision.SAVE);
					}
				});
				
			} else if (button.getDecision() == Decision.EXIT) {
				button.setOnAction((actionEvent) -> {
					if (displayDoNotShowAgainCheckBox && doNotShowAgainCheckBox.isSelected()) {
						executeActionForDecision(Decision.EXIT_REMEMBER);
					} else {
						executeActionForDecision(Decision.EXIT);
					}
				});
				
			} else if (button.getDecision() == Decision.CANCEL) {
				button.setOnAction((actionEvent) -> {
					executeActionForDecision(Decision.CANCEL);
				});
			}
		});
	}
	
	public void setActionForDecision(Decision decision, Action action) {
		actions.put(decision, action);
	}
	
	private void executeActionForDecision(Decision decision) {
		actions.get(decision).execute(null);
	}
}
