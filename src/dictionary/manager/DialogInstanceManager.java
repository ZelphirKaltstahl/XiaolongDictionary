/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.manager;

import dictionary.dialogs.AddVocablesDialog;
import dictionary.dialogs.ChangeVocableDialog;
import dictionary.dialogs.InsertSpecialCharacterDialog;
import dictionary.dialogs.SearchVocablesDialog;
import dictionary.dialogs.TrainVocablesDialog;
import dictionary.dialogs.confirmations.SaveVocablesConfirmationDialog;
import dictionary.dialogs.options.BigCharacterBoxOptionsDialog;
import dictionary.model.Vocable;
import java.util.List;
import javafx.stage.Modality;

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
	private static SaveVocablesConfirmationDialog saveVocablesConfirmationDialogInstance;
	
	// OPTIONS
	private static BigCharacterBoxOptionsDialog bigCharacterBoxOptionsDialogInstance;
	
	public static AddVocablesDialog getAddVocablesDialogInstance() {
		if(addVocablesDialogInstance == null) {
			addVocablesDialogInstance = new AddVocablesDialog(Modality.NONE);
			addVocablesDialogInstance.initialize();
		}
		return addVocablesDialogInstance;
	}
	
	public static ChangeVocableDialog getChangeVocableDialogInstanceForVocable(Vocable vocable) {
		if(changeVocableDialogInstance == null) {
			changeVocableDialogInstance = new ChangeVocableDialog(Modality.NONE);
			changeVocableDialogInstance.initialize(vocable);
			return changeVocableDialogInstance;
		} else {
			changeVocableDialogInstance.setVocable(vocable);
			return changeVocableDialogInstance;
		}
	}
	
	public static SearchVocablesDialog getSearchVocablesDialogInstance() {
		if(searchVocablesDialogInstance == null) {
			searchVocablesDialogInstance = new SearchVocablesDialog(Modality.NONE);
			searchVocablesDialogInstance.initialize();
		}
		return searchVocablesDialogInstance;
	}
	
	public static InsertSpecialCharacterDialog getInsertSpecialCharacterDialogInstance() {
		if(insertSpecialCharacterDialogInstance == null) {
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
	
	public static SaveVocablesConfirmationDialog getSaveVocablesConfirmationDialogInstance() {
		if (saveVocablesConfirmationDialogInstance == null) {
			saveVocablesConfirmationDialogInstance = new SaveVocablesConfirmationDialog();
			saveVocablesConfirmationDialogInstance.initialize();
		}
		return saveVocablesConfirmationDialogInstance;
	}
	
	public static void closeAllDialogs() {
		if (addVocablesDialogInstance != null) addVocablesDialogInstance.close();
		if (searchVocablesDialogInstance != null) searchVocablesDialogInstance.close();
		if (insertSpecialCharacterDialogInstance != null) insertSpecialCharacterDialogInstance.close();
		if (trainVocablesDialogInstance != null) trainVocablesDialogInstance.close();
		if (changeVocableDialogInstance != null) changeVocableDialogInstance.close();
		if (bigCharacterBoxOptionsDialogInstance != null) bigCharacterBoxOptionsDialogInstance.close();
		if (saveVocablesConfirmationDialogInstance != null) saveVocablesConfirmationDialogInstance.close();
	}
}
