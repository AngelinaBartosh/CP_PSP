package sample.controllers;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.database.DatabaseConnection;
import sample.models.Auto;
import sample.models.Cart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sample.controllers.LoginController.*;

import static sample.controllers.LoginController.Username;

public class CartController {
    public static ObservableList<Cart> cartObservableList = FXCollections.observableArrayList();
    public TableView<Cart> cartTable;
    public TableView<Cart> myCartTable;
    public static Stage cartStage;
    public Label overallPriceField;

    public void updateCart(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT autostore.auto.id, autostore.auto.brand, autostore.auto.model, autostore.auto.generation, " +
                "autostore.detail.price, autostore.detail.transmission, autostore.detail.fuel, autostore.detail.year, autostore.detail.volumeE FROM autostore.cart " +
                "INNER JOIN autostore.auto ON (autostore.cart.autoId = autostore.auto.id) " +
                "INNER JOIN autostore.detail ON (autostore.auto.id = autostore.detail.autoId);  ";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Cart> cars = FXCollections.observableArrayList();

        while(resultSet.next()){
            var cart = new Cart(
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
            cars.add(cart);
        }

        cartTable.setItems(cars);
        cartTable.setPlaceholder(new Label("В вашей корзине пока нет товаров, удачной покупки!"));


        try {
            overallPriceField.setText("Overall cost: " + String.valueOf(getOverallCost()) + "$");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        myCartTable.setVisible(false);
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT cart.*, auto.*, detail.* FROM cart " +
                "INNER JOIN auto ON (cart.autoId = auto.id) " +
                "INNER JOIN detail ON (auto.id = detail.autoId) ";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Cart> cars = FXCollections.observableArrayList();

        while(resultSet.next()){
            var cart = new Cart(
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
            cars.add(cart);
        }

        cartTable.setItems(cars);
        cartTable.setPlaceholder(new Label("В вашей корзине пока нет товаров, удачной покупки!"));

        try {
            overallPriceField.setText("К оплате: " + String.valueOf(getOverallCost()) + "$");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TableColumn<Cart, String> brandColumn = new TableColumn<>("Марка");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Cart, String> modelColumn = new TableColumn<>("Модель");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Cart, String> generationColumn = new TableColumn<>("Поколение");
        generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Cart, String> priceColumn = new TableColumn<>("Цена($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Cart, String> transmissionColumn = new TableColumn<>("Трансмиссия");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Cart, String> fuelColumn = new TableColumn<>("Тип топлива");
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Cart, String> yearColumn = new TableColumn<>("Год");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Cart, String> volumeEColumn = new TableColumn<>("Объем двигателя");
        volumeEColumn.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        cartTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        brandColumn.setMaxWidth( 1f * Integer.MAX_VALUE * 30);
        modelColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        generationColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        priceColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        transmissionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        fuelColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        yearColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        volumeEColumn.setMaxWidth(1f * Integer.MAX_VALUE * 30);

        cartTable.getColumns().add(brandColumn);
        cartTable.getColumns().add(modelColumn);
        cartTable.getColumns().add(generationColumn);
        cartTable.getColumns().add(priceColumn);
        cartTable.getColumns().add(transmissionColumn);
        cartTable.getColumns().add(fuelColumn);
        cartTable.getColumns().add(yearColumn);
        cartTable.getColumns().add(volumeEColumn);

        TableColumn<Cart, String> brandColumn1 = new TableColumn<>("Марка");
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Cart, String> modelColumn1 = new TableColumn<>("Модель");
        modelColumn1.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Cart, String> generationColumn1 = new TableColumn<>("Поколение");
        generationColumn1.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Cart, String> priceColumn1 = new TableColumn<>("Цена($)");
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Cart, String> transmissionColumn1 = new TableColumn<>("Трансмиссия");
        transmissionColumn1.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Cart, String> fuelColumn1 = new TableColumn<>("Тип топлива");
        fuelColumn1.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Cart, String> yearColumn1 = new TableColumn<>("Год");
        yearColumn1.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Cart, String> volumeEColumn1 = new TableColumn<>("Объем двигателя");
        volumeEColumn1.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        myCartTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        brandColumn1.setMaxWidth( 1f * Integer.MAX_VALUE * 30);
        modelColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        generationColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        priceColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        transmissionColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        fuelColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        yearColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        volumeEColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);

        myCartTable.getColumns().add(brandColumn1);
        myCartTable.getColumns().add(modelColumn1);
        myCartTable.getColumns().add(generationColumn1);
        myCartTable.getColumns().add(priceColumn1);
        myCartTable.getColumns().add(transmissionColumn1);
        myCartTable.getColumns().add(fuelColumn1);
        myCartTable.getColumns().add(yearColumn1);
        myCartTable.getColumns().add(volumeEColumn1);
    }

    int totalPrice = 0;
    public Integer getOverallCost() throws SQLException, ClassNotFoundException {
        /*int sum = 0;
        for(var item: cartObservableList){
            sum += item.getTotalPrice();
        }
        return sum;*/
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT SUM(totalPrice) FROM cart";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        while (resultSet.next()){
            totalPrice = resultSet.getInt("SUM(totalPrice)");
        }

        return totalPrice;
    }

    public void closeCart(ActionEvent actionEvent) {
        cartStage.close();
        cartStage = null;
    }

    public void purchase(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String selectSql = "SELECT auto.id, cart.autoId FROM auto " +
                "INNER JOIN cart ON (auto.id = cart.autoId)";

        String insertSql = "INSERT INTO purchases(username, auto_id) VALUES (?, ?)";

        ResultSet resultSet = connection.createStatement().executeQuery(selectSql);

        while(resultSet.next()){
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, Username);
            preparedStatement.setInt(2, resultSet.getInt("id"));
            preparedStatement.executeUpdate();
        }

        if(cartObservableList.size() != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cart");
            alert.setHeaderText(null);
            alert.setContentText("Purchased successfully!");
            alert.showAndWait();
            cartObservableList.clear();
            clearCart(mouseEvent);
        }
    }

    public void hideMyCar(MouseEvent mouseEvent){
        myCartTable.setVisible(false);
    }

    public void showMyCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        myCartTable.setVisible(true);
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT auto.*, detail.*, purchases.* FROM autostore.purchases " +
                "INNER JOIN autostore.auto ON (purchases.auto_id = auto.id) " +
                "INNER JOIN autostore.detail ON (auto.id = detail.autoId) " +
                "WHERE autostore.purchases.username = " + "'" + Username + "'";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Cart> cars = FXCollections.observableArrayList();

        while(resultSet.next()){
            var cart = new Cart(
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
            cars.add(cart);
        }

        myCartTable.setItems(cars);
        myCartTable.setPlaceholder(new Label("В вашей корзине пока нет товаров, удачной покупки!"));
    }

    public void addToCart(Auto auto){
        int index = -1;
        for(var cartItem : cartObservableList){
            if(cartItem.getBrand().equals(auto.getBrand()) &&
                    cartItem.getModel().equals(auto.getModel()) &&
                    cartItem.getGeneration().equals(auto.getGeneration()) &&
                    cartItem.getPrice() == auto.getPrice() &&
                    cartItem.getTransmission().equals(auto.getTransmission()) &&
                    cartItem.getFuel().equals(auto.getFuel()) &&
                    cartItem.getYear() == auto.getYear() &&
                    cartItem.getVolumeE() == auto.getVolumeE())
            {
                index = cartObservableList.indexOf(cartItem);
            }
        }
        if(index != -1) {
            cartObservableList.remove(cartObservableList.get(index));
        }
        cartObservableList.add(new Cart(auto.getBrand(), auto.getModel(), auto.getGeneration(), auto.getPrice(), auto.getTransmission(),
                auto.getFuel(), auto.getYear(), auto.getVolumeE()));
    }

    public void deleteCartItem(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        var cartItem = cartTable.getSelectionModel().getSelectedItem();
        if(cartItem != null){
            Main.client.oos.writeInt(5);
            Main.client.oos.writeObject(cartItem);
            updateCart(mouseEvent);
        }
        if(cartItem != null) {
            cartObservableList.remove(cartItem);
            updateCart(mouseEvent);
        }
        updateCart(mouseEvent);
    }

    public void clearCart(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        cartObservableList.clear();
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "DELETE FROM cart";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            updateCart(mouseEvent);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
