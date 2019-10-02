package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudentDialog extends Dialog {

    Connection con = DBConnector.getConnection();

    @FXML
    private Button add_student_btn;

    @FXML
    private TextField input_first_name;

    @FXML
    private TextField input_last_name;

    @FXML
    private ComboBox combo_age;

    @FXML
    public void initialize() {
        combo_age.getItems().removeAll(combo_age.getItems());
        combo_age.getItems().addAll("6","7","8","9","10","11","12","13","14","15");
    }

    public int addStudent() {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Student(firstName, lastName, age) VALUES (?, ?, ?)");
            stmt.setString(1, input_first_name.getText());
            stmt.setString(2, input_last_name.getText());
            stmt.setInt(3, Integer.valueOf((String) combo_age.getValue()));

//            if (stmt.executeUpdate() == 1) System.out.println(fN + " added successfully! You can try listing" +
//                    "the students again to see the changes\n") ;
            int result = stmt.executeUpdate();
            add_student_btn.getScene().getWindow().hide();
            con.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        add_student_btn.getScene().getWindow().hide();
        return -1;
    }

}
