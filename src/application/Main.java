package application;

import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	public static MyQueue<Order> orders = new MyQueue<>();
	public static MyStack<Order> finished = new MyStack<>();

	@Override
	public void start(Stage stage) {
		try {
			
			MyDoubleLinkedList data = new MyDoubleLinkedList();
			LoginPane loginPane = new LoginPane(stage, data);
			
			Scene scene = new Scene(loginPane);
			stage.setScene(scene);
			stage.setHeight(700);
			stage.setWidth(1400);
			stage.setTitle("Car Agency System");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			GeneralPanes.errorAlert("Error!");
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
