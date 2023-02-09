package src.wordle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.SceneManager;

public class ScoreController {
    @FXML
    TableView<Score> scoreBoard;
    @FXML
    Button newGameButton;
    @FXML
    MenuItem exitMenu;
    @FXML
    MenuItem aboutMenu;
    @FXML
    MenuItem mainMenu;
    @FXML
    MenuItem listStudentMenu;
    @FXML
    MenuItem addStudentMenu;
    SceneManager sceneManager;
    @FXML
    void newGame(Event e){
        System.out.println("new game");
        sceneManager.goToSceneCallback("wordle").call(null);
    }
    @FXML
    void tutorial(Event e){
        sceneManager.goToSceneCallback("gameTutorial").call(null);
    }
    void initialize(){
        
    }
    public void init(SceneManager sceneManager){
        this.sceneManager = sceneManager;
        mainMenu.setOnAction(sceneManager.goToSceneEventHandler("menu"));
        exitMenu.setOnAction(e -> System.exit(0));
        aboutMenu.setOnAction(sceneManager.goToSceneEventHandler("about"));
        listStudentMenu.setOnAction(sceneManager.goToSceneEventHandler("studentList"));
        addStudentMenu.setOnAction(sceneManager.goToSceneEventHandler("studentAdd"));
        TableColumn<Score, String> dateCol = new TableColumn<>("Date");
        TableColumn<Score, String> scoreCol = new TableColumn<>("Score");
        TableColumn<Score, String> targetCol = new TableColumn<>("Target");
        
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        targetCol.setCellValueFactory(new PropertyValueFactory<>("target"));
        scoreBoard.getColumns().addAll(dateCol, targetCol, scoreCol);
        
        load();
    }
    public void load(){
        Score[] scores = (Score[]) ScoreManager.readScores().toArray(new Score[0]);
        for (int i = 0; i < scores.length; i++) {
            scoreBoard.getItems().add(scores[i]);
        }
    }
}
