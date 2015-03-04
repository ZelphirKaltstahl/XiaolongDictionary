package dictionary;

import dictionary.manager.CustomControlsInstanceManager;
import dictionary.customcontrols.XLDBigCharacterBox;
import dictionary.customcontrols.XLDMenuBar;
import dictionary.customcontrols.XLDVocableTable;
import dictionary.dialogs.TrainVocablesDialog;
import dictionary.manager.DialogInstanceManager;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;

public class MainApp extends Application {

	//Parents
	private Scene scene;
	private BorderPane root;
	private Stage primaryStage;

	//Control Areas
	private VBox topVBox;
	private VBox vocableActionsVBox;

	//GUI Controls
	private Label heading;
	private XLDVocableTable<Vocable> xldVocableTable;

	//ActionButtons
	private Label actionButtonsLabel;
	private Button addVocableButton;
	private Button deleteVocablesButton;
	private Button changeVocablesButton;
	private Button setLevelButton;
	private Button trainVocablesButton;
	private Button searchVocablesButton;

	//BigCharacterBox
	private VBox xldBigCharacterBoxHBox;
	private XLDBigCharacterBox xldBigCharacterBox;

	//Status Label
	private HBox statusLabelHBox;
	private Label versionLabel;
	private Label lastActionLabel;
	private Label currentModeLabel;
	
	//Big character box
	private static final String INITIAL_BIG_CHARACTER_BOX_STRING = "中";

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		Settings.getInstance().readSettings();
		//Settings.setDefaultValues();

		setUserCSS();

		initializeGUIControls();
		addControls();
		addActionListeners();
		addExitListener();

		ManagerInstanceManager.getVocableManagerInstance().loadVocables();
		//ManagerInstanceManager.getVocableFileManagerInstance().loadVocablesFromFile(new File(Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME)));
		
		stage.show();
	}

	private void setUserCSS() {
		//scene.getStylesheets().add("../resources/css/styles.css");
		//Application.setUserAgentStylesheet(this.getClass().getResource("/resources/css/style.css").toString());
	}

	private void initializeGUIControls() {
		primaryStage.setTitle("Xiaolong Dictionary");

		root = new BorderPane();

		//scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		scene = new Scene(root);
		scene.setFill(Color.AZURE);
		primaryStage.setScene(scene);

		topVBox = new VBox(0);
		topVBox.setAlignment(Pos.CENTER);

		// Actions
		vocableActionsVBox = new VBox(2);
		vocableActionsVBox.setAlignment(Pos.TOP_CENTER);
		vocableActionsVBox.setPadding(new Insets(10));

		actionButtonsLabel = new Label("Actions");
		actionButtonsLabel.getStyleClass().add("actions_label");

		addVocableButton = new Button("Add vocable");
		addVocableButton.setMaxWidth(Double.MAX_VALUE);
		deleteVocablesButton = new Button("Delete vocables");
		deleteVocablesButton.setMaxWidth(Double.MAX_VALUE);
		changeVocablesButton = new Button("Change vocables");
		changeVocablesButton.setMaxWidth(Double.MAX_VALUE);
		setLevelButton = new Button("Set learn level");
		setLevelButton.setMaxWidth(Double.MAX_VALUE);
		trainVocablesButton = new Button("Train vocables");
		trainVocablesButton.setMaxWidth(Double.MAX_VALUE);
		searchVocablesButton = new Button("Search vocables");
		searchVocablesButton.setMaxWidth(Double.MAX_VALUE);

		//vocable table
		xldVocableTable = new XLDVocableTable<>();
		xldVocableTable.initializeVocableTable();
		
		// Big Character
		xldBigCharacterBoxHBox = new VBox();
		xldBigCharacterBoxHBox.setAlignment(Pos.TOP_CENTER);
		/*xldBigCharacterBoxLabel = new Label("Characters");
		xldBigCharacterBoxLabel.setAlignment(Pos.CENTER);
		xldBigCharacterBoxLabel.setTextAlignment(TextAlignment.CENTER);*/
		xldBigCharacterBox = CustomControlsInstanceManager.createXLDBigCharacterBoxInstance(MainApp.INITIAL_BIG_CHARACTER_BOX_STRING, "Characters");
		try {
			xldBigCharacterBox.setIgnoredCharacters(Settings.getInstance().getSettingsProperty(Settings.getInstance().IGNORED_CHARACTERS_SETTING_NAME));
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
		xldVocableTable.bindXLDBigCharacterBox(xldBigCharacterBox);

		// Status Labels
		statusLabelHBox = new HBox();

		try {
			versionLabel = new Label("Version: " + Settings.getInstance().getSettingsProperty(Settings.getInstance().APPLICATION_VERSION_PROPERTY_NAME));
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		lastActionLabel = new Label("Last action: No last action");
		currentModeLabel = new Label("Mode: Browsing vocables");

		versionLabel.setTextAlignment(TextAlignment.RIGHT);
		currentModeLabel.setTextAlignment(TextAlignment.CENTER);
		lastActionLabel.setTextAlignment(TextAlignment.LEFT);

		versionLabel.setAlignment(Pos.CENTER_RIGHT);
		currentModeLabel.setAlignment(Pos.CENTER);
		lastActionLabel.setAlignment(Pos.CENTER_LEFT);

		versionLabel.setMaxWidth(Double.MAX_VALUE);
		lastActionLabel.setMaxWidth(Double.MAX_VALUE);
		currentModeLabel.setMaxWidth(Double.MAX_VALUE);

		HBox.setHgrow(lastActionLabel, Priority.ALWAYS);
		HBox.setHgrow(currentModeLabel, Priority.ALWAYS);
		HBox.setHgrow(versionLabel, Priority.ALWAYS);

		// CSS
		setUserStyleSheet();
	}

	private void setUserStyleSheet() {
		//MainApp.getUserAgentStylesheet();
		String cssPath = "resources/css/style.css";
		scene.getStylesheets().addAll(cssPath);
	}

	private void addControls() {
		addGUIControlsToTop();
		addVocableActionButtons();
		addVocableTable();
		addBigCharacterBox();
		addStatusLabel();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addGUIControlsToTop() {
		addMenuBar();
		root.setTop(topVBox);
	}

	private void addMenuBar() {
		XLDMenuBar xldMenuBar = new XLDMenuBar(primaryStage);
		xldMenuBar.prefWidthProperty().bind(primaryStage.widthProperty());

		topVBox.getChildren().add(xldMenuBar);
		//((BorderPane) scene.getRoot()).getChildren().addAll(xldMenuBar);
	}

	private void addVocableActionButtons() {
		vocableActionsVBox.getChildren().add(actionButtonsLabel);
		vocableActionsVBox.getChildren().add(addVocableButton);
		vocableActionsVBox.getChildren().add(deleteVocablesButton);
		vocableActionsVBox.getChildren().add(changeVocablesButton);
		vocableActionsVBox.getChildren().add(trainVocablesButton);
		vocableActionsVBox.getChildren().add(setLevelButton);
		vocableActionsVBox.getChildren().add(searchVocablesButton);
		root.setLeft(vocableActionsVBox);
	}

	private void addVocableTable() {
		root.setCenter(xldVocableTable);
	}

	private void addBigCharacterBox() {
		/*
		bigCharacterTextFlow.getChildren().addAll(initialBigCharacterBoxText);
		bigCharacterBoxVBox.getChildren().add(bigCharacterTextFlow);

		bigCharacterBoxButtonHBox.getChildren().addAll(previousCharacterButton, nextCharacterButton);
		bigCharacterBoxVBox.getChildren().addAll(bigCharacterBoxButtonHBox);
		*/
		//xldBigCharacterBoxHBox.getChildren().add(xldBigCharacterBoxLabel);
		xldBigCharacterBoxHBox.getChildren().add(xldBigCharacterBox);
		root.setRight(xldBigCharacterBoxHBox);
	}

	private void addStatusLabel() {
		statusLabelHBox.getChildren().addAll(lastActionLabel, currentModeLabel, versionLabel);
		root.setBottom(statusLabelHBox);
	}

	private void addActionListeners() {

		addVocableButton.setOnAction((ActionEvent event) -> {
			addVocableButtonActionPerformed();
		});
		
		changeVocablesButton.setOnAction((ActionEvent event) -> {
			changeVocableButtonActionPerformed();
		});
		
		deleteVocablesButton.setOnAction((ActionEvent event) -> {
			deleteVocablesButtonActionPerformed();
		});
		
		trainVocablesButton.setOnAction((ActionEvent) -> {
			trainVocablesButtonActionPerformed();
		});
		
		searchVocablesButton.setOnAction((ActionEvent event) -> {
			searchVocablesButtonActionPerformed();
		});
	}

	private void addExitListener() {
		primaryStage.setOnCloseRequest((WindowEvent event) -> {
			event.consume();
			Action response = ControlFXDialogDisplayer.showExitConfirmationDialog(primaryStage);
			
			if (response == Dialog.Actions.YES) {
				Settings.getInstance().writeSettings();
				ManagerInstanceManager.getVocableManagerInstance().saveVocables();
				DialogInstanceManager.closeAllDialogs();
				primaryStage.close();
			} else if (response == Dialog.Actions.NO) {
				
			}
		});
	}
	
	private void addVocableButtonActionPerformed() {
		DialogInstanceManager.getAddVocablesDialogInstance().show();
		DialogInstanceManager.getAddVocablesDialogInstance().requestFocus();
	}
	
	private void deleteVocablesButtonActionPerformed() {
		if (!xldVocableTable.getSelectionModel().getSelectedItems().isEmpty()) {
			Action response = ControlFXDialogDisplayer.showDeleteVocablesConfirmationDialog(primaryStage);
			
			if (response == Dialog.Actions.YES) {
				ObservableList<Vocable> listOfSelectedVocables = (ObservableList <Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
				ManagerInstanceManager.getVocableManagerInstance().deleteVocables(listOfSelectedVocables);
				//xldVocableTable.getSelectionModel().clearSelection();
				
			}
		} else {
			ControlFXDialogDisplayer.showNoVocablesSelectedForDeletionDialog(primaryStage);
		}
	}
	
	private void changeVocableButtonActionPerformed() {
		if (!xldVocableTable.getSelectionModel().getSelectedItems().isEmpty()) {
			Vocable changingVocable = (Vocable) xldVocableTable.getSelectionModel().getSelectedItem();
			DialogInstanceManager.getChangeVocableDialogInstanceForVocable(changingVocable).show();
			DialogInstanceManager.getChangeVocableDialogInstanceForVocable(changingVocable).requestFocus();
		} else {
			ControlFXDialogDisplayer.showNoVocableSelectedForChangeVocableDialog(primaryStage);
		}
	}
	
	private void trainVocablesButtonActionPerformed() {
		if (!xldVocableTable.getSelectionModel().getSelectedItems().isEmpty()) {
			ObservableList<Vocable> listOfSelectedVocables = (ObservableList <Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
			TrainVocablesDialog trainVocablesDialog = DialogInstanceManager.getTrainVocablesDialogInstanceForVocables(listOfSelectedVocables);
			trainVocablesDialog.show();
			trainVocablesDialog.requestFocus();
		} else {
			ControlFXDialogDisplayer.showNoVocablesSelectedForTrainingDialog(primaryStage);
		}
	}
	
	private void searchVocablesButtonActionPerformed() {
		DialogInstanceManager.getSearchVocablesDialogInstance().show();
		DialogInstanceManager.getSearchVocablesDialogInstance().requestFocus();
	}
}
