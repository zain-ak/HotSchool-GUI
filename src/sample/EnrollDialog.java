package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EnrollDialog extends Dialog {

    private static Connection con;
    ObservableList<String> students = FXCollections.observableArrayList();
    ObservableList<String> courses = FXCollections.observableArrayList();

    ArrayList<Student> studentArrayList = new ArrayList<>();
    ArrayList<Course> courseArrayList = new ArrayList<>();

    @FXML
    public Button enroll_btn;
    @FXML
    public Button cancel_btn;

    @FXML
    public Label status_label;

    @FXML
    public ComboBox combo_select_student;

    @FXML
    public ComboBox combo_select_course;

    @FXML
    public void initialize() {
        con = DBConnector.getConnection();
        loadStudents();
        combo_select_student.getItems().removeAll(combo_select_student.getItems());
        combo_select_student.setItems(students);
        combo_select_course.setItems(courses);
    }

    @FXML
    private void enrollStudent() {

        int sID = returnStudentID((String)combo_select_student.getValue());
        int cID = returnCourseID((String)combo_select_course.getValue());

        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO StudentCourse(studentID, courseID) VALUES (?, ?)");
            stmt.setInt(1, sID);
            stmt.setInt(2, cID);

            if (stmt.executeUpdate() == 1) {
                status_label.setText("Student enrolled successfully!");
                status_label.setVisible(true);
                status_label.setTextFill(Color.GREEN);
            }
            else {
                status_label.setText("Error enrolling student");
                status_label.setVisible(true);
                status_label.setTextFill(Color.RED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() throws SQLException {
        cancel_btn.getScene().getWindow().hide();
        con.close();
    }

    private int returnStudentID(String name) {
        for (Student s: studentArrayList) {

            if (s.getName().equals(name)) {
                System.out.println(s.getName());
                return s.getId();
            }
        }
        return -1;
    }

    private int returnCourseID(String name) {
        for (Course c : courseArrayList) {
            if (c.getName().equals(name)) {
                return c.getId();
            }
        }
        return -1;
    }

    private void loadStudents() {
        if (con != null) {
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Student");
                while (rs.next()) {
                    studentArrayList.add(new Student(rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getInt("id")
                    ));
                    students.add(rs.getString("firstName") + " " + rs.getString("lastName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCourses(int sID) {
        if (con != null) {
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Course");
                if (!courses.isEmpty()) { courses.clear(); courseArrayList.clear(); }
                while (rs.next()) {
                    courseArrayList.add(new Course(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTime("startTime")
                    ));
                    courses.add(rs.getString("name"));
                }

                PreparedStatement stmt = con.prepareStatement(
                        "SELECT DISTINCT sc.courseID, c.name FROM StudentCourse sc " +
                         "JOIN Course c ON sc.courseID = c.id  " +
                         "WHERE sc.studentID = ?");
                stmt.setInt(1, sID);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    if (courses.contains((String)rs.getString("name"))) {
                        courses.remove((String)rs.getString("name"));
                        courseArrayList.remove((String)rs.getString("name"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleComboBoxAction() {
        loadCourses(returnStudentID((String)combo_select_student.getValue()));
    }
}
