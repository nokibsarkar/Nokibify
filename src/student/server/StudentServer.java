package src.student.server;

import java.io.*;
import java.util.TreeMap;

import src.MyLogger;

public class StudentServer {
    private static final String file = "src/student/student.ser";
    private final static TreeMap<Integer, Student> students = new TreeMap<Integer, Student>();
    public static int studentCount = 0;
    
    public static TreeMap<Integer, Student> readStudents() {
        // Open the file
        if (students.isEmpty()) {
            System.out.println("Reading from the file");
            TreeMap<Integer, Student> temp = new TreeMap<Integer, Student>();
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                // Read the file into a TreeMap of students where the key is the roll number
                temp = (TreeMap<Integer, Student>) in.readObject();
                in.close();
                fileIn.close();
            } catch (ClassNotFoundException | IOException i) {
                temp = new TreeMap<Integer, Student>();
                MyLogger.log(i);
            }
            students.putAll(temp);
        }
        // Return the students TreeMap
        return students;
    }
    public static Student getStudent(int roll) {
        // Open the file
        readStudents();
        return students.get(roll);
    }
    private static void writeStudents() {
        // Open the file
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(students);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + file);
        } catch (IOException i) {
            MyLogger.log(i);
        }
    }
    public static void addStudent(Student student) {
        // Open the file
        readStudents();
        students.putIfAbsent(student.getRoll(), student);
        studentCount++;
        writeStudents();
    }
    public static void updateStudent(Student student) {
        // Open the file
        readStudents();
        students.put(student.getRoll(), student);
        writeStudents();
    }
    public static void deleteStudent(int roll) {
        // Open the file
        readStudents();
        students.remove(roll);
        studentCount--;
        writeStudents();
    }
}
