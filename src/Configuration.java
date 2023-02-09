package src;

import java.io.FileReader;
import java.io.IOException;

import javafx.event.Event;
import javafx.scene.input.KeyEvent;


public abstract class Configuration {
    static StageOptions defaultStageOptions = new StageOptions();
    static StageOptions loginStageOptions = new StageOptions(600, 450, false, "Login");
    static StageOptions gameScenStageOptions = new StageOptions(800, 650, false, "Wordle");
    static StageOptions studentShowOptions = new StageOptions(500, 350, false, "Student Management");
    static StageOptions studentListOptions = new StageOptions(600, 450, false, "Student List");
    static StageOptions studentRegisterOptions = new StageOptions(600, 450, false, "Student Register");
    static StageOptions studentEditOptions = new StageOptions(600, 450, false, "Student Edit");
    static String gameHelp = "No help available";
    public static String getGameHelp(){
        if(gameHelp == null){
            try (// read from the file
            FileReader fr = new FileReader("src/wordle/gameHelp.txt")) {
                int i;
                StringBuilder sb = new StringBuilder();
                while ((i = fr.read()) != -1) {
                    sb.append((char) i);
                }
                gameHelp = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gameHelp;
    }
    public static StageOptions getOptions(String name){
        switch(name){
            case "login":
                return loginStageOptions;
            case "game":
                return gameScenStageOptions;
            case "studentShow":
                return studentShowOptions;
            case "studentList":
                return studentListOptions;
            case "studentRegister":
                return studentRegisterOptions;
            case "studentEdit":
                return studentEditOptions;
            default:
                return defaultStageOptions;
        }
    }
    public static boolean isAction(Event event){
        if(event == null) return false;

        String eventType = event.getEventType().toString();
        if(eventType.equals("MOUSE_CLICKED") || eventType.equals("ACTION")){
            return true;
        }
        if(eventType.equals("KEY_RELEASED") && ((KeyEvent) event).getCode().toString().equals("ENTER")){
            return true;
        }
        return false;
    }
}
