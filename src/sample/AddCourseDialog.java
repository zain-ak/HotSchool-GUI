package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class AddCourseDialog extends Dialog {

    Connection con = DBConnector.getConnection();

    @FXML
    private Button add_course_btn;

    @FXML
    private TextField input_course_name;

    @FXML
    private ComboBox combo_time;

    @FXML
    public void initialize() {
        combo_time.getItems().removeAll(combo_time.getItems());
        combo_time.getItems().addAll("9:00","10:00","11:00","12:00","13:00",
                                                "14:00","15:00","16:00");
    }

    public int addCourse() {

        System.out.println(combo_time.getValue());
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Course(name, startTime) VALUES (?, ?)");
            stmt.setString(1, input_course_name.getText());
            stmt.setTime(2, Time.valueOf((String)combo_time.getValue() + ":00"));

//            if (stmt.executeUpdate() == 1) System.out.println(fN + " added successfully! You can try listing" +
//                    "the students again to see the changes\n") ;
            int result = stmt.executeUpdate();
            add_course_btn.getScene().getWindow().hide();
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        add_course_btn.getScene().getWindow().hide();
        return -1;
    }


}
