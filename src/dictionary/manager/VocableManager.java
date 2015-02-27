/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.manager;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.tasks.VocableSearchTask;
import dictionary.exceptions.VocableAlreadyExistsException;
import dictionary.helpers.ListOperationsHelper;
import dictionary.listeners.VocableListLoadedListener;
import dictionary.model.Settings;
import dictionary.model.Vocable;
import dictionary.model.VocableSearchData;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;

/**
 *
 * @author XiaoLong
 */
public class VocableManager implements VocableListLoadedListener {

	private ObservableList<Vocable> vocableList;
	private ObservableList<Vocable> searchResultVocableList;
	
	//private boolean vocableIsInVocableList = false;

	public VocableManager() {
		// empty
	}
	
	public void initialize () {
		vocableList = FXCollections.observableArrayList();
		searchResultVocableList = FXCollections.observableArrayList();
		
		registerAsListener();
	}
	
	private void registerAsListener() {
		ManagerInstanceManager.getVocableFileManagerInstance().registerVocableListLoadedListener(this);
	}

	public void addVocableListChangeListener(ListChangeListener<Vocable> listChangeListener) {
		vocableList.addListener(listChangeListener);
	}

	public void addSearchResultVocableListChangeListener(ListChangeListener<Vocable> listChangeListener) {
		searchResultVocableList.addListener(listChangeListener);
	}

	public void addVocable(Vocable vocable) throws VocableAlreadyExistsException {
		if (!isVocableInDictionary(vocable)) {
			vocableList.add(vocable);
		} else {
			throw new VocableAlreadyExistsException("Vocable is already in the dictionary.");
		}
	}

	public void deleteVocables(ObservableList<Vocable> listOfVocables) {
		vocableList.removeAll(listOfVocables);
		searchResultVocableList.removeAll(listOfVocables);
	}

	public void deleteVocable(Vocable vocable) {
		vocableList.remove(vocable);
		searchResultVocableList.remove(vocable);
	}

	public void changeVocable(Vocable oldVocable, Vocable changedVocable) {
		int indexOfOldVocable = vocableList.indexOf(oldVocable);
		if(indexOfOldVocable != -1) {
			vocableList.set(indexOfOldVocable, changedVocable);
		}
		
		indexOfOldVocable = searchResultVocableList.indexOf(oldVocable);
		if(indexOfOldVocable != -1) {
			searchResultVocableList.set(indexOfOldVocable, changedVocable);
		}
	}

	public void searchVocables(VocableSearchData vocableSearchData) {
		VocableSearchTask vocableSearchTask;
		ObservableList<Vocable> listOfVocables;

		if (vocableSearchData.isANDSearch()) {
			listOfVocables = searchResultVocableList;
		} else { //OR and normal search
			listOfVocables = vocableList;
		}

		vocableSearchTask = new VocableSearchTask(vocableSearchData, listOfVocables);
		
		
		vocableSearchTask.setOnSucceeded((Event workerStateEvent) -> {
			
			if (vocableSearchData.isANDSearch()) {
				searchResultVocableList.clear();
				searchResultVocableList.addAll((ObservableList<Vocable>) vocableSearchTask.getValue());
				
			} else if (vocableSearchData.isORSearch()) {
				((ObservableList<Vocable>) vocableSearchTask.getValue())
					.stream()
					.filter(((vocable) -> !searchResultVocableList.contains(vocable)))
					.forEach((vocable) -> searchResultVocableList.add(vocable));
				
			} else {
				searchResultVocableList.clear();
				searchResultVocableList.addAll((ObservableList<Vocable>) vocableSearchTask.getValue());
			}
		});
		
		vocableSearchTask.setOnCancelled((workerStateEvent) -> {
			ControlFXDialogDisplayer.showSearchCancelledDialog(null);
		});

		vocableSearchTask.setOnFailed((workerStateEvent) -> {
			ControlFXDialogDisplayer.showSearchFailedDialog(null);
		});
		
		vocableSearchTask.run();
	}

	public ObservableList<Vocable> getVocableList() {
		return vocableList;
	}
	
	/*public void setVocableList(List<Vocable> listOfVocables) {
		this.vocableList = FXCollections.observableArrayList(listOfVocables);
		notifyVocableListChangeListeners();
	}*/

	public ObservableList<Vocable> getSearchResultList() {
		return searchResultVocableList;
	}

	private boolean isVocableInDictionary(Vocable checkedVocable) {
		// Since the gui needs to react on this, there is no speed increasement if done in a task another thread than the gui thread
		// assume vocable is in vocable list until proven otherwise
		/*vocableIsInVocableList = true;
		
		VocableExistenceCheckTask vocableExistenceCheckTask = new VocableExistenceCheckTask(checkedVocable, vocableList);
		
		vocableExistenceCheckTask.setOnSucceeded((workerStateEvent) -> {
			System.out.println("RESULT OF TASK: " + (boolean) vocableExistenceCheckTask.getValue());
			vocableIsInVocableList = (boolean) vocableExistenceCheckTask.getValue();
			System.out.println("Existence Check Succeeded");
		});
		
		vocableExistenceCheckTask.setOnCancelled((workerStateEvent) -> {
			vocableIsInVocableList = true;
			System.out.println("Existence Check Cancelled");
		});

		vocableExistenceCheckTask.setOnFailed((workerStateEvent) -> {
			vocableIsInVocableList = true;
			System.out.println("Existence Check Failed");
		});
		
		vocableExistenceCheckTask.run();
		System.out.println("Existence Check Running");
		
		System.out.println("After checking for existence of the vocable: " + vocableIsInVocableList);
		
		return vocableIsInVocableList;*/
		return vocableList.parallelStream().anyMatch(
			(currentVocable) -> (
				ListOperationsHelper.compareStringLists(currentVocable.getFirstLanguageTranslations(), checkedVocable.getFirstLanguageTranslations())
				&& ListOperationsHelper.compareStringLists(currentVocable.getFirstLanguagePhoneticScripts(), checkedVocable.getFirstLanguagePhoneticScripts())
				&& ListOperationsHelper.compareStringLists(currentVocable.getSecondLanguageTranslations(), checkedVocable.getSecondLanguageTranslations())
				&& ListOperationsHelper.compareStringLists(currentVocable.getSecondLanguagePhoneticScripts(), checkedVocable.getSecondLanguagePhoneticScripts())
			)
		);
	}

	@Override
	public void reactOnLoadedVocableList() {
		System.out.println("Notified of: Vocable List Loaded");
		vocableList.clear();
		vocableList.addAll(ManagerInstanceManager.getVocableFileManagerInstance().getVocableList());
		//notifyVocableListChangeListeners();
	}
	
	public void saveVocables() {
		try {
			System.out.println("Saving vocable list in:|" + Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME) + "|");
			File saveFile = new File(Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME));
			ManagerInstanceManager.getVocableFileManagerInstance().saveToXMLFile(vocableList, saveFile);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(VocableManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void saveSearchResult() {
		try {
			System.out.println("Saving search result vocable list in:|" + Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME) + "|");
			File saveFile = new File(Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME));
			ManagerInstanceManager.getVocableFileManagerInstance().saveToXMLFile(searchResultVocableList, saveFile);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(VocableManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void loadVocables() {
		String path = null;
		try {
			path = Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(VocableManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Now loading vocable file from file:|"+path+"|.");
		
		List<Vocable> newList = null;
		try {
			newList = ManagerInstanceManager.getVocableFileManagerInstance().loadVocablesFromFile(
				new File(Settings.getInstance().getSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME))
			);
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(VocableManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		System.out.println("Size of new list:|" + newList.size() + "|");
		
		vocableList.clear();
		vocableList.addAll(newList);
		System.out.println("Vocable list updated.");
		
		searchResultVocableList.clear();
		searchResultVocableList.addAll(newList);
		System.out.println("Search result vocable list updated.");
		
		/*searchResultVocableList.forEach((Vocable vocable) -> System.out.println(
			"|"+vocable.getFirstLanguageTranslations()+"|")
		);*/
	}
}
