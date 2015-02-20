/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.manager;

import dictionary.dialogs.AddVocablesDialog;
import dictionary.dialogs.InsertSpecialCharacterDialog;
import dictionary.dialogs.SearchVocablesDialog;
import dictionary.dialogs.TrainVocablesDialog;
import dictionary.model.Vocable;
import java.util.List;
import javafx.stage.Modality;

/**
 *
 * @author XiaoLong
 */
public class DialogInstanceManager {
	private static AddVocablesDialog addVocablesDialogInstance;
	private static TrainVocablesDialog trainVocablesDialogInstance;
	private static SearchVocablesDialog searchVocablesDialogInstance;
	private static InsertSpecialCharacterDialog insertSpecialCharacterDialogInstance;
	
	public static AddVocablesDialog getAddVocablesDialogInstance() {
		if(addVocablesDialogInstance == null) {
			addVocablesDialogInstance = new AddVocablesDialog(Modality.NONE);
			addVocablesDialogInstance.init();
		}
		return addVocablesDialogInstance;
	}
	
	public static SearchVocablesDialog getSearchVocablesDialogInstance() {
		if(searchVocablesDialogInstance == null) {
			searchVocablesDialogInstance = new SearchVocablesDialog(Modality.NONE);
			searchVocablesDialogInstance.init();
		}
		return searchVocablesDialogInstance;
	}
	
	public static InsertSpecialCharacterDialog getInsertSpecialCharacterDialogInstance() {
		if(insertSpecialCharacterDialogInstance == null) {
			insertSpecialCharacterDialogInstance = new InsertSpecialCharacterDialog(Modality.NONE);
			insertSpecialCharacterDialogInstance.init();
		}
		return insertSpecialCharacterDialogInstance;
	}
	
	public static TrainVocablesDialog getTrainVocablesDialogInstanceForVocables(List<Vocable> trainingVocables) {
		trainVocablesDialogInstance = new TrainVocablesDialog(Modality.NONE);
		trainVocablesDialogInstance.initialize(trainingVocables);
		return trainVocablesDialogInstance;
	}
	
	public static void closeAllDialogs() {
		if (addVocablesDialogInstance != null) addVocablesDialogInstance.close();
		if (searchVocablesDialogInstance != null) searchVocablesDialogInstance.close();
		if (insertSpecialCharacterDialogInstance != null) insertSpecialCharacterDialogInstance.close();
		if (trainVocablesDialogInstance != null) trainVocablesDialogInstance.close();
	}
}
