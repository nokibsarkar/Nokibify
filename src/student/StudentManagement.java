package src.student;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import src.Configuration;
import src.SceneManager;
import src.WholeScene;
import src.student.server.Student;
class RequestResult {
    public boolean success;
    public String message;
    public Object data;
    public RequestResult(boolean status, String message, Object data) {
        this.success = status;
        this.message = message;
        this.data = data;
    }
    public RequestResult(boolean status, String message) {
        this.success = status;
        this.message = message;
    }
    public RequestResult(boolean status) {
        this.success = status;
    }
    public RequestResult() {
    }
}
public class StudentManagement {
    private StudentListController studentListController;
    private StudentRegisterController studentRegisterController;
    private StudentEditController studentEditController;
    private WholeScene studentListScene;
    private WholeScene studentRegisterScene;
    private WholeScene studentEditFormScene;
    private SceneManager sceneManager;

    private void loadStudentListScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentList.fxml"));
        Parent root = loader.load();
        studentListController = (StudentListController) loader.getController();
        studentListController.init(sceneManager);
        studentListScene = new WholeScene(root);
        studentListScene.setDataHandlerCallback(result -> {
            RequestResult requestResult = (RequestResult) result;
            if (requestResult.success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText(requestResult.message);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText(requestResult.message);
                alert.showAndWait();
            }
            studentListController.listStudents(null);
            return null;
        });
        studentListScene.setStageOptions(Configuration.getOptions("studentList"));
        sceneManager.addScene(studentListScene, "studentList");
    }

    private void loadStudentRegisterScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentRegister.fxml"));
        Parent root = loader.load();
        studentRegisterController = (StudentRegisterController) loader.getController();
        studentRegisterController.init(sceneManager);
        studentRegisterScene = new WholeScene(root);
        studentRegisterScene.setStageOptions(Configuration.getOptions("studentRegister"));
        sceneManager.addScene(studentRegisterScene, "studentRegister");
    }

    private void loadStudentEditFormScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("studentEdit.fxml"));
        Parent root = loader.load();
        studentEditController = (StudentEditController) loader.getController();
        studentEditController.init(sceneManager);
        studentEditFormScene = new WholeScene(root);
        studentEditFormScene.setDataHandlerCallback(student -> {
            try {
                studentEditController.setStudent((Student) student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
        studentEditFormScene.setStageOptions(Configuration.getOptions("studentEdit"));
        sceneManager.addScene(studentEditFormScene, "studentEdit");
    }

    public StudentManagement(SceneManager scm) throws IOException {
        sceneManager = scm;
        // StudentServer.addStudent(new Student(2010029, "Namzul Haque Naqib", "Kazla",
        // "4.00", "ECE", "2020-21", "2-1", "nokibsarkar"));
        loadStudentListScene();
        loadStudentEditFormScene();
        loadStudentRegisterScene();

    }
}
