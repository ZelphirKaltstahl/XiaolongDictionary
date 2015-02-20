/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dictionary.helpers;

import java.util.List;

/**
 *
 * @author XiaoLong
 */
public class ListOperationsHelper {
	
	
	/**
	 * This method checks if two {@link List}s of Strings are equal, by checkíng if their are subsets of eachother.
	 * @param listA
	 * @param listB
	 * @return returns true if the two lists are equal
	 */
	public static boolean compareStringLists(List<String> listA, List<String> listB) {
		boolean result = true;
		
		//Is listA a subset of listB?
		for(String str1 : listA) {
			if(!listB.contains(str1)) {
				result = false;
			}
		}
		
		//Is listB a subset of listA?
		for(String str2 : listB) {
			if(!listA.contains(str2)) {
				result = false;
			}
		}
		
		return result;
	}
}
