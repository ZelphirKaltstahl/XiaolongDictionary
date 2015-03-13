/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.listeners;

import dictionary.dialogs.confirmations.Decision;

/**
 *
 * @author xiaolong
 */
public interface DecisionListener {
	
	public void reactOnDecision(Object responder, Decision decision);
	
}
