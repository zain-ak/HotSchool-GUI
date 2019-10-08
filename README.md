# HotSchool GUI
HotSchool is a GUI application created using JavaFX & SceneBuilder, expanded on from an existing college assignment that ran only the console. This was my first
attempt at creating a desktop GUI application.

## Features
- The application is linked to a MySQL backend using JDBC and MySQL Connector. The front tables (`TableViews`) are loaded from the database dynamically, and whenever any changes are made to the database, they are updated through the `ObservableList`. A refresh button was added so that the latest changes can be seen in case any changes are made on the back-end;
- Table items can be clicked to bring up an information card for any given course or student. These cards include a `ListView` to view enrolled students/courses;
- The *Add Student* and *Add Course* buttons allow for user-created students/courses. The user will know if the changes are successful as the `TableView` will immediately reflect the changes on the main window:
  <br>
  #### Adding a Student
  <img src="AddingStudent.gif" width="700" /> 
  <br><br>
  <h4>Adding a Course</h4> 
  <img src="AddingCourse.gif" width="700" /> <br><br>
- The *Enroll Student* and *Un-enroll Student*  buttons allow users to add or remove students from courses. User input has been made easy by loading the `ComboBox` list of courses dynamically based on the student selected in the first `ComboBox` and querying the database for what courses they're (not)enrolled in
  - A `Label` has been used in each of these dialogs if the changes made to the database are successful or not. Because of this, a *Cancel* button has also been added to close the dialog box
  <br>
  <h4>Enrolling & Unenrolling</h4> 
  <br> <img src="EnrollingUnEnrolling.gif" width="700" /> <br> 


## Remarks
There were additional features I wanted to implement but didn't due to time constraints:
- Double clicking on an item in the `TableView` to show a card displaying the information of the given course or student
  - Additional options within the card to enroll or un-enroll in from a course
- Better MVC design, I think the core program has a good MVC implementation but the dialogs are certainly lacking. *Then again, the point of this projet was really to get some exposure building an application in JavaFX along with SceneBuilder*
- Styling elements with JavaFX using FXML and SceneBuilder. Coming from an Android background, and some minimal styling this doesn't seem very challenging, it would also be interesting how CSS is integrated with FXML.

Overall, this was a good introduction to JavaFX. There's plenty of documentation online to find your way, and I do think that JavaFX is quite a powerful tool to build attractive desktop applications. I'll look to implement some project ideas in the future to build my skillset.



