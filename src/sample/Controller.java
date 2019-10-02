package sample;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Connection con = DBConnector.getConnection();
    public Button add_student_btn;
    public Button quitBtn;

    @FXML
    private TableView<Student> student_table;

    @FXML
    private TableColumn<Student, Integer> col_student_id;

    @FXML
    private TableColumn<Student, String> col_first_name;

    @FXML
    private TableColumn<Student, String> col_last_name;

    @FXML
    private TableColumn<Student, String> col_age;

    @FXML
    private TableView<Course> course_table;

    @FXML
    private TableColumn<Course, Integer> col_course_id;

    @FXML
    private TableColumn<Course, Time> col_start_time;

    @FXML
    private TableColumn<Course, String> col_course_name;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (con != null) {
            loadCourses();
            loadStudents();
        }

        col_course_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_start_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_course_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        col_student_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("age"));

        student_table.setItems(students);
        course_table.setItems(courses);

        student_table.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    //TODO: Show enrolled courses.
                }
            });
            return row;
        });

        course_table.setRowFactory(tv -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    //TODO: Show enrolled students.
                    System.out.println("This is gay man");
                }
            });
            return row;
        });
    }



    private void loadStudents() {
        if (con != null) {
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Student");
                if (!students.isEmpty()) {
                    students.removeAll(students);
                }
                while (rs.next()) {
                    students.add(new Student(rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getInt("age"),
                            rs.getInt("id")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCourses() {
        if (con != null) {
            try {
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Course");
                if (!courses.isEmpty()) {
                    courses.removeAll(courses);
                }
                while (rs.next()) {
                    courses.add(new Course(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTime("startTime")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addStudent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_student_dialog.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add a New Student");
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHiding(event -> {
                loadStudents();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addCourse() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add_course_dialog.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add a New Course");
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHiding(event -> {
                loadCourses();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void enrollStudent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("enroll_dialog.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Enroll a Student");
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHiding(event -> {
                loadStudents();

                course_table.setItems(courses);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unenrollStudent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("unenroll_dialog.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Un-enroll a Student");
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHiding(event -> {
                loadStudents();

                course_table.setItems(courses);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refresh() {
        loadStudents();
        loadCourses();
        System.out.println("Yay");
    }

    @FXML
    private void quitApplication() throws SQLException {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
        con.close();
    }
}
