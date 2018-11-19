package nz.ac.massey.cs;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.ResourceReference;
import org.junit.Test.None;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TasksPage extends WebPage {

	private static final long serialVersionUID = 1L;


	public TasksPage() {

        add(new EntryForm("entryForm"));

        Form listForm = new Form("listForm");
        Form listForm2 = new Form("listForm2");			//use for filter 
        Form listForm3 = new Form("listForm3");			//use for persistence 
        add(listForm);
        add(listForm2);
        add(listForm3);
        
		Date now = new Date();
		Label dateTimeLabel = new Label("datetime", now.toString());
		add(dateTimeLabel);

		WicketApplication app = (WicketApplication) this.getApplication();
		TaskList collection = app.getTaskList();
		final List<Task> tasks = collection.getTasks();

		PropertyListView taskListView =
				new PropertyListView("task_list", tasks) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(ListItem item) {

						item.add(new Label("name"));
						item.add(new Label("description"));
						item.add(new Label("date"));
						item.add(new Label("title"));

						item.add(new AjaxCheckBox("completed") {
							@Override
							protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
							}
						});
					}
				};

		add(new Link<Void>("selectAll") {
			@Override
			public void onClick() {
				for(Task t: tasks) {
					t.setComplete(true);
				}

			}
		});

		TaskList col0 =new TaskList();						
		final List<Task> task0 = col0.getTasks();			//task0 used for persistence 
		
		add(new Link<Void>("delAll") {				
			@Override
			public void onClick() {
				for(Task t: tasks) {
					if (!task0.contains(t))
						task0.add(t);
				}
				Iterator<Task> it = tasks.iterator();
				while(it.hasNext()){						//delete all completed tasks
				    Task x = it.next();
				    if(x.isComplete()==true){
				        it.remove();
				    }
				}

			}
		});
		
		TaskList col =new TaskList();
		final List<Task> task3 = col.getTasks();			//task3 used for filter
		
		add(new Link<Void>("comed") {
			@Override
			public void onClick() {
				Iterator<Task> it = task3.iterator();		//delete all tasks in task3
				while(it.hasNext()){
				    Task x = it.next();
				    
				        it.remove();
				    
				}
				for(Task t: tasks) {						//show completed tasks 
					if(t.isComplete()==true) {
						task3.add(t);
					}
				}

			}
		});
		
		add(new Link<Void>("active") {
			@Override
			public void onClick() {
				Iterator<Task> it = task3.iterator();
				while(it.hasNext()){
				    Task x = it.next();
				    
				        it.remove();
				    
				}
				for(Task t: tasks) {					//show active tasks 
					if(t.isComplete()!=true) {
						task3.add(t);
					}
				}

			}
		});
		
		add(new Link<Void>("all") {
			@Override
			public void onClick() {
				Iterator<Task> it = task3.iterator();
				while(it.hasNext()){
				    Task x = it.next();
				    
				        it.remove();
				    
				}
				for(Task t: tasks) {					//show all tasks 
					
						task3.add(t);
					
				}

			}
		});
		
		
		PropertyListView taskListView2 =						//show filter in page
				new PropertyListView("task3", task3) {
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(ListItem item) {

						item.add(new Label("name"));
						item.add(new Label("description"));
						item.add(new Label("date"));
						item.add(new Label("title"));

						item.add(new AjaxCheckBox("completed") {
							@Override
							protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
							}
						});
					}
				};
		

				final Label message=new Label("innum","");			//show the number of incomplete tasks
	            add(message);
				Form form = new Form("numform") {
		            protected void onSubmit() {
		            	TaskList col2 =new TaskList();
						List<Task> task2 = col2.getTasks();
		            	Iterator<Task> it = task2.iterator();
						while(it.hasNext()){
						    Task x = it.next();
						        it.remove();
						}
						for(Task t: tasks) {					//the number of incomplete tasks equals task2.size()
							if(t.isComplete()!=true) {
								task2.add(t);
							}
						}
						message.setDefaultModelObject(task2.size());
		            }
		        };
		        this.add(form);
	           
		        
		       
		        
				add(new Link<Void>("save") {				//for persistence ,save these tasks in "1.csv"
					@Override
					public void onClick() {
						for(Task t: tasks) {				//to make sure all the tasks is in task0
							if (!task0.contains(t))
								task0.add(t);
						}
						BufferedWriter writer = null;
						try {
							writer = new BufferedWriter(new FileWriter(new File("./1.csv"),true));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						     
						 try {
							 for(Task t: task0) {
								 writer.write("name:");
								 writer.write(t.getName());
								 writer.write(",");
								 writer.write("des:");
								 writer.write(t.getDescription());
								 writer.write(",");
								 writer.write("date:");
								 writer.write(t.getDate());
								 writer.write(",");
								 writer.write("title:");
								 writer.write(t.getTitle());
								 writer.write(",");
								 writer.write("com:");
								 if(t.isComplete()==true) {
									 writer.write("true");
									 writer.newLine();
								 }
								 else {
									 writer.write("false");
									 writer.newLine();
								}
							 }
							 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						 try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						 
					}
				});
				
				TaskList col01 = new TaskList();
				final List<Task> task01 = col01.getTasks();
				
				
				add(new Link<Void>("show") {						//show the data in "1.csv"
					
					@Override
					public void onClick() {
						List<String> aList=new ArrayList<>();
						while(task01.size()>0) {
							task01.remove(0);
						}
						for(Task i :task01) {
							System.out.println(i.getName());
						}
//						Iterator<Task> it = task01.iterator();
//						while(it.hasNext()){
//						    it.remove();
//						}
						
						try {
				            ArrayList<String[]> csvList = new ArrayList<String[]>(); 
				            BufferedReader reader = new BufferedReader(new FileReader("./1.csv"));
				            String contentLine = reader.readLine();
				            while (contentLine != null) {

				            	aList.add(contentLine);
//				                System.out.println(contentLine);

				                contentLine = reader.readLine();

				              }
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
						
						for(String i:aList) {
							i.indexOf("name:");
							Task newtask =new Task(i.substring(i.indexOf("name:")+5,i.indexOf("des:")-1), i.substring(i.indexOf("des:")+4,i.indexOf("date:")-1), i.substring(i.indexOf("date:")+5,i.indexOf("title:")-1), i.substring(i.indexOf("title:")+6,i.indexOf("com:")-1));
							if(i.substring(i.indexOf("com:")+4).equals("true")) {
								newtask.setComplete(true);
							}
							
							task01.add(newtask);
							
						}
						
					}
				});

				PropertyListView taskListView01 =							//use for persistence ,show tasks have been save
						new PropertyListView("com_task", task01) {
							private static final long serialVersionUID = 1L;

							@Override
							protected void populateItem(ListItem item) {

								item.add(new Label("name"));
								item.add(new Label("description"));
								item.add(new Label("date"));
								item.add(new Label("title"));

								item.add(new AjaxCheckBox("completed") {
									@Override
									protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
									}
								});
							}
						};
						
				

				Label environmentLabel=new Label("environment",System.getProperty("wicket.configuration"));
		        add(environmentLabel);						//show the environment 
		
		listForm.add(taskListView);
		listForm2.add(taskListView2);
		listForm3.add(taskListView01); 

	}

}
