package root;
import java.io.IOException;

/*
 * This program would create an window with java swing
 * The window consists of 
 */
import javax.swing.*;
import root.Login;
class Main extends JFrame{
    public static MyLogger logger ;
    public String authorString = "Nazmul Haque Naqib";
    public String versionString = "1.0";
    Main(){
        try {
            logger = new MyLogger();
            logger.log(MyLogger.LogLevel.INFO, "Author: " + authorString);
        } catch (SecurityException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setLocationRelativeTo(null);
    }
    private void showStudents(){
        System.out.println("Showing students");
        StudentForm studentForm = new StudentForm(this);
    }
    protected void finalize(){
        System.out.println("Exit");
        logger.log(MyLogger.LogLevel.INFO, "Program Terminated");
        logger.close();
    }
    public static void main(String args[]){
        Main main = new Main();
        Login login = new Login(main);
        login.setLocationRelativeTo(null);
        login.registerLoginCallback(() -> {
            System.out.println("From callback");
            login.close();
            main.showStudents();
            return "Success";
        });
        main.setSize(500, 500);
        main.setVisible(true);
    }
}