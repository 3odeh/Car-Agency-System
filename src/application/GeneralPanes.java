package application;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// This class contain pane methods to user it in other classes 
public class GeneralPanes {

	// This method return the location pane have the name of location and 2 buttons
	// for statistic and info of the location
	public static GridPane brandPane(DoubleNode current, Stage stage, MyDoubleLinkedList cu) {

		GridPane gp = new GridPane();
		Button infoBtn = new Button("More");
		infoBtn.setPrefWidth(100);
		Label name = new Label((current == null) ? "All" : current.getBrand());
		name.setStyle("-fx-font-weight: bold; -fx-font-size: 18;-fx-alignment:CENTER;");
		name.setMaxWidth(Double.MAX_VALUE);
		name.setAlignment(Pos.CENTER);
		infoBtn.setAlignment(Pos.CENTER);
		gp.add(name, 1, 1);
		gp.add(infoBtn, 1, 2);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(15);
		gp.setHgap(15);
		gp.setPadding(new Insets(30));
		infoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (current == null) {
					BrandPane lp = new BrandPane(cu, stage);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				} else {
					BrandPane lp = new BrandPane(cu, current, stage);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				}
			}
		});
	
		return gp;
	}
	
	public static GridPane brandPane(DoubleNode current, Stage stage, MyDoubleLinkedList cu,Customer customer) {

		GridPane gp = new GridPane();
		Button infoBtn = new Button("More");
		infoBtn.setPrefWidth(100);
		Label name = new Label((current == null) ? "All" : current.getBrand());
		name.setStyle("-fx-font-weight: bold; -fx-font-size: 18;-fx-alignment:CENTER;");
		name.setMaxWidth(Double.MAX_VALUE);
		name.setAlignment(Pos.CENTER);
		infoBtn.setAlignment(Pos.CENTER);
		gp.add(name, 1, 1);
		gp.add(infoBtn, 1, 2);
		gp.setAlignment(Pos.CENTER);
		gp.setVgap(15);
		gp.setHgap(15);
		gp.setPadding(new Insets(30));
		infoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (current == null) {
					BrandPane lp = new BrandPane(cu, stage,customer);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				} else {
					BrandPane lp = new BrandPane(cu, current, stage,customer);
					Scene scene = new Scene(lp);
					stage.setScene(scene);
				}
			}
		});
	
		return gp;
	}

	// This method have alert stage to show error message to the user
	public static void errorAlert(String message) {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText(message);
		a.showAndWait();
	}
	
	// This method have alert stage to show error message to the user
		public static void completAlert(String message) {
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setContentText(message);
			a.showAndWait();
		}

	// This method have warning stage to show warning message to the user
	public static boolean warningMessage(String string) {
		Alert a = new Alert(AlertType.WARNING);
		a.setContentText(string);
		Optional<ButtonType> result = a.showAndWait();
		return (result.get() == ButtonType.OK);

	}
}
