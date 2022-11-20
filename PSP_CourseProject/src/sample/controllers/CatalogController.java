package sample.controllers;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import sample.Main;
import sample.database.DatabaseConnection;
import sample.models.Auto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

import sample.controllers.LoginController.*;

import static sample.controllers.CartController.cartObservableList;
import static sample.controllers.CartController.cartStage;

public class CatalogController extends Transition{
    public TableView<Auto> autoTable;
    public Label brandLabel;
    public Label modelLabel;
    public Label generationLabel;
    public TextField filterField;
    public ComboBox<String> choicesComboBox;
    public CartController cartController;
    public Button addToCartButton;
    public Pane infoPane;
    public TextField totalPriceField;

    public TextField fromPrice;
    public TextField beforePrice;
    public TextField fromYear;
    public TextField beforeYear;
    public TextField fromVolumeE;
    public TextField beforeVolumeE;

    private FilteredList<Auto> filteredListAuto;
    private ObservableList<Auto> cars;


    public CatalogController()
    {
        cartController = new CartController();
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException, IOException {
        infoPane.setVisible(false);
        addToCartButton.setVisible(false);
        totalPriceField.setVisible(false);

        ObservableList<String> choices = FXCollections.observableArrayList("Марка", "Модель", "Поколение", "Тип трансмиссии", "Тип топлива");
        choicesComboBox.setItems(choices);
        choicesComboBox.setValue("Марка");

        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT * FROM auto INNER JOIN detail ON auto.id = detail.autoId";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        cars = FXCollections.observableArrayList();
        while (resultSet.next()) {
            System.out.println("resultSet: " + resultSet.getInt("id"));
            var auto = new Auto(
                    resultSet.getInt("id"),
                    resultSet.getString("brand"),
                    resultSet.getString("model"),
                    resultSet.getString("generation"),
                    resultSet.getInt("price"),
                    resultSet.getString("transmission"),
                    resultSet.getString("fuel"),
                    resultSet.getInt("year"),
                    resultSet.getFloat("volumeE")
                    );
            cars.add(auto);
        }

        autoTable.setItems(cars);
        autoTable.setPlaceholder(new Label("It seems, there are no products here!"));

        TableColumn<Auto, String> brandColumn = new TableColumn<>("Марка");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Auto, String> modelColumn = new TableColumn<>("Модель");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Auto, String> generationColumn = new TableColumn<>("Поколение");
        generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Auto, String> priceColumn = new TableColumn<>("Цена($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Auto, String> transmissionColumn = new TableColumn<>("Трансмиссия");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Auto, String> fuelColumn = new TableColumn<>("Тип топлива");
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Auto, String> yearColumn = new TableColumn<>("Год");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Auto, String> volumeEColumn = new TableColumn<>("Объем двигателя");
        volumeEColumn.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        brandColumn.setPrefWidth(100);
        modelColumn.setPrefWidth(60);
        generationColumn.setPrefWidth(80);
        priceColumn.setPrefWidth(55);
        transmissionColumn.setPrefWidth(90);
        fuelColumn.setPrefWidth(53);
        yearColumn.setPrefWidth(52);
        volumeEColumn.setPrefWidth(90);

        autoTable.getColumns().add(brandColumn);
        autoTable.getColumns().add(modelColumn);
        autoTable.getColumns().add(generationColumn);
        autoTable.getColumns().add(priceColumn);
        autoTable.getColumns().add(transmissionColumn);
        autoTable.getColumns().add(fuelColumn);
        autoTable.getColumns().add(yearColumn);
        autoTable.getColumns().add(volumeEColumn);

        autoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(autoTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = autoTable.getSelectionModel().getSelectedItem();

                    System.out.println("Selected Value " + sel.getBrand() + " " + sel.getModel() + " " + sel.getGeneration());
                    brandLabel.setText("Brand: " + sel.getBrand());
                    modelLabel.setText("Model: " + sel.getModel());
                    generationLabel.setText("Generation: " + sel.getGeneration());
                    infoPane.setVisible(true);
                    addToCartButton.setVisible(true);
                    totalPriceField.setVisible(true);
                    var selected = autoTable.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        int cost = Integer.parseInt("0" + selected.getPrice());
                        if(cost != 0){
                            totalPriceField.setText(String.valueOf(cost));
                        }else{
                            totalPriceField.clear();
                        }
                    }
                }
                else{
                    infoPane.setVisible(false);
                    addToCartButton.setVisible(false);
                    totalPriceField.setVisible(false);
                }
            }
        });

        choicesComboBox.getSelectionModel().selectedItemProperty().addListener(
                (options, oldValue, newValue) -> {
                    filterField.setText("");
                }
        );

        fromPrice.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        beforePrice.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        fromYear.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        beforeYear.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        fromVolumeE.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        beforeVolumeE.selectedTextProperty().addListener((options, oldValue, newValue) ->{
            fromPrice.setText("");
        });

        FilteredList<Auto> filteredProducts1 = new FilteredList<>(cars, b->true);
        FilteredList<Auto> filteredProducts2 = new FilteredList<>(filteredProducts1, b->true);
        FilteredList<Auto> filteredProducts3 = new FilteredList<>(filteredProducts2, b->true);
        FilteredList<Auto> filteredProducts4 = new FilteredList<>(filteredProducts3, b->true);
        FilteredList<Auto> filteredProducts5 = new FilteredList<>(filteredProducts4, b->true);
        FilteredList<Auto> filteredProducts6 = new FilteredList<>(filteredProducts5, b->true);

        fromPrice.textProperty().addListener(obs->{
            String filter = fromPrice.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts1.setPredicate(cars -> true);
            }
            else{
                filteredProducts1.setPredicate(cars -> cars.getPrice() >= Integer.parseInt(fromPrice.getText()));
            }
        });

        beforePrice.textProperty().addListener(obs->{
            String filter = beforePrice.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts2.setPredicate(cars -> true);
            }
            else{
                filteredProducts2.setPredicate(cars -> cars.getPrice() <= Integer.parseInt(beforePrice.getText()));
            }
        });

        fromYear.textProperty().addListener(obs->{
            String filter = fromYear.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts3.setPredicate(cars -> true);
            }
            else{
                filteredProducts3.setPredicate(cars -> cars.getYear() >= Integer.parseInt(fromYear.getText()));
            }
        });

        beforeYear.textProperty().addListener(obs->{
            String filter = beforeYear.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts4.setPredicate(cars -> true);
            }
            else{
                filteredProducts4.setPredicate(cars -> cars.getYear() <= Integer.parseInt(beforeYear.getText()));
            }
        });

        fromVolumeE.textProperty().addListener(obs->{
            String filter = fromVolumeE.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts5.setPredicate(cars -> true);
            }
            else{
                filteredProducts5.setPredicate(cars -> cars.getVolumeE() >= Float.parseFloat(fromVolumeE.getText()));
            }
        });

        beforeVolumeE.textProperty().addListener(obs->{
            String filter = beforeVolumeE.getText();
            if(filter == null || filter.length() == 0){
                filteredProducts6.setPredicate(cars -> true);
            }
            else{
                filteredProducts6.setPredicate(cars -> cars.getVolumeE() <= Float.parseFloat(beforeVolumeE.getText()));
            }
        });

        filterField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            filteredProducts6.setPredicate(car -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                String choice = (String) choicesComboBox.getValue();
                switch (choice) {
                    case "Марка":
                        return car.getBrand().toLowerCase().contains(lowerCaseFilter);
                    case "Модель":
                        return car.getModel().toLowerCase().contains(lowerCaseFilter);
                    case "Поколение":
                        return car.getGeneration().toLowerCase().contains(lowerCaseFilter);
                    case "Тип трансмиссии":
                        return car.getTransmission().toLowerCase().contains(lowerCaseFilter);
                    case "Тип топлива":
                        return car.getFuel().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return false;
                }
            });
        }));

        SortedList<Auto> sortedProducts1 = new SortedList<>(filteredProducts6);
        sortedProducts1.comparatorProperty().bind(autoTable.comparatorProperty());
        autoTable.setItems(filteredProducts6);
    }

    public Integer getOverallCost(){
        int sum = 0;
        for(var item: cartObservableList){
            sum += item.getTotalPrice();
        }
        return sum;
    }

    public void addToCart(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedCar = autoTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null){
            cartController.addToCart(selectedCar);
            try {
                System.out.println("selectCar.getId(): " + selectedCar.getId());
                Main.client.oos.writeInt(4);
                Main.client.oos.writeObject(selectedCar);
                System.out.println("Auto " + selectedCar.getBrand() + " added to cart");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void logOut(ActionEvent actionEvent) {
        if(cartStage!= null){
            cartStage.close();
            cartStage = null;
        }
        cartObservableList.clear();
        Stage login = getNewStage(actionEvent, "../views/Login.fxml", true);
        if(login != null) {
            login.show();
        }
    }

    public void showCart(ActionEvent actionEvent) {
        if(cartStage == null) {
            cartStage = getNewStage(actionEvent, "../views/Cart.fxml", false);
            if(cartStage != null) {
                cartStage.show();
            }
        }
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

}
