/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.listeners;

import dictionary.model.Vocable;

/**
 *
 * @author XiaoLong
 */
public interface VocableChangeListener {
	
	public void updateOnVocableChange(Vocable oldVocable, Vocable changedVocable);
	
}
