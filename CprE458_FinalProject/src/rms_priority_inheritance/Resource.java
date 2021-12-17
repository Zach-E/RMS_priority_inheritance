package rms_priority_inheritance;

import java.util.ArrayList;

public class Resource {
	
	private int id;
	
	private String name;
	
	private boolean isLocked;
	
	//id of Task that has locked the resource
	private int lockedBy;
	
	private Task taskLock = null;
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	public Resource(String name) {
		this.name = name;
	}

	public int get_id() {
		return id;
	}
	
	protected void set_id(int i) {
		id = i;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public int lockedBy() {
		return lockedBy;
	}
	
	public boolean lock(int id, Task t) {
		if(!isLocked) {
			lockedBy = id;
			isLocked = true;
			taskLock = t;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean unlock() {
		if(isLocked) {
			lockedBy = -1;
			isLocked = false;
			taskLock = null;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addTask(Task t) {
		tasks.add(t);
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public String get_name() {
		return name;
	}
	
	public char print_name() {
		return name.charAt(0);
	}
	
	public Task getOwner() {
		return taskLock;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
