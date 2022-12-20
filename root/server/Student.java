package root.server;

import java.io.Serializable;

public class Student implements Serializable {
    private int roll;
    private String name;
    private String address;
    private String cgpa;
    private String department;
    private String session;
    private String semester;
    private String email;
    public Student(int roll, String name, String address, String cgpa, String department, String session, String semester, String email){
        this.roll = roll;
        this.name = name;
        this.address = address;
        this.cgpa = cgpa;
        this.department = department;
        this.session = session;
        this.semester = semester;
        this.email = email;
    }
    public int getRoll() {
        return roll;
    }
    public void setRoll(int roll) {
        this.roll = roll;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCgpa() {
        return cgpa;
    }
    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    private String getResult(){
        String message = "Roll: " + roll + " got " + cgpa + " CGPA in " + semester + " semester of " + session + " session";
        return message;
    }
    public void sendResult(){
        System.out.println(getResult());
    }
}
