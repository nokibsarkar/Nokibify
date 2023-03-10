package src.student;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import src.SceneManager;
import src.student.server.Student;
import src.student.server.StudentServer;

public class StudentRegisterController {
    SceneManager sceneManager;
    @FXML
    public Button editCancelButton;
    @FXML
    public Button editSaveButton;
    @FXML
    public TextField rollInput;
    @FXML
    public TextField nameInput;
    @FXML
    public TextField emailInput;
    @FXML
    public DatePicker dobInput;
    @FXML
    public ChoiceBox<Student.GENDER> genderInput;
    @FXML
    public ChoiceBox<Student.DEPARTMENT> deptInput;
    @FXML
    public TextField cgpaInput;
    @FXML
    public TextArea addressInput;
    @FXML
    public ChoiceBox<Student.SEMESTER> semInput;
    public void init(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        genderInput.getItems().add(Student.GENDER.MALE);
        genderInput.getItems().add(Student.GENDER.FEMALE);
        

        for(Student.DEPARTMENT dept : Student.DEPARTMENT.values())
            deptInput.getItems().add(dept);
        for(Student.SEMESTER sem : Student.SEMESTER.values())
            semInput.getItems().add(sem);
    }
    @FXML
    public void save(Event e){
        Student student = new Student();
        student.setRoll(Integer.parseInt(rollInput.getText()));
        student.setName(nameInput.getText());
        student.setEmail(emailInput.getText());
        student.setAddress(addressInput.getText());
        student.setCgpa(cgpaInput.getText());
        student.setSemester(semInput.getValue());
        student.setDepartment(deptInput.getValue());
        StudentServer.addStudent(student);
        sceneManager.goToSceneCallback("studentList", new RequestResult(true, "Successfully Registered")).call(null);
    }
    @FXML
    public void cancel(Event e){
        sceneManager.goToSceneCallback("studentList").call(null);
    }
}
