package root;

/*
 * This program would create an window with java swing
 * The window consists of login username input and password input
 * When the user click the login button, the program would check the username and password
 * using sha256 algorithm
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.imageio.*;
import java.io.*;
import java.util.concurrent.Callable;
import java.awt.image.BufferedImage;

public class Login extends JFrame implements ActionListener {
    private JLabel usernameLabel = new JLabel("Username : ", SwingConstants.RIGHT);
    private JLabel passwordLabel = new JLabel("Password : ", SwingConstants.RIGHT);
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("<html><p style='text-align:center'>Login</p></html>");
    private final String username = "RuIT";
    private final String password = "RuIT";
    private JLabel returnMessage = new JLabel("Both the username and password is " + username, SwingConstants.CENTER);
    /**
     *
     */
    private JFrame parent;
    private Container contentPan;
    private Callable<String> loginCallback;
    private Border padding = BorderFactory.createEmptyBorder(50, 50, 50, 50);
    public void registerLoginCallback(Callable<String> loginCallback){
        this.loginCallback = loginCallback;
    }
    private void login(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
           System.out.println("Login Success");
           try{
              this.loginCallback.call();
           }catch(Exception e){
               System.out.println("Error");
           }
       }else{
           System.out.println("Login Failed");
           String response = "Sorry, your login has been failed";
           this.showResponse(response, false);
           this.cancel();
       }
    }
    private void cancel(){
        System.out.println("Cancel");
    }
    private void showResponse(String response, boolean success){
        if(success){
            this.returnMessage.setText(response);
            this.returnMessage.setForeground(Color.GREEN);
        }else{
            this.returnMessage.setText(response);
            this.returnMessage.setForeground(Color.RED);
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent e){
        String username = this.usernameField.getText();
        char[] password = this.passwordField.getPassword();
        this.login(username, String.valueOf(password));

    }
    private void addLogo(){
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("root/RUET_logo.svg.png"));
            ImageIcon ico = new ImageIcon(myPicture.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
            contentPan.add(new JLabel(ico));
            Main.logger.log(MyLogger.LogLevel.INFO, "Logo Added");
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Main.logger.log(MyLogger.LogLevel.WARNING, "Logo not found : " + e.getMessage());
        }
    }
    private void addTextfields(){
        JPanel internalPanel = new JPanel(new GridLayout(0, 2));
        //internalPanel.setBorder(this.padding);
        internalPanel.add(this.usernameLabel);
        internalPanel.add(this.usernameField);
        internalPanel.add(this.passwordLabel);
        internalPanel.add(this.passwordField);
        contentPan.add(internalPanel);
    }
    private void createGUI(){
        GridLayout layout = new GridLayout(0, 1);
        contentPan.setLayout(layout);
        addLogo();
        contentPan.add(this.returnMessage);
        addTextfields();
        this.loginButton.setBorder(padding);
        contentPan.add(this.loginButton);
    }
    Login(JFrame parent){
        super();
        this.parent = parent;
        contentPan = parent.getContentPane();
        parent.setResizable(false);
        this.loginButton.addActionListener(this);
        Main.logger.log(MyLogger.LogLevel.INFO, "Action listener for login button added");
        createGUI();
        Main.logger.log(MyLogger.LogLevel.INFO, "Login Window Created");
        // parent.addWindowListener(new WindowAdapter(){
        //     public void windowClosing(WindowEvent e){
        //         close();
        //     }
        // });
    }
    public void close(){
        contentPan.removeAll();
        parent.setVisible(false);
        parent.setResizable(true);
        Main.logger.log(MyLogger.LogLevel.INFO, "Login Window Closed");
        parent.removeWindowListener(null);
    }
   
}