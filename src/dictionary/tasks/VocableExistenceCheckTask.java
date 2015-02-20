/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.tasks;

import dictionary.model.Vocable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author xiaolong
 */
public class VocableExistenceCheckTask extends Task {
	
	private final ObservableList<Vocable> listOfVocables;
	private final Vocable searchedVocable;
	
	public VocableExistenceCheckTask(Vocable vocable, ObservableList<Vocable> listOfVocables) {
		this.listOfVocables = listOfVocables;
		this.searchedVocable = vocable;
	}
	
	@Override
	protected Object call() throws Exception {
		boolean result;
		
		updateMessage("Checking existing vocables ...");
		result = performExistenceCheck(searchedVocable, listOfVocables);
		updateMessage("Check complete.");
		
		return result;
	}
	
	private boolean performExistenceCheck(final Vocable vocable, ObservableList<Vocable> listOfVocables) {
		
		List<Vocable> res = new ArrayList<>();
		
		listOfVocables.stream().filter((voc) -> (voc.equals(vocable))).forEach((voc) -> {
			res.add(voc);
		});
		
		res.forEach(
			(voc) -> System.out.println(
				"The vocable is a duplicate of: \n" +
				"FL:" + vocable.getFirstLanguageTranslationsAsString() + "\n" +
				"FLPS:" + vocable.getFirstLanguagePhoneticScriptsAsString() + "\n" +
				"SL:" + vocable.getSecondLanguageTranslationsAsString() + "\n" +
				"SLPS:" + vocable.getSecondLanguagePhoneticScriptsAsString() + "\n"
			)
		);
		
		return listOfVocables.parallelStream().anyMatch(
			(vocable2) -> (vocable2.equals(vocable))
		);
	}
	
}
