/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.manager;

import dictionary.customcontrols.XLDBigCharacterBox;

/**
 *
 * @author xiaolong
 */
public class CustomControlsInstanceManager {
	
	/**
	 * Creates a new instance of {@link XLDBigCharacterBox}, initializes it and then returns this instance.
	 * @param initialCharacters the characters, which are initially shown in the big character box
	 * @return an instance of {@link XLDBigCharacterBox}
	 */
	public static XLDBigCharacterBox createXLDBigCharacterBoxInstance(String initialCharacters) {
		XLDBigCharacterBox xldBigCharacterBox = new XLDBigCharacterBox(initialCharacters);
		xldBigCharacterBox.init();
		return xldBigCharacterBox;
	}
	
	public static XLDBigCharacterBox createXLDBigCharacterBoxInstance(String initialCharacters, String labelText) {
		XLDBigCharacterBox xldBigCharacterBox = new XLDBigCharacterBox(initialCharacters, labelText);
		xldBigCharacterBox.init();
		return xldBigCharacterBox;
	}
}
