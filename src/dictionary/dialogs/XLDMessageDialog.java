/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.dialogs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author XiaoLong
 */
public class XLDMessageDialog extends Stage {
	
	private static final int MAX_WIDTH = 300;
	private static final int MAX_HEIGHT = 400;
	
	private final String title;
	private final String message;
	
	private TextFlow messageTextFlow;
	
	
	public XLDMessageDialog(String title, String message, Modality modality) {
		this.title = title;
		this.message = message;
		initModality(modality);
	}

	public void init() {
		setTitle(title);
		
		messageTextFlow = new TextFlow();
		messageTextFlow.getChildren().add(new Text(message));
		messageTextFlow.setTextAlignment(TextAlignment.JUSTIFY);
		messageTextFlow.setMaxWidth(MAX_WIDTH);
		messageTextFlow.setMaxHeight(MAX_HEIGHT);
		
		Button okButton = new Button("OK");
		
		VBox vBox = new VBox(messageTextFlow, okButton);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(5);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		
		Scene scene = new Scene(vBox);

		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});

		setScene(scene);
		sizeToScene();
		setResizable(false);
	}
}
