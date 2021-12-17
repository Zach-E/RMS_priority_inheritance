package rms_priority_inheritance;

import java.util.ArrayList;

public class TaskSet {
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private ArrayList<Resource> resources = new ArrayList<Resource>();
	private Task[] order;
	private boolean use_priority_inheritance;
			
	public TaskSet(ArrayList<Task> t, ArrayList<Resource> r, boolean pr_in) {
		resources = processResources(r);
		tasks = processTasks(t);
		use_priority_inheritance = pr_in;
		order = new Task[tasks.size()];
		initialize_priority(tasks);
		calculate_blocking(tasks);
	}
	
	public TaskSet(ArrayList<Task> t, Resource r, boolean pr_in) {
		resources = processResource(r);
		tasks = processTasks(t);
		use_priority_inheritance = pr_in;
		order = new Task[tasks.size()];
		initialize_priority(tasks);
		calculate_blocking(tasks);
	}
	
	public TaskSet(ArrayList<Task> t, boolean pr_in) {
		//resources = processResource(r);
		tasks = processTasks(t);
		order = new Task[tasks.size()];
		use_priority_inheritance = pr_in;
		initialize_priority(tasks);
		//calculate_blocking(tasks);
	}
	
	public ArrayList<Task> tasks() {
		return tasks;
	}
	
	public ArrayList<Resource> resources() {
		return resources;
	}
	
	public Task[] order() {
		return order;
	}
	
	private ArrayList<Task> processTasks(ArrayList<Task> ts) {
		for(int i = 0; i < ts.size(); i++) {
			ts.get(i).set_id(i);
		}
		return ts;
	}
	
	private ArrayList<Resource> processResources(ArrayList<Resource> rs) {
		for(int i = 0; i < rs.size(); i++) {
			rs.get(i).set_id(i);
		}
		return rs;
	}
	
	private ArrayList<Resource> processResource(Resource r) {
		r.set_id(0);
		ArrayList<Resource> rs = new ArrayList<Resource>();
		rs.add(r);
		return rs;
	}
	
	private void initialize_priority(ArrayList<Task> tasks) {
		for(int i = 0; i < order.length; i++) {
			order[i] = tasks.get(i);
		}
		Qsort(order, 0, order.length-1);
		int curPriority = order.length;
		for(int i = 0; i < order.length; i++) {
			if(i != 0) {
				if(order[i-1].get_pi() != order[i].get_pi()) {
					curPriority--;
				}
			}
			order[i].set_initial_priority(curPriority);
			order[i].set_current_priority(curPriority, null);
		}
	}
	
	public static int partition(Task[] tasks, int low, int high) 
    { 
		//selecting pivot point
		int pivot = tasks[high].get_pi(); 
		
	    //index of smaller element
        int i = (low-1); 
        
        //tests each point except the pivot
        for (int j=low; j<high; j++) 
        { 
           
        	//if current element is smaller than the pivot...
        	if (tasks[j].get_pi() < pivot) 
            { 
                //increment i to track where the pivot will go later
        		i++; 
        		
        		//swap data of index i and index j
                Task temp = tasks[i]; 
                tasks[i] = tasks[j];
                tasks[j] = temp;
            } 
        } 
        
        /*
         * swap data of index i+1 with the pivot (this is where the pivot 
         * should go in terms of sorting
         */
        Task temp = tasks[i+1]; 
        tasks[i+1] = tasks[high]; 
        tasks[high] = temp; 
        
        //returns the index where the partition went
        return i+1; 
    } 

	private static void Qsort(Task[] tasks, int low, int high) 
    { 
        //base case (sort of, if the index is the same it would simply return the ArrayList 
		if (low < high) 
        { 
            //returns the split location of the al
			int pi = partition(tasks, low, high); 
			
			//sorts the lower partition by constantly swapping around a new pivot
            Qsort(tasks, low, pi-1); 
            
            //sorts the upper partition by constantly swapping around a new pivot
            Qsort(tasks, pi+1, high); 
        }
        return;
    }
	
	private void calculate_blocking(ArrayList<Task> tasks) {
		//direct blocking
		for(int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).numResources() == 2) {
				ArrayList<Task> r_tasks = tasks.get(i).getResource1().getTasks();
				for(int j = 0; j < r_tasks.size(); j++) {
					if(tasks.get(i).get_initial_priority() >= r_tasks.get(j).get_initial_priority() && 
					tasks.get(i).get_id() != r_tasks.get(j).get_id()) {
						tasks.get(i).addToDirectBlocking(r_tasks.get(j).get_cy());
					}
				}
				r_tasks = tasks.get(i).getResource2().getTasks();
				for(int j = 0; j < r_tasks.size(); j++) {
					if(tasks.get(i).get_initial_priority() >= r_tasks.get(j).get_initial_priority() && 
					tasks.get(i).get_id() != r_tasks.get(j).get_id()) {
						tasks.get(i).addToDirectBlocking(r_tasks.get(j).get_cy()-r_tasks.get(j).get_cy2Time());
					}
				}
			}
			else if(tasks.get(i).hasResource()) {
				ArrayList<Task> r_tasks = tasks.get(i).getResource1().getTasks();
				for(int j = 0; j < r_tasks.size(); j++) {
					if(tasks.get(i).get_initial_priority() >= r_tasks.get(j).get_initial_priority() && 
					tasks.get(i).get_id() != r_tasks.get(j).get_id()) {
						tasks.get(i).addToDirectBlocking(r_tasks.get(j).get_cy());
					}
				}
			}
		}
		//inheritance blocking
		if(use_priority_inheritance) {
			for(int k = 0; k < tasks.size(); k++) {
				if(tasks.get(k).numResources() == 2) {
					ArrayList<Task> r_t = tasks.get(k).getResource1().getTasks();
					int highest_priority = -1;
					for(int z = 0; z < r_t.size(); z++) {
						if(r_t.get(z).get_initial_priority() > tasks.get(k).get_initial_priority()) {
							if(r_t.get(z).get_initial_priority() > highest_priority) {
								highest_priority = r_t.get(z).get_initial_priority();
							}
						}
					}
					for(int y = 0; y < tasks.size(); y++) {
						if(tasks.get(k).get_id() != tasks.get(y).get_id() &&
						tasks.get(y).get_initial_priority() < highest_priority && 
						tasks.get(y).get_initial_priority() > tasks.get(k).get_initial_priority()) {
							tasks.get(y).addToInheritanceBlocking(tasks.get(k).get_cy());
						}
					}
					r_t = tasks.get(k).getResource2().getTasks();
					highest_priority = -1;
					for(int z = 0; z < r_t.size(); z++) {
						if(r_t.get(z).get_initial_priority() > tasks.get(k).get_initial_priority()) {
							if(r_t.get(z).get_initial_priority() > highest_priority) {
								highest_priority = r_t.get(z).get_initial_priority();
							}
						}
					}
					for(int y = 0; y < tasks.size(); y++) {
						if(tasks.get(k).get_id() != tasks.get(y).get_id() &&
						tasks.get(y).get_initial_priority() < highest_priority && 
						tasks.get(y).get_initial_priority() > tasks.get(k).get_initial_priority()) {
						tasks.get(y).addToInheritanceBlocking(r_t.get(k).get_cy()-r_t.get(k).get_cy2Time());
						}
					}
				}
				
				else if(tasks.get(k).hasResource()) {
					ArrayList<Task> r_t = tasks.get(k).getResource1().getTasks();
					int highest_priority = -1;
					for(int z = 0; z < r_t.size(); z++) {
						if(r_t.get(z).get_initial_priority() > tasks.get(k).get_initial_priority()) {
							if(r_t.get(z).get_initial_priority() > highest_priority) {
								highest_priority = r_t.get(z).get_initial_priority();
							}
						}
					}
					for(int y = 0; y < tasks.size(); y++) {
						if(tasks.get(k).get_id() != tasks.get(y).get_id() &&
						tasks.get(y).get_initial_priority() < highest_priority && 
						tasks.get(y).get_initial_priority() > tasks.get(k).get_initial_priority()) {
							tasks.get(y).addToInheritanceBlocking(tasks.get(k).get_cy());
						}
					}
				}
			}
		}	
		//totals
		for(int w = 0; w < tasks.size(); w++) {
			tasks.get(w).calculateBlocking();
		}
	}
}
