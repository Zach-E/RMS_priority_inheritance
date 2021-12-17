package gui;

import java.awt.Component;
import java.awt.Dimension;
//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import rms_priority_inheritance.Resource;
import rms_priority_inheritance.Scheduler;
import rms_priority_inheritance.Task;
import rms_priority_inheritance.TaskSet;

//import javax.swing.JTable;
//import java.awt.Color;
//import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
//import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
//import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
//import javax.swing.JEditorPane;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.Toolkit;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField taskName;
	private JTextField period_text;
	private JTextField cx_text;
	private JTextField cy_text;
	private JTextField cz_text;
	private JTextField resource_text;
	private JTextField length_text;
	private boolean pr_in;
	private boolean resource2;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//formatter
	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	/**
	 * Create the frame.
	 */
	public Frame() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		ArrayList<Resource> resources = new ArrayList<Resource>();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.setBounds(0, 0, screen.width, screen.height);
		
		JPanel tab1_c = new JPanel();
		JPanel tab2_c = new JPanel();
		JPanel tab3_c = new JPanel();
		
		// add tab with title
		String tooltip = "Input a task set";
        tabbedPane.addTab("Input", null, tab1_c, tooltip);
        tab1_c.setLayout(null);
        
        JLabel lblAddTasksTo = new JLabel("Add tasks to task set");
        lblAddTasksTo.setBounds(12, 13, 123, 16);
        tab1_c.add(lblAddTasksTo);
        
        DefaultListModel<Task> tasklist_model = new DefaultListModel<Task>();
        DefaultListModel<Resource> rlist_model = new DefaultListModel<Resource>();
        
        taskName = new JTextField();
        taskName.setBounds(58, 42, 116, 22);
        tab1_c.add(taskName);
        taskName.setColumns(10);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(12, 45, 56, 16);
        tab1_c.add(lblName);
        
        JLabel lblResource = new JLabel("Resource:");
        lblResource.setBounds(182, 45, 67, 16);
        tab1_c.add(lblResource);
        
        JComboBox<Resource> comboBox = new JComboBox<Resource>();
        comboBox.addItem(null);
        comboBox.setBounds(250, 42, 56, 22);
        tab1_c.add(comboBox);
        
        JLabel lblPeriod = new JLabel("Period:");
        lblPeriod.setBounds(12, 77, 56, 16);
        tab1_c.add(lblPeriod);
        
        period_text = new JTextField();
        period_text.setBounds(58, 74, 36, 22);
        tab1_c.add(period_text);
        period_text.setColumns(10);
        
        JLabel cx_lbl = new JLabel("Cx:");
        cx_lbl.setBounds(106, 77, 56, 16);
        tab1_c.add(cx_lbl);
        
        cx_text = new JTextField();
        cx_text.setBounds(133, 74, 36, 22);
        tab1_c.add(cx_text);
        cx_text.setColumns(10);
        
        JLabel lblCy = new JLabel("Cy:");
        lblCy.setBounds(182, 77, 56, 16);
        tab1_c.add(lblCy);
        
        cy_text = new JTextField();
        cy_text.setBounds(205, 74, 36, 22);
        tab1_c.add(cy_text);
        cy_text.setColumns(10);
        
        JLabel lblCz = new JLabel("Cz:");
        lblCz.setBounds(250, 77, 56, 16);
        tab1_c.add(lblCz);
        
        cz_text = new JTextField();
        cz_text.setBounds(273, 74, 36, 22);
        tab1_c.add(cz_text);
        cz_text.setColumns(10);
        
        JComboBox<Resource> comboBox_1 = new JComboBox<Resource>();
        comboBox_1.addItem(null);
        comboBox_1.setBounds(500, 42, 56, 22);
        tab1_c.add(comboBox_1);
        
        JLabel lblResource_1 = new JLabel("Resource 2:");
        lblResource_1.setBounds(421, 45, 90, 16);
        tab1_c.add(lblResource_1);
        
        JLabel lblCy_1 = new JLabel("Cy_2:");
        lblCy_1.setBounds(568, 45, 56, 16);
        tab1_c.add(lblCy_1);
        
        textField = new JTextField();
        textField.setBounds(606, 42, 36, 22);
        tab1_c.add(textField);
        textField.setColumns(10);
        
        JLabel lblCyIsHow = new JLabel("Cy_2 is how long it takes for the second resource to lock in the critical section.");
        lblCyIsHow.setBounds(161, 126, 542, 16);
        tab1_c.add(lblCyIsHow);
        
        resource2 = false;
		comboBox_1.setVisible(false);
		lblResource_1.setVisible(false);
		lblCy_1.setVisible(false);
		textField.setVisible(false);
		lblCyIsHow.setVisible(false);
        
        JCheckBox chckbxResources = new JCheckBox("2 Resources");
        chckbxResources.setBounds(314, 41, 99, 25);
        chckbxResources.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(chckbxResources.isSelected()) {
        			resource2 = true;
        			comboBox_1.setVisible(true);
        			lblResource_1.setVisible(true);
        			lblCy_1.setVisible(true);
        			textField.setVisible(true);
        			lblCyIsHow.setVisible(true);
        		}
        		else {
        			resource2 = false;
        			comboBox_1.setVisible(false);
        			lblResource_1.setVisible(false);
        			lblCy_1.setVisible(false);
        			textField.setVisible(false);
        			lblCyIsHow.setVisible(false);
        		}
        	}
        });
        tab1_c.add(chckbxResources);
        
        JButton btnAddTask = new JButton("Add Task");
        btnAddTask.setBounds(346, 73, 97, 25);
        btnAddTask.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(resource2) {
        			Task t_new = new Task(taskName.getText(), (Resource) comboBox.getSelectedItem(), (Resource) comboBox_1.getSelectedItem(), Integer.parseInt(period_text.getText()),
        					Integer.parseInt(cx_text.getText()), Integer.parseInt(cy_text.getText()), Integer.parseInt(textField.getText()), Integer.parseInt(cz_text.getText()));
        			tasks.add(t_new);
        			tasklist_model.addElement(t_new);
        		}
        		else if(comboBox.getSelectedItem() == null) {
        			Task t_new = new Task(taskName.getText(), Integer.parseInt(period_text.getText()), 
        					Integer.parseInt(cx_text.getText()));
        			tasks.add(t_new);
        			tasklist_model.addElement(t_new);
        		}
        		else {
        			Task t_new = new Task(taskName.getText(), (Resource) comboBox.getSelectedItem(), Integer.parseInt(period_text.getText()),
        					Integer.parseInt(cx_text.getText()), Integer.parseInt(cy_text.getText()), Integer.parseInt(cz_text.getText()));
        			tasks.add(t_new);
        			tasklist_model.addElement(t_new);
        		}
        	}
        });
        tab1_c.add(btnAddTask);
        
        JLabel lblNoteCxIs = new JLabel("Note: Cx is computation time before critical section, Cy is computation time during critical section, and Cz is computation");
        lblNoteCxIs.setBounds(12, 106, 704, 22);
        tab1_c.add(lblNoteCxIs);
        
        JLabel lblDuringCriticalSection = new JLabel("time after critical section.");
        lblDuringCriticalSection.setBounds(12, 126, 149, 16);
        tab1_c.add(lblDuringCriticalSection);
        
        JLabel lblAddResourceTo = new JLabel("Add resource to task set");
        lblAddResourceTo.setBounds(12, 155, 149, 16);
        tab1_c.add(lblAddResourceTo);
        
        JLabel lblName_1 = new JLabel("Name:");
        lblName_1.setBounds(12, 180, 56, 16);
        tab1_c.add(lblName_1);
        
        resource_text = new JTextField();
        resource_text.setBounds(58, 177, 116, 22);
        tab1_c.add(resource_text);
        resource_text.setColumns(10);
        
        JButton btnAddResource = new JButton("Add Resource");
        btnAddResource.setBounds(209, 176, 123, 25);
        btnAddResource.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
    			Resource r_new = new Resource(resource_text.getText());
    			resources.add(r_new);
    			rlist_model.addElement(r_new);
    			comboBox.addItem(r_new);
    			comboBox_1.addItem(r_new);
        	}
        });
        tab1_c.add(btnAddResource);
        
        JList<Task> task_list = new JList<Task>(tasklist_model);
        task_list.setBounds(12, 248, 237, 143);
//        JScrollPane task_scroll = new JScrollPane();
//        task_scroll.setViewportView(task_list);
//        task_list.setLayoutOrientation(JList.VERTICAL);
        tab1_c.add(task_list);
        
        JList<Resource> list_1 = new JList<Resource>(rlist_model);
        list_1.setBounds(290, 248, 175, 143);
        tab1_c.add(list_1);
        
        JLabel lblTasks = new JLabel("Tasks:");
        lblTasks.setBounds(12, 231, 56, 16);
        tab1_c.add(lblTasks);
        
        JLabel lblResources = new JLabel("Resources:");
        lblResources.setBounds(290, 231, 76, 16);
        tab1_c.add(lblResources);
        
        JButton btnDeleteTask = new JButton("Delete task");
        btnDeleteTask.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(task_list.getSelectedIndex() != -1) {
        			tasks.remove(tasklist_model.getElementAt(task_list.getSelectedIndex()));
        			tasklist_model.removeElementAt(task_list.getSelectedIndex());	
        		}
        	}
        });
        btnDeleteTask.setBounds(12, 396, 111, 25);
        tab1_c.add(btnDeleteTask);
        
        JButton btnDeleteResource = new JButton("Delete Resource");
        btnDeleteResource.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(list_1.getSelectedIndex() != -1) {
        			resources.remove(rlist_model.getElementAt(list_1.getSelectedIndex()));
        			comboBox.removeItem(rlist_model.getElementAt(list_1.getSelectedIndex()));
        			comboBox_1.removeItem(rlist_model.getElementAt(list_1.getSelectedIndex()));
        			rlist_model.removeElementAt(list_1.getSelectedIndex());
        		}
        	}
        });
        btnDeleteResource.setBounds(290, 396, 140, 25);
        tab1_c.add(btnDeleteResource);
        
        JCheckBox chckbxPriorityInheritance = new JCheckBox("Priority Inheritance");
        chckbxPriorityInheritance.setBounds(12, 205, 140, 25);
        chckbxPriorityInheritance.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(chckbxPriorityInheritance.isSelected()) {
        			pr_in = true;
        		}
        		else {
        			pr_in = false;
        		}
        	}
        });
        tab1_c.add(chckbxPriorityInheritance);
 
        // add tab with title and icon
        tooltip = "Scheduability checks";
        tabbedPane.addTab("Analysis", null, tab2_c, tooltip);
        tab2_c.setLayout(null);
        
        JLabel lblScheduabilityCheck = new JLabel("Scheduability Check:");
        lblScheduabilityCheck.setBounds(12, 13, 118, 16);
        tab2_c.add(lblScheduabilityCheck);
        
        JLabel lblSumOfcibipi = new JLabel("Sum of ((Ci+Bi)/Pi)");
        lblSumOfcibipi.setBounds(63, 42, 138, 16);
        tab2_c.add(lblSumOfcibipi);
        
        JLabel lblIi = new JLabel("i(2^(1/i)-1)");
        lblIi.setBounds(294, 42, 66, 16);
        tab2_c.add(lblIi);
        
        JLabel lblSum = new JLabel(" ");
        lblSum.setBounds(63, 87, 118, 16);
        tab2_c.add(lblSum);
        
        JLabel lblI = new JLabel(" ");
        lblI.setBounds(294, 87, 103, 16);
        tab2_c.add(lblI);
        
        JLabel label = new JLabel("<=");
        label.setBounds(195, 87, 56, 16);
        label.setVisible(false);
        tab2_c.add(label);
        
        JLabel label_1 = new JLabel(">");
        label_1.setBounds(193, 87, 56, 16);
        label_1.setVisible(false);
        tab2_c.add(label_1);
        
        JLabel lblExactAnalysis = new JLabel("Exact Analysis");
        lblExactAnalysis.setBounds(12, 217, 86, 16);
        tab2_c.add(lblExactAnalysis);
        
        JLabel sch_result = new JLabel(" ");
        sch_result.setBounds(12, 156, 453, 16);
        tab2_c.add(sch_result);
        
        JLabel exact_result = new JLabel(" ");
        exact_result.setBounds(12, 280, 453, 16);
        tab2_c.add(exact_result);
        
        TaskSet ts = new TaskSet(tasks, resources, pr_in);
		Scheduler s = new Scheduler(24, ts, pr_in);
        
        JButton btnRun = new JButton("Run");
        btnRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if(resources.size() > 1) {
        			boolean sch_check = s.scheduablilty_check();
        			Task exact_check = s.exact_analysis();
        			lblSum.setText(String.valueOf(s.get_sum()));
        			lblI.setText(String.valueOf(s.get_i()));
        			label.setVisible(sch_check);
        			label_1.setVisible(!sch_check);
        			if(sch_check) {
        				sch_result.setText("Task set is scheduable under the scheduability check");
        			}
        			else {
        				sch_result.setText("Task set is not scheduable under the scheduability check");
        			}
        			if(exact_check == null) {
        				exact_result.setText("Task set is scheduable under exact analysis");
        			}
        			else {
        				String exact_text = "Task " + exact_check.get_name() + " is not scheduable";
        				exact_result.setText(exact_text);
        			}
        		}
        		else if(resources.size() > 0) {
        			TaskSet ts = new TaskSet(tasks, resources.get(0), pr_in);
        			Scheduler s = new Scheduler(24, ts, pr_in);
        			boolean sch_check = s.scheduablilty_check();
        			Task exact_check = s.exact_analysis();
        			lblSum.setText(String.valueOf(s.get_sum()));
        			lblI.setText(String.valueOf(s.get_i()));
        			label.setVisible(sch_check);
        			label_1.setVisible(!sch_check);
        			if(sch_check) {
        				sch_result.setText("Task set is scheduable under the scheduability check");
        			}
        			else {
        				sch_result.setText("Task set is not scheduable under the scheduability check");
        			}
        			if(exact_check == null) {
        				exact_result.setText("Task set is scheduable under exact analysis");
        			}
        			else {
        				String exact_text = "Task " + exact_check.get_name() + " is not scheduable";
        				exact_result.setText(exact_text);
        			}
        		}
        		else {
        			TaskSet ts = new TaskSet(tasks, pr_in);
        			Scheduler s = new Scheduler(24, ts, pr_in);
        			boolean sch_check = s.scheduablilty_check();
        			Task exact_check = s.exact_analysis();
        			lblSum.setText(String.valueOf(s.get_sum()));
        			lblI.setText(String.valueOf(s.get_i()));
        			label.setVisible(sch_check);
        			label_1.setVisible(!sch_check);
        			if(sch_check) {
        				sch_result.setText("Task set is scheduable under the scheduability check");
        			}
        			else {
        				sch_result.setText("Task set is not scheduable under the scheduability check");
        			}
        			if(exact_check == null) {
        				exact_result.setText("Task set is scheduable under exact analysis");
        			}
        			else {
        				String exact_text = "Task " + exact_check.get_name() + " is not scheduable";
        				exact_result.setText(exact_text);
        			}
        		}
        		//btnRun.setEnabled(false);
        	}
        });
        btnRun.setBounds(142, 9, 97, 25);
        tab2_c.add(btnRun);
 
        // add tab with title, icon and tooltip
        tooltip = "The schedule for the task set";
        tabbedPane.addTab("Schedule", null, tab3_c, tooltip);
        tab3_c.setLayout(null);
        
        JLabel lblLengthOfSchedule = new JLabel("Length of schedule:");
        lblLengthOfSchedule.setBounds(12, 43, 118, 16);
        tab3_c.add(lblLengthOfSchedule);
        
        length_text = new JTextField();
        length_text.setBounds(128, 40, 39, 22);
        tab3_c.add(length_text);
        length_text.setColumns(10);
        
        JLabel lblSchedule = new JLabel("Schedule");
        lblSchedule.setBounds(12, 14, 56, 16);
        tab3_c.add(lblSchedule);
        
        JLabel schedule_lbl = new JLabel("");
        schedule_lbl.setFont(new Font("Courier New", Font.PLAIN, 12));
        schedule_lbl.setBounds(0, 514, screen.width, 16);
        tab3_c.add(schedule_lbl);
        
        JLabel lengthT = new JLabel("");
        lengthT.setBounds(0, 528, 24, 24);
        tab3_c.add(lengthT);
        
        JLabel lblTaskSet = new JLabel("Task Set(ci, pi):");
        lblTaskSet.setVisible(false);
        lblTaskSet.setBounds(12, 72, 118, 16);
        tab3_c.add(lblTaskSet);
        
        JLabel label_3 = new JLabel("");
        label_3.setFont(new Font("Courier New", Font.PLAIN, 12));
        label_3.setBounds(0, 456, 1920, 16);
        tab3_c.add(label_3);
        
        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.setBounds(179, 39, 97, 25);
        btnCalculate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		TaskSet set;
        		if(resources.size() > 1) {
        			set = new TaskSet(tasks, resources, pr_in);
        		}
        		else if(resources.size() > 0) {
        			set = new TaskSet(tasks, resources.get(0), pr_in);
        		}
        		else {
        			set = new TaskSet(tasks, pr_in);
        		}
        		Scheduler schedule = new Scheduler(Integer.parseInt(length_text.getText()), set, pr_in);
        		schedule.schedule();
        		String scheduleString = schedule.printSchedule();
        		schedule_lbl.setText(scheduleString);
        		label_3.setText(schedule.printPrioritySchedule());
        		for(int i = 1; i <= set.resources().size(); i++) {
        			String title = set.resources().get(i-1).get_name() + " is Locked by: ";
        			JLabel rlbl = new JLabel(title);
        	        rlbl.setBounds(0, (427-i*32)-16, 1000, 16);
        	        tab3_c.add(rlbl);
        			JLabel rs = new JLabel(schedule.printResourceSchedule(i-1));
        			rs.setFont(new Font("Courier New", Font.PLAIN, 12));
        			rs.setBounds(0, 427 - i*32, 1920, 16);
        			tab3_c.add(rs);
        		}
        		lengthT.setText(length_text.getText());
        		int lengthI = Integer.parseInt(length_text.getText());
        		lengthT.setLocation(lengthI*28, 528);
        		
        		for(int i = 1; i < lengthI; i++) {
        			JLabel next = new JLabel(Integer.toString(i));
        			next.setBounds(i*28, 528, 24, 24);
        			tab3_c.add(next);
        		}
        		
        		for(int i = 1; i <= tasks.size(); i++) {
        			JLabel next_task = new JLabel(tasks.get(i-1).toBrief());
        			next_task.setBounds(12, 72+i*24, screen.width/2, 24);
        			tab3_c.add(next_task);
        		}
        		lblTaskSet.setVisible(true);
        		setBounds(0, 0, screen.width, screen.height);
        	}
        });
        tab3_c.add(btnCalculate);
        
        JLabel label_2 = new JLabel("0");
        label_2.setBounds(0, 528, 24, 24);
        tab3_c.add(label_2);
        
        JLabel lblSchedule_1 = new JLabel("Schedule:");
        lblSchedule_1.setBounds(0, 485, 73, 16);
        tab3_c.add(lblSchedule_1);
        
        JLabel lblPriorityOfTask = new JLabel("Priority of Task:");
        lblPriorityOfTask.setBounds(0, 427, 97, 16);
        tab3_c.add(lblPriorityOfTask);
        
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Component[] comps = tab3_c.getComponents();
        		for(int i = 0; i < comps.length; i++) {
        			comps[i].setVisible(false);
        		}
        		lblSchedule.setVisible(true);
    			length_text.setVisible(true);
    			lblSchedule_1.setVisible(true);
    			lblLengthOfSchedule.setVisible(true);
    			schedule_lbl.setVisible(true);
    			lengthT.setVisible(true);
    			lblTaskSet.setVisible(true);
    			label_3.setVisible(true);
    			lblPriorityOfTask.setVisible(true);
    			btnCalculate.setVisible(true);
    			btnReset.setVisible(true);
    			label_2.setVisible(true);
        	}
        });
        btnReset.setBounds(288, 39, 97, 25);
        tab3_c.add(btnReset);
        
		contentPane.add(tabbedPane);
	}
}
