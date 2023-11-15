package application;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

// This class is a home screen
public class HomePane extends BorderPane {

	// This constructor make a object and add the component to the pane
	public HomePane(MyDoubleLinkedList list, Stage stage) {

		// The top of the Screen (border pane)
		Button backBtn = new Button("Sign out");
		Label searchLabel = new Label("Search :");
		TextField searchTF = new TextField();
		Button allBtn = new Button("All");
		HBox searchHbox = new HBox(searchTF, allBtn);
		searchHbox.setSpacing(10);
		VBox searchVbox = new VBox(searchLabel, searchHbox);
		Button loadCarsBtn = new Button("Load Cars");
		Button saveCarsBtn = new Button("Save Cars");
		Button ordersBtn = new Button("Orders");
		
		HBox topHbox = new HBox(loadCarsBtn, searchVbox, saveCarsBtn,ordersBtn);
		topHbox.setMargin(ordersBtn, new Insets(0, 0, 0, 50));
		topHbox.setAlignment(Pos.CENTER);
		topHbox.setSpacing(30);

		VBox topVbox = new VBox(backBtn, topHbox);
		this.setMargin(topVbox, new Insets(30));
		this.setTop(topVbox);
		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				stage.setScene(new Scene(new LoginPane(stage, list)));

			}
		});
		
		ordersBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {		
				stage.setScene(new Scene(new OrderPane(list, stage)));				
			}
		});

		// The center of the Screen (border pane)
		GridPane gp = new GridPane();
		fillCarsData(list, gp, stage);
		ScrollPane sp = new ScrollPane(gp);
		this.setCenter(sp);

		// This file chooser to select file by user
		FileChooser loadFileChooser = new FileChooser();
		loadFileChooser.setTitle("Select file");
		loadFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All", "*.csv", "*.txt"),
				new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("Excel File", "*.csv"));

		// This action for load button, To let user select file and load the data then
		// display the location
		loadCarsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = loadFileChooser.showOpenDialog(stage);
				if (file != null && file.canRead()) {
					loadCarsBtn.setText(file.getName());
					try {

						int errorCount = list.readCar(file);
						fillCarsData(list, gp, stage);
						if (errorCount != 0)
							GeneralPanes.warningMessage("There are " + errorCount + " cars did not added");

					} catch (Exception e) {
						GeneralPanes.errorAlert("Error");
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});

	

		// This file chooser to save the file in the selected place
		FileChooser saveFileChooser = new FileChooser();
		saveFileChooser.setTitle("Save File");
		saveFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"),
				new ExtensionFilter("Excel File", "*.csv"));

		// This action for save button, To let user select file and save the data
		saveCarsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = saveFileChooser.showSaveDialog(stage);
				if (file != null) {
					try {
						if (!file.exists())
							file.createNewFile();
						list.printListToFile(file);
					} catch (Exception e) {
						GeneralPanes.errorAlert(e.getMessage());
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});
		

		// This action for search button, To search about the location name
		searchTF.setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				String text = searchTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					fillCarsDataWithSearch(list, gp, text, stage);
				} else {
					fillCarsData(list, gp, stage);
				}

			}
		});

		// This action for all button, to display all location on the center of screen
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				fillCarsData(list, gp, stage);
				searchTF.clear();
			}
		});
	}

	// This method to display all location in the center of screen
	private void fillCarsData(MyDoubleLinkedList list, GridPane gp, Stage stage) {

		// To remove all old component
		gp.getChildren().clear();

		// This for Add section (Add new location)
		Button addBtn = new Button("Add brand");
		TextField addTF = new TextField();
		Label errorLabel = new Label();
		errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox addVBox = new VBox(addTF, addBtn, errorLabel);
		addVBox.setSpacing(5);
		addBtn.setPrefHeight(40);
		addBtn.setPrefWidth(170);
		addTF.setPrefWidth(170);
		addTF.setMaxWidth(170);
		gp.setMargin(addVBox, new Insets(45, 0, 0, 45));
		gp.add(addVBox, 0, 0);

		if (!list.isEmpty()) {
			// Add all location
			list.addBrandToGP(gp, stage);
		} else {
			// no data for location
			Label emptyLabel = new Label("Empty List");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

		// This action for add button, To add new location
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = addTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					if (list.add(text)) {
						fillCarsData(list, gp, stage);
					} else {
						errorLabel.setText("Error exist brand");
					}
				} else {
					errorLabel.setText("Please enter the brand");
				}
			}
		});

	}

	// This method to search input name then display all location that start of that
	// name
	private void fillCarsDataWithSearch(MyDoubleLinkedList list, GridPane gp, String search, Stage stage) {

		// To remove all old component
		gp.getChildren().clear();

		// This for Add section (Add new location)
		Button addBtn = new Button("Add");
		TextField addTF = new TextField();
		Label errorLabel = new Label();
		errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox addVBox = new VBox(addTF, addBtn, errorLabel);
		addVBox.setSpacing(5);
		addBtn.setPrefHeight(40);
		addBtn.setPrefWidth(170);
		addTF.setPrefWidth(170);
		addTF.setMaxWidth(170);
		gp.setMargin(addVBox, new Insets(45, 0, 0, 45));
		gp.add(addVBox, 0, 0);

		if (!list.isEmpty()) {
			// Add all searched location
			list.addBrandToGP(gp, search, stage);
		} else {
			// no data for locations
			Label emptyLabel = new Label("Empty List");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

		// This action for add button, To add new location
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = addTF.getText();
				if (text != null && !text.isEmpty()) {
					if (list.add(text)) {
						text = text.strip();
						fillCarsData(list, gp, stage);
					} else {
						errorLabel.setText("Error exist brand");
					}

				} else {
					errorLabel.setText("Please enter the brand");
				}
			}
		});

	}

	// This constructor make a object and add the component to the pane
	public HomePane(MyDoubleLinkedList list, Stage stage, Customer customer) {

		// The top of the Screen (border pane)
		Button backBtn = new Button("Sign out");
		Label searchLabel = new Label("Search :");
		TextField searchTF = new TextField();
		Button allBtn = new Button("All");
		HBox searchHbox = new HBox(searchTF, allBtn);
		searchHbox.setSpacing(10);
		VBox searchVbox = new VBox(searchLabel, searchHbox);
		Button loadCarsBtn = new Button("Load Cars");
		HBox topHbox = new HBox(loadCarsBtn, searchVbox);
		topHbox.setSpacing(30);
		topHbox.setAlignment(Pos.CENTER);

		VBox topVbox = new VBox(backBtn, topHbox);
		this.setMargin(topVbox, new Insets(30));
		this.setTop(topVbox);

		// The center of the Screen (border pane)
		GridPane gp = new GridPane();
		fillCarsData(list, gp, stage, customer);
		ScrollPane sp = new ScrollPane(gp);
		this.setCenter(sp);

		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				stage.setScene(new Scene(new LoginPane(stage, list)));
			}
		});
		// This file chooser to select file by user
		FileChooser loadFileChooser = new FileChooser();
		loadFileChooser.setTitle("Select file");
		loadFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All", "*.csv", "*.txt"),
				new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("Excel File", "*.csv"));

		// This action for load button, To let user select file and load the data then
		// display the location
		loadCarsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = loadFileChooser.showOpenDialog(stage);
				if (file != null && file.canRead()) {
					loadCarsBtn.setText(file.getName());
					try {

						int errorCount = list.readCar(file);
						fillCarsData(list, gp, stage, customer);
						if (errorCount != 0)
							GeneralPanes.warningMessage("There are " + errorCount + " cars did not added");

					} catch (Exception e) {
						GeneralPanes.errorAlert("Error");
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});

		// This action for search button, To search about the location name
		searchTF.setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				String text = searchTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					fillCarsDataWithSearch(list, gp, text, stage, customer);
				} else {
					fillCarsData(list, gp, stage, customer);
				}

			}
		});

		// This action for all button, to display all location on the center of screen
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				fillCarsData(list, gp, stage, customer);
				searchTF.clear();
			}
		});
	}

	// This method to display all location in the center of screen
	private void fillCarsData(MyDoubleLinkedList list, GridPane gp, Stage stage, Customer customer) {

		// To remove all old component
		gp.getChildren().clear();
		if (!list.isEmpty()) {
			// Add all location
			list.addBrandToGP(gp, stage, customer);
		} else {
			// no data for location
			Label emptyLabel = new Label("No data, please load the data");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

	}

	// This method to search input name then display all location that start of that
	// name
	private void fillCarsDataWithSearch(MyDoubleLinkedList list, GridPane gp, String search, Stage stage,
			Customer customer) {

		// To remove all old component
		gp.getChildren().clear();

		if (!list.isEmpty()) {
			// Add all searched location
			list.addBrandToGP(gp, search, stage, customer);
		} else {
			// no data for locations
			Label emptyLabel = new Label("Empty List");
			gp.add(emptyLabel, 1, 0);
			gp.setMargin(emptyLabel, new Insets(45));
		}

	}

}
