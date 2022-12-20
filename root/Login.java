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
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

import java.security.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.Callable;
import java.awt.image.BufferedImage;

public class Login extends JFrame implements ActionListener {
    private JLabel usernameLabel = new JLabel("Username : ", SwingConstants.RIGHT);
    private JLabel passwordLabel = new JLabel("Password : ", SwingConstants.RIGHT);
    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("<html><p style='text-align:center'>Login</p></html>");
    private JLabel returnMessage = new JLabel("Return Message", SwingConstants.CENTER);
    /**
     *
     */
    private Callable<String> loginCallback;
    private Border padding = BorderFactory.createEmptyBorder(50, 50, 50, 50);
    public void registerLoginCallback(Callable<String> loginCallback){
        this.loginCallback = loginCallback;
    }
    private void login(String username, String password){
        if(username.equals("admin") && password.equals("admin")){
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
    Login(){
        super();
        Main.logger.log(MyLogger.LogLevel.INFO, "Login Window Created");
        Container contentPan = this.getContentPane();
        this.loginButton.setMaximumSize(new Dimension(40, 40));
        Main.logger.log(MyLogger.LogLevel.INFO, "Login Window Created");
        this.loginButton.addActionListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GridLayout layout = new GridLayout(0, 1);
        this.setLayout(layout);
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File("/home/nokib/Data/Projects/Personal/OOP-Lab/Nokibify/root/RUET_logo.svg.png"));
            ImageIcon ico = new ImageIcon(myPicture.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
            contentPan.add(new JLabel(ico));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        contentPan.add(this.returnMessage);
        
        JPanel internalPanel = new JPanel(new GridLayout(0, 2));
        //internalPanel.setBorder(this.padding);
        
        internalPanel.add(this.usernameLabel);
        internalPanel.add(this.usernameField);
        internalPanel.add(this.passwordLabel);
        internalPanel.add(this.passwordField);

        this.loginButton.setBorder(padding);
        contentPan.add(internalPanel);
        this.setSize(500, 500);
        contentPan.add(this.loginButton);
        
        this.setResizable(false);;
    }
    public void close(){
        this.setVisible(false);
        System.out.println("Closed");
    }
   
}