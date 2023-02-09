package src.student.server;
import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
    private int roll;
    private String name;
    private String address;
    private String cgpa;
    private DEPARTMENT department;
    private String session;
    private SEMESTER semester;
    private String email;
    private GENDER gender;
    private String phone;
    private LocalDate birthday;
    public static enum GENDER {
        MALE("Male"),
        FEMALE("Female");
        public final String label;
        private GENDER(String text) {
            this.label = text;
        }
        public String toString(){
            return this.label;
        }
    }
    public static enum DEPARTMENT {
        CSE("CSE"),
        EEE("EEE"),
        ECE("ECE"),
        BBA("BBA"),
        MBA("MBA"),
        LLB("LLB"),
        PHARMACY("Pharmacy");
        public String label;
        private DEPARTMENT(String text) {
            this.label = text;
        }
        public String toString(){
            return this.label;
        }
    }
    public static enum SEMESTER {
        ONE_ONE("1-1"),
        ONE_TWO("1-2"),
        TWO_ONE("2-1"),
        TWO_TWO("2-2"),
        THREE_ONE("3-1"),
        THREE_TWO("3-2"),
        FOUR_ONE("4-1"),
        FOUR_TWO("4-2");
        public String label;
        private SEMESTER(String text) {
            this.label = text;
        }
        public String toString(){
            return this.label;
        }
    }
    public Student(){

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
    public DEPARTMENT getDepartment() {
        return department;
    }
    public void setDepartment(DEPARTMENT department) {
        this.department = department;
    }
    public void setDepartment(String department) {
        this.department = DEPARTMENT.valueOf(department);
    }
    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
    public SEMESTER getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = SEMESTER.valueOf(semester);
    }
    public void setSemester(SEMESTER semester) {
        this.semester = semester;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(GENDER gender){
        this.gender = gender;
    }
    public void setGender(String gender){
        this.gender = GENDER.valueOf(gender);
    }
    public GENDER getGender(){
        return gender;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return phone;
    }
    public void setBirthday(String birthday){
        this.birthday = LocalDate.parse(birthday);
    }
    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
    }
    public LocalDate getBirthday(){
        return birthday;
    }
    public String getResult(){
        String message = "Roll: " + roll + " got " + cgpa + " CGPA in " + semester + " semester of " + session + " session";
        return message;
    }
    public void sendResult(){
        System.out.println(getResult());
    }
}
