package dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiaoLong
 *
 */
public class Vocable {
	
	private List<String> firstLanguageTranslations;
	private List<String> firstLanguagePhoneticScripts;
	private List<String> secondLanguageTranslations;
	private List<String> secondLanguagePhoneticScripts;
	private List<String> topics;
	private List<String> chapters;
	private String learnLevel;
	private String relevanceLevel;
	private String description;
	
	public Vocable() {
		super();
	}
	
	public Vocable(
		List<String> firstLanguage,
		List<String> firstLanguagePhoneticScript,
		List<String> secondLanguage,
		List<String> secondLanguagePhoneticScript,
		List<String> topic,
		List<String> chapter,
		String learnLevel,
		String relevanceLevel,
		String description)
	{
		this.firstLanguageTranslations = firstLanguage;
		this.firstLanguagePhoneticScripts = firstLanguagePhoneticScript;
		this.secondLanguageTranslations = secondLanguage;
		this.secondLanguagePhoneticScripts = secondLanguagePhoneticScript;
		this.topics = topic;
		this.chapters = chapter;
		this.learnLevel = learnLevel;
		this.relevanceLevel = relevanceLevel;
		this.description = description;
	}

	public List<String> getFirstLanguageTranslations() {
		return firstLanguageTranslations;
	}
	
	public String getFirstLanguageTranslationsAsString() {
		String result = firstLanguageTranslations.get(0);
		for(int i = 1; i < firstLanguageTranslations.size(); i++) {
			result += ", " + firstLanguageTranslations.get(i);
		}
		return result;
	}
	
	public void setFirstLanugageTranslations(ArrayList<String> firstLanguageTranslations) {
		this.firstLanguageTranslations = firstLanguageTranslations;
	}

	public List<String> getFirstLanguagePhoneticScripts() {
		return firstLanguagePhoneticScripts;
	}
	
	public String getFirstLanguagePhoneticScriptsAsString() {
		String result = firstLanguagePhoneticScripts.get(0);
		for(int i = 1; i < firstLanguagePhoneticScripts.size(); i++) {
			result += ", " + firstLanguagePhoneticScripts.get(i);
		}
		return result;
	}

	public void setFirstLanguagePhoneticScripts(ArrayList<String> firstLanguagePhoneticScripts) {
		this.firstLanguagePhoneticScripts = firstLanguagePhoneticScripts;
	}
	
	public List<String> getSecondLanguageTranslations() {
		return secondLanguageTranslations;
	}
	
	public String getSecondLanguageTranslationsAsString() {
		String result = secondLanguageTranslations.get(0);
		for(int i = 1; i < secondLanguageTranslations.size(); i++) {
			result += ", " + secondLanguageTranslations.get(i);
		}
		return result;
	}

	public void setSecondLanguageTranslations(ArrayList<String> secondLanguageTranslations) {
		this.secondLanguageTranslations = secondLanguageTranslations;
	}

	public List<String> getSecondLanguagePhoneticScripts() {
		return secondLanguagePhoneticScripts;
	}
	
	public String getSecondLanguagePhoneticScriptsAsString() {
		String result = secondLanguagePhoneticScripts.get(0);
		for(int i = 1; i < secondLanguagePhoneticScripts.size(); i++) {
			result += ", " + secondLanguagePhoneticScripts.get(i);
		}
		return result;
	}

	public void setSecondLanguagePhoneticScripts(ArrayList<String> secondLanguagePhoneticScripts) {
		this.secondLanguagePhoneticScripts = secondLanguagePhoneticScripts;
	}
	
	public List<String> getTopics() {
		return topics;
	}

	public void setTopic(ArrayList<String> topics) {
		this.topics = topics;
	}
	
	public List<String> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters) {
		this.chapters = chapters;
	}

	public String getLearnLevel() {
		return learnLevel;
	}

	public void setLearnLevel(String learnLevel) {
		this.learnLevel = learnLevel;
	}
	
	public String getRelevanceLevel() {
		return this.relevanceLevel;
	}
	
	public void setRelevanceLevel(String relevanceLevel) {
		this.relevanceLevel = relevanceLevel;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addFirstLanguage(String newFirstLanguageMeaning){
		this.firstLanguageTranslations.add(newFirstLanguageMeaning);
	}
	
	public void addFirstLanguagePhoneticScript(String additionalFirstLanguagePhoneticScript){
		this.firstLanguagePhoneticScripts.add(additionalFirstLanguagePhoneticScript);
	}
	
	public void addSecondLanguage(String newChineseMeaning){
		this.secondLanguageTranslations.add(newChineseMeaning);
	}
	
	public void addSecondLanguagePhoneticScript(String additionalSecondLanguagePhoneticScript){
		this.secondLanguagePhoneticScripts.add(additionalSecondLanguagePhoneticScript);
	}
	
	public void addTopic(String newTopic){
		this.topics.add(newTopic);
	}
	
	public void addChapter(String additionalChapter) {
		this.chapters.add(additionalChapter);
	}
	
	public boolean equals(Vocable vocable) {
		boolean result = true;
		
		if(!this.firstLanguageTranslations.equals(vocable.firstLanguageTranslations)) {
			return false;
		}
		if(!this.secondLanguageTranslations.equals(vocable.secondLanguageTranslations)) {
			return false;
		}
		if(!this.firstLanguagePhoneticScripts.equals(vocable.firstLanguagePhoneticScripts)) {
			return false;
		}
		if(!this.secondLanguagePhoneticScripts.equals(vocable.secondLanguagePhoneticScripts)) {
			return false;
		}
		
		return result;
	}
}