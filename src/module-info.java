module DS_Project2 {
	requires javafx.controls;
	requires javafx.graphics;

	opens application to javafx.graphics, javafx.fxml,javafx.base;
}
