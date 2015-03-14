/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.manager;

import dictionary.customcontrols.XLDGenericMessageDialogButton;
import dictionary.dialogs.AddVocablesDialog;
import dictionary.dialogs.ChangeVocableDialog;
import dictionary.dialogs.InsertSpecialCharacterDialog;
import dictionary.dialogs.SearchVocablesDialog;
import dictionary.dialogs.TrainVocablesDialog;
import dictionary.customcontrols.XLDGenericMessageDialog;
import dictionary.dialogs.options.BigCharacterBoxOptionsDialog;
import dictionary.exceptions.SettingNotFoundException;
import dictionary.model.Decision;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author XiaoLong
 */
public class DialogInstanceManager {

	private static AddVocablesDialog addVocablesDialogInstance;
	private static ChangeVocableDialog changeVocableDialogInstance;
	private static TrainVocablesDialog trainVocablesDialogInstance;
	private static SearchVocablesDialog searchVocablesDialogInstance;
	private static InsertSpecialCharacterDialog insertSpecialCharacterDialogInstance;

	private static XLDGenericMessageDialog<Object> saveVocablesOnExitConfirmationDialogInstance;
	private static XLDGenericMessageDialog<Object> exitConfirmationDialogInstance;
	private static XLDGenericMessageDialog<Object> addingDuplicateVocableDialogInstance;

	// OPTIONS
	private static BigCharacterBoxOptionsDialog bigCharacterBoxOptionsDialogInstance;

	public static AddVocablesDialog getAddVocablesDialogInstance() {
		if (addVocablesDialogInstance == null) {
			addVocablesDialogInstance = new AddVocablesDialog(Modality.NONE);
			addVocablesDialogInstance.initialize();
		}
		return addVocablesDialogInstance;
	}

	public static ChangeVocableDialog getChangeVocableDialogInstanceForVocable(Vocable vocable) {
		if (changeVocableDialogInstance == null) {
			changeVocableDialogInstance = new ChangeVocableDialog(Modality.NONE);
			changeVocableDialogInstance.initialize(vocable);
			return changeVocableDialogInstance;
		} else {
			changeVocableDialogInstance.setVocable(vocable);
			return changeVocableDialogInstance;
		}
	}

	public static SearchVocablesDialog getSearchVocablesDialogInstance() {
		if (searchVocablesDialogInstance == null) {
			searchVocablesDialogInstance = new SearchVocablesDialog(Modality.NONE);
			searchVocablesDialogInstance.initialize();
		}
		return searchVocablesDialogInstance;
	}

	public static InsertSpecialCharacterDialog getInsertSpecialCharacterDialogInstance() {
		if (insertSpecialCharacterDialogInstance == null) {
			insertSpecialCharacterDialogInstance = new InsertSpecialCharacterDialog(Modality.NONE);
			insertSpecialCharacterDialogInstance.initialize();
		}
		return insertSpecialCharacterDialogInstance;
	}

	public static TrainVocablesDialog getTrainVocablesDialogInstanceForVocables(List<Vocable> trainingVocables) {
		trainVocablesDialogInstance = new TrainVocablesDialog(Modality.NONE);
		trainVocablesDialogInstance.initialize(trainingVocables);
		return trainVocablesDialogInstance;
	}

	public static BigCharacterBoxOptionsDialog getBigCharacterBoxOptionsDialogInstance() {
		if (bigCharacterBoxOptionsDialogInstance == null) {
			System.out.println("creating new instance of big character box options dialog");
			bigCharacterBoxOptionsDialogInstance = new BigCharacterBoxOptionsDialog();
			bigCharacterBoxOptionsDialogInstance.initialize();
		}
		return bigCharacterBoxOptionsDialogInstance;
	}

	public static XLDGenericMessageDialog<Object> getAddingDuplicateVocableDialogInstance(Stage owner) {
		if (addingDuplicateVocableDialogInstance == null) {
			String dialogTitle = "Duplicate Vocable";
			String dialogMessage = "The vocable you want to add is already in the currently active dictionary.";
			boolean dialogDisplaysDoNotShowAgainCheckBox = false;
			boolean dialogHasData = false;

			addingDuplicateVocableDialogInstance = new XLDGenericMessageDialog<>(
					dialogTitle,
					dialogMessage,
					dialogDisplaysDoNotShowAgainCheckBox,
					dialogHasData,
					new XLDGenericMessageDialogButton("OK", Decision.OK)
			);

			addingDuplicateVocableDialogInstance.initModality(Modality.APPLICATION_MODAL);
			addingDuplicateVocableDialogInstance.initOwner(owner);
			addingDuplicateVocableDialogInstance.initialize();

			addingDuplicateVocableDialogInstance.setActionForDecision(
					Decision.OK,
					(dictionary.model.Action) (Object value) -> {
						addingDuplicateVocableDialogInstance.close();
					}
			);

		}

		return addingDuplicateVocableDialogInstance;
	}

	public static XLDGenericMessageDialog<Object> getExitConfirmationDialogInstance(Stage owner) {
		if (exitConfirmationDialogInstance == null) {
			try {
				String dialogTitle = "Exit Confirmation";
				String dialogMessage = "Do you really want to exit " + Settings.getInstance().getSettingsProperty(Settings.getInstance().APPLICATION_NAME_SETTING_NAME) + "?";
				boolean dialogDisplaysDoNotShowAgainCheckBox = true;
				boolean dialogHasData = false;

				exitConfirmationDialogInstance = new XLDGenericMessageDialog<>(
						dialogTitle,
						dialogMessage,
						dialogDisplaysDoNotShowAgainCheckBox,
						dialogHasData,
						new XLDGenericMessageDialogButton("Yes", Decision.YES),
						new XLDGenericMessageDialogButton("No", Decision.NO)
				);

				exitConfirmationDialogInstance.initModality(Modality.APPLICATION_MODAL);
				exitConfirmationDialogInstance.initOwner(owner);
				exitConfirmationDialogInstance.initialize();

				exitConfirmationDialogInstance.setActionForDecision(
						Decision.YES,
						(dictionary.model.Action) (Object value) -> {
							Settings.getInstance().writeSettings();
							DialogInstanceManager.closeAllDialogs();
							owner.close();
						}
				);

				exitConfirmationDialogInstance.setActionForDecision(
						Decision.YES_REMEMBER,
						(dictionary.model.Action) (Object value) -> {
							Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_EXIT_CONFIRMATION_SETTING_NAME, Boolean.toString(false));

							Settings.getInstance().writeSettings();
							DialogInstanceManager.closeAllDialogs();
							owner.close();
						}
				);

				exitConfirmationDialogInstance.setActionForDecision(
						Decision.NO,
						(dictionary.model.Action) (Object value) -> {
							exitConfirmationDialogInstance.close();
						}
				);

				exitConfirmationDialogInstance.setActionForDecision(
						Decision.NO_REMEMBER,
						(dictionary.model.Action) (Object value) -> {
							exitConfirmationDialogInstance.close();
						}
				);

			} catch (SettingNotFoundException ex) {
				Logger.getLogger(DialogInstanceManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return exitConfirmationDialogInstance;
	}

	public static XLDGenericMessageDialog<Object> getSaveVocablesOnExitConfirmationDialogInstance(Stage owner) {
		if (saveVocablesOnExitConfirmationDialogInstance == null) {

			String dialogTitle = "Save Vocable Changes Confirmation";
			String dialogMessage = "Before you leave ...\nThere are unsaved vocable changes. Do you want to save these changes?";
			boolean dialogDisplaysDoNotShowAgainCheckBox = true;
			boolean dialogHasData = false;

			saveVocablesOnExitConfirmationDialogInstance = new XLDGenericMessageDialog<>(
					dialogTitle,
					dialogMessage,
					dialogDisplaysDoNotShowAgainCheckBox,
					dialogHasData,
					new XLDGenericMessageDialogButton("Yes", Decision.YES),
					new XLDGenericMessageDialogButton("No", Decision.NO)
			);

			saveVocablesOnExitConfirmationDialogInstance.initModality(Modality.APPLICATION_MODAL);
			saveVocablesOnExitConfirmationDialogInstance.initOwner(owner);
			saveVocablesOnExitConfirmationDialogInstance.initialize();

			saveVocablesOnExitConfirmationDialogInstance.setActionForDecision(
					Decision.YES,
					(dictionary.model.Action) (Object value) -> {
						ManagerInstanceManager.getVocableManagerInstance().saveVocables();
						saveVocablesOnExitConfirmationDialogInstance.close();
					}
			);

			saveVocablesOnExitConfirmationDialogInstance.setActionForDecision(
					Decision.YES_REMEMBER,
					(dictionary.model.Action) (Object value) -> {
						Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME, Boolean.toString(false));
						Settings.getInstance().changeSettingsProperty(Settings.getInstance().SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME, Boolean.toString(true));
						ManagerInstanceManager.getVocableManagerInstance().saveVocables();
						saveVocablesOnExitConfirmationDialogInstance.close();
					}
			);

			saveVocablesOnExitConfirmationDialogInstance.setActionForDecision(
					Decision.NO,
					(dictionary.model.Action) (Object value) -> {
						saveVocablesOnExitConfirmationDialogInstance.close();
					}
			);

			saveVocablesOnExitConfirmationDialogInstance.setActionForDecision(
					Decision.NO_REMEMBER,
					(dictionary.model.Action) (Object value) -> {
						Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME, Boolean.toString(false));
						Settings.getInstance().changeSettingsProperty(Settings.getInstance().SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME, Boolean.toString(false));
						saveVocablesOnExitConfirmationDialogInstance.close();
					}
			);

		}
		return saveVocablesOnExitConfirmationDialogInstance;
	}

	public static void closeAllDialogs() {
		if (addVocablesDialogInstance != null) {
			addVocablesDialogInstance.close();
		}
		if (searchVocablesDialogInstance != null) {
			searchVocablesDialogInstance.close();
		}
		if (insertSpecialCharacterDialogInstance != null) {
			insertSpecialCharacterDialogInstance.close();
		}
		if (trainVocablesDialogInstance != null) {
			trainVocablesDialogInstance.close();
		}
		if (changeVocableDialogInstance != null) {
			changeVocableDialogInstance.close();
		}
		if (bigCharacterBoxOptionsDialogInstance != null) {
			bigCharacterBoxOptionsDialogInstance.close();
		}
		if (exitConfirmationDialogInstance != null) {
			exitConfirmationDialogInstance.close();
		}
		if (saveVocablesOnExitConfirmationDialogInstance != null) {
			saveVocablesOnExitConfirmationDialogInstance.close();
		}
	}
}
