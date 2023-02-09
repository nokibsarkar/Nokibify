package src;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class WholeScene extends Scene implements SceneInterface {
    private String name;
    private int index;
    private StageOptions stageOptions = new StageOptions();
    public Callback<Object, Void> dataHandlerCallback;
    public WholeScene() {
        super(null);
    }
    public void setStageOptions(StageOptions stageOptions) {
        this.stageOptions = stageOptions;
    }
    public StageOptions getStageOptions() {
        return stageOptions;
    }
    public WholeScene(Parent root) {
        super(root);
    }

    public WholeScene(Parent root, double width, double height, Color color) {
        super(root, width, height, color);
        stageOptions.width = width;
        stageOptions.height = height;
        
    }

    public WholeScene(Parent root, double width, double height) {
        super(root, width, height);
        stageOptions.width = width;
        stageOptions.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    @Override
    public void handleData(Object data) {
        if (dataHandlerCallback != null) {
            dataHandlerCallback.call(data);
        }
        
    }
    public void setDataHandlerCallback(Callback<Object, Void> dataHandlerCallback) {
        this.dataHandlerCallback = dataHandlerCallback;
    }
}
