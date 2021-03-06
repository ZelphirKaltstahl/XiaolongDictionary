package dictionary;

import dictionary.manager.CustomControlsInstanceManager;
import dictionary.customcontrols.XLDBigCharacterBox;
import dictionary.customcontrols.XLDVocableDetailsBox;
import dictionary.customcontrols.XLDMenuBar;
import dictionary.customcontrols.XLDVocableTable;
import dictionary.dialogs.TrainVocablesDialog;
import dictionary.manager.DialogInstanceManager;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.manager.ManagerInstanceManager;
import dictionary.model.Decision;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

	// Vocable details box
	private XLDVocableDetailsBox xldVocableDetailsBox;
	private ScrollPane vocableDetailsScrollPane;
	private GridPane centerGridPane;

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		Settings.getInstance().readSettings();
		Settings.setDefaultValues();

		setUserCSS();

		initializeGUIControls();
		addControls();
		addActionEventHandlers();
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
		primaryStage.setResizable(true);

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

		centerGridPane = new GridPane();
		centerGridPane.setMaxWidth(Double.MAX_VALUE);
		centerGridPane.setMaxHeight(Double.MAX_VALUE);
		centerGridPane.getColumnConstraints().add(new ColumnConstraints());
		centerGridPane.getColumnConstraints().get(0).setPercentWidth(100);
		centerGridPane.getRowConstraints().add(new RowConstraints());
		centerGridPane.getRowConstraints().get(0).setPercentHeight(80);
		centerGridPane.getRowConstraints().add(new RowConstraints());
		centerGridPane.getRowConstraints().get(1).setPercentHeight(20);

		//centerVBox.setAlignment(Pos.TOP_CENTER);
		//centerVBox.setPrefWidth(Double.MAX_VALUE);
		//vocable table
		xldVocableTable = new XLDVocableTable<>();
		xldVocableTable.initializeVocableTable();

		xldVocableDetailsBox = ManagerInstanceManager.getCustomControlsInstanceManager().getXLDVocableDetailsBox();

		vocableDetailsScrollPane = new ScrollPane();
		vocableDetailsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		vocableDetailsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		vocableDetailsScrollPane.setMaxWidth(Double.MAX_VALUE);
		vocableDetailsScrollPane.setMaxHeight(Double.MAX_VALUE);
		vocableDetailsScrollPane.setFitToWidth(true);

		// Big Character
		xldBigCharacterBoxHBox = new VBox();
		xldBigCharacterBoxHBox.setAlignment(Pos.TOP_CENTER);
		/*xldBigCharacterBoxLabel = new Label("Characters");
		 xldBigCharacterBoxLabel.setAlignment(Pos.CENTER);
		 xldBigCharacterBoxLabel.setTextAlignment(TextAlignment.CENTER);*/
		xldBigCharacterBox = CustomControlsInstanceManager.createXLDBigCharacterBoxInstance(MainApp.INITIAL_BIG_CHARACTER_BOX_STRING, "Characters");
		try {
			xldBigCharacterBox.setIgnoredCharacters(Settings.getInstance().getSettingsProperty(Settings.getInstance().BIG_CAHRACTER_BOX_IGNORED_CHARACTERS_SETTING_NAME));
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
		xldVocableTable.bindXLDBigCharacterBox(xldBigCharacterBox);
		xldVocableTable.bindXLDVocableDetailsBox(xldVocableDetailsBox);

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
		addControlsCenter();
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

	private void addControlsCenter() {
		root.setCenter(centerGridPane);
		centerGridPane.add(xldVocableTable, 0, 0);
		vocableDetailsScrollPane.setContent(xldVocableDetailsBox);
		HBox stretchBox = new HBox();
		centerGridPane.add(vocableDetailsScrollPane, 0, 1);
		//addVocableTable();
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

	private void addActionEventHandlers() {

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
			try {
				event.consume();
				
				if (!ManagerInstanceManager.getVocableManagerInstance().getVocableSavedProperty().get()) {
					if (Settings.getInstance().getSettingsProperty(Settings.getInstance().DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME).equals(Boolean.toString(true))) {
						DialogInstanceManager.getSaveVocablesOnExitConfirmationDialogInstance(primaryStage).showAndWait();
					} else {
						if (Settings.getInstance().getSettingsProperty(Settings.getInstance().SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME).equals(Boolean.toString(true))) {
							ManagerInstanceManager.getVocableManagerInstance().saveVocables();
						}
					}
				}
				
				if (Settings.getInstance().getSettingsProperty(Settings.getInstance().DIALOG_SHOW_EXIT_CONFIRMATION_SETTING_NAME).equals(Boolean.toString(true))) {
					DialogInstanceManager.getExitConfirmationDialogInstance(primaryStage).showAndWait();
				} else {
					exit();
				}
				
			} catch (SettingNotFoundException ex) {
				Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	private void addVocableButtonActionPerformed() {
		DialogInstanceManager.getAddVocablesDialogInstance().show();
		DialogInstanceManager.getAddVocablesDialogInstance().requestFocus();
	}

	private void deleteVocablesButtonActionPerformed() {
		if (!xldVocableTable.getSelectionModel().getSelectedItems().isEmpty()) {
			try {
				if (Settings.getInstance().getSettingsProperty(Settings.getInstance().DIALOG_SHOW_DELETE_VOCABLE_CONFIRMATION_SETTING_NAME).equals(Boolean.toString(true))) {
					
					DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).setActionForDecision(
							Decision.YES,
							(dictionary.model.Action) (Object value) -> {
								ObservableList<Vocable> listOfSelectedVocables = (ObservableList<Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
								ManagerInstanceManager.getVocableManagerInstance().deleteVocables(listOfSelectedVocables);
								DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).close();
							}
					);
					
					DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).setActionForDecision(
							Decision.YES_REMEMBER,
							(dictionary.model.Action) (Object value) -> {
								Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_DELETE_VOCABLE_CONFIRMATION_SETTING_NAME, Boolean.toString(false));
								Settings.getInstance().changeSettingsProperty(Settings.getInstance().DELETE_SELECTED_VOCABLES_ON_BUTTON_CLICK_SETTING_NAME, Boolean.toString(true));
								ObservableList<Vocable> listOfSelectedVocables = (ObservableList<Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
								ManagerInstanceManager.getVocableManagerInstance().deleteVocables(listOfSelectedVocables);
								DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).close();
							}
					);
					
					DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).setActionForDecision(
							Decision.NO,
							(dictionary.model.Action) (Object value) -> DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).close()
					);
					
					DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).setActionForDecision(
							Decision.NO_REMEMBER,
							(dictionary.model.Action) (Object value) -> DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).close()
					);
					
					DialogInstanceManager.getDeleteVocablesConfirmationDialog(primaryStage).show();
					
				} else {
					if (Settings.getInstance().getSettingsProperty(Settings.getInstance().DELETE_SELECTED_VOCABLES_ON_BUTTON_CLICK_SETTING_NAME).equals(Boolean.toString(true))) {
						ObservableList<Vocable> listOfSelectedVocables = (ObservableList<Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
						ManagerInstanceManager.getVocableManagerInstance().deleteVocables(listOfSelectedVocables);
					}
				}
			} catch (SettingNotFoundException ex) {
				Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
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
			ObservableList<Vocable> listOfSelectedVocables = (ObservableList<Vocable>) xldVocableTable.getSelectionModel().getSelectedItems();
			List<Vocable> trainingVocables = new ArrayList<>();

			listOfSelectedVocables.stream().forEach((vocable) -> trainingVocables.add(vocable));

			TrainVocablesDialog trainVocablesDialog = DialogInstanceManager.getTrainVocablesDialogInstanceForVocables(trainingVocables);
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

	private void exit() {
		Settings.getInstance().writeSettings();
		DialogInstanceManager.closeAllDialogs();
		primaryStage.close();
	}
}
