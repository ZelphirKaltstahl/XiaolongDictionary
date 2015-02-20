/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.buttons;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author XiaoLong
 */
public class InsertSpecialCharacterButton extends Button {
	
	public InsertSpecialCharacterButton() {
		initialize();
	}

	private void initialize() {
		String insertSpecialCharacterImagePath = "resources/images/insert_special_character.png";
		Image specialCharacterImage = new Image(insertSpecialCharacterImagePath); //getClass().getResourceAsStream(insertSpecialCharacterImagePath)
		this.setGraphic(new ImageView(specialCharacterImage));
	}
}
