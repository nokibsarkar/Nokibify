package src;

public class StageOptions {
    public double width;
    public double height;
    public boolean resizable;
    public String title;
    public StageOptions(double width, double height, boolean resizable, String title){
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.title = title;
    }
    public StageOptions(double width, double height, boolean resizable){
        this.width = width;
        this.height = height;
        this.resizable = resizable;
        this.title = "Default title";
    }
    public StageOptions(double width, double height){
        this.width = width;
        this.height = height;
        this.resizable = true;
        this.title = "Default Resizable title ";
    }
    public StageOptions(){
        this.width = 600;
        this.height = 400;
        this.resizable = true;
        this.title = "Default title with default size";
    }
}