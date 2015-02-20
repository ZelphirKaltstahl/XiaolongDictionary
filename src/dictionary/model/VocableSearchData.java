/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.model;

public class VocableSearchData {

	private final boolean searchFirstLanguageTranslationsAttribute;
	private final boolean searchFirstLanguagePhoneticScriptAttribute;
	private final boolean searchSecondLanguageTranslationsAttribute;
	private final boolean searchSecondLanguagePhoneticScriptAttribute;
	private final boolean searchTopicAttribute;
	private final boolean searchChapterAttribute;
	private final boolean searchLearnLevelAttribute;
	private final boolean searchRelevanceLevelAttribute;
	private final boolean searchDescriptionAttribute;

	private final String searchedFirstLanguageTranslations;
	private final String searchedFirstLanguagePhoneticScript;
	private final String searchedSecondLanguageTranslations;
	private final String searchedSecondLanguagePhoneticScript;
	private final String searchedTopic;
	private final String searchedChapter;
	private final String searchedLearnLevel;
	private final String searchedRelevanceLevel;
	private final String searchedDescription;
	
	private final boolean negateFirstLanguageTranslation;
	private final boolean negateFirstLanguagePhoneticScript;
	private final boolean negateSecondLanguageTranslation;
	private final boolean negateSecondLanguagePhoneticScript;
	private final boolean negateTopic;
	private final boolean negateChapter;
	private final boolean negateLearnLevel;
	private final boolean negateRelevanceLevel;
	private final boolean negateDescription;
	
	private final boolean negatedSearch;
	private final boolean andSearch;
	private final boolean orSearch;
	private final boolean standardSearch;
	private final boolean matchCase;
	private final boolean wholeWordMatch;

	public VocableSearchData (
		boolean searchFirstLanguageTranslationsAttribute,
		boolean searchFirstLanguagePhoneticScriptAttribute,
		boolean searchSecondLanguageTranslationsAttribute,
		boolean searchSecondLanguagePhoneticScriptAttribute,
		boolean searchTopicAttribute,
		boolean searchChapterAttribute,
		boolean searchLearnLevelAttribute,
		boolean searchRelevanceLevelAttribute,
		boolean searchDescriptionAttribute,

		boolean negateFirstLanguageTranslation,
		boolean negateFirstLanguagePhoneticScript,
		boolean negateSecondLanguageTranslation,
		boolean negateSecondLanguagePhoneticScript,
		boolean negateTopic,
		boolean negateChapter,
		boolean negateLearnLevel,
		boolean negateRelevanceLevel,
		boolean negateDescription,
		
		String searchedFirstLanguageTranslations,
		String searchedFirstLanguagePhoneticScript,
		String searchedSecondLanguageTranslations,
		String searchedSecondLanguagePhoneticScript,
		String searchedTopic,
		String searchedChapter,
		String searchedLearnLevel,
		String searchedRelevanceLevel,
		String searchedDescription,
			
		boolean negatedSearch,
		boolean andSearch,
		boolean orSearch,
		boolean standardSearch,
		boolean matchCase,
		boolean exactMatch
	) {
		this.searchFirstLanguageTranslationsAttribute = searchFirstLanguageTranslationsAttribute;
		this.searchFirstLanguagePhoneticScriptAttribute = searchFirstLanguagePhoneticScriptAttribute;
		this.searchSecondLanguageTranslationsAttribute = searchSecondLanguageTranslationsAttribute;
		this.searchSecondLanguagePhoneticScriptAttribute = searchSecondLanguagePhoneticScriptAttribute;
		this.searchTopicAttribute = searchTopicAttribute;
		this.searchChapterAttribute = searchChapterAttribute;
		this.searchLearnLevelAttribute = searchLearnLevelAttribute;
		this.searchRelevanceLevelAttribute = searchRelevanceLevelAttribute;
		this.searchDescriptionAttribute = searchDescriptionAttribute;
		
		this.negateFirstLanguageTranslation = negateFirstLanguageTranslation;
		this.negateFirstLanguagePhoneticScript = negateFirstLanguagePhoneticScript;
		this.negateSecondLanguageTranslation = negateSecondLanguageTranslation;
		this.negateSecondLanguagePhoneticScript = negateSecondLanguagePhoneticScript;
		this.negateTopic = negateTopic;
		this.negateChapter = negateChapter;
		this.negateLearnLevel = negateLearnLevel;
		this.negateRelevanceLevel = negateRelevanceLevel;
		this.negateDescription = negateDescription;
		
		this.searchedFirstLanguageTranslations = searchedFirstLanguageTranslations;
		this.searchedFirstLanguagePhoneticScript = searchedFirstLanguagePhoneticScript;
		this.searchedSecondLanguageTranslations = searchedSecondLanguageTranslations;
		this.searchedSecondLanguagePhoneticScript = searchedSecondLanguagePhoneticScript;
		this.searchedTopic = searchedTopic;
		this.searchedChapter = searchedChapter;
		this.searchedLearnLevel = searchedLearnLevel;
		this.searchedRelevanceLevel = searchedRelevanceLevel;
		this.searchedDescription = searchedDescription;
		
		this.negatedSearch = negatedSearch;
		this.andSearch = andSearch;
		this.orSearch = orSearch;
		this.standardSearch = standardSearch;
		this.matchCase = matchCase;
		this.wholeWordMatch = exactMatch;
	}

	public boolean isSearchFirstLanguageTranslationsAttribute() {
		return searchFirstLanguageTranslationsAttribute;
	}

	public boolean isSearchFirstLanguagePhoneticScriptAttribute() {
		return searchFirstLanguagePhoneticScriptAttribute;
	}

	public boolean isSearchSecondLanguageTranslationsAttribute() {
		return searchSecondLanguageTranslationsAttribute;
	}

	public boolean isSearchSecondLanguagePhoneticScriptAttribute() {
		return searchSecondLanguagePhoneticScriptAttribute;
	}

	public boolean isSearchTopicAttribute() {
		return searchTopicAttribute;
	}

	public boolean isSearchChapterAttribute() {
		return searchChapterAttribute;
	}

	public boolean isSearchLearnLevelAttribute() {
		return searchLearnLevelAttribute;
	}

	public boolean isSearchRelevanceLevelAttribute() {
		return searchRelevanceLevelAttribute;
	}

	public boolean isSearchDescriptionAttribute() {
		return searchDescriptionAttribute;
	}

	public String getSearchedFirstLanguageTranslation() {
		return getSearchedFirstLanguageTranslations();
	}

	public String getSearchedFirstLanguagePhoneticScript() {
		return searchedFirstLanguagePhoneticScript;
	}

	public String getSearchedSecondLanguageTranslation() {
		return getSearchedSecondLanguageTranslations();
	}

	public String getSearchedSecondLanguagePhoneticScript() {
		return searchedSecondLanguagePhoneticScript;
	}

	public String getSearchedTopic() {
		return searchedTopic;
	}

	public String getSearchedChapter() {
		return searchedChapter;
	}

	public String getSearchedLearnLevel() {
		return searchedLearnLevel;
	}

	public String getSearchedRelevanceLevel() {
		return searchedRelevanceLevel;
	}

	public String getSearchedDescription() {
		return searchedDescription;
	}

	public boolean isNegatedSearch() {
		return negatedSearch;
	}

	public boolean isANDSearch() {
		return isAndSearch();
	}

	public boolean isORSearch() {
		return isOrSearch();
	}

	public boolean isMatchCase() {
		return matchCase;
	}

	public boolean isWholeWordMatch() {
		return wholeWordMatch;
	}

	public String getSearchedFirstLanguageTranslations() {
		return searchedFirstLanguageTranslations;
	}

	public String getSearchedSecondLanguageTranslations() {
		return searchedSecondLanguageTranslations;
	}

	public boolean isNegateFirstLanguageTranslation() {
		return negateFirstLanguageTranslation;
	}

	public boolean isNegateFirstLanguagePhoneticScript() {
		return negateFirstLanguagePhoneticScript;
	}

	public boolean isNegateSecondLanguageTranslation() {
		return negateSecondLanguageTranslation;
	}

	public boolean isNegateSecondLanguagePhoneticScript() {
		return negateSecondLanguagePhoneticScript;
	}

	public boolean isNegateTopic() {
		return negateTopic;
	}

	public boolean isNegateChapter() {
		return negateChapter;
	}

	public boolean isNegateLearnLevel() {
		return negateLearnLevel;
	}

	public boolean isNegateRelevanceLevel() {
		return negateRelevanceLevel;
	}

	public boolean isNegateDescription() {
		return negateDescription;
	}

	public boolean isAndSearch() {
		return andSearch;
	}

	public boolean isOrSearch() {
		return orSearch;
	}

	public boolean isStandardSearch() {
		return standardSearch;
	}
	
	public void print() {
		System.out.println("First Language T Selected:"+isSearchFirstLanguageTranslationsAttribute());
		System.out.println("First Language PS Selected:"+isSearchFirstLanguagePhoneticScriptAttribute());
		System.out.println("Second Language T Selected:"+isSearchSecondLanguageTranslationsAttribute());
		System.out.println("Second Language PS Selected:"+isSearchSecondLanguagePhoneticScriptAttribute());
		System.out.println("Topic Selected:"+isSearchTopicAttribute());
		System.out.println("Chapter Selected:"+isSearchChapterAttribute());
		System.out.println("LearnLevel Selected:"+isSearchLearnLevelAttribute());
		System.out.println("Relevance Selected:"+isSearchRelevanceLevelAttribute());
		System.out.println("Description Selected:"+isSearchDescriptionAttribute());
		System.out.println("----------------------------------------");
		System.out.println("Negate First Language T Selected:"+isNegateFirstLanguageTranslation());
		System.out.println("Negate First Language PS Selected:"+isNegateFirstLanguagePhoneticScript());
		System.out.println("Negate Second Language T Selected:"+isNegateSecondLanguageTranslation());
		System.out.println("Negate Second Language PS Selected:"+isNegateSecondLanguagePhoneticScript());
		System.out.println("Negate Topic Selected:"+isNegateTopic());
		System.out.println("Negate Chapter Selected:"+isNegateChapter());
		System.out.println("Negate LearnLevel Selected:"+isNegateLearnLevel());
		System.out.println("Negate Relevance Selected:"+isNegateRelevanceLevel());
		System.out.println("Negate Description Selected:"+isNegateDescription());
		System.out.println("----------------------------------------");
		System.out.println("First Language T:"+getSearchedFirstLanguageTranslation());
		System.out.println("First Language PS:"+getSearchedFirstLanguagePhoneticScript());
		System.out.println("Second Language T:"+getSearchedSecondLanguageTranslation());
		System.out.println("Second Language PS:"+getSearchedSecondLanguagePhoneticScript());
		System.out.println("Topic:"+getSearchedTopic());
		System.out.println("Chapter:"+getSearchedChapter());
		System.out.println("LearnLevel:"+getSearchedLearnLevel());
		System.out.println("RelevanceLevel:"+getSearchedRelevanceLevel());
		System.out.println("Description:"+getSearchedDescription());
		System.out.println("----------------------------------------");
		System.out.println("NOT:"+isNegatedSearch());
		System.out.println("AND:"+isANDSearch());
		System.out.println("OR:"+isORSearch());
		System.out.println("StandardSearch:"+isStandardSearch());
		System.out.println("WholeWord:"+isWholeWordMatch());
		System.out.println("CaseSen:"+isMatchCase());
	}
}
