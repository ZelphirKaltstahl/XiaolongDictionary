/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.customcontrols;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author XiaoLong
 */
public class XLDMenuBar extends MenuBar {
	private Menu fileMenu;
	private Menu optionsMenu;
	private Menu helpMenu;
	private MenuItem fileMenu_createNewDictionary_menuItem;
	private MenuItem fileMenu_openDictionary_menuItem;
	private MenuItem fileMenu_saveDictionary_menuItem;
	private MenuItem fileMenu_saveDictionaryAs_menuItem;
	private MenuItem fileMenu_saveSearchResultAs_menuItem;
	private MenuItem fileMenu_exit_menuItem;
	
	private MenuItem optionsMenu_vocables_menuItem;
	private MenuItem optionsMenu_search_menuItem;
	private MenuItem optionsMenu_language_menuItem;
	private MenuItem optionsMenu_vocableTable_menuItem;
	private MenuItem optionsMenu_training_menuItem;
	private MenuItem optionsMenu_bigCharacterBox_menuItem;
	private MenuItem optionsMenu_dialogs_menuItem;
	private MenuItem optionsMenu_specialCharacters_menuItem;
	private MenuItem optionsMenu_searchHistory_menuItem;
	
	private MenuItem helpMenu_help_menuItem;
	private MenuItem helpMenu_info_menuItem;
	
	public XLDMenuBar() {
		initializeUIControls();
		addGUIControls();
	}

	private void initializeUIControls() {
		fileMenu = new Menu("File");
		optionsMenu = new Menu("Options");
		helpMenu = new Menu("Help");
		
		fileMenu_createNewDictionary_menuItem = new MenuItem("Create new dictionary ...");
		fileMenu_openDictionary_menuItem = new MenuItem("Open dictionary ...");
		fileMenu_saveDictionary_menuItem = new MenuItem("Save dictionary ...");
		fileMenu_saveDictionaryAs_menuItem = new MenuItem("Save dictionary as ...");
		fileMenu_saveSearchResultAs_menuItem = new MenuItem("Save search result as ...");
		fileMenu_exit_menuItem = new MenuItem("Exit");
		
		optionsMenu_bigCharacterBox_menuItem = new MenuItem("Big Character Box");
		optionsMenu_dialogs_menuItem = new MenuItem("Dialogs");
		optionsMenu_language_menuItem = new MenuItem("Language");
		optionsMenu_searchHistory_menuItem = new MenuItem("Search History");
		optionsMenu_search_menuItem = new MenuItem("Search");
		optionsMenu_specialCharacters_menuItem = new MenuItem("Special Characters");
		optionsMenu_training_menuItem = new MenuItem("Training");
		optionsMenu_vocableTable_menuItem = new MenuItem("Vocable Table");
		optionsMenu_vocables_menuItem = new MenuItem("Vocable");
		
		helpMenu_help_menuItem = new MenuItem("Help");
		helpMenu_info_menuItem = new MenuItem("Info");
		//options items
		//help items
	}
	
	private void addGUIControls() {
		getMenus().add(fileMenu);
		getMenus().add(optionsMenu);
		getMenus().add(helpMenu);
		
		fileMenu.getItems().add(fileMenu_createNewDictionary_menuItem);
		fileMenu.getItems().add(fileMenu_openDictionary_menuItem);
		fileMenu.getItems().add(fileMenu_saveDictionary_menuItem);
		fileMenu.getItems().add(fileMenu_saveDictionaryAs_menuItem);
		fileMenu.getItems().add(fileMenu_saveSearchResultAs_menuItem);
		fileMenu.getItems().add(fileMenu_exit_menuItem);
		
		optionsMenu.getItems().add(optionsMenu_vocables_menuItem);
		optionsMenu.getItems().add(optionsMenu_vocableTable_menuItem);
		optionsMenu.getItems().add(optionsMenu_training_menuItem);
		optionsMenu.getItems().add(optionsMenu_specialCharacters_menuItem);
		optionsMenu.getItems().add(optionsMenu_search_menuItem);
		optionsMenu.getItems().add(optionsMenu_searchHistory_menuItem);
		optionsMenu.getItems().add(optionsMenu_language_menuItem);
		optionsMenu.getItems().add(optionsMenu_dialogs_menuItem);
		optionsMenu.getItems().add(optionsMenu_bigCharacterBox_menuItem);
		
		helpMenu.getItems().add(helpMenu_help_menuItem);
		helpMenu.getItems().add(helpMenu_info_menuItem);
	}
}
