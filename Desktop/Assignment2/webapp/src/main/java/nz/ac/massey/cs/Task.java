package nz.ac.massey.cs;

import java.io.Serializable;
import java.util.Date;

// This class models a Task item

public class Task implements Serializable {

    private String description;
    private boolean completed;
    private String name;
    private String title;
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private Integer id;
    private String date;


    public Task(String name,String description,String date2,String title) {
        this.name = name;
        this.description = description;
        this.date=date2;
        this.title=title;
        this.completed = false;
    }
    public boolean isComplete() {
        return completed;
    }
    public void setComplete(boolean complete) { completed = complete; }
}
