/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.model;

import dictionary.exceptions.SettingNotFoundException;
import dictionary.listeners.SettingsPropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Font;

/**
 *
 * @author XiaoLong
 */
public class Settings {
	
	private static Settings instance;
	
	// general application settings or properties
	public final String APPLICATION_PATH_SETTING_NAME = "application_path";
	public final String APPLICATION_NAME_SETTING_NAME = "application_name";
	public final String APPLICATION_VERSION_PROPERTY_NAME = "application_version";
	
	// file settings
	public final String XLD_SETTING_FILENAME_SETTING_NAME = "settings_filename";
	public final String XLD_VOCABLE_FILENAME_SETTING_NAME = "vocables_filename";
	
	// main window settings
	public final String SCENE_WIDTH_SETTING_NAME = "main_window_width";
	public final String SCENE_HEIGHT_SETTING_NAME = "main_window_height";
	
	// language settings
	public final String FIRST_LANGUAGE_SETTING_NAME = "first_language_name";
	public final String FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME = "first_language_phonetic_script_name";
	public final String SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME = "second_language_phonetic_script_name";
	public final String SECOND_LANGUAGE_SETTING_NAME = "second_language_name";
	
	// translation separator settings
	public final String SEPARATOR_CHARACTER_SETTING_NAME = "separator_character";
	public final String SEPARATOR_REGEX_SETTING_NAME = "separator_regular_expression";
	
	public final String NO_INFORMATION_STRING_SETTING_NAME = "no_information_string";
	
	// big character box settings
	public final String BIG_CAHRACTER_BOX_IGNORED_CHARACTERS_SETTING_NAME = "ignored_characters";
	public final String BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME = "big_character_box_font_size";
	public final String BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME = "big_character_box_font_name";
	public final String BIG_CHARACTER_BOX_FONT_STYLE_SETTING_NAME = "big_character_box_font_style";
	public final String BIG_CHARACTER_BOX_LOOP_SETTING_NAME = "big_character_box_loop";
	
	// special character settings
	public final String SPECIAL_CHARACTERS_SETTING_NAME = "special_characters";
	
	// dialogs
	// add vocable dialog
	public final String ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_SELECTED_SETTING_NAME = "preserve_first_language_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME = "preserve_first_language_phonetic_script_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_SELECTED_SETTING_NAME = "preserve_second_language_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME = "preserve_second_language_phonetic_script_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_TOPIC_SELECTED_SETTING_NAME = "preserve_topic_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_CHAPTER_SELECTED_SETTING_NAME = "preserve_chapter_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_LEARN_LEVEL_SELECTED_SETTING_NAME = "preserve_learn_level_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_RELEVANCE_LEVEL_SELECTED_SETTING_NAME = "preserve_relevance_selected";
	public final String ADD_VOCABLE_DIALOG_PRESERVE_DESCRIPTION_SELECTED_SETTING_NAME = "preserve_description_selected";
	
	// vocable training settings
	public final String VOCABLE_TRAINING_DIRECTION_SETTING_NAME = "vocable_training_direction";
	
	public final String VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME = "vocable_training_show_first_language";
	public final String VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME = "vocable_training_show_second_language";
	public final String VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME = "vocable_training_show_first_language_phonetic_script";
	public final String VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME = "vocable_training_show_second_language_phonetic_script";
	
	public final String VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME = "vocable_training_show_description";
	
	public final String VOCABLE_TRAINING_CUSTOM_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME = "vocable_training_custom_new_learn_level_selected";
	public final String VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME = "vocable_training_predefined_new_learn_level_selected";
	public final String VOCABLE_TRAINING_CUSTOM_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME = "vocable_training_custom_new_relevance_level_selected";
	public final String VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME = "vocable_training_predefined_new_relevance_level_selected";
	
	public final String VOCABLE_PREDEFINED_LEARN_LEVELS_SETTING_NAME = "vocable_training_predefined_new_learn_levels";
	public final String VOCABLE_PREDEFINED_RELEVANCE_LEVELS_SETTING_NAME = "vocable_training_predefined_new_relevance_levels";
	
	// DIALOGS
	public final String DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME = "dialog_show_save_vocable_changes_confirmation";
	public final String DIALOG_SHOW_EXIT_CONFIRMATION_SETTING_NAME = "dialog_show_exit_confirmation";
	
	public final String SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME = "save_vocable_changes_on_exit";
	
	// other values
	private final String FIRST_LANGUAGE_TO_SECOND_LANGUAGE_TRAINING_DIRECTION = Boolean.toString(true);
	private final String SECOND_LANGUAGE_TO_FIRST_LANGUAGE_TRAINING_DIRECTION = Boolean.toString(false);
	
	
	// private constructor -> singleton
	private Settings() {
		// empty
	}
	
	public static Settings getInstance() {
		if(instance == null) {
			instance = new Settings();
		}
		return instance;
	}
	
	// array for iteration and saving all settings to the hash map
	private final String[] ALL_SETTINGS_NAMES = {
		APPLICATION_PATH_SETTING_NAME,
		APPLICATION_NAME_SETTING_NAME,
		APPLICATION_VERSION_PROPERTY_NAME,
		
		XLD_SETTING_FILENAME_SETTING_NAME,
		XLD_VOCABLE_FILENAME_SETTING_NAME,
		
		SCENE_WIDTH_SETTING_NAME,
		SCENE_HEIGHT_SETTING_NAME,
		
		FIRST_LANGUAGE_SETTING_NAME,
		FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
		SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
		SECOND_LANGUAGE_SETTING_NAME,
		
		SEPARATOR_CHARACTER_SETTING_NAME,
		SEPARATOR_REGEX_SETTING_NAME,
		
		NO_INFORMATION_STRING_SETTING_NAME,
		
		BIG_CAHRACTER_BOX_IGNORED_CHARACTERS_SETTING_NAME,
		
		SPECIAL_CHARACTERS_SETTING_NAME,
		
		BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME,
		BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME,
		BIG_CHARACTER_BOX_FONT_STYLE_SETTING_NAME,
		BIG_CHARACTER_BOX_LOOP_SETTING_NAME,
		
		ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_TOPIC_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_CHAPTER_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_LEARN_LEVEL_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_RELEVANCE_LEVEL_SELECTED_SETTING_NAME,
		ADD_VOCABLE_DIALOG_PRESERVE_DESCRIPTION_SELECTED_SETTING_NAME,
		
		VOCABLE_TRAINING_DIRECTION_SETTING_NAME,
		VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME,
		VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME,
		VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
		VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME,
		VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME,
		VOCABLE_TRAINING_CUSTOM_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME,
		VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME,
		VOCABLE_TRAINING_CUSTOM_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME,
		VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME,
		VOCABLE_PREDEFINED_LEARN_LEVELS_SETTING_NAME,
		VOCABLE_PREDEFINED_RELEVANCE_LEVELS_SETTING_NAME,
		
		DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME,
		DIALOG_SHOW_EXIT_CONFIRMATION_SETTING_NAME,
		SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME
	};
	
	private final HashMap<String, String> settings = new HashMap<>();
	
	private final Map<String, List<SettingsPropertyChangeListener>> settingsPropertyChangeListeners = new HashMap<>();
	
	public static void setDefaultValues() {
		try {
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().APPLICATION_NAME_SETTING_NAME, "Xiaolong Dictionary");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().APPLICATION_PATH_SETTING_NAME, System.getProperty("user.dir"));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().APPLICATION_VERSION_PROPERTY_NAME, "2.0");
			
			// file settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().XLD_SETTING_FILENAME_SETTING_NAME, "xld.settings");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().XLD_VOCABLE_FILENAME_SETTING_NAME, "vocables.xml");
			
			// main window settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SCENE_WIDTH_SETTING_NAME, "500");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SCENE_HEIGHT_SETTING_NAME, "400");
			
			// language settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_SETTING_NAME, "German");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, "IPA");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_SETTING_NAME, "Mandarin");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, "Pīnyīn");
			
			// vocable settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME, "/");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SEPARATOR_REGEX_SETTING_NAME, "\\s*"+Settings.getInstance().getSettingsProperty(Settings.getInstance().SEPARATOR_CHARACTER_SETTING_NAME)+"\\s*");
			
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().NO_INFORMATION_STRING_SETTING_NAME, "---");
			
			// special characters
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SPECIAL_CHARACTERS_SETTING_NAME, "āáǎàēéěèīíǐìōóǒòūúǔùǖǘǚǜ");
			
			// big character box settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_NAME_SETTING_NAME, "Dialog");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_SIZE_SETTING_NAME, "50");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_FONT_STYLE_SETTING_NAME, "Regular");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CHARACTER_BOX_LOOP_SETTING_NAME, "false");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().BIG_CAHRACTER_BOX_IGNORED_CHARACTERS_SETTING_NAME, "abcdefghijklmnopqrstuvwxyzäöüß ,/ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ");
			
			// dialogs settings
			// add vocable dialog settings
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_FIRST_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_SECOND_LANGUAGE_PHONETIC_SCRIPT_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_TOPIC_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_CHAPTER_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_LEARN_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_RELEVANCE_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().ADD_VOCABLE_DIALOG_PRESERVE_DESCRIPTION_SELECTED_SETTING_NAME, Boolean.toString(false));
			
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_DIRECTION_SETTING_NAME, Settings.getInstance().FIRST_LANGUAGE_TO_SECOND_LANGUAGE_TRAINING_DIRECTION); // first to second language
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_SETTING_NAME, Boolean.toString(true));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_FIRST_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(true));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_SECOND_LANGUAGE_PHONETIC_SCRIPT_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_SHOW_DESCRIPTION_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_LEARN_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(true));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_CUSTOM_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(false));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_TRAINING_PREDEFINED_NEW_RELEVANCE_LEVEL_SELECTED_SETTING_NAME, Boolean.toString(true));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_PREDEFINED_LEARN_LEVELS_SETTING_NAME, "LVL5,LVL4,LVL3,LVL2,LVL1");
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().VOCABLE_PREDEFINED_RELEVANCE_LEVELS_SETTING_NAME, "LVL5,LVL4,LVL3,LVL2,LVL1");
			
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_SAVE_VOCABLE_CHANGES_CONFIRMATION_SETTING_NAME, Boolean.toString(true));
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().DIALOG_SHOW_EXIT_CONFIRMATION_SETTING_NAME, Boolean.toString(true));
			
			Settings.getInstance().changeSettingsProperty(Settings.getInstance().SAVE_VOCABLE_CHANGES_ON_EXIT_SETTING_NAME, Boolean.toString(true));
			
		} catch (SettingNotFoundException ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
		
		}
	}
	
	public void readSettings() {
		final String DEFAULT_SETTINGS_FILE_NAME = "xld.settings";
		Properties properties = new Properties() {
			@Override
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}
		};
		InputStream input = null;

		try {
			input = new FileInputStream(DEFAULT_SETTINGS_FILE_NAME);
			final InputStreamReader inputStreamReader = new InputStreamReader(input, "UTF-8");
			
			// load a properties file
			properties.load(inputStreamReader);
			
			for(String settingName : ALL_SETTINGS_NAMES) {
				if(properties.getProperty(settingName) != null) {
					settings.put(settingName, properties.getProperty(settingName));
				}
			}
			
			settings.forEach(
				(key, value) -> System.out.println(key+"|"+value)
			);

		} catch (IOException ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
			
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		}
		
		
		//readSettingsFromFile(new File(DEFAULT_SETTINGS_FILE_NAME));
		
		settings.forEach((settingName, value) -> System.out.println("KEY:"+settingName+"|VALUE:"+value));
	}
	
	public void writeSettings() {
		final String DEFAULT_SETTINGS_FILE_NAME = "xld.settings";
		Properties properties = new Properties() {
			@Override
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}
		};
		OutputStream settingsOutputStream = null;
		Writer settingsWriter;
		
		try {
			settingsOutputStream = new FileOutputStream(DEFAULT_SETTINGS_FILE_NAME);
			settingsWriter = new OutputStreamWriter(settingsOutputStream, Charset.forName("UTF-8"));
			
			settings.forEach(
				(settingsName, value) -> {
					System.out.println("Saving SETTING:|" + settingsName + "| VALUE:|" + value + "|");
					properties.setProperty(settingsName, value);
				}
			);
			
			// save properties to project root folder
			properties.store(settingsWriter, null);
			
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if(settingsOutputStream != null) {
				try {
					settingsOutputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		
		//final String DEFAULT_XML_SETTINGS_FILE_NAME = "xld_settings.xml";
		//writeSettingsToXMLFile(instance, new File(DEFAULT_XML_SETTINGS_FILE_NAME));
	}
	
	
	public void registerSettingsPropertyChangeListener(String settingName, SettingsPropertyChangeListener settingsPropertyChangeListener) {
		if(settingsPropertyChangeListeners.get(settingName) == null) {
			settingsPropertyChangeListeners.put(settingName, new ArrayList<>());
		}
		settingsPropertyChangeListeners.get(settingName).add(settingsPropertyChangeListener);
	}
	
	private void notifyListeners(String settingName, String value) {
		//Consumer<SettingsPropertyChangeListener> settingsPropertyChangeListenerConsumer = (listener) -> notifyListener(listener, settingName, value);
		if(settingsPropertyChangeListeners.get(settingName) != null) {
			settingsPropertyChangeListeners.get(settingName).forEach(
				(listener) -> notifyListener(listener, settingName, value)
			);
		}
	}
	
	private void notifyListener(SettingsPropertyChangeListener settingsPropertyChangeListener, String settingName, String value) {
		settingsPropertyChangeListener.update(settingName, value);
	}
	
	public void changeSettingsProperty(String settingName, String value) {
		System.out.println("Changing Setting:|"+settingName+"|, using value:|"+value+"|");
		settings.put(settingName, value);
		notifyListeners(settingName, value);
	}
	
	public String getSettingsProperty(String settingName) throws SettingNotFoundException {
		if(settings.get(settingName) != null) {
			return settings.get(settingName);
		} else {
			throw new SettingNotFoundException(String.format("Setting \"%s\" not found!", settingName));
		}
	}
	
	/*
	private boolean writeSettingsToXMLFile(Object object, File file) {
		XStream xStream = new XStream();
		OutputStream outputStream = null;
		Writer writer = null;

		try {
			outputStream = new FileOutputStream(file);
			writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
			xStream.toXML(object, writer);
		}
		catch (Exception ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
		}

		return true;
	}
	*/
	/*
	private void readSettingsFromFile(File file) {
		XStream xStream = new XStream();
		try {
			FileInputStream settingsFileInputStream = new FileInputStream(file);
			FileReader settingsFileReader = new FileReader(file);
			BufferedReader bufferedSettingsReader = new BufferedReader(settingsFileReader);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
		}
		instance = (Settings) xStream.fromXML(file);
	}
	*/
}
