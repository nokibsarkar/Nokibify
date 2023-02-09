package src;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import src.login.LoginController;
import src.student.StudentManagement;
import src.wordle.Wordle;

public class Main extends Application {
    SceneManager sceneManager = new SceneManager();
    StudentManagement studentManagement;
    MenuBar menuBar = new MenuBar();
    
    void contructStudentManagement() throws IOException{
        MyLogger.log("Constructing the student management");
        studentManagement = new StudentManagement(sceneManager);
    }
    void constructLoginPage() throws IOException{
        MyLogger.log("Constructing the login page");
        // Create the root
        MyLogger.log("Loading the login page from fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login/login.fxml"));
        Parent root = loader.load();
        // create welcome text
        LoginController loginController = (LoginController) loader.getController();
        MyLogger.log("Setting up the login success callback");
        loginController.setupLoginSuccessCallback(sceneManager.goToSceneCallback("menu"));
        MyLogger.log("Setting up the login failure callback");
        WholeScene loginPage = new WholeScene(root);
        loginPage.setStageOptions(Configuration.getOptions("login"));
        MyLogger.log("Adding the scene to the scene manager");
        sceneManager.addScene(loginPage, "login");
    }
    private void constructGamePage(){
        MyLogger.log("Constructing the game page");
        // Create the root
        Wordle gameScene = new Wordle(sceneManager);
        MyLogger.log("Adding the scene to the scene manager");
        sceneManager.addScene(gameScene, "wordle");
    }
    private void constructMenuScene() throws IOException{
        MyLogger.log("Constructing the menu scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        WholeScene menuScene = new WholeScene(root);
        MenuController menuController = (MenuController) loader.getController();
        menuController.init(sceneManager);
        
        menuScene.setStageOptions(Configuration.getOptions("login"));
        MyLogger.log("Adding the scene to the scene manager");
        sceneManager.addScene(menuScene, "menu");
    }
    void construct(Stage stage){
        MyLogger.log("Constructing the scenes");
        try {
            sceneManager.setStage(stage);
            constructLoginPage();
            constructGamePage();
            contructStudentManagement();
            constructMenuScene();
            MyLogger.log("Adding Icon");
            stage.getIcons().add(loadIcon());
            MyLogger.log("Ctrl + X handler");
            stage.addEventHandler(KeyEvent.KEY_RELEASED,  e -> {
                // check is it ctrl + c
                if(e.isControlDown() && e.getCode().toString().equals("X")){
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            MyLogger.log(e);
        }

    }
    Image loadIcon(){
        MyLogger.log("Loading icon");
        Image icon = new Image("assets/ruet logo.png");
        return icon;
    }
    public void start(Stage stage) {
        construct(stage);

        sceneManager.goToSceneCallback("menu").call(null);
        MyLogger.log("Showing the stage");
        stage.show();
    }

    public static void main(String[] args) {
        MyLogger.init();
        MyLogger.log("Starting the application");
        launch();
    }

}