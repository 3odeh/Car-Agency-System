package application;

import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

// This class is a location screen that display the information of location
public class BrandPane extends BorderPane {

	// This constructor make a object and add the component and information of
	// selected location to the pane
	public BrandPane(MyDoubleLinkedList list, DoubleNode current, Stage stage) {

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<Car> tv = new TableView<Car>();

		// Make a column name of martyr
		TableColumn<Car, String> modeleCol = new TableColumn<Car, String>("Model");
		modeleCol.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
		modeleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		modeleCol.setSortable(false);
		// This to handle update the name of martyr
		modeleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {
			@Override
			public void handle(CellEditEvent<Car, String> event) {
				TablePosition<Car, String> pos = event.getTablePosition();
				String newMode = event.getNewValue();
				if (newMode != null && !newMode.isEmpty()) {
					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row);
					car.setModel(newMode);
				} else {
					GeneralPanes.errorAlert("Please check the model");
					tv.refresh();
				}
			}
		});

		TableColumn<Car, String> colorCol = new TableColumn<Car, String>("Color");
		colorCol.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
		colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		colorCol.setSortable(false);
		// This to handle update the name of martyr
		colorCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {
			@Override
			public void handle(CellEditEvent<Car, String> event) {
				TablePosition<Car, String> pos = event.getTablePosition();
				String newColor = event.getNewValue();
				if (newColor != null && !newColor.isEmpty()) {
					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row);
					car.setColor(newColor);
				} else {
					GeneralPanes.errorAlert("Please check the color");
					tv.refresh();
				}
			}
		});

		// Make a column age of martyr
		TableColumn<Car, String> priceCol = new TableColumn<Car, String>("Price");
		// This to customize the insert data to the column
		priceCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Price"));
		priceCol.setSortable(false);
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// This to handle update the age of martyr
		priceCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {
			@Override
			public void handle(CellEditEvent<Car, String> event) {
				TablePosition<Car, String> pos = event.getTablePosition();
				String newPrice = event.getNewValue();
				if (newPrice != null && !newPrice.isEmpty()) {
					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row);
					car.setPrice(newPrice);
				} else {
					GeneralPanes.errorAlert("Please check the price");
					tv.refresh();

				}

			}
		});

		// Make a column age of martyr
		TableColumn<Car, String> yearCol = new TableColumn<Car, String>("Year");
		// This to customize the insert data to the column
		yearCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Car, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Car, String> data) {
				return new SimpleStringProperty("" + data.getValue().getYear());
			}
		});
		yearCol.setSortable(false);
		yearCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// This to handle update the age of martyr
		yearCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {
			@Override
			public void handle(CellEditEvent<Car, String> event) {
				TablePosition<Car, String> pos = event.getTablePosition();
				try {
					Short newYear = Short.parseShort(event.getNewValue());
					if (newYear >= 1900) {
						int row = pos.getRow();
						Car car = event.getTableView().getItems().get(row);
						car.setYear(newYear);
						current.getStart().notifyChangeDate(car);
						tv.getItems().clear();
						current.getStart().addDataToTableView(tv);
					} else {
						GeneralPanes.errorAlert("Please check the year");
						tv.refresh();
					}
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the year");
					tv.refresh();
				}

			}
		});

		// Make a column age of martyr
		TableColumn<Car, String> repeatCountCol = new TableColumn<Car, String>("Number of cars");
		// This to customize the insert data to the column
		repeatCountCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Car, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Car, String> data) {
						return new SimpleStringProperty("" + data.getValue().getCountRepeat());
					}
				});
		repeatCountCol.setSortable(false);
		repeatCountCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// This to handle update the age of martyr
		repeatCountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Car, String>>() {
			@Override
			public void handle(CellEditEvent<Car, String> event) {
				TablePosition<Car, String> pos = event.getTablePosition();
				try {
					int newYear = Integer.parseInt(event.getNewValue());

					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row);
					car.setRepeat(newYear);
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the year");
					tv.refresh();
				}

			}
		});

		// Add all columns to the tabel view
		tv.getColumns().addAll(modeleCol, colorCol, priceCol, repeatCountCol, yearCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(true);
		// Add all martyr in this location
		current.getStart().addDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section
		Label brandLabel = new Label(current.getBrand());
		brandLabel.setFont(new Font("Arial", 20));
		Button allBtn = new Button("All");
		allBtn.setPrefWidth(150);
		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				current.getStart().addDataToTableView(tv);
			}
		});

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button deleteCarBtn = new Button("Delete car");
		deleteCarBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(brandLabel, allBtn, tv, deleteCarBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));

		// This action for delete button, To delete the selected martyr
		deleteCarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Car car = tv.getSelectionModel().getSelectedItem();
				if (current.getStart().remove(car)) {
					tv.getItems().remove(car);
					tv.getSelectionModel().clearSelection();
				} else {
					GeneralPanes.errorAlert("Please select a car to remove");
				}

			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				Car car = tv.getSelectionModel().getSelectedItem();
				if (car != null) {
					deleteCarBtn.setText("Delete " + car.getModel());
				} else {
					deleteCarBtn.setText("Delete car");
				}
			}

		});

		// The bottom of the Screen (border pane) , Add new martyr section

		Label addErrorLabel = new Label();
		addErrorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		Label modelLabel = new Label("Model");
		TextField modelTF = new TextField();
		VBox modelVBox = new VBox(modelLabel, modelTF);
		modelVBox.setAlignment(Pos.CENTER);
		modelVBox.setSpacing(5);

		Label colorLabel = new Label("Color");
		TextField colorTF = new TextField();
		VBox colorVBox = new VBox(colorLabel, colorTF);
		colorVBox.setAlignment(Pos.CENTER);
		colorVBox.setSpacing(5);

		Label priceLabel = new Label("Price");
		TextField priceTF = new TextField();
		VBox priceVBox = new VBox(priceLabel, priceTF);
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(5);

		Label yearLabel = new Label("Year");
		TextField yearTF = new TextField();
		VBox yearVBox = new VBox(yearLabel, yearTF);
		yearVBox.setAlignment(Pos.CENTER);
		yearVBox.setSpacing(5);

		HBox bottomHBox = new HBox(modelVBox, colorVBox, priceVBox, yearVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button addBtn = new Button("Add");
		addBtn.setPrefWidth(150);
		Button searchBtn = new Button("Search");
		searchBtn.setPrefWidth(150);

		HBox searchAddHbox = new HBox(addBtn, searchBtn);
		searchAddHbox.setSpacing(20);
		searchAddHbox.setAlignment(Pos.CENTER);

		VBox bottomVBox = new VBox(addErrorLabel, bottomHBox, searchAddHbox);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));

		VBox centerAllVBox = new VBox(centerVBox, bottomVBox);
		centerAllVBox.setSpacing(20);
		this.setCenter(centerAllVBox);

		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String model = modelTF.getText();
				String color = colorTF.getText();
				String price = priceTF.getText();
				short year;
				try {
					year = Short.parseShort(yearTF.getText());
				} catch (Exception e) {
					year = 0;
				}
				tv.getItems().clear();
				current.getStart().addDataToTableView(tv, new Car(model, year, color, price));
			}
		});

		// This action for add martyr button, To add new martyr
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String model = modelTF.getText();
				if (model != null && !model.isEmpty()) {
					String color = colorTF.getText();
					if (color != null && !color.isEmpty()) {
						String price = priceTF.getText();
						if (price != null && !price.isEmpty()) {

							String yearString = yearTF.getText();
							if (yearString != null && !yearString.isEmpty()) {
								try {
									short year = Short.parseShort(yearString);
									if (year >= 1900) {

										Car car = new Car(model, year, color, price);
										int place = current.getStart().add(car);
										if (place == -1) {
											addErrorLabel.setText("The car " + (car.getModel()) + " add successfully");
											tv.getItems().add(car);
										} else if (place == -2) {
											addErrorLabel.setText("The car " + (car.getModel()) + " add successfully");
										} else if (place >= 0) {
											addErrorLabel.setText("The car " + (car.getModel()) + " add successfully");
											tv.getItems().add(place, car);
										} else if (place == -1) {
											addErrorLabel.setText("The car " + (car.getModel()) + " does not added");
										}

									} else {
										addErrorLabel.setText("Please check the year");
									}
								} catch (Exception e) {
									addErrorLabel.setText("Please check the year");
								}
							} else {
								addErrorLabel.setText("Please check the year");
							}

						} else {
							addErrorLabel.setText("Please check the price");
						}
					} else {
						addErrorLabel.setText("Please enter the color");
					}

				} else {
					addErrorLabel.setText("Please enter the model");
				}
			}
		});

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The right of the Screen (border pane)
		TextField updateTF = new TextField();
		updateTF.setMaxWidth(170);
		updateTF.setPrefWidth(170);
		Button updateBtn = new Button("update brand");
		updateBtn.setMaxWidth(170);
		updateBtn.setPrefWidth(170);
		Label updateLabel = new Label();
		updateLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");
		VBox updateVBox = new VBox(updateTF, updateBtn, updateLabel);
		updateVBox.setSpacing(5);
		Button deleteBtn = new Button("delete brand");
		deleteBtn.setMaxWidth(170);
		deleteBtn.setPrefWidth(170);
		VBox righVBox = new VBox(deleteBtn, updateVBox);
		righVBox.setSpacing(20);
		this.setMargin(righVBox, new Insets(30));
		this.setRight(righVBox);
		// This action for delete location button, To delete a current location
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				list.removeDoubleNode(current);
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// This action for update name button, To update the name of current location
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String text = updateTF.getText();
				if (text != null && !text.isEmpty()) {
					text = text.strip();
					if (text.equals(current.getBrand())) {
						updateLabel.setText("Error it is same brand");
					} else if (list.changeName(text, current)) {
						brandLabel.setText(text);
					} else {
						updateLabel.setText("Error exist brand");
					}
				} else {
					updateLabel.setText("Please enter the brand");
				}
			}
		});

	}

	// This constructor make a object and add the component and information of
	// All locations to the pane
	public BrandPane(MyDoubleLinkedList list, Stage stage) {

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<AllData<Car>> tv = new TableView<AllData<Car>>();
		// Make a column name of martyr
		TableColumn<AllData<Car>, String> modelCol = new TableColumn<AllData<Car>, String>("Model");
		// This to customize the insert data to the name column
		modelCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getModel());
					}
				});
		modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
		modelCol.setSortable(false);
		// This to handle update the age of martyr
		modelCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				String newModel = event.getNewValue();
				int row = pos.getRow();
				if (newModel != null && !newModel.isEmpty()) {
					Car car = event.getTableView().getItems().get(row).getInfo();
					car.setModel(newModel);
				} else {
					GeneralPanes.errorAlert("Please check the model");
					tv.refresh();
				}
			}
		});

		// Make a column age of martyr
		TableColumn<AllData<Car>, String> colorCol = new TableColumn<AllData<Car>, String>("Color");
		// This to customize the insert data to the age column
		colorCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getColor());
					}
				});
		colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		colorCol.setSortable(false);
		// This to handle update the age of martyr
		colorCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				String newColor = event.getNewValue();
				if (newColor != null && !newColor.isEmpty()) {
					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row).getInfo();
					car.setColor(newColor);
				} else {
					GeneralPanes.errorAlert("Please check the color");
					tv.refresh();
				}
			}
		});

		// Make a date of death column
		TableColumn<AllData<Car>, String> priceCol = new TableColumn<AllData<Car>, String>("Price");
		// This to customize the insert data to the date of death column
		priceCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getPrice());
					}
				});
		// This to handle update the date of death of martyr
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		priceCol.setSortable(false);
		priceCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				String newPrice = event.getNewValue();
				if (newPrice != null && !newPrice.isEmpty()) {
					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row).getInfo();
					car.setPrice(newPrice);
				} else {
					GeneralPanes.errorAlert("Please check the price");
					tv.refresh();
				}
			}
		});

		// Make a gender column
		TableColumn<AllData<Car>, String> yearCol = new TableColumn<AllData<Car>, String>("Year");
		yearCol.setCellFactory(TextFieldTableCell.forTableColumn());
		yearCol.setSortable(false);
		// This to customize the insert data to the gender column
		yearCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getYear() + "");
					}
				});

		// This to handle update the gender of martyr
		yearCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				try {
					Short newYear = Short.parseShort(event.getNewValue());
					if (newYear >= 1900) {
						int row = pos.getRow();
						AllData<Car> allData = event.getTableView().getItems().get(row);
						allData.getInfo().setYear(newYear);
						allData.getCurrent().getStart().notifyChangeDate(allData.getInfo());
						tv.getItems().clear();
						list.addAllDataToTableView(tv);
					} else {
						GeneralPanes.errorAlert("Please check the year");
						tv.refresh();
					}
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the year");
					tv.refresh();
				}

			}
		});

		// Make a location column
		TableColumn<AllData<Car>, String> brandCol = new TableColumn<AllData<Car>, String>("Brand");
		brandCol.setCellFactory(TextFieldTableCell.forTableColumn());
		brandCol.setSortable(false);
		// This to customize the insert data to the location column
		brandCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(data.getValue().getCurrent().getBrand());
					}
				});

		// This to handle update the location of martyr
		brandCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				String newBrand = event.getNewValue();
				int row = pos.getRow();
				if (newBrand != null && !newBrand.isEmpty()) {
					AllData<Car> allData = event.getTableView().getItems().get(row);
					boolean check = allData.getCurrent().getStart().remove(allData.getInfo());
					if (check) {
						list.add(allData.getInfo(), newBrand.trim());
						// allData.getCurrent().setBrand(newBrand);
						tv.getItems().clear();
						list.addAllDataToTableView(tv);
					} else {
						GeneralPanes.errorAlert("Please check the brand");
						tv.refresh();
					}
				} else {
					GeneralPanes.errorAlert("Please check the brand");
					tv.refresh();
				}
			}
		});

		// Make a column age of martyr
		TableColumn<AllData<Car>, String> repeatCountCol = new TableColumn<AllData<Car>, String>("Number of cars");
		// This to customize the insert data to the column
		repeatCountCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty("" + data.getValue().getInfo().getCountRepeat());
					}
				});
		repeatCountCol.setSortable(false);
		repeatCountCol.setCellFactory(TextFieldTableCell.forTableColumn());
		// This to handle update the age of martyr
		repeatCountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AllData<Car>, String>>() {
			@Override
			public void handle(CellEditEvent<AllData<Car>, String> event) {
				TablePosition<AllData<Car>, String> pos = event.getTablePosition();
				try {
					int newYear = Integer.parseInt(event.getNewValue());

					int row = pos.getRow();
					Car car = event.getTableView().getItems().get(row).getInfo();
					car.setRepeat(newYear);
				} catch (Exception e) {
					GeneralPanes.errorAlert("Please check the year");
					tv.refresh();
				}

			}
		});

		// Add all columns to the tabel view
		tv.getColumns().addAll(modelCol, colorCol, priceCol,repeatCountCol, yearCol, brandCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(true);
		// Add all martyr
		list.addAllDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section

		Button allBtn = new Button("All");
		allBtn.setPrefWidth(150);

		Label allLabel = new Label("All");
		allLabel.setFont(new Font("Arial", 20));

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button deleteCarBtn = new Button("Delete car");
		deleteCarBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(allLabel, allBtn, tv, deleteCarBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));

		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				list.addAllDataToTableView(tv);
			}
		});

		// This action for delete button, To delete the selected martyr
		deleteCarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AllData allData = tv.getSelectionModel().getSelectedItem();
				if (allData != null &&allData.getCurrent().getStart().remove(allData.getInfo())) {

					tv.scrollTo(tv.selectionModelProperty().getValue().getSelectedIndex() - 16);
					tv.getItems().remove(allData);
					tv.getSelectionModel().clearSelection();
				} else {
					GeneralPanes.errorAlert("Please select a car to remove");
				}
			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				AllData<Car> allData = tv.getSelectionModel().getSelectedItem();
				if (allData != null) {
					deleteCarBtn.setText("Delete " + allData.getInfo().getModel());
				} else {
					deleteCarBtn.setText("Delete Car");
				}
			}
		});

		// The bottom of the Screen (border pane) , Add new martyr section
		Label addErrorLabel = new Label();
		addErrorLabel.setStyle("-fx-text-fill:red;-fx-font-weight: bold; -fx-font-size: 16;-fx-alignment:CENTER;");

		Label modelLabel = new Label("Model");
		TextField modelTF = new TextField();
		VBox modelVBox = new VBox(modelLabel, modelTF);
		modelVBox.setAlignment(Pos.CENTER);
		modelVBox.setSpacing(5);

		Label colorLabel = new Label("Color");
		TextField colorTF = new TextField();
		VBox colorVBox = new VBox(colorLabel, colorTF);
		colorVBox.setAlignment(Pos.CENTER);
		colorVBox.setSpacing(5);

		Label priceLabel = new Label("Price");
		TextField priceTF = new TextField();
		VBox priceVBox = new VBox(priceLabel, priceTF);
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(5);

		Label yearLabel = new Label("Year");
		TextField yearTF = new TextField();
		VBox yearVBox = new VBox(yearLabel, yearTF);
		yearVBox.setAlignment(Pos.CENTER);
		yearVBox.setSpacing(5);

		Label brandLabel = new Label("Brand");
		TextField brandTF = new TextField();
		VBox brandVBox = new VBox(brandLabel, brandTF);
		brandVBox.setAlignment(Pos.CENTER);
		brandVBox.setSpacing(5);

		HBox bottomHBox = new HBox(modelVBox, colorVBox, priceVBox, yearVBox, brandVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button addBtn = new Button("Add");
		addBtn.setPrefWidth(150);
		Button searchBtn = new Button("Search");
		searchBtn.setPrefWidth(150);
		HBox searchHbox = new HBox(addBtn, searchBtn);
		searchHbox.setSpacing(50);
		searchHbox.setAlignment(Pos.CENTER);
		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String brand = brandTF.getText();
				String model = modelTF.getText();
				String color = colorTF.getText();
				String price = priceTF.getText();
				short year;
				try {
					year = Short.parseShort(yearTF.getText());
				} catch (Exception e) {
					year = 0;
				}
				tv.getItems().clear();
				list.addAllDataToTableView(tv, brand, new Car(model, year, color, price));

			}
		});

		VBox bottomVBox = new VBox(addErrorLabel, bottomHBox, searchHbox);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));

		VBox centerAllVBox = new VBox(centerVBox, bottomVBox);
		centerAllVBox.setSpacing(20);
		this.setCenter(centerAllVBox);

		// This action for add martyr button, To add new martyr
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String model = modelTF.getText();
				if (model != null && !model.isEmpty()) {
					String color = colorTF.getText();
					if (color != null && !color.isEmpty()) {
						String price = priceTF.getText();
						if (price != null && !price.isEmpty()) {

							String yearString = yearTF.getText();
							if (yearString != null && !yearString.isEmpty()) {
								try {
									short year = Short.parseShort(yearString);
									if (year >= 1900) {
										String brand = brandTF.getText();
										if (brand != null && !brand.isEmpty()) {
											Car car = new Car(model, year, color, price);
											list.add(car, brand);
											tv.getItems().clear();
											list.addAllDataToTableView(tv);
											addErrorLabel.setText("The car " + (car.getModel()) + " add successfully");
										} else {
											addErrorLabel.setText("Please check the brand");
										}
									} else {
										addErrorLabel.setText("Please check the year");
									}
								} catch (Exception e) {
									addErrorLabel.setText("Please check the year");
								}
							} else {
								addErrorLabel.setText("Please check the year");
							}

						} else {
							addErrorLabel.setText("Please check the price");
						}
					} else {
						addErrorLabel.setText("Please enter the color");
					}

				} else {
					addErrorLabel.setText("Please enter the model");
				}
			}
		});

		Pane empty = new Pane();
		empty.setPrefWidth(100);
		this.setRight(empty);
	}

	// This constructor make a object and add the component and information of
	// selected location to the pane
	public BrandPane(MyDoubleLinkedList list, DoubleNode current, Stage stage, Customer customer) {

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<Car> tv = new TableView<Car>();

		// Make a column name of martyr
		TableColumn<Car, String> modeleCol = new TableColumn<Car, String>("Model");
		modeleCol.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
		modeleCol.setSortable(false);

		TableColumn<Car, String> colorCol = new TableColumn<Car, String>("Color");
		colorCol.setCellValueFactory(new PropertyValueFactory<Car, String>("color"));
		colorCol.setSortable(false);
		// This to handle update the name of martyr

		// Make a column age of martyr
		TableColumn<Car, String> priceCol = new TableColumn<Car, String>("Price");
		// This to customize the insert data to the column
		priceCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Price"));
		priceCol.setSortable(false);

		// Make a column age of martyr
		TableColumn<Car, String> yearCol = new TableColumn<Car, String>("Year");
		// This to customize the insert data to the column
		yearCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Car, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Car, String> data) {
				return new SimpleStringProperty("" + data.getValue().getYear());
			}
		});
		yearCol.setSortable(false);

		// Add all columns to the tabel view
		tv.getColumns().addAll(modeleCol, colorCol, priceCol, yearCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(true);
		// Add all martyr in this location
		current.getStart().addDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section
		Label brandLabel = new Label(current.getBrand());
		brandLabel.setFont(new Font("Arial", 20));
		Button allBtn = new Button("All");
		allBtn.setPrefWidth(150);
		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				current.getStart().addDataToTableView(tv);
			}
		});

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button orderCarBtn = new Button("order car");
		orderCarBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(brandLabel, allBtn, tv, orderCarBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));

		// This action for delete button, To delete the selected martyr
		orderCarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Car car = tv.getSelectionModel().getSelectedItem();
				if (car != null) {
					Main.orders.enqueue(new Order(customer, car, current.getBrand(), new Date(), false));
					GeneralPanes.completAlert("Order successfully " + car.getModel() + " : " + car.getYear());
				} else {
					GeneralPanes.errorAlert("Please select a car to remove");
				}
				
			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				Car car = tv.getSelectionModel().getSelectedItem();
				if (car != null) {
					orderCarBtn.setText("Order :" + car.getModel());
				} else {
					orderCarBtn.setText("Order car");
				}
			}

		});

		// The bottom of the Screen (border pane) , Add new martyr section

		Label modelLabel = new Label("Model");
		TextField modelTF = new TextField();
		VBox modelVBox = new VBox(modelLabel, modelTF);
		modelVBox.setAlignment(Pos.CENTER);
		modelVBox.setSpacing(5);

		Label colorLabel = new Label("Color");
		TextField colorTF = new TextField();
		VBox colorVBox = new VBox(colorLabel, colorTF);
		colorVBox.setAlignment(Pos.CENTER);
		colorVBox.setSpacing(5);

		Label priceLabel = new Label("Price");
		TextField priceTF = new TextField();
		VBox priceVBox = new VBox(priceLabel, priceTF);
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(5);

		Label yearLabel = new Label("Year");
		TextField yearTF = new TextField();
		VBox yearVBox = new VBox(yearLabel, yearTF);
		yearVBox.setAlignment(Pos.CENTER);
		yearVBox.setSpacing(5);

		HBox bottomHBox = new HBox(modelVBox, colorVBox, priceVBox, yearVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button searchBtn = new Button("Search");
		searchBtn.setPrefWidth(150);

		VBox bottomVBox = new VBox(bottomHBox, searchBtn);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));

		VBox centerAllVBox = new VBox(centerVBox, bottomVBox);
		centerAllVBox.setSpacing(20);
		this.setCenter(centerAllVBox);

		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String model = modelTF.getText();
				String color = colorTF.getText();
				String price = priceTF.getText();
				short year;
				try {
					year = Short.parseShort(yearTF.getText());
				} catch (Exception e) {
					year = 0;
				}
				tv.getItems().clear();
				current.getStart().addDataToTableView(tv, new Car(model, year, color, price));
			}
		});

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage, customer);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		Pane empty = new Pane();
		empty.setPrefWidth(100);
		this.setRight(empty);

	}

	// This constructor make a object and add the component and information of
	// All locations to the pane
	public BrandPane(MyDoubleLinkedList list, Stage stage, Customer customer) {

		// The left of the Screen (border pane)
		Button backBtn = new Button("Back");
		this.setMargin(backBtn, new Insets(30));
		this.setLeft(backBtn);
		// This action for back button, To return to the home page
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				HomePane homePane = new HomePane(list, stage, customer);
				Scene homeScene = new Scene(homePane);
				stage.setScene(homeScene);
			}
		});

		// The center of the Screen (border pane)
		// Make a table view of martyr
		TableView<AllData<Car>> tv = new TableView<AllData<Car>>();
		// Make a column name of martyr
		TableColumn<AllData<Car>, String> modelCol = new TableColumn<AllData<Car>, String>("Model");
		// This to customize the insert data to the name column
		modelCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getModel());
					}
				});
		modelCol.setSortable(false);

		// Make a column age of martyr
		TableColumn<AllData<Car>, String> colorCol = new TableColumn<AllData<Car>, String>("Color");
		// This to customize the insert data to the age column
		colorCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getColor());
					}
				});
		colorCol.setSortable(false);

		// Make a date of death column
		TableColumn<AllData<Car>, String> priceCol = new TableColumn<AllData<Car>, String>("Price");
		// This to customize the insert data to the date of death column
		priceCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getPrice());
					}
				});
		// This to handle update the date of death of martyr
		priceCol.setSortable(false);

		// Make a gender column
		TableColumn<AllData<Car>, String> yearCol = new TableColumn<AllData<Car>, String>("Year");
		yearCol.setSortable(false);
		// This to customize the insert data to the gender column
		yearCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						return new SimpleStringProperty(data.getValue().getInfo().getYear() + "");
					}
				});

		// Make a location column
		TableColumn<AllData<Car>, String> brandCol = new TableColumn<AllData<Car>, String>("Brand");
		brandCol.setSortable(false);
		// This to customize the insert data to the location column
		brandCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<AllData<Car>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<AllData<Car>, String> data) {
						// TODO Auto-generated method stub
						return new SimpleStringProperty(data.getValue().getCurrent().getBrand());
					}
				});

		// Add all columns to the tabel view
		tv.getColumns().addAll(modelCol, colorCol, priceCol, yearCol, brandCol);
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tv.setEditable(false);
		// Add all martyr
		list.addAllDataToTableView(tv);

		// The top of the center of Screen, the search (martyr) section

		Button allBtn = new Button("All");
		allBtn.setPrefWidth(150);

		Label allLabel = new Label("All");
		allLabel.setFont(new Font("Arial", 20));

		// The bottom of the center of Screen, the delete (selected martyr) section
		Button orderCarBtn = new Button("Order car");
		orderCarBtn.setPrefWidth(150);

		// Then add the top , mid and bottom sections to the center of screen
		VBox centerVBox = new VBox(allLabel, allBtn, tv, orderCarBtn);
		centerVBox.setAlignment(Pos.TOP_CENTER);
		centerVBox.setSpacing(5);
		this.setMargin(centerVBox, new Insets(40));

		// This action for all button, to display all martyr on the table view
		allBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				tv.getItems().clear();
				list.addAllDataToTableView(tv);
			}
		});

		// This action for delete button, To delete the selected martyr
		orderCarBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				AllData<Car> allData = tv.getSelectionModel().getSelectedItem();
				if (allData != null) {
					Main.orders.enqueue(new Order(customer, allData.getInfo(), allData.getCurrent().getBrand(), new Date(), false));
					GeneralPanes.completAlert("Order successfully, " + allData.getInfo().getModel() + " : " +  allData.getInfo().getYear());
				} else {
					GeneralPanes.errorAlert("Please select a car to remove");
				}
				
				
				
			}
		});

		// This listener for label view to change the text of delete button
		tv.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
			@Override
			public void changed(ObservableValue<? extends TablePosition> arg0, TablePosition arg1, TablePosition arg2) {
				AllData<Car> allData = tv.getSelectionModel().getSelectedItem();
				if (allData != null) {
					orderCarBtn.setText("Order " + allData.getInfo().getModel());
				} else {
					orderCarBtn.setText("Order Car");
				}
			}
		});

		Label modelLabel = new Label("Model");
		TextField modelTF = new TextField();
		VBox modelVBox = new VBox(modelLabel, modelTF);
		modelVBox.setAlignment(Pos.CENTER);
		modelVBox.setSpacing(5);

		Label colorLabel = new Label("Color");
		TextField colorTF = new TextField();
		VBox colorVBox = new VBox(colorLabel, colorTF);
		colorVBox.setAlignment(Pos.CENTER);
		colorVBox.setSpacing(5);

		Label priceLabel = new Label("Price");
		TextField priceTF = new TextField();
		VBox priceVBox = new VBox(priceLabel, priceTF);
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(5);

		Label yearLabel = new Label("Year");
		TextField yearTF = new TextField();
		VBox yearVBox = new VBox(yearLabel, yearTF);
		yearVBox.setAlignment(Pos.CENTER);
		yearVBox.setSpacing(5);

		Label brandLabel = new Label("Brand");
		TextField brandTF = new TextField();
		VBox brandVBox = new VBox(brandLabel, brandTF);
		brandVBox.setAlignment(Pos.CENTER);
		brandVBox.setSpacing(5);

		HBox bottomHBox = new HBox(modelVBox, colorVBox, priceVBox, yearVBox, brandVBox);
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(20);

		Button searchBtn = new Button("Search");
		searchBtn.setPrefWidth(150);

		// This action for search button, To search about the martyr name
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String brand = brandTF.getText();
				String model = modelTF.getText();
				String color = colorTF.getText();
				String price = priceTF.getText();
				short year;
				try {
					year = Short.parseShort(yearTF.getText());
				} catch (Exception e) {
					year = 0;
				}
				tv.getItems().clear();
				list.addAllDataToTableView(tv, brand, new Car(model, year, color, price));

			}
		});

		VBox bottomVBox = new VBox(bottomHBox, searchBtn);
		bottomVBox.setAlignment(Pos.CENTER);
		bottomVBox.setSpacing(10);
		this.setMargin(bottomVBox, new Insets(40));

		VBox centerAllVBox = new VBox(centerVBox, bottomVBox);
		centerAllVBox.setSpacing(20);
		this.setCenter(centerAllVBox);

		Pane empty = new Pane();
		empty.setPrefWidth(100);
		this.setRight(empty);
	}

}
