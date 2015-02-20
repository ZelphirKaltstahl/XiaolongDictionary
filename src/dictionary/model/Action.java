/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary.model;

/**
 *
 * @author xiaolong
 */
public interface Action<T> {
	
	public void execute(T value);
	
}
