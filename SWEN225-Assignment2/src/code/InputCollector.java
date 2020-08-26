package code;

import code.Game.WorkState;

public class InputCollector {

	private WorkState workState;
	private Object input;
	
	public InputCollector() {
		
	}
	
	public void setWorkStateTo(WorkState state) {
		workState = state;
		
	}
	
	public WorkState getWorkStateTo(WorkState state) {
		return workState;
		
	}
	
}
