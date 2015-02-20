
package dictionary.customdatastructures;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xiaolong
 * @param <T> the type which is contained by the list elements
 */
public class DoubleLinkedList<T> {
	
	private int size = 0;
	
	public DoubleLinkedListElement<T> head = null;
	public DoubleLinkedListElement<T> lastElement = null;
	
	private DoubleLinkedListElement<T> currentElement = null;
	
	public DoubleLinkedList() {
		// empty constructor
	}
	
	public DoubleLinkedList(List<T> values) {
		setValues(values);
	}
	
	public final void setValues(List<T> values) {
		clearList();
		values.stream().forEach((value) -> {
			addValue(value);
		});
	}
	
	public List<T> getValues() {
		
		List<T> listOfCharacters = new ArrayList<>();
		
		if(!isEmpty()) {
			DoubleLinkedListElement<T> element = head;
			
			listOfCharacters.add(element.value);
			while (element.nextElement != null) {
				listOfCharacters.add(element.nextElement.value);
				element = element.nextElement;
			}
		}
		
		List<T> result;
		result = new ArrayList<>();
		listOfCharacters.stream().forEach((character) -> {
			result.add(character);
		});
		
		return result;
	}
	
	public T getNextValue() {
		if(currentElement.nextElement != null) {
			currentElement = currentElement.nextElement;
		}
		return currentElement.value;
	}
	
	public T getPreviousValue() {
		if(currentElement.previousElement != null) {
			currentElement = currentElement.previousElement;
		}
		return currentElement.value;
	}
	
	public T getCurrentValue() {
		T result = null;
		if(!isEmpty()) {
			result = currentElement.value;
		}
		return result;
	}
	
	public boolean isEmpty() {
		return (head == null);
	}
	
	private void addValue(T value) {
		size++;
		
		if(isEmpty()) {
			DoubleLinkedListElement<T> newElement = new DoubleLinkedListElement<>(value);
			head = newElement;
			lastElement = newElement;
			currentElement = newElement;
		} else {
			DoubleLinkedListElement<T> newElement = new DoubleLinkedListElement<>(value);
			lastElement.nextElement = newElement;
			newElement.previousElement = lastElement;
			lastElement = newElement;
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public void clearList() {
		head = null;
		lastElement = null;
		currentElement = null;
	}
}
