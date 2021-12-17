package rms_priority_inheritance;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		/*
		Task T1 = new Task("T1", 8, 2);
		Resource R = new Resource("R");
		Task T2 = new Task("T2", R, 12, 0, 4, 0);
		Task T3 = new Task("T3", R, 6, 1, 1, 0);
		*/
		
		Resource R = new Resource("R");
		Resource S = new Resource("S");
		Task T1 = new Task("T1", S, R, 12, 0, 8, 4, 0);
		Task T2 = new Task("T2", R, S, 7, 0, 2, 1, 0);
		Task T3 = new Task("T3", S, 4, 1, 1, 0);
		/*
		Task T1 = new Task("T1", 8, 2);
		Task T2 = new Task("T2", 6, 1);
		Task T3 = new Task("T3", 6, 1);
		*/
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		ArrayList<Resource> rs = new ArrayList<Resource>();
		tasks.add(T1);
		tasks.add(T2);
		rs.add(R);
		rs.add(S);
		tasks.add(T3);
		
		TaskSet set = new TaskSet(tasks, rs, true);
		Scheduler scheduler = new Scheduler(32, set, true);
		scheduler.schedule();
		System.out.println(scheduler.printSchedule());
		return;
	}
}
