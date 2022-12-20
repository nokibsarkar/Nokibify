package root;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import root.server.Student;
import root.server.StudentServer;

import java.awt.*;
import java.util.TreeMap;

public class StudentList {
    private final JTable table = new JTable();
    private JFrame parent;
    private final String[] columnNames = { "Roll", "Name", "Address", "CGPA", "Department", "Session", "Semester",
            "Email" };
    private final JButton sendResulButton = new JButton("Send Result");
    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private WindowAdapter focusAdapter;

    private void addColumns() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);
    }

    private DefaultTableModel showTable() {
        addColumns();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TreeMap<Integer, Student> students = StudentServer.readStudents();
        for (Student student : students.values()) {
            model.addRow(new Object[] {
                    student.getRoll(),
                    student.getName(),
                    student.getAddress(),
                    student.getCgpa(),
                    student.getDepartment(),
                    student.getSession(),
                    student.getSemester(),
                    student.getEmail()
            });
        }
        return model;
    }

    private void onAddButtonClicked() {
        StudentForm studentForm = new StudentForm(parent);
        studentForm.setVisible(true);
        studentForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentForm.addWindowListener(focusAdapter);
    }
    private void onEditButtonClicked() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent, "Please select a row first");

            return;
        }
        int roll = (int) table.getValueAt(selectedRow, 0);
        Student student = StudentServer.getStudent(roll);
        if (student == null) {
            JOptionPane.showMessageDialog(parent, "Student not found");
            return;
        }
        StudentForm studentForm = new StudentForm(parent);
        studentForm.setUpdate(roll);
        studentForm.setVisible(true);
        studentForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentForm.addWindowListener(focusAdapter);
    }
    private void onDeleteButtonClicked() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent, "Please select a row first");

            return;
        }
        int roll = (int) table.getValueAt(selectedRow, 0);
        Student student = StudentServer.getStudent(roll);
        if (student == null) {
            JOptionPane.showMessageDialog(parent, "Student not found");
            return;
        }
        int dialogResult = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete this student?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            StudentServer.deleteStudent(roll);
            showTable();
        }
    }
    private void onSendResultButtonClicked() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parent, "Please select a row first");

            return;
        }
        int roll = (int) table.getValueAt(selectedRow, 0);
        Student student = StudentServer.getStudent(roll);
        if (student == null) {
            JOptionPane.showMessageDialog(parent, "Student not found");
            return;
        }
        JOptionPane.showMessageDialog(parent, student.getResult());
    }

    private void attatchListeners() {
        addButton.addActionListener(e -> onAddButtonClicked());
        sendResulButton.addActionListener(e -> onSendResultButtonClicked());
        editButton.addActionListener(e -> onEditButtonClicked());
        deleteButton.addActionListener(e -> onDeleteButtonClicked());
    }

    StudentList(JFrame parent) {
        this.parent = parent;
        Container contentPan = parent.getContentPane();
        contentPan.removeAll();
        showTable();
        focusAdapter = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                showTable();
            }
        };
        attatchListeners();
        
        contentPan.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        contentPan.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(addButton);
        panel.add(sendResulButton);
        contentPan.add(panel, BorderLayout.SOUTH);
        parent.setVisible(true);
    }
}
