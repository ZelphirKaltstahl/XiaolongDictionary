package dictionary.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import dictionary.exceptions.SettingNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@XStreamAlias("vocable")
public class Vocable {
	
	//@XStreamAlias("firstLanguageTranslations")
	@XStreamImplicit(itemFieldName="firstLanguageTranslations")
	private List<String> firstLanguageTranslations;
	
	//@XStreamAlias("firstLanguagePhoneticScripts")
	@XStreamImplicit(itemFieldName="firstLanguagePhoneticScripts")
	private List<String> firstLanguagePhoneticScripts;
	
	//@XStreamAlias("secondLanguageTranslations")
	@XStreamImplicit(itemFieldName="secondLanguageTranslations")
	private List<String> secondLanguageTranslations;
	
	//@XStreamAlias("secondLanguagePhoneticScripts")
	@XStreamImplicit(itemFieldName="secondLanguagePhoneticScripts")
	private List<String> secondLanguagePhoneticScripts;
	
	//@XStreamAlias("topics")
	@XStreamImplicit(itemFieldName="topics")
	private List<String> topics;
	
	
	//@XStreamAlias("chapters")
	@XStreamImplicit(itemFieldName="chapters")
	private List<String> chapters;
	
	@XStreamAlias("learnLevel")
	private String learnLevel;
	@XStreamAlias("relevanceLevel")
	private String relevanceLevel;
	@XStreamAlias("description")
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
			String description) {
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
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}

		String result = firstLanguageTranslations.get(0);
		for (int i = 1; i < firstLanguageTranslations.size(); i++) {
			result += " " + separatorCharacter + " " + firstLanguageTranslations.get(i);
		}
		return result;
	}

	public void setFirstLanugageTranslations(List<String> firstLanguageTranslations) {
		this.firstLanguageTranslations = firstLanguageTranslations;
	}

	public void setFirstLanguageTranslationsAsString(String firstLanguageTranslations) {
		this.firstLanguageTranslations = separateString(firstLanguageTranslations);
	}

	public List<String> getFirstLanguagePhoneticScripts() {
		return firstLanguagePhoneticScripts;
	}

	public String getFirstLanguagePhoneticScriptsAsString() {
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}

		String result = firstLanguagePhoneticScripts.get(0);
		for (int i = 1; i < firstLanguagePhoneticScripts.size(); i++) {
			result += " " + separatorCharacter + " " + firstLanguagePhoneticScripts.get(i);
		}
		return result;
	}

	public void setFirstLanguagePhoneticScripts(List<String> firstLanguagePhoneticScripts) {
		this.firstLanguagePhoneticScripts = firstLanguagePhoneticScripts;
	}

	public void setFirstLanguagePhoneticScriptsAsString(String firstLanguagePhoneticScripts) {
		this.firstLanguagePhoneticScripts = separateString(firstLanguagePhoneticScripts);
	}

	public List<String> getSecondLanguageTranslations() {
		return secondLanguageTranslations;
	}

	public String getSecondLanguageTranslationsAsString() {
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}

		String result = secondLanguageTranslations.get(0);
		for (int i = 1; i < secondLanguageTranslations.size(); i++) {
			result += " " + separatorCharacter + " " + secondLanguageTranslations.get(i);
		}
		return result;
	}

	public void setSecondLanguageTranslations(List<String> secondLanguageTranslations) {
		this.secondLanguageTranslations = secondLanguageTranslations;
	}

	public void setSecondLanguageTranslationsAsString(String secondLanguageTranslations) {
		this.secondLanguageTranslations = separateString(secondLanguageTranslations);
	}

	public List<String> getSecondLanguagePhoneticScripts() {
		return secondLanguagePhoneticScripts;
	}

	public String getSecondLanguagePhoneticScriptsAsString() {
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}

		String result = secondLanguagePhoneticScripts.get(0);
		for (int i = 1; i < secondLanguagePhoneticScripts.size(); i++) {
			result += " " + separatorCharacter + " " + secondLanguagePhoneticScripts.get(i);
		}
		return result;
	}

	public void setSecondLanguagePhoneticScripts(List<String> secondLanguagePhoneticScripts) {
		this.secondLanguagePhoneticScripts = secondLanguagePhoneticScripts;
	}

	public void setSecondLanguagePhoneticScriptsAsString(String secondLanguagePhoneticScripts) {
		this.secondLanguagePhoneticScripts = separateString(secondLanguagePhoneticScripts);
	}

	public List<String> getTopics() {
		return topics;
	}

	public String getTopicsAsString() {
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}
		
		if(topics.isEmpty()) {
			System.out.println("SIZE 0 VOC:"+firstLanguageTranslations);
		}
		
		String result = topics.get(0);
		for (int i = 1; i < topics.size(); i++) {
			result += " " + separatorCharacter + " " + topics.get(i);
		}
		return result;
	}

	public void setTopic(List<String> topics) {
		this.topics = topics;
	}

	public void setTopicsAsString(String topics) {
		this.topics = separateString(topics);
	}

	public List<String> getChapters() {
		return chapters;
	}

	public String getChaptersAsString() {
		String separatorCharacter;
		try {
			separatorCharacter = Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME);
		} catch (SettingNotFoundException ex) {
			separatorCharacter = "/";
		}

		String result = chapters.get(0);
		for (int i = 1; i < chapters.size(); i++) {
			result += " " + separatorCharacter + " " + chapters.get(i);
		}
		return result;
	}

	public void setChapters(List<String> chapters) {
		this.chapters = chapters;
	}

	public void setChaptersAsString(String chapters) {
		this.chapters = separateString(chapters);
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

	public void addFirstLanguage(String newFirstLanguageMeaning) {
		this.firstLanguageTranslations.add(newFirstLanguageMeaning);
	}

	public void addFirstLanguagePhoneticScript(String additionalFirstLanguagePhoneticScript) {
		this.firstLanguagePhoneticScripts.add(additionalFirstLanguagePhoneticScript);
	}

	public void addSecondLanguage(String newChineseMeaning) {
		this.secondLanguageTranslations.add(newChineseMeaning);
	}

	public void addSecondLanguagePhoneticScript(String additionalSecondLanguagePhoneticScript) {
		this.secondLanguagePhoneticScripts.add(additionalSecondLanguagePhoneticScript);
	}

	public void addTopic(String newTopic) {
		this.topics.add(newTopic);
	}

	public void addChapter(String additionalChapter) {
		this.chapters.add(additionalChapter);
	}

	public boolean equals(Vocable vocable) {
		boolean result = true;

		if (!this.firstLanguageTranslations.equals(vocable.firstLanguageTranslations)) {
			return false;
		}
		if (!this.secondLanguageTranslations.equals(vocable.secondLanguageTranslations)) {
			return false;
		}
		if (!this.firstLanguagePhoneticScripts.equals(vocable.firstLanguagePhoneticScripts)) {
			return false;
		}
		if (!this.secondLanguagePhoneticScripts.equals(vocable.secondLanguagePhoneticScripts)) {
			return false;
		}

		return result;
	}

	private List<String> separateString(String str) {
		String[] tmp = str.split(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME, -1);
		List<String> listOfElems = new ArrayList<>();
		for (String elem : tmp) {
			listOfElems.add(elem.trim());
		}
		return listOfElems;
	}
}
