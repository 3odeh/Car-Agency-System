package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPane extends BorderPane {

	public LoginPane(Stage stage, MyDoubleLinkedList data) {
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(5);
		gp.setVgap(10);

		Label errorLabel = new Label();
		errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label nameLabel = new Label("Name : ");
		TextField nameTF = new TextField();
		Label phoneNumberLabel = new Label("Phone Number : ");
		TextField phoneNumberTF = new TextField();
		Button continueBtn = new Button("Continue");
		Button adminBtn = new Button("Continue as Admin");
		continueBtn.setPrefWidth(300);
		adminBtn.setPrefWidth(300);

		gp.add(nameLabel, 1, 1);
		gp.add(nameTF, 2, 1);
		gp.add(phoneNumberLabel, 1, 2);
		gp.add(phoneNumberTF, 2, 2);
		gp.add(errorLabel, 1, 3, 2, 3);
		gp.add(continueBtn, 1, 6, 2, 6);
		gp.add(adminBtn, 1, 9, 2, 9);

		continueBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				String name = nameTF.getText();
				String phone = phoneNumberTF.getText();
				if (name != null && !name.isEmpty()) {
					if (name.equals("admin")) {
						HomePane homePane = new HomePane(data, stage);
						Scene homeScene = new Scene(homePane);
						stage.setScene(homeScene);
						return;
					}
					if (phone != null && !phone.isEmpty()) {
						Customer customer = new Customer(name, phone);
						HomePane homePane = new HomePane(data, stage, customer);
						Scene homeScene = new Scene(homePane);
						stage.setScene(homeScene);
						return;

					} else {
						errorLabel.setText("Please enter your phone number");
					}
				} else {
					errorLabel.setText("Please enter your name");
				}

			}
		});

		adminBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(data, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
				return;
			}
		});
		this.setCenter(gp);
	}

}
