/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.model;

/**
 *
 * @author xiaolong
 */
public enum Decision {
	
	OK,
	OK_REMEMBER,
	YES,
	YES_REMEMBER,
	NO,
	NO_REMEMBER,
	CANCEL,
	SAVE,
	SAVE_REMEMBER,
	EXIT,
	EXIT_REMEMBER;
	
	
	public static Decision getActionForName(String actionName) {
		Decision result;
		
		switch (actionName.toLowerCase()) {
			case "ok":
				result = OK;
				break;
			case "ok_remember":
				result = OK_REMEMBER;
				break;
			case "yes":
				result = YES;
				break;
			case "yes_remember":
				result = YES_REMEMBER;
				break;
			case "no":
				result = NO;
				break;
			case "no_remember":
				result = NO_REMEMBER;
				break;
			case "cancel":
				result = CANCEL;
				break;
			case "save":
				result = SAVE;
				break;
			case "save_remember":
				result = SAVE_REMEMBER;
				break;
			case "exit":
				result = EXIT;
				break;
			case "exit_remember":
				result = EXIT_REMEMBER;
				break;
			default:
				result = CANCEL;
		}
		
		return result;
	}
	
}
