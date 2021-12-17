package rms_priority_inheritance;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class Scheduler {
	
	private TaskSet set;
	
	private int length;
	
	private String[] schedule;
	
	private String[] priorityOfTask;
	
	private ArrayList<String[]> resourceUsage;
	
	private double check_sum;
	private double check_i;
	
	private boolean isDeadlocked = false;
	
	private int deadLockedAt = -1;
	
	private boolean use_priority_inheritance;
	
	public Scheduler(int length, TaskSet s, boolean pr_in) {
		this.length = length;
		set = s;
		use_priority_inheritance = pr_in;
		schedule = new String[this.length];
		priorityOfTask = new String[this.length];
		resourceUsage = new ArrayList<String[]>();
		for(int i = 0; i < set.resources().size(); i++) {
			resourceUsage.add(new String[this.length]);
		}
		for(int i = 0; i < schedule.length; i++) {
			schedule[i] = null;
			priorityOfTask[i] = null;
			for(int j = 0; j < resourceUsage.size(); j++) {
				resourceUsage.get(j)[i] = null;
			}
		}
		if(scheduablilty_check()) {
			System.out.println("Task set is scheduable under the scheduability check");
		}
		else {
			Task problem = exact_analysis();
			if(problem == null) {
				System.out.println("Task set is scheduable under exact analysis");
			}
			else {
				System.out.println("Task " + problem.get_name() + " is not scheduable");
			}
		}
	}
	
	public String[] schedule() {
		PriorityQueue<Task> priority = new PriorityQueue<Task>(new TaskComparator());
		for(int i = 0; i < set.tasks().size(); i++) {
			set.tasks().get(i).resetTask();
			priority.offer(set.tasks().get(i));
		}
		//stack for holding tasks that will reenter queue
		Stack<Task> holding = new Stack<Task>();
		//each second
		for(int i = 0; i < length; i++) {
			//if no tasks available
			if(priority.isEmpty()) {
				schedule[i] = "   ";
				priorityOfTask[i] = "   ";
				for(int j = 0; j < resourceUsage.size(); j++) {
					Task owner = set.resources().get(j).getOwner();
					if(owner != null) {
						resourceUsage.get(j)[i] = owner.get_name().substring(0, 3);
					}
					else {
						resourceUsage.get(j)[i] = "   ";
					}	
				}
				for(int j = 0; j < set.tasks().size(); j++) {
					if((i+1)%set.tasks().get(j).get_pi() == 0 && i != 1) {
						priority.offer(set.tasks().get(j));
					}
				}
				continue;
			}
			
			//look at top priority task
			Task cur = priority.peek();
			//add time for now
			cur.comp_timeaa();
			while(!priority.isEmpty()) {
				//if top priority has no resource it can be added
				if(!cur.hasResource()) {
					break;
				}
				else {
					//if top priority is already in or passed critical section
					if(cur.comp_time() > cur.get_cx()+1) {
						if(cur.comp_time() == cur.get_cx()+cur.get_cy2Time()+1 && cur.numResources() == 2) {
							boolean lock2 = cur.lockResource(priority, use_priority_inheritance, cur.getResource2());
							if(lock2) {
								break;
							}
							else {
								cur.comp_timess();
								holding.push(priority.poll());
								if(priority.isEmpty()) {
									isDeadlocked = true;
									deadLockedAt = i;
									return null;
								}
								cur = priority.peek();
								cur.comp_timeaa();
								continue;
							}
						}
						break;
					}
					//if top priority will enter critical section
					else if(cur.comp_time() == cur.get_cx()+1 && cur.get_cy() != 0) {
						boolean locked = cur.lockResource(priority, use_priority_inheritance, cur.getResource1());
						if(locked) {
							break;
						}
						else {
							cur.comp_timess();
							holding.push(priority.poll());
							if(priority.isEmpty()) {
								isDeadlocked = true;
								deadLockedAt = i;
								return null;
							}
							cur = priority.peek();
							cur.comp_timeaa();
							continue;
						}
					}
					//top priority is before critical section
					else {
						break;
					}
				}
			}
			schedule[i] = cur.get_name().substring(0, 3);
			String taskPriority = Integer.toString(cur.get_current_priority());
			while(taskPriority.length() < 3) {
				taskPriority += " ";
			}
			
			for(int j = 0; j < resourceUsage.size(); j++) {
				Task owner = set.resources().get(j).getOwner();
				if(owner != null) {
					resourceUsage.get(j)[i] = owner.get_name().substring(0, 3);
				}
				else {
					resourceUsage.get(j)[i] = "   ";
				}	
			}
			
			priorityOfTask[i] = taskPriority;
			if(cur.comp_time() >= cur.get_cx()+cur.get_cy() && cur.hasResource()) {
				cur.unlockResource(priority, cur.getResource1());
				if(cur.numResources() == 2) {
					cur.unlockResource(priority, cur.getResource2());
				}
			}
			//remove if done for period
			if(cur.comp_time() >= cur.get_ci()) {
				cur.set_comp_time(0);
				priority.remove(cur);
			}
			//reinsert any held tasks in stack
			while(!holding.empty()) {
				Task reinsert = holding.pop();
				priority.offer(reinsert);
			}
			for(int j = 0; j < set.tasks().size(); j++) {
				if((i+1)%set.tasks().get(j).get_pi() == 0 && i != 1) {
					priority.offer(set.tasks().get(j));
				}
			}
			
		}
		return schedule;
	}
	
	public boolean scheduablilty_check() {
		double sum = 0;
		for(int i = 0; i < set.tasks().size(); i++) {
			double ci = set.tasks().get(i).get_ci();
			double bi = set.tasks().get(i).get_bi();
			double pi = set.tasks().get(i).get_pi();
			sum += ((ci + bi)/pi);
		}
		double sizi = set.tasks().size();
		double exp = Math.pow(2, (1/sizi));
		double check = sizi*(exp-1);
		check_sum = sum;
		check_i = check;
		return sum <= check;
	}

	public Task exact_analysis() {
		ArrayList<Task> c_high_task = new ArrayList<Task>();
		for(int i = 0; i < set.order().length; i++) {
			Task cur = set.order()[i];
			c_high_task.add(cur);
			int c_ = cur.get_ci() + cur.get_bi();
			int t_old = c_;
			for(int j = 0; j < c_high_task.size() - 1; j++) {
				t_old += set.order()[j].get_ci();
			}
			int t_new = Wi(t_old, c_high_task, i, c_);
			while(t_old != t_new && t_new <= cur.get_pi()) {
				t_old = t_new;
				t_new = Wi(t_old, c_high_task, i, c_);
			}
			if(t_new > cur.get_pi()) {
				return cur;
			}
		}
		return null;
	}
	
	private int Wi(int t, ArrayList<Task> tasks, int index, int c_) {
		int sum = 0;
		for(int i = 0; i < tasks.size(); i++) {
			if(i == index) {
				sum += c_*Math.ceil(t/tasks.get(i).get_pi());
			}
			else {
				double pi = tasks.get(i).get_pi();
				double roundUp = t/pi;
				double factor = Math.ceil(roundUp);
				sum += tasks.get(i).get_ci()*factor;
			}
		}
		return sum;
	}
	
	public String printSchedule() {
		String result = "|";
		if(isDeadlocked) {
			for(int i = 0; i < deadLockedAt; i++) {
				result += schedule[i];
				result += "|";
			}
			result += "Schedule became deadlocked";
			result +="|";
			return result;
		}
		for(int i = 0; i < schedule.length; i++) {
			result += schedule[i];
			result += "|";
		}
		return result;
	}
	
	public String printPrioritySchedule() {
		String result = "|";
		if(isDeadlocked) {
			for(int i = 0; i < deadLockedAt; i++) {
				result += priorityOfTask[i];
				result += "|";
			}
			result += "Schedule became deadlocked";
			result +="|";
			return result;
		}
		for(int i = 0; i < schedule.length; i++) {
			result += priorityOfTask[i];
			result += "|";
		}
		return result;
	}
	
	public String printResourceSchedule(int i) {
		String[] r_schedule = resourceUsage.get(i);
		String result = "|";
		if(isDeadlocked) {
			for(int j = 0; j < deadLockedAt; j++) {
				result += r_schedule[j];
				result += "|";
			}
			result += "Schedule became deadlocked";
			result +="|";
			return result;
		}
		for(int j = 0; j < r_schedule.length; j++) {
			result += r_schedule[j];
			result += "|";
		}
		return result;
		
	}
	
	class TaskComparator implements Comparator<Task> {
		public int compare(Task t1, Task t2) {
			int compare = - (t1.get_current_priority() - t2.get_current_priority());
			/*
			if(compare == 0) {
				if(t1.tieBreaker() && !t2.tieBreaker()) {
					return -1;
				}
				else if(!t1.tieBreaker() && t2.tieBreaker()) {
					return 1;
				}
				else {
					return 0;
				}
			}
			*/
			return compare;
		}
	}
	
	public double get_sum() {
		return check_sum;
	}
	
	public double get_i() {
		return check_i;
	}
}
