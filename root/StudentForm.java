package root;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

import root.server.Student;
import root.server.StudentServer;

public class StudentForm extends JFrame {
    private final JTextField rollField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextField addressField = new JTextField(10);
    private final JTextField cgpaField = new JTextField(10);
    private final JTextField departmentField = new JTextField(10);
    private final JTextField sessionField = new JTextField(10);
    private final JTextField semesterField = new JTextField(10);
    private final JTextField emailField = new JTextField(10);
    private final JButton submitButton = new JButton("Submit");
    private final JButton readButton = new JButton("Read");
    private boolean isUpdate = false;
    private JFrame parent;
    private void addFields(){
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Roll:", SwingConstants.RIGHT));
        panel.add(rollField);
        panel.add(new JLabel("Name:", SwingConstants.RIGHT));
        panel.add(nameField);
        panel.add(new JLabel("Address:", SwingConstants.RIGHT));
        panel.add(addressField);
        panel.add(new JLabel("CGPA:", SwingConstants.RIGHT));
        panel.add(cgpaField);
        panel.add(new JLabel("Department:", SwingConstants.RIGHT));
        panel.add(departmentField);
        panel.add(new JLabel("Session:", SwingConstants.RIGHT));
        panel.add(sessionField);
        panel.add(new JLabel("Semester:", SwingConstants.RIGHT));
        panel.add(semesterField);
        panel.add(new JLabel("Email:", SwingConstants.RIGHT));
        panel.add(emailField);
        panel.add(readButton);
        panel.add(submitButton);
        this.getContentPane().add(panel);
    }
    private void updateFields(Student student){
        rollField.setText(String.valueOf(student.getRoll()));
        rollField.setEditable(false);
        nameField.setText(student.getName());
        addressField.setText(student.getAddress());
        cgpaField.setText(student.getCgpa());
        departmentField.setText(student.getDepartment());
        sessionField.setText(student.getSession());
        semesterField.setText(student.getSemester());
        emailField.setText(student.getEmail());
    }
    private void updateFields(int roll){
        Student student = StudentServer.getStudent(roll);
        updateFields(student);
    }
    private void update(){
        Student student = new Student(
            Integer.parseInt(rollField.getText()), 
            nameField.getText(), 
            addressField.getText(), 
            cgpaField.getText(), 
            departmentField.getText(), 
            sessionField.getText(), 
            semesterField.getText(), 
            emailField.getText()
        );
        StudentServer.updateStudent(student);
    }
    private void resetFields(){
        rollField.setText("");
        nameField.setText("");
        addressField.setText("");
        cgpaField.setText("");
        departmentField.setText("");
        sessionField.setText("");
        semesterField.setText("");
        emailField.setText("");
    }
    private void submit(){
        Student student = new Student(
            Integer.parseInt(rollField.getText()), 
            nameField.getText(), 
            addressField.getText(), 
            cgpaField.getText(), 
            departmentField.getText(), 
            sessionField.getText(), 
            semesterField.getText(), 
            emailField.getText()
        );
        StudentServer.addStudent(student);
        resetFields();
    }
    public StudentForm(JFrame parent) {
        super("Student Form");
        this.parent = parent;
        addFields();
        submitButton.addActionListener(e -> {
            
        });
        readButton.addActionListener(e -> {
            HashMap<Integer, Student> students = StudentServer.readStudents();
            // iterate through the hashmap
            for (HashMap.Entry<Integer, Student> entry : students.entrySet()) {
                Student std = entry.getValue();
                System.out.println(std.getRoll() + " " + std.getName() + " " + std.getDepartment());
            }
        });
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void setUpdate(boolean isUpdate){
        if(isUpdate){
            submitButton.setText("Update");
        }
        this.isUpdate = isUpdate;
    }
    public boolean isUpdate(){
        return this.isUpdate;
    }
    public static void main(String args[]){
        Student student = new Student(
            1, "Nazmul", 
            "Dhaka", "3.5", 
            "CSE", 
            "2017-2018", 
            "1st", "nokibsarkar@gmail.com"
        );
        StudentServer.addStudent(student);
        HashMap<Integer, Student> students = StudentServer.readStudents();
        // iterate through the hashmap
        for (HashMap.Entry<Integer, Student> entry : students.entrySet()) {
            Student std = entry.getValue();
            System.out.println(std.getRoll() + " " + std.getName() + " " + std.getDepartment());
        }
    }
}
