package application;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class OrderPane extends BorderPane {
	private Order order;

	public OrderPane(MyDoubleLinkedList list, Stage stage) {
		// The top of the Screen (border pane)
		Button backBtn = new Button("Back");

		Button loadOrdersBtn = new Button("Load Orders");
		Button saveOrdersBtn = new Button("Save Orders");

		HBox topHbox = new HBox(loadOrdersBtn, saveOrdersBtn);
		topHbox.setAlignment(Pos.CENTER);
		topHbox.setSpacing(30);

		VBox topVbox = new VBox(backBtn, topHbox);
		this.setMargin(topVbox, new Insets(30));
		this.setTop(topVbox);
		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.setScene(new Scene(new HomePane(list, stage)));
			}
		});

		FileChooser loadFileChooser = new FileChooser();
		loadFileChooser.setTitle("Select file");
		loadFileChooser.getExtensionFilters().addAll(new ExtensionFilter("All", "*.csv", "*.txt"),
				new ExtensionFilter("Text File", "*.txt"), new ExtensionFilter("Excel File", "*.csv"));

		loadOrdersBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = loadFileChooser.showOpenDialog(stage);
				if (file != null && file.canRead()) {
					loadOrdersBtn.setText(file.getName());
					try {

						int errorCount = 0;
						Scanner scanner = new Scanner(file);
					
						MyStack<Order> tmpStack = new MyStack<>();
						while (!Main.finished.isEmpty()) {
							Order o = Main.finished.pop();
							tmpStack.push(o);
						}

						MyQueue<Order> tmpQueue = new MyQueue<>();
						while (scanner.hasNext()) {
							String[] line = scanner.nextLine().split(",");
							if (line.length != 9) {
								errorCount++;
								continue;
							}

							try {
								Customer customer = new Customer(line[0].trim(), line[1].trim());
								Car car = new Car(line[3].trim(), Short.parseShort(line[4].trim()), line[5].trim(),
										line[6].trim());

								Order order = new Order(customer, car, line[2].trim(), line[7].trim(),
										line[8].trim().equalsIgnoreCase("Finished"));

								if (order.isFinished()) {
									Main.finished.push(order);
								} else {
									tmpQueue.enqueue(order);
								}
							} catch (Exception e) {
								errorCount++;
							}
						}
						scanner.close();

						while (!tmpStack.isEmpty() || !Main.orders.isEmpty()) {
							if (!tmpStack.isEmpty()) {
								Order o = tmpStack.pop();
								Main.finished.push(o);
							}
							if (!Main.orders.isEmpty()) {
								tmpQueue.enqueue(Main.orders.dequeue());
							}
						}
						Main.orders = tmpQueue;

						if (errorCount != 0)
							GeneralPanes.warningMessage("There are " + errorCount + " orders did not added");

						initPane();
					} catch (Exception e) {
						GeneralPanes.errorAlert("Error");
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});

		FileChooser saveFileChooser = new FileChooser();
		saveFileChooser.setTitle("Save File");
		saveFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text File", "*.txt"),
				new ExtensionFilter("Excel File", "*.csv"));
		saveOrdersBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				File file = saveFileChooser.showSaveDialog(stage);
				if (file != null) {
					try {
						if (!file.exists())
							file.createNewFile();
						
						MyQueue<Order> tmpQueue = new MyQueue<>();
						MyStack<Order> tmpStack = new MyStack<>();
						PrintWriter pw = new PrintWriter(file);
						
						
						while (!Main.finished.isEmpty() || !Main.orders.isEmpty()) {
							if (!Main.finished.isEmpty()) {
								Order o = Main.finished.pop();
								tmpStack.push(o);
								pw.println(o.getInfo());
							}
							if (!Main.orders.isEmpty()) {
								Order o = Main.orders.dequeue();
								tmpQueue.enqueue(o);
								pw.println(o.getInfo());
							}
						}
						pw.close();
						while (!tmpStack.isEmpty()) {
							Main.finished.push(tmpStack.pop());
						}
						
						 Main.orders = tmpQueue;

					} catch (Exception e) {
						GeneralPanes.errorAlert(e.getMessage());
					}
				} else {
					GeneralPanes.warningMessage("No selected file");
				}

			}
		});

		initPane();

	}

	private void initPane() {
		TableView<Order> tv = new TableView<Order>();
		this.setCenter(tv);
		this.setMargin(tv, new Insets(0, 30, 0, 30));
		initTabelView(tv);
		tv.setPrefHeight(100);
		tv.setMaxHeight(270);
		Label emptyFinishedLabel = new Label("No Finished Order");
		emptyFinishedLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		tv.setPlaceholder(emptyFinishedLabel);

		GridPane gp = new GridPane();
		this.setBottom(gp);
		this.setMargin(gp, new Insets(20));
		gp.setAlignment(Pos.TOP_CENTER);

		if (Main.orders.isEmpty()) {
			Label errorLabel = new Label("No order");
			errorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
			gp.add(errorLabel, 0, 0);
		} else {

			order = Main.orders.getFont();
		
			insertInfo(order, gp);
			Button acceptBtn = new Button("Accept Order");
			acceptBtn.setPrefWidth(150);
			acceptBtn.setPrefHeight(40);
			Button cancelBtn = new Button("Cancel Order");
			cancelBtn.setPrefWidth(150);
			cancelBtn.setPrefHeight(40);
			Button processBtn = new Button("In Process Order");
			processBtn.setPrefWidth(150);
			processBtn.setPrefHeight(40);
			gp.add(acceptBtn, 6, 2);
			gp.add(cancelBtn, 6, 5);
			gp.add(processBtn, 6, 7);

			if (order.getCar().getRepeat() <= 0) {
				acceptBtn.setDisable(true);
			} else {
				acceptBtn.setDisable(false);
			}

			acceptBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Main.finished.push(order);
					tv.getItems().add(0, order);
					if (tv.getItems().size() > 10)
						tv.getItems().remove(10);
					order.getCar().decrementCountRepeat();
					Main.orders.dequeue();
					order = Main.orders.getFont();
					if (order != null) {
						gp.getChildren().clear();
						gp.add(acceptBtn, 6, 2);
						gp.add(cancelBtn, 6, 5);
						gp.add(processBtn, 6, 7);
						insertInfo(order, gp);
						if (order.getCar().getRepeat() <= 0) {
							acceptBtn.setDisable(true);
						} else {
							acceptBtn.setDisable(false);
						}
					} else {
						gp.getChildren().clear();
						Label errorLabel = new Label("No order");
						errorLabel.setStyle(
								"-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
						gp.add(errorLabel, 0, 0);
					}
				}
			});

			cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					order = Main.orders.dequeue();
					if (order != null) {
						gp.getChildren().clear();
						gp.add(acceptBtn, 6, 2);
						gp.add(cancelBtn, 6, 5);
						gp.add(processBtn, 6, 7);
						insertInfo(order, gp);
					} else {
						gp.getChildren().clear();
						Label errorLabel = new Label("No order");
						errorLabel.setStyle(
								"-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
						gp.add(errorLabel, 0, 0);
					}

				}
			});

			processBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					Main.orders.enqueue(order);
					order = Main.orders.dequeue();

					if (order != null) {
						gp.getChildren().clear();
						gp.add(acceptBtn, 6, 2);
						gp.add(cancelBtn, 6, 5);
						gp.add(processBtn, 6, 7);
						insertInfo(order, gp);
					} else {
						gp.getChildren().clear();
						Label errorLabel = new Label("No order");
						errorLabel.setStyle(
								"-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
						gp.add(errorLabel, 0, 0);
					}

				}
			});
		}

	}

	private void initTabelView(TableView<Order> tv) {
		TableColumn<Order, String> nameCol = new TableColumn<Order, String>("Customer Name");
		// This to customize the insert data to the name column
		nameCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCustomer().getName());
					}
				});
		nameCol.setSortable(false);

		TableColumn<Order, String> phoneNumberCol = new TableColumn<Order, String>("Customer Mobile");
		// This to customize the insert data to the name column
		phoneNumberCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCustomer().getMobileNumber());
					}
				});
		phoneNumberCol.setSortable(false);

		TableColumn<Order, String> modelCol = new TableColumn<Order, String>("Model");
		// This to customize the insert data to the name column
		modelCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCar().getModel());
					}
				});
		modelCol.setSortable(false);

		// Make a column age of martyr
		TableColumn<Order, String> colorCol = new TableColumn<Order, String>("Color");
		// This to customize the insert data to the age column
		colorCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCar().getColor());
					}
				});
		colorCol.setSortable(false);

		// Make a date of death column
		TableColumn<Order, String> priceCol = new TableColumn<Order, String>("Price");
		// This to customize the insert data to the date of death column
		priceCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCar().getPrice());
					}
				});
		// This to handle update the date of death of martyr
		priceCol.setSortable(false);

		// Make a gender column
		TableColumn<Order, String> yearCol = new TableColumn<Order, String>("Year");
		yearCol.setSortable(false);
		// This to customize the insert data to the gender column
		yearCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						return new SimpleStringProperty(data.getValue().getCar().getYear() + "");
					}
				});

		// Make a location column
		TableColumn<Order, String> brandCol = new TableColumn<Order, String>("Brand");
		brandCol.setSortable(false);
		// This to customize the insert data to the location column
		brandCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(data.getValue().getBrand());
					}
				});

		// Make a location column
		TableColumn<Order, String> dateCol = new TableColumn<Order, String>("Order Date");
		dateCol.setSortable(false);
		// This to customize the insert data to the location column
		dateCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Order, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Order, String> data) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(data.getValue().getSimpleDate());
					}
				});

		// Add all columns to the tabel view
		tv.getColumns().addAll(nameCol, phoneNumberCol, brandCol, modelCol, colorCol, priceCol, yearCol, dateCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(false);

		MyStack<Order> tmpStack = new MyStack<>();
		for (int i = 0; i < 10; i++) {
			if (Main.finished.isEmpty())
				break;
			Order tmp = Main.finished.pop();
			tmpStack.push(tmp);
			tv.getItems().add(tmp);
		}
		while (!tmpStack.isEmpty()) {
			
			Main.finished.push(tmpStack.pop());
		}

	}

	private void insertInfo(Order order, GridPane gp) {

		Label customerLabel = new Label("Customer :");
		customerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label nameCustomerLabel = new Label(order.getCustomer().getName());
		nameCustomerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label phoneNumberCustomerLabel = new Label(order.getCustomer().getMobileNumber());
		phoneNumberCustomerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		gp.add(customerLabel, 1, 1);
		gp.add(nameCustomerLabel, 2, 2);
		gp.add(phoneNumberCustomerLabel, 4, 2);

		Label carLabel = new Label("Car :");
		carLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label brandCarLabel = new Label("Brand : " + order.getBrand());
		brandCarLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label modelCarLabel = new Label("Model : " + order.getCar().getModel());
		modelCarLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label colorCarLabel = new Label("Color : " + order.getCar().getColor());
		colorCarLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		Label priceCarLabel = new Label("Price : " + order.getCar().getPrice());
		priceCarLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label yearCarLabel = new Label("Year : " + order.getCar().getYear());
		yearCarLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		Label availableCarLabel = new Label("Available : " + order.getCar().getCountRepeat());
		availableCarLabel.setStyle(((order.getCar().getRepeat() <= 0) ? "-fx-text-fill:red;" : "")
				+ "-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		gp.add(carLabel, 1, 4);
		gp.add(brandCarLabel, 2, 5);
		gp.add(modelCarLabel, 4, 5);
		gp.add(priceCarLabel, 2, 6);
		gp.add(yearCarLabel, 4, 6);
		gp.add(colorCarLabel, 2, 7);
		gp.add(availableCarLabel, 4, 7);

		Label dateOrderLabel = new Label(order.getSimpleDate() + "    ");
		dateOrderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		gp.add(dateOrderLabel, 3, 9);
		gp.setMargin(dateOrderLabel, new Insets(12));

		Pane emptyPane = new Pane();
		emptyPane.setPrefWidth(200);

		gp.add(emptyPane, 5, 2);

	}

}
