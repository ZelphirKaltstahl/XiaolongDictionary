/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.tasks;

import dictionary.model.Vocable;
import dictionary.model.VocableSearchData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 *
 * @author xiaolong
 */
public class VocableSearchTask extends Task {

	private final VocableSearchData vocableSearchData;
	private final ObservableList<Vocable> listOfVocables;

	private final Predicate<Vocable> matchesFirstLanguageSearchCriteriasPredicate = (vocable) -> (matchesFirstLanguageSearchCriterias(vocable));
	private final Predicate<Vocable> matchesFirstLanguagePhoneticScriptSearchCriteriasPredicate = (vocable) -> (matchesFirstLanguagePhoneticScriptSearchCriterias(vocable));
	private final Predicate<Vocable> matchesSecondLanguageSearchCriteriasPredicate = (vocable) -> (matchesSecondLanguageSearchCriterias(vocable));
	private final Predicate<Vocable> matchesSecondLanguagePhoneticScriptSearchCriteriasPredicate = (vocable) -> (matchesSecondLanguagePhoneticScriptSearchCriterias(vocable));
	
	private final Predicate<Vocable> matchesTopicSearchCriteriasPredicate = (vocable) -> (matchesTopicSearchCriterias(vocable));
	private final Predicate<Vocable> matchesChapterSearchCriteriasPredicate = (vocable) -> (matchesChapterSearchCriterias(vocable));
	private final Predicate<Vocable> matchesLearnLevelSearchCriteriasPredicate = (vocable) -> (matchesLearnLevelSearchCriterias(vocable));
	private final Predicate<Vocable> matchesRelevanceLevelSearchCriteriasPredicate = (vocable) -> (matchesRelevanceLevelSearchCriterias(vocable));
	private final Predicate<Vocable> matchesDescriptionSearchCriteriasPredicate = (vocable) -> (matchesDescriptionSearchCriterias(vocable));
	
	private final Predicate<Vocable> updateResultsPredicate = (vocable) -> (updateResults(vocable));
	private final Predicate<Vocable> updateCounterPredicate = (vocable) -> (updateCounter(vocable));
	private final Predicate<Vocable> cancelPredicate = (vocable) -> (isTaskCancelled(vocable));
	
	private final List<Vocable> preliminaryResultList = new ArrayList<>();
	private int counter = 0;
	
	public VocableSearchTask(VocableSearchData vocableSearchData, ObservableList<Vocable> listOfVocables) {
		this.vocableSearchData = vocableSearchData;
		this.listOfVocables = listOfVocables;
	}

	@Override
	protected ObservableList<Vocable> call() throws Exception {
		ObservableList<Vocable> results;
		
		updateMessage("Searching vocables ...");
		results = performStreamSearch(listOfVocables);
		updateMessage("Search complete.");
		
		return results;
	}

	private boolean matchesFirstLanguageSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchFirstLanguageTranslationsAttribute() && vocableSearchData.isNegateFirstLanguageTranslation()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguageTranslation(), vocable.getFirstLanguageTranslations())) {
				return false;
			}
		}

		else if (vocableSearchData.isSearchFirstLanguageTranslationsAttribute() && !vocableSearchData.isNegateFirstLanguageTranslation()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguageTranslation(), vocable.getFirstLanguageTranslations())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean matchesFirstLanguagePhoneticScriptSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchFirstLanguagePhoneticScriptAttribute() && vocableSearchData.isNegateFirstLanguagePhoneticScript()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguagePhoneticScript(), vocable.getFirstLanguagePhoneticScripts())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchFirstLanguagePhoneticScriptAttribute() && !vocableSearchData.isNegateFirstLanguagePhoneticScript()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguagePhoneticScript(), vocable.getFirstLanguagePhoneticScripts())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean matchesSecondLanguageSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchSecondLanguageTranslationsAttribute() && vocableSearchData.isNegateSecondLanguageTranslation()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguageTranslation(), vocable.getSecondLanguageTranslations())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchSecondLanguageTranslationsAttribute() && !vocableSearchData.isNegateSecondLanguageTranslation()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguageTranslation(), vocable.getSecondLanguageTranslations())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean matchesSecondLanguagePhoneticScriptSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchSecondLanguagePhoneticScriptAttribute() && vocableSearchData.isNegateSecondLanguagePhoneticScript()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguagePhoneticScript(), vocable.getSecondLanguagePhoneticScripts())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchSecondLanguagePhoneticScriptAttribute() && !vocableSearchData.isNegateSecondLanguagePhoneticScript()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguagePhoneticScript(), vocable.getSecondLanguagePhoneticScripts())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchesTopicSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchTopicAttribute() && vocableSearchData.isNegateTopic()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedTopic(), vocable.getTopics())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchTopicAttribute() && !vocableSearchData.isNegateTopic()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedTopic(), vocable.getTopics())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean matchesChapterSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchChapterAttribute() && vocableSearchData.isNegateChapter()) {
			if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedChapter(), vocable.getChapters())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchChapterAttribute() && !vocableSearchData.isNegateChapter()) {
			if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedChapter(), vocable.getChapters())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean matchesLearnLevelSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchLearnLevelAttribute() && vocableSearchData.isNegateLearnLevel()) {
			if (isSearchStringInString(vocableSearchData.getSearchedLearnLevel(), vocable.getLearnLevel())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchLearnLevelAttribute() && !vocableSearchData.isNegateLearnLevel()) {
			if (!isSearchStringInString(vocableSearchData.getSearchedLearnLevel(), vocable.getLearnLevel())) {
				return false;
			}
		}
		
		return true;
	}
		
	private boolean matchesRelevanceLevelSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchRelevanceLevelAttribute() && vocableSearchData.isNegateRelevanceLevel()) {
			if (isSearchStringInString(vocableSearchData.getSearchedRelevanceLevel(), vocable.getRelevanceLevel())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchRelevanceLevelAttribute() && !vocableSearchData.isNegateRelevanceLevel()) {
			if (!isSearchStringInString(vocableSearchData.getSearchedRelevanceLevel(), vocable.getRelevanceLevel())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchesDescriptionSearchCriterias(Vocable vocable) {
		if (vocableSearchData.isSearchDescriptionAttribute() && vocableSearchData.isNegateDescription()) {
			if (isSearchStringInString(vocableSearchData.getSearchedDescription(), vocable.getDescription())) {
				return false;
			}
		}

		if (vocableSearchData.isSearchDescriptionAttribute() && !vocableSearchData.isNegateDescription()) {
			if (!isSearchStringInString(vocableSearchData.getSearchedDescription(), vocable.getDescription())) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean updateCounter(Vocable vocable) {
		counter++;
		updateProgress(counter, listOfVocables.size());
		//System.out.println("Counter updated: " + counter);
		return true;
	}
	
	private boolean updateResults(Vocable vocable) {
		preliminaryResultList.add(vocable);
		updateValue(preliminaryResultList);
		//System.out.println("Result updated.");
		return true;
	}
	
	private boolean isTaskCancelled(Vocable vocable) {
		if (isCancelled()) {
			this.cancel(); // no intermediate result is returned though
		}
		return true;
	}
	
	private ObservableList<Vocable> performStreamSearch(ObservableList<Vocable> listOfVocables) {
		ObservableList<Vocable> result;
		
		result = FXCollections.observableArrayList(
			listOfVocables.parallelStream()
				.filter(cancelPredicate)
				.filter(updateCounterPredicate)
					
				.filter(matchesFirstLanguageSearchCriteriasPredicate)
				.filter(matchesFirstLanguagePhoneticScriptSearchCriteriasPredicate)
				.filter(matchesSecondLanguageSearchCriteriasPredicate)
				.filter(matchesSecondLanguagePhoneticScriptSearchCriteriasPredicate)
				.filter(matchesTopicSearchCriteriasPredicate)
				.filter(matchesChapterSearchCriteriasPredicate)
				.filter(matchesLearnLevelSearchCriteriasPredicate)
				.filter(matchesRelevanceLevelSearchCriteriasPredicate)
				.filter(matchesDescriptionSearchCriteriasPredicate)
					
				.filter(updateResultsPredicate)
				.collect(Collectors.toList())
		);
		
		if(vocableSearchData.isNegatedSearch()) {
			List<Vocable> invertedResult = new ArrayList<>(listOfVocables);
			invertedResult.removeAll(result);
			return FXCollections.observableArrayList(invertedResult);
		}
		
		return result;
	}
	
	private ObservableList<Vocable> performSearch(ObservableList<Vocable> listOfVocables) {
		ObservableList<Vocable> results = FXCollections.observableArrayList();
		
		// counter variable for updating the progress
		int i = 0;
		
		for (Vocable vocable : listOfVocables) {
			
			
					
			//update the result value, so that interrupted searches still have in between results
			updateValue(results);

			//Cancel task if necessary
			if (isCancelled()) {
				break;
			}

			//counter for the vocables
			i++;

			/*
			How this search works:
			If only one attribute doesn't contain what it should or doesn't meet a condition 
			specified in the vocable search data, the vocable is skipped and not added to the 
			results of the search. The adding to the search result is only happening at the end of
			the for loop, so vocables, which don't meet the search criteria will never reach that
			point.
			/*
			====================================
			=====FIRST LANGUAGE TRANSLATION=====
			====================================
			*/
			if (vocableSearchData.isSearchFirstLanguageTranslationsAttribute() && vocableSearchData.isNegateFirstLanguageTranslation()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguageTranslation(), vocable.getFirstLanguageTranslations())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchFirstLanguageTranslationsAttribute() && !vocableSearchData.isNegateFirstLanguageTranslation()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguageTranslation(), vocable.getFirstLanguageTranslations())) {
					continue;
				}
			}
			
			/*
			========================================
			=====FIRST LANGUAGE PHONETIC SCRIPT=====
			========================================
			*/
			if (vocableSearchData.isSearchFirstLanguagePhoneticScriptAttribute() && vocableSearchData.isNegateFirstLanguagePhoneticScript()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguagePhoneticScript(), vocable.getFirstLanguagePhoneticScripts())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchFirstLanguagePhoneticScriptAttribute() && !vocableSearchData.isNegateFirstLanguagePhoneticScript()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguagePhoneticScript(), vocable.getFirstLanguagePhoneticScripts())) {
					continue;
				}
			}

			/*
			========================================
			=====SECOND LANGUAGE TRANSLATIONS=======
			========================================
			*/
			if (vocableSearchData.isSearchSecondLanguageTranslationsAttribute() && vocableSearchData.isNegateSecondLanguageTranslation()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguageTranslation(), vocable.getSecondLanguageTranslations())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchSecondLanguageTranslationsAttribute() && !vocableSearchData.isNegateSecondLanguageTranslation()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguageTranslation(), vocable.getSecondLanguageTranslations())) {
					continue;
				}
			}
			
			/*
			=========================================
			=====SECOND LANGUAGE PHONETIC SCRIPT=====
			=========================================
			*/
			if (vocableSearchData.isSearchSecondLanguagePhoneticScriptAttribute() && vocableSearchData.isNegateSecondLanguagePhoneticScript()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguagePhoneticScript(), vocable.getSecondLanguagePhoneticScripts())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchSecondLanguagePhoneticScriptAttribute() && !vocableSearchData.isNegateSecondLanguagePhoneticScript()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguagePhoneticScript(), vocable.getSecondLanguagePhoneticScripts())) {
					continue;
				}
			}

			/*
			===============
			=====TOPIC=====
			===============
			*/
			if (vocableSearchData.isSearchTopicAttribute() && vocableSearchData.isNegateTopic()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedTopic(), vocable.getTopics())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchTopicAttribute() && !vocableSearchData.isNegateTopic()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedTopic(), vocable.getTopics())) {
					continue;
				}
			}

			/*
			=================
			=====CHAPTER=====
			=================
			*/
			if (vocableSearchData.isSearchChapterAttribute() && vocableSearchData.isNegateChapter()) {
				if (isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedChapter(), vocable.getChapters())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchChapterAttribute() && !vocableSearchData.isNegateChapter()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedChapter(), vocable.getChapters())) {
					continue;
				}
			}
			
			/*
			=====================
			=====LEARN LEVEL=====
			=====================
			*/
			if (vocableSearchData.isSearchLearnLevelAttribute() && vocableSearchData.isNegateLearnLevel()) {
				if (isSearchStringInString(vocableSearchData.getSearchedLearnLevel(), vocable.getLearnLevel())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchLearnLevelAttribute() && !vocableSearchData.isNegateLearnLevel()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedLearnLevel(), vocable.getLearnLevel())) {
					continue;
				}
			}

			/*
			=========================
			=====RELEVANCE LEVEL=====
			=========================
			*/
			if (vocableSearchData.isSearchRelevanceLevelAttribute() && vocableSearchData.isNegateRelevanceLevel()) {
				if (isSearchStringInString(vocableSearchData.getSearchedRelevanceLevel(), vocable.getRelevanceLevel())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchRelevanceLevelAttribute() && !vocableSearchData.isNegateRelevanceLevel()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedRelevanceLevel(), vocable.getRelevanceLevel())) {
					continue;
				}
			}

			/*
			======================
			=====DESCRIPTION =====
			======================
			*/
			if (vocableSearchData.isSearchDescriptionAttribute() && vocableSearchData.isNegateDescription()) {
				if (isSearchStringInString(vocableSearchData.getSearchedDescription(), vocable.getDescription())) {
					continue;
				}
			}
			
			if (vocableSearchData.isSearchDescriptionAttribute() && !vocableSearchData.isNegateDescription()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedDescription(), vocable.getDescription())) {
					continue;
				}
			}

			//if everything is in the vocable add it to the result of the search
			results.add(vocable);
			//update the progress of the task so that it can be displayed somewhere
			updateProgress(i, listOfVocables.size());
		}

		return results;
	}

	
	private ObservableList<Vocable> performNegatedSearch(ObservableList<Vocable> listOfVocables) {
		ObservableList<Vocable> results = FXCollections.observableArrayList();

		listOfVocables.stream().forEach((vocable) -> {
			results.add(vocable);
		});

		int i = 0;

		for (Vocable vocable : listOfVocables) {

			i++;
			updateProgress(i, listOfVocables.size());

			//if only one attribute doesn't contain what it should, it is skipped and not added to the results of the search
			if (vocableSearchData.isSearchFirstLanguageTranslationsAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguageTranslation(), vocable.getFirstLanguageTranslations())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchFirstLanguagePhoneticScriptAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedFirstLanguagePhoneticScript(), vocable.getFirstLanguagePhoneticScripts())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchSecondLanguageTranslationsAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguageTranslation(), vocable.getSecondLanguageTranslations())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchSecondLanguagePhoneticScriptAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedSecondLanguagePhoneticScript(), vocable.getSecondLanguagePhoneticScripts())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchTopicAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedTopic(), vocable.getTopics())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchChapterAttribute()) {
				if (!isSearchStringInVocableAttributeValue(vocableSearchData.getSearchedChapter(), vocable.getChapters())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchLearnLevelAttribute()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedLearnLevel(), vocable.getLearnLevel())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchRelevanceLevelAttribute()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedRelevanceLevel(), vocable.getRelevanceLevel())) {
					continue;
				}
			}

			if (vocableSearchData.isSearchDescriptionAttribute()) {
				if (!isSearchStringInString(vocableSearchData.getSearchedDescription(), vocable.getDescription())) {
					continue;
				}
			}

			//if everything is in the vocable add it to the result of the search
			results.remove(vocable);
		}

		return results;
	}

	/**
	 * This method searches a given String in a {@link List} of Strings. It is
	 * required for searching through attributes which are represented by Lists
	 * instead of simple Strings.
	 *
	 * @param searchString the searched String
	 * @param vocableAttributeValues the List of Strings representing the
	 * vocable attribute
	 * @return true if the String is found in the vocable attribute
	 */
	private boolean isSearchStringInVocableAttributeValue(String searchString, List<String> vocableAttributeValues) {
		if (vocableSearchData.isMatchCase() && vocableSearchData.isWholeWordMatch()) {
			return vocableAttributeValues.stream()
				.anyMatch(
					(vocableAttributeValue) -> (vocableAttributeValue.equals(searchString))
				);
			
		} else if (vocableSearchData.isMatchCase() && !vocableSearchData.isWholeWordMatch()) {
			return vocableAttributeValues.stream().anyMatch((vocableAttributeValue) -> (vocableAttributeValue.contains(searchString)));
			
		} else if (!vocableSearchData.isMatchCase() && vocableSearchData.isWholeWordMatch()) {
			return vocableAttributeValues.stream().anyMatch((vocableAttributeValue) -> (vocableAttributeValue.toUpperCase().equals(searchString.toUpperCase())));
			
		} else if (!vocableSearchData.isMatchCase() && !vocableSearchData.isWholeWordMatch()) {
			return vocableAttributeValues.stream().anyMatch((vocableAttributeValue) -> (vocableAttributeValue.toUpperCase().contains(searchString.toUpperCase())));
		}

		return false;
	}

	/**
	 * This method searches a given String in a String. It is required for
	 * searching through attributes of {@link Vocable}s which are represented by
	 * simple Strings.
	 * 
	 * @param searchString the searched String
	 * @param vocableAttributeValue the List of Strings representing the
	 * vocable attribute
	 * @return true if the String is found in the vocable attribute
	 */
	private boolean isSearchStringInString(String searchString, String vocableAttributeValue) {
		if (vocableSearchData.isMatchCase() && vocableSearchData.isWholeWordMatch()) {
			if (vocableAttributeValue.equals(searchString)) {
				return true;
			}
		} else if (vocableSearchData.isMatchCase() && !vocableSearchData.isWholeWordMatch()) {
			if (vocableAttributeValue.contains(searchString)) {
				return true;
			}
		} else if (!vocableSearchData.isMatchCase() && vocableSearchData.isWholeWordMatch()) {
			if (vocableAttributeValue.toUpperCase().equals(searchString.toUpperCase())) {
				return true;
			}
		} else if (!vocableSearchData.isMatchCase() && !vocableSearchData.isWholeWordMatch()) {
			if (vocableAttributeValue.toUpperCase().contains(searchString.toUpperCase())) {
				return true;
			}
		}

		return false;
	}

}
