/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.dialogs;

import dictionary.manager.DialogInstanceManager;
import javafx.scene.control.TextInputControl;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author XiaoLong
 */
public abstract class XLDDialog extends Stage {
	
	protected TextInputControl lastActiveTextInputControl;
	protected int caretPosition;
	
	public XLDDialog(Modality modality) {
		initModality(modality);
	}
	
	public void init() {
		initializeControls();
		addControls();
		addActionListeners();
		addFocusChangeListeners();
	}

	protected abstract void initializeControls();

	protected abstract void addControls();

	protected abstract void addActionListeners();

	protected abstract void addFocusChangeListeners();
	
	
	public int getCaretPosition() {
		return caretPosition;
	}
	
	protected void insertSpecialCharacterButtonActionPerformed() {
		if(lastActiveTextInputControl != null) {
			lastActiveTextInputControl.requestFocus();
			DialogInstanceManager.getInsertSpecialCharacterDialogInstance().show();
			DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveParent(this);
			DialogInstanceManager.getInsertSpecialCharacterDialogInstance().setActiveTextField(lastActiveTextInputControl);
			DialogInstanceManager.getInsertSpecialCharacterDialogInstance().requestFocus();
			lastActiveTextInputControl.positionCaret(caretPosition);
		} else {
			System.out.println("lastActiveTextInputControl was NULL!");
		}
	}
}
