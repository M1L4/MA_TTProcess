package de.ma.lamp.ttprocess.execution;

import java.util.EventObject;

public class ExecutionEvent extends EventObject {
	//http://stackoverflow.com/questions/1658702/how-do-i-make-a-class-extend-observable-when-it-has-extended-another-class-too
	
	private static final long serialVersionUID = 1L;	

	// This event definition is stateless but you could always
	// add other information here.
	public ExecutionEvent(Object source) {
		super(source);
	}
}