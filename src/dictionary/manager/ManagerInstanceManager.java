/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.manager;

/**
 *
 * @author XiaoLong
 */
public class ManagerInstanceManager {
	
	private static SearchHistoryManager searchHistoryManager;
	private static VocableFileManager vocableFileManager;
	private static VocableManager vocableManager;
	private static CustomControlsInstanceManager customControlsManager;
	
	public static SearchHistoryManager getSearchHistoryManagerInstance() {
		if(ManagerInstanceManager.searchHistoryManager == null) {
			ManagerInstanceManager.searchHistoryManager = new SearchHistoryManager();
		}
		return ManagerInstanceManager.searchHistoryManager;
	}
	
	public static VocableFileManager getVocableFileManagerInstance() {
		if(ManagerInstanceManager.vocableFileManager == null) {
			ManagerInstanceManager.vocableFileManager = new VocableFileManager();
		}
		return ManagerInstanceManager.vocableFileManager;
	}
	
	public static VocableManager getVocableManagerInstance() {
		if(ManagerInstanceManager.vocableManager == null) {
			ManagerInstanceManager.vocableManager = new VocableManager();
			ManagerInstanceManager.vocableManager.initialize();
		}
		return ManagerInstanceManager.vocableManager;
	}
	
	public static CustomControlsInstanceManager getCustomControlsInstanceManager() {
		if (ManagerInstanceManager.customControlsManager == null) {
			ManagerInstanceManager.customControlsManager = new CustomControlsInstanceManager();
		}
		return customControlsManager;
	}
}
