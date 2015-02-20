/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.manager;

import dictionary.helpers.ControlFXDialogDisplayer;
import dictionary.tasks.VocableSearchTask;
import dictionary.exceptions.VocableAlreadyExistsException;
import dictionary.helpers.ListOperationsHelper;
import dictionary.listeners.VocableListChangeListener;
import dictionary.listeners.VocableListLoadedListener;
import dictionary.listeners.VocableSearchPerformedListener;
import dictionary.model.Vocable;
import dictionary.model.VocableSearchData;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author XiaoLong
 */
public class VocableManager implements VocableListLoadedListener {

	private ObservableList<Vocable> vocableList;
	private ObservableList<Vocable> searchResultVocableList;

	private final ArrayList<VocableListChangeListener> vocableListChangeListeners;
	private final ArrayList<VocableSearchPerformedListener> vocableSearchPerformedListeners;
	
	//private boolean vocableIsInVocableList = false;

	public VocableManager() {
		this.vocableListChangeListeners = new ArrayList<>();
		this.vocableSearchPerformedListeners = new ArrayList<>();
		registerAsListener();
	}

	private void registerAsListener() {
		ManagerInstanceManager.getVocableFileManagerInstance().registerVocableListLoadedListener(this);
	}

	public void registerVocableListChangeListener(VocableListChangeListener vocableListChangeListener) {
		vocableListChangeListeners.add(vocableListChangeListener);
	}

	public void registerVocableSearchPerformedListener(VocableSearchPerformedListener vocableSearchPerformedListener) {
		vocableSearchPerformedListeners.add(vocableSearchPerformedListener);
	}

	public void notifyVocableListChangeListeners() {
		vocableListChangeListeners.stream().forEach(
				(vocableListChangeListener) -> {
					vocableListChangeListener.updateOnVocableListChange();
				}
		);
	}

	public void notifyVocableSearchPerformedListeners() {
		vocableSearchPerformedListeners.stream().forEach(
				(vocableSearchPerformedListener) -> {
					vocableSearchPerformedListener.updateOnSearchResultChange();
				}
		);
	}

	public void addVocable(Vocable vocable) throws VocableAlreadyExistsException {
		if (!isVocableInDictionary(vocable)) {
			vocableList.add(vocable);
			notifyVocableListChangeListeners();
		} else {
			throw new VocableAlreadyExistsException("Vocable is already in the dictionary.");
		}
	}

	public void deleteVocables(ObservableList<Vocable> listOfVocables) {
		vocableList.removeAll(listOfVocables);
		//searchResultVocableList.removeAll(listOfVocables); //not needed because searchResult contains references to vocablelist, so the vocable are also deleted from the search result
	}

	public void deleteVocable(Vocable vocable) {
		vocableList.remove(vocable);
		//searchResultVocableList.remove(vocable); //not needed because searchResult contains references to vocablelist, so the vocable are also deleted from the search result
	}

	public void changeVocable(Vocable oldVocable, Vocable changedVocable) {
		System.out.println("Change Vocable");
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
		
		
		vocableSearchTask.setOnSucceeded((workerStateEvent) -> {
			if (vocableSearchData.isANDSearch()) {
				searchResultVocableList = (ObservableList<Vocable>) vocableSearchTask.getValue();
				
			} else if (vocableSearchData.isORSearch()) {
				((ObservableList<Vocable>) vocableSearchTask.getValue())
					.stream() //TODO: does this have to be single core?!
					.filter(((vocable) -> !searchResultVocableList.contains(vocable)))
					.forEach((vocable) -> searchResultVocableList.add(vocable));
				
			} else {
				searchResultVocableList = (ObservableList<Vocable>) vocableSearchTask.getValue();
			}
			notifyVocableSearchPerformedListeners();
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

	public ObservableList<Vocable> getSearchResultList() {
		return searchResultVocableList;
	}

	private boolean isVocableInDictionary(Vocable checkedVocable) {
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
		vocableList = FXCollections.observableArrayList(ManagerInstanceManager.getVocableFileManagerInstance().getVocableListFromFile());
		notifyVocableListChangeListeners();
	}
}
