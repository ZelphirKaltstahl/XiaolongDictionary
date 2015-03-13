/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.helpers;

import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 *
 * @author xiaolong
 */
public class FontOperations {
	
	public static void listFontWeights() {
		for (FontWeight fw : FontWeight.values()) {
			System.out.println(fw.name());
		}
	}
	
	public static void listFontPostures() {
		for (FontPosture fp : FontPosture.values()) {
			System.out.println(fp.name());
		}
	}
	
	public static FontWeight getFontWeightFromFontStyle(String fontStyle) {
		FontWeight result = null;
		
		if (fontStyle.toLowerCase().contains("black")) {
			result = FontWeight.BLACK;
		} else if (fontStyle.toLowerCase().contains("bold")) {
			result = FontWeight.BOLD;
		} else if (fontStyle.toLowerCase().contains("medium")) {
			result = FontWeight.MEDIUM;
		} else if (fontStyle.toLowerCase().contains("light")) {
			result = FontWeight.LIGHT;
		} else if (fontStyle.toLowerCase().contains("normal")) {
			result = FontWeight.NORMAL;
		} else if (fontStyle.toLowerCase().contains("thin")) {
			result = FontWeight.THIN;
		} else if (fontStyle.toLowerCase().contains("extrabold") || fontStyle.toLowerCase().contains("extra_bold") || fontStyle.toLowerCase().contains("extra bold")) {
			result = FontWeight.EXTRA_BOLD;
		} else if (fontStyle.toLowerCase().contains("extralight") || fontStyle.toLowerCase().contains("extra_light") || fontStyle.toLowerCase().contains("extra light")) {
			result = FontWeight.EXTRA_LIGHT;
		} else if (fontStyle.toLowerCase().contains("semibold") || fontStyle.toLowerCase().contains("semi_bold") || fontStyle.toLowerCase().contains("semi bold")) {
			result = FontWeight.SEMI_BOLD;
		}
		
		return result;
	}
	
	public static FontPosture getFontPostureFromFontStyle(String fontStyle) {
		FontPosture result = null;
		
		if (fontStyle.toLowerCase().contains("italic")) {
			result = FontPosture.ITALIC;
		} else if (fontStyle.toLowerCase().contains("regular")) {
			result = FontPosture.REGULAR;
		}
		
		return result;
	}
}
