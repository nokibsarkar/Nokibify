package src.wordle;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import src.Configuration;
import src.MyLogger;
import src.SceneManager;
import src.WholeScene;



public class Wordle extends WholeScene {
    private int WORDLENGTH = 5;
    private int CHANCES = 5;
    double BOX_H_GAP = 20;
    WordBox wordBox = new WordBox();
    private TextField[] inputCells;
    private GridPane gamePane = new GridPane();
    private VBox verticalBox = new VBox();
    private Font bigFont = new Font(20);
    private Font mediumFont = new Font(20);
    private int currentRow = 0;
    private GridPane inputPane = new GridPane();
    private Button restartButton = new Button("Restart");
    private Label result = new Label();
    SceneManager sceneManager;

    private void showResult(boolean correct) {
        inputPane.setVisible(false);
        result.setVisible(true);
        restartButton.setVisible(true);
        // show the result
        if (!correct) {
            result.setTextFill(javafx.scene.paint.Color.RED);
            result.setText("You have guessed " + wordBox.getCurrentGuess() + " incorrectly!");
        } else {
            result.setTextFill(javafx.scene.paint.Color.GREEN);
            result.setText("You have guessed " + wordBox.getCurrentGuess() + " correctly!");
        }
        result.setFont(mediumFont);
        result.setAlignment(Pos.CENTER);
        restartButton.setOnAction(e -> {
            restart();
            restartButton.setVisible(false);
            result.setVisible(false);
        });
        restartButton.setVisible(true);
    }

    private void submit(String word) {
        word = word.toUpperCase();
        String[] colors = wordBox.matchGuess(word, "green", "blue", "red");
        for (int currentColumn = 0; currentColumn < WORDLENGTH; currentColumn++) {
            TextField cell = new TextField();
            cell.setText(word.substring(currentColumn, currentColumn + 1));
            cell.setAlignment(Pos.CENTER);
            cell.setEditable(false);
            // cell.setDisable(true);
            cell.setStyle("-fx-background-color: " + colors[currentColumn] + "; -fx-text-fill: white;");
            // cell.minHeightProperty().bind(cell.minWidthProperty());
            cell.setFont(bigFont);
            GridPane.setConstraints(cell, currentColumn, currentRow);
            gamePane.getChildren().add(cell);
        }
        boolean guessed = word.equals(wordBox.getCurrentGuess());
        if(guessed)
            ScoreManager.addScore(CHANCES - currentRow, wordBox.getCurrentGuess());
        if (guessed || currentRow >= CHANCES) {
            // game over
            showResult(guessed);
            return;
        } else {
            currentRow++;
            evacuateCells();
            inputCells[0].requestFocus();
        }
    }

    private void evacuateCells() {
        for (int i = 0; i < WORDLENGTH; i++) {
            inputCells[i].setText("");
        }
    }

    private GridPane generateInputPane() {
        // add 1x5 textfields
        inputCells = new TextField[WORDLENGTH];

        for (int j = 0; j < WORDLENGTH; j++) {
            TextField t = new TextField();
            t.setAlignment(Pos.CENTER);
            t.setFont(bigFont);
            
            t.setOnKeyReleased(e -> {
                int currentIndex = inputPane.getChildren().indexOf(t);
                KeyCode code = e.getCode();
                if (code.isLetterKey()) {
                    // A letter key was pressed
                    t.setText(code.toString());
                    // move to next textfield
                    if (currentIndex == WORDLENGTH - 1) {
                        return;
                    }
                    if (currentIndex < WORDLENGTH - 1) {
                        TextField nextField = (TextField) inputPane.getChildren().get(currentIndex + 1);
                        nextField.requestFocus();
                    }
                } else if (code.equals(KeyCode.SHIFT)) {
                    // do nothing
                } else if (code.equals(KeyCode.BACK_SPACE) || code.equals(KeyCode.DELETE)) {
                    // backspace was pressed
                    if (t.getText().length() == 0) {
                        // move to previous textfield
                        if (currentIndex == 0) {
                            return;
                        }
                        TextField prevField = (TextField) inputPane.getChildren().get(currentIndex - 1);
                        prevField.requestFocus();
                    }
                } else if (code.equals(KeyCode.ENTER)) {
                    if (getCurrentGuess().length() == WORDLENGTH) {
                        submit(getCurrentGuess());
                        evacuateCells();
                    }
                } else {
                    // Some other key was pressed
                    t.setText("");
                }
            });
            t.minHeightProperty().bind(t.minWidthProperty());
            GridPane.setConstraints(t, j, 0);
            inputPane.getChildren().add(t);
            inputCells[j] = t;
        }
        inputPane.setHgap(BOX_H_GAP);
        inputPane.setVgap(10);
        return inputPane;

    }

    private void restart() {
        currentRow = 0;
        gamePane.getChildren().clear();
        evacuateCells();
        inputPane.setVisible(true);
        gamePane.setVisible(true);
        inputCells[0].requestFocus();
        wordBox.chooseRandomWord();
    }

    private String getCurrentGuess() {
        String word = "";
        for (int i = 0; i < WORDLENGTH; i++) {
            word += ((TextField) inputCells[i]).getText();
        }
        return word;
    }

    MenuBar generateMenu() {
        MenuBar menuBar = new MenuBar();

        Menu optionsMenu = new Menu("Options");
        Menu gameMenu = new Menu("Game");
        MenuItem mainMenu = new MenuItem("Main Menu");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem about = new MenuItem("Help");
        MenuItem highScores = new MenuItem("High Scores");
        MenuItem logout = new MenuItem("Logout");
        MenuItem exit = new MenuItem("Exit");

        Menu student = new Menu("Student");
        MenuItem studentInfo = new MenuItem("Student List");
        MenuItem addStudent = new MenuItem("Add Student");
        student.getItems().addAll(studentInfo, addStudent);

        gameMenu.getItems().addAll(newGame);
        gameMenu.getItems().add(highScores);
        gameMenu.getItems().add(about);

        optionsMenu.getItems().add(mainMenu);
        optionsMenu.getItems().add(student);
        optionsMenu.getItems().add(logout);
        optionsMenu.getItems().add(exit);

        newGame.setOnAction(e -> restart());
        exit.setOnAction(e -> System.exit(0));
        logout.setOnAction(sceneManager.goToSceneEventHandler("login"));
        mainMenu.setOnAction(sceneManager.goToSceneEventHandler("menu"));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Help");
            alert.setHeaderText("How to Play");
            alert.setContentText(Configuration.getGameHelp());
            alert.showAndWait();
        });
        studentInfo.setOnAction(sceneManager.goToSceneEventHandler("studentList"));
        addStudent.setOnAction(sceneManager.goToSceneEventHandler("studentRegister"));
        highScores.setOnAction(sceneManager.goToSceneEventHandler("score", new Object(){
            public boolean success = true;
        }));
        menuBar.getMenus().addAll(optionsMenu, gameMenu);
        return menuBar;
    }
    void loadScoreScene(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreBoard.fxml"));
        try {
            Parent root = loader.load();
            ScoreController controller = (ScoreController) loader.getController();
            controller.init(sceneManager);
            WholeScene scene = new WholeScene(root);
            scene.setDataHandlerCallback( e -> {
                controller.load();
                return null;
            });
            sceneManager.addScene( scene, "score");
        } catch (IOException e) {
            MyLogger.log(e);
        }
        
    }
    public Wordle(SceneManager sceneManager) {
        super(new VBox());
        this.setStageOptions(Configuration.getOptions("wordle"));

        this.sceneManager = sceneManager;
        
        GridPane inputs = generateInputPane();
        MenuBar menuBar = generateMenu();
        gamePane.setHgap(BOX_H_GAP);
        ObservableList<Node> vChildren = verticalBox.getChildren();
        vChildren.add(menuBar);
        vChildren.add(gamePane);
        vChildren.add(inputs);
        vChildren.add(result);
        vChildren.add(restartButton);

        restartButton.setFont(mediumFont);
        restartButton.setVisible(false);
        result.setVisible(false);
        loadScoreScene();
        this.setRoot(verticalBox);
    }

}
