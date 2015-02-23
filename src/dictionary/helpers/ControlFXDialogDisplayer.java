/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.helpers;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author xiaolong
 */
public class ControlFXDialogDisplayer {
	
	public static void showAddingDuplicateVocableDialog(Object dialogOwner) {
		Dialogs.create()
			.owner(dialogOwner)
			.title("Duplicate Vocable")
			.masthead("Information")
			.message("The vocable you want to add is already in the currently active dictionary.")
			.showInformation();
	}
	
	public static void showEmptyInputFieldsDialog(Object dialogOwner) {
		Dialogs.create()
			.owner(dialogOwner)
			.title("Empty Fields")
			.masthead("Information")
			.message("One or more input fields are empty. Please enter values.\n(You can enter a place holder like \"---\" and check the preserve check box, if you do not wish to bother with some input fields.)")
			.showInformation();
	}
	
	public static Action showExitConfirmationDialog(Object dialogOwner) {
		return Dialogs.create()
			.owner(dialogOwner).title("Confirm Exit")
			.masthead("Exit Xiaolong Dictionary")
			.message("Do you really want to exit Xiaolong Dictionary?")
			.actions(Dialog.Actions.YES, Dialog.Actions.NO)
			.showConfirm();
	}
	
	public static Action showDeleteVocablesConfirmationDialog(Object dialogOwner) {
		return Dialogs.create()
			.owner(dialogOwner).title("Confirm Deletion")
			.masthead("Deletion of vocables")
			.message("Do you really want to delete the selected vocables?")
			.actions(Dialog.Actions.YES, Dialog.Actions.NO)
			.showConfirm();
	}
	
	public static void showNoVocablesSelectedForDeletionDialog(Object dialogOwner) {
		Dialogs.create()
			.owner(dialogOwner)
			.title("Deletion of Vocables")
			.masthead("No vocables selected")
			.message("Please select the vocables you want to delete from the vocable table first.")
			.showInformation();
	}
	
	public static void showNoVocableSelectedForChangeVocableDialog(Object dialogOwner) {
		Dialogs.create()
			.owner(dialogOwner)
			.title("Change of Vocable")
			.masthead("No vocables selected")
			.message("Please select the vocable you want to change from the vocable table first.")
			.showInformation();
	}
	
	public static void showNoVocablesSelectedForTrainingDialog(Object dialogOwner) {
		Dialogs.create()
			.owner(dialogOwner)
			.title("Training of Vocables")
			.masthead("No vocables selected")
			.message("Please select the vocables you want to train from the vocable table first.")
			.showInformation();
	}
	
	public static void showNoVocableAttributesSelectedForSearch(Object dialogOwner) {
		Action response = Dialogs.create()
			.owner(dialogOwner)
			.title("Search")
			.masthead("No vocable attribute selected")
			.message("To search for vocables, you must select at least one vocable attribute.")
			.actions(Dialog.Actions.OK)
			.showConfirm();
	}
	
	public static void showSearchCancelledDialog(Object dialogOwner) {
		Action response = Dialogs.create()
			.owner(dialogOwner).title("Search cancelled")
			.masthead("Search cancelled")
			.message("The search has been cancelled.")
			.actions(Dialog.Actions.OK)
			.showConfirm();
	}
	
	public static void showSearchFailedDialog(Object dialogOwner) {
		Action response = Dialogs.create()
			.owner(dialogOwner).title("Search failed")
			.masthead("Search failed")
			.message("The search has failed.")
			.actions(Dialog.Actions.OK)
			.showConfirm();
	}
	
	
}
