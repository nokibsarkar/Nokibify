package src;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Callback;

class SceneHistory {
    enum type {
        PUSH, POP, REPLACE
    };

    type t;
    Object data;
    int sceneIndex;
}

interface SceneInterface {
    public void setName(String name);

    public String getName();

    public void setIndex(int index);

    public int getIndex();
    public void handleData(Object data);
};


public class SceneManager {
    private Stage stage;
    private WholeScene currentScene;
    private ArrayList<WholeScene> scenes;
    private HashMap<String, Integer> sceneMap;
    private ArrayList<SceneHistory> history;

    SceneManager() {
        scenes = new ArrayList<WholeScene>();
        sceneMap = new HashMap<String, Integer>();
        history = new ArrayList<SceneHistory>();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int addScene(WholeScene scene) {
        scenes.add(scene);
        sceneMap.put(scene.getName(), scenes.size() - 1);
        return scenes.size() - 1;
    }

    public int addScene(WholeScene scene, String name) {
        MyLogger.log( name);
        scenes.add(scene);
        scene.setName(name);
        sceneMap.put(name, scenes.size() - 1);
        return scenes.size() - 1;
    }

    public int addScene(WholeScene scene, String name, int index) {
        scenes.add(index, scene);
        sceneMap.put(name, index);
        return index;
    }
    private void setCurrentScene(int index, StageOptions options, Object data) {
        
        currentScene = scenes.get(index);
        MyLogger.log("Setting the scene to " + currentScene.getName());
            stage.setScene(currentScene);
            if(data != null) {
                currentScene.handleData(data);
            }
            if (options != null) {
                stage.setHeight(options.height);
                stage.setWidth(options.width);
                stage.setResizable(options.resizable);
                stage.setTitle(options.title);
            } else {
                StageOptions opts = currentScene.getStageOptions();
                stage.setHeight(opts.height);
                stage.setWidth(opts.width);
                stage.setResizable(opts.resizable);
                stage.setTitle(opts.title);
            }
            pushScene(currentScene.getName(), data);
    }
    public Callback<Void, Void> goToSceneCallback(String name, StageOptions options, Object data) {
        Callback<Void, Void> callback = (Void) -> {
            if(!sceneMap.containsKey(name)) {
                MyLogger.log("Scene " + name + " does not exist");
                return null;
            }
            int index = sceneMap.get(name);
            setCurrentScene(index, options, data);
            return null;
        };
        return callback;
    }

    // return an event handler
    public EventHandler<ActionEvent> goToSceneEventHandler(String name, StageOptions options, Object data) {
        EventHandler<ActionEvent> eventHandler = (ActionEvent) -> {
            if(!sceneMap.containsKey(name)) {
                MyLogger.log("Scene " + name + " does not exist");
                return;
            }
            int index = sceneMap.get(name);
            setCurrentScene(index, options, data);
        };
        return eventHandler;
    }

    public Callback<Void, Void> goToSceneCallback(int index, StageOptions options, Object data) {
        Callback<Void, Void> callback = (Void) -> {
            
            setCurrentScene(index, options, data);
            return null;
        };
        return callback;
    }

    public void pushScene(String name, Object data) {
        MyLogger.log( "Pushing " + name);
        int index = sceneMap.get(name);
        MyLogger.log(index);
        SceneHistory sceneHistory = new SceneHistory();
        sceneHistory.data = data;
        sceneHistory.sceneIndex = index;
        sceneHistory.t = SceneHistory.type.PUSH;
        history.add(sceneHistory);
    }

    public Callback<Void, Void> goToSceneCallback(String name) {
        return goToSceneCallback(name, null, null);
    }
    public Callback<Void, Void> goToSceneCallback(String name, Object data) {
        return goToSceneCallback(name, null, data);
    }
    public Callback<Void, Void> goToSceneCallback(String name, StageOptions options) {
        return goToSceneCallback(name, options, null);
    }
    public Callback<Void, Void> goToSceneCallback(int index) {
        return goToSceneCallback(index, null, null);
    }
    public Callback<Void, Void> goToSceneCallback(int index, Object data) {
        return goToSceneCallback(index, null, data);
    }

    public EventHandler<ActionEvent> goToSceneEventHandler(String windowName) {
        return goToSceneEventHandler(windowName, null, null);
    }
    public EventHandler<ActionEvent> goToSceneEventHandler(String windowName, Object data) {
        return goToSceneEventHandler(windowName, null, data);
    }
    public EventHandler<ActionEvent> goToSceneEventHandler(String windowName, StageOptions options) {
        return goToSceneEventHandler(windowName, options, null);
    }
}
