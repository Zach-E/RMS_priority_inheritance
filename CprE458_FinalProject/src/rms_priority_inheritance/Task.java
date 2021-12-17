package rms_priority_inheritance;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Task {
	
	//id of task
	private int id;
	
	//name of task
	private String name;
	
	//sum of cx, cy, cz, total computation time
	private int ci_time;
	
	//comp time before critical section
	private int cx_time;
	//comp time during critical section
	private int cy_time;
	//comp time after critical section
	private int cz_time;
	
	private int cy2_time;
	
	//period of task
	private int pi;
	
	//priority in a task set with no inheritance
	private int initial_priority;
	
	//priority in a task set with inheritance
	private int current_priority;
	
	private int total_blocking = 0;
	
	private int direct_blocking = 0;
	
	private int inheritance_blocking = 0;
	
	private boolean tieBreaker = false;
	
	private Resource resource1 = null;
	
	private Resource resource2 = null;
	
	private int resourceAmount;
	
	private int comp_time;
	
	public Task(String name, int p, int cx) {
		if(name.length() < 3) {
			while(name.length() != 3) {
				name += " ";
			}
		}
		this.name = name;
		pi = p;
		cx_time = cx;
		cy_time = 0;
		cz_time = 0;
		ci_time = cx;
		resourceAmount=0;
	}
	
	public Task(String name, Resource r, int p, int cx, int cy, int cz) {
		if(name.length() < 3) {
			while(name.length() != 3) {
				name += " ";
			}
		}
		this.name = name;
		r.addTask(this);
		resource1 = r;
		pi = p;
		cx_time = cx;
		cy_time = cy;
		cz_time = cz;
		ci_time = cx+cy+cz;
		resourceAmount=1;
	}
	
	public Task(String name, Resource r1, Resource r2, int p, int cx, int cy, int cy2, int cz) {
		if(name.length() < 3) {
			while(name.length() != 3) {
				name += " ";
			}
		}
		this.name = name;
		r1.addTask(this);
		r2.addTask(this);
		resource1 = r1;
		resource2 = r2;
		pi = p;
		cx_time = cx;
		cy_time = cy;
		cy2_time = cy2;
		cz_time = cz;
		ci_time = cx+cy+cz;
		resourceAmount=2;
	}
	
	public int get_ci() {
		return ci_time;
	}
	
	public int get_cx() {
		return cx_time;
	}
	
	public int get_cy() {
		return cy_time;
	}
	
	public int get_cz() {
		return cz_time;
	}
	
	public int get_pi() {
		return pi;
	}
	
	public int get_initial_priority() {
		return initial_priority;
	}
	
	public int get_current_priority() {
		return current_priority;
	}
	
	protected void set_id(int i) {
		id = i;
	}
	
	public int get_id() {
		return id;
	}
	
	public String get_name() {
		return name;
	}
	
	protected void set_initial_priority(int i) {
		initial_priority = i;
	}
	
	protected void set_current_priority(int i, PriorityQueue<Task> heap) {
		current_priority = i;
		if(heap != null) {
			heap.remove(this);
			heap.offer(this);
		}
	}
	
	public boolean hasResource() {
		return resource1 != null;
	}
	
	public Resource getResource1() {
		return resource1;
	}
	
	public Resource getResource2() {
		return resource2;
	}
	
	public void addToDirectBlocking(int i) {
		direct_blocking += i;
	}
	
	public void addToInheritanceBlocking(int i) {
		inheritance_blocking += i;
	}
	
	public void calculateBlocking() {
		total_blocking = direct_blocking + inheritance_blocking;
	}
	
	public int get_bi() {
		return total_blocking;
	}
	
	public int comp_time() {
		return comp_time;
	}
	
	public void set_comp_time(int i) {
		comp_time = i;
	}
	
	public void comp_timeaa() {
		comp_time++;
	}
	
	public void comp_timess() {
		comp_time--;
	}
	
	public boolean lockResource(PriorityQueue<Task> heap, boolean priority_inheritance, Resource resource) {
		if(resource == null) {
			return false;
		}
		if(resource.lock(id, this)) {
			if(priority_inheritance) {
				/*
				ArrayList<Task> r_tasks = resource.getTasks();
				int highest_priority = -1;
				int indexOfHighest = -1;
				for(int i = 0; i < r_tasks.size(); i++) {
					if(r_tasks.get(i).get_initial_priority() > highest_priority) {
						highest_priority = r_tasks.get(i).get_initial_priority();
						indexOfHighest = i;
					}
				}
				set_current_priority(highest_priority, heap);
				if(r_tasks.get(indexOfHighest).get_id() != this.get_id()) {
					r_tasks.get(indexOfHighest).tieBreaker(true);
				}
				*/
			}
			return true;
		}
		else {
			if(resource.getOwner().current_priority < this.initial_priority && priority_inheritance)
			resource.getOwner().set_current_priority(this.initial_priority, heap);
		}
		return false;
	}
	
	public boolean unlockResource(PriorityQueue<Task> heap, Resource resource) {
		if(resource == null) {
			return false;
		}
		if(resource.unlock()) {
			if(current_priority == initial_priority) {
				return true;
			}
			else {
				ArrayList<Task> r_tasks = resource.getTasks();
				int indexOfHighest = -1;
				int highest_priority = -1;
				for(int i = 0; i < r_tasks.size(); i++) {
					if(r_tasks.get(i).get_initial_priority() > highest_priority) {
						indexOfHighest = i;
						highest_priority = r_tasks.get(i).get_initial_priority();
					}
				}
				r_tasks.get(indexOfHighest).tieBreaker(false);
				set_current_priority(initial_priority, heap);
				return true;
			}
		}
		return false;
	}
	
	public boolean tieBreaker() {
		return tieBreaker;
	}
	
	public void tieBreaker(boolean set) {
		tieBreaker = set;
	}
	
	public int numResources() {
		return resourceAmount;
	}
	
	public int get_cy2Time() {
		return cy2_time;
	}
	
	public void resetTask() {
		tieBreaker = false;
		comp_time = 0;
		current_priority = initial_priority;
	}
	
	@Override
	public String toString() {
		String result;
		if(resourceAmount == 2) {
			result = this.name + "(" + this.resource1.get_name() + "," + this.resource2.get_name() + "," + Integer.toString(this.pi) + "," + Integer.toString(this.cx_time) + "," + Integer.toString(this.cy_time) + "," + Integer.toString(this.cz_time) + ")";
		}
		else if(resource1 != null) {
			result = this.name + "(" + this.resource1.get_name() + "," + Integer.toString(this.pi) + "," + Integer.toString(this.cx_time) + "," + Integer.toString(this.cy_time) + "," + Integer.toString(this.cz_time) + ")";
		}
		else {
			result = this.name + "(" + Integer.toString(this.pi) + "," + Integer.toString(this.cx_time) + ")";
		}
		return result;
	}
	
	public String toBrief() {
		return this.name + "(" + this.get_ci() + "," + this.get_pi() + ")";
	}
}
