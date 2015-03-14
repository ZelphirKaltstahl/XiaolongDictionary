/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customcontrols;

import dictionary.model.Decision;
import javafx.scene.control.Button;

/**
 *
 * @author xiaolong
 */
public class XLDGenericMessageDialogButton extends Button {
	
	private final Decision decision;
	
	public XLDGenericMessageDialogButton(String text, Decision decision) {
		super(text);
		this.decision = decision;
	}
	
	public Decision getDecision() {
		return decision;
	}
}
