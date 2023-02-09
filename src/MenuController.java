package src;

import javafx.fxml.FXML;

public class MenuController {
    private SceneManager sceneManager;
    public void init(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }
    @FXML
    public void logout(){
        sceneManager.goToSceneCallback("login").call(null);
    }
    @FXML
    public void goToWordle(){
        sceneManager.goToSceneCallback("wordle").call(null);
    }
    @FXML
    public void goToStudentManagement(){
        sceneManager.goToSceneCallback("studentList").call(null);
    }
}
