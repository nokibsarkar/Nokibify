package root.server;

import java.io.*;
import java.util.HashMap;

public class StudentServer {
    private static final String file = "student.ser";
    private final static HashMap<Integer, Student> students = new HashMap<Integer, Student>();
    public static int studentCount = 0;

    public static HashMap<Integer, Student> readStudents() {
        // Open the file
        if (students.isEmpty()) {
            System.out.println("Reading from the file");
            HashMap<Integer, Student> temp = new HashMap<Integer, Student>();
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                // Read the file into a hashmap of students where the key is the roll number
                temp = (HashMap<Integer, Student>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                temp = new HashMap<Integer, Student>();
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                temp = new HashMap<Integer, Student>();
                System.out.println("Student class not found");
                c.printStackTrace();
            }
            students.putAll(temp);
        }
        // Return the students hashmap
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
            System.out.printf("Serialized data is saved in " + file);
        } catch (IOException i) {
            i.printStackTrace();
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
