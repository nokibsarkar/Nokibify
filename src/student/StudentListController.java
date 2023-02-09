package src.student;

import src.Configuration;
import src.SceneManager;
import src.student.server.Student;
import src.student.server.StudentServer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class StudentListController {
    @FXML
    public TableView<Student> studentList;
    @FXML
    public Button registerButton;
    @FXML
    public Button editButton;
    @FXML
    public Button showButton;
    @FXML
    MenuItem exitMenu;
    @FXML
    MenuItem aboutMenu;
    @FXML
    MenuItem mainMenu;
    @FXML
    MenuItem newGameMenu;
    @FXML
    MenuItem scoreMenu;
    @FXML
    MenuItem gameTutorialMenu;

    private Dialog<Void> showDialog = new Dialog<Void>();
    private SceneManager sceneManager;

    public void init(SceneManager sceneManager) {
        this.sceneManager = sceneManager;

        

        TableColumn<Student, String> rollColumn = new TableColumn<>("Roll");
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
        TableColumn<Student, String> semesterColumn = new TableColumn<>("Semester");
        TableColumn<Student, String> cgpaColumn = new TableColumn<>("CGPA");

        rollColumn.setCellValueFactory(new PropertyValueFactory<>("roll"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        cgpaColumn.setCellValueFactory(new PropertyValueFactory<>("cgpa"));

        studentList.getColumns().add(rollColumn);
        studentList.getColumns().add(nameColumn);
        studentList.getColumns().add(addressColumn);
        studentList.getColumns().add(semesterColumn);
        studentList.getColumns().add(cgpaColumn);
        listStudents(null);

        showDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = showDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        showDialog.getDialogPane().setPrefHeight(Configuration.getOptions("studentShow").height);
        showDialog.getDialogPane().setPrefWidth(Configuration.getOptions("studentShow").width);
        exitMenu.setOnAction(e -> System.exit(0));
        mainMenu.setOnAction(sceneManager.goToSceneEventHandler("menu"));
        
        aboutMenu.setOnAction(e -> sceneManager.goToSceneCallback("about").call(null));
        newGameMenu.setOnAction(e -> sceneManager.goToSceneCallback("wordle").call(null));
        scoreMenu.setOnAction(e -> sceneManager.goToSceneCallback("score").call(null));
        gameTutorialMenu.setOnAction(e -> sceneManager.goToSceneCallback("gameTutorial").call(null));
        listStudents(null);
    }
    @FXML
    void addStudent(Event e) {
        if(Configuration.isAction(e))
            sceneManager.goToSceneCallback("studentRegister", null).call(null);
    }

    @FXML void listStudents(Event e) {
        if(e != null && !Configuration.isAction(e)) return;
        studentList.getItems().clear();
        Student[] students = StudentServer.readStudents().values().toArray(new Student[0]);
        for (int i = 0; i < students.length; i++) {
            studentList.getItems().add(students[i]);
        }
    }
    @FXML
    void editStudentPrompt(Event e) {
        Student selected = studentList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No student selected");
            alert.setContentText("Please select a student to edit");
            alert.showAndWait();
            return;
        }
        if(!Configuration.isAction(e)) return;
        System.out.println("edit student");
        sceneManager.goToSceneCallback("studentEdit", selected).call(null);
    }

    @FXML
    void showStudent(Event e) {
        Student selected = studentList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("No student selected");
            return;
        }
        if(!Configuration.isAction(e)) return;
        showDialog.setTitle("Student Details");
        DialogPane dialogPane = showDialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #ffffa3;");
        String content = "Roll: " + selected.getRoll() + "\n";
        content += "Name: " + selected.getName() + "\n";
        content += "Gender: " + selected.getGender() + "\n";
        content += "Email: " + selected.getEmail() + "\n";
        content += "Date of Birth: " + selected.getBirthday() + "\n";
        content += "Address: " + selected.getAddress() + "\n";
        content += "Semester: " + selected.getSemester() + "\n";
        content += "CGPA: " + selected.getCgpa() + "\n";
        content += "Department: " + selected.getDepartment() + "\n";
        content += "Session: " + selected.getSession() + "\n";
        HBox hBox = new HBox();
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #357a38;");
        editButton.setTextFill(Color.WHITE);
        editButton.setPrefWidth(100);
        editButton.setPrefHeight(30);
        editButton.setOnAction(event -> {
            editStudentPrompt(event);
            showDialog.close();
        });
        hBox.getChildren().add(new Label(content));
        hBox.getChildren().add(editButton);
        dialogPane.setContent(hBox);

        showDialog.showAndWait();
    }

    @FXML
    void showStudentDoubleClick(MouseEvent e) {
        if (e.getClickCount() > 1) {
            showStudent(e);
        }
    }
}
