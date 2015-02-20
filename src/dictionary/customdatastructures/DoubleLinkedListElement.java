/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.customdatastructures;

/**
 *
 * @author xiaolong
 */
public class DoubleLinkedListElement<T> {
	public DoubleLinkedListElement<T> nextElement;
	public DoubleLinkedListElement<T> previousElement;
	
	public T value;
	
	public DoubleLinkedListElement(T value) {
		this.value = value;
	}
	
}
