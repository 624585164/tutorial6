package nz.ac.massey.cs;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

// form with two fields for adding a task item

public class EntryForm extends Form<Void> {

    private RequiredTextField nameField;
    private RequiredTextField descriptionField;
    private RequiredTextField dateField;
    private RequiredTextField titleField;


    public EntryForm(String id) {
        super(id);
        nameField = new RequiredTextField("name", Model.of(""));
        descriptionField = new RequiredTextField("description", Model.of(""));
        dateField = new RequiredTextField("date", Model.of(""));
        titleField = new RequiredTextField("title", Model.of(""));
        add(nameField);
        add(descriptionField);
        add(dateField);
        add(titleField);
    }

    // adds the task when the form is submitted (by clicking the Add button)
    protected void onSubmit() {
        super.onSubmit();
        String name = (String)nameField.getDefaultModelObject();
        String description = (String)descriptionField.getDefaultModelObject();
        String date = (String)dateField.getDefaultModelObject();
        String title = (String)titleField.getDefaultModelObject();

        descriptionField.clearInput();
        descriptionField.setModelObject(null);
        nameField.clearInput();
        nameField.setModelObject(null);
        dateField.clearInput();
        dateField.setModelObject(null);
        titleField.clearInput();
        titleField.setModelObject(null);
        

        WicketApplication app = (WicketApplication) this.getApplication();
        TaskList collection = app.getTaskList();
        collection.addTask(new Task(name,description,date,title));

    }
}
