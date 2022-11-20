package sample.controllers;

import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Main;
import sample.database.DatabaseConnection;
import sample.models.Auto;
import sample.models.Purchases;
import sample.models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.awt.Button;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AdminController extends Transition{
    public TableView<Auto> autoTable;
    public TableView<User> userTable;
    @FXML
    public TableView<Purchases> userCarTable;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    public TextField brandField;
    public TextField modelField;
    public TextField generationField;
    public TextField priceField;
    public TextField transmissionField;
    public TextField fuelField;
    public TextField yearField;
    public TextField volumeEField;

    public TextField emailField;
    public TextField usernameField;
    public TextField passwordField;
    public TextField roleField;

    public static Button showDiagram;

    public BorderPane borderPane;

    public void updateCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        updateUser(mouseEvent);
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sql = "SELECT * FROM auto INNER JOIN detail ON auto.id = detail.autoId";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        ObservableList<Auto> cars = FXCollections.observableArrayList();

        while (resultSet.next()) {
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
        System.out.println("Auto table data updated");
    }

    public void updateUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlUser = "SELECT * FROM users";

        ResultSet resultSetUser = connection.createStatement().executeQuery(sqlUser);
        ObservableList<User> users = FXCollections.observableArrayList();

        while (resultSetUser.next()) {
            var user = new User(
                    resultSetUser.getInt("id"),
                    resultSetUser.getString("email"),
                    resultSetUser.getString("username"),
                    resultSetUser.getString("password"),
                    resultSetUser.getString("role")
            );
            users.add(user);
        }
        userTable.setItems(users);
        userTable.setPlaceholder(new Label("It seems, there are no products here!"));
        System.out.println("Users table data updated");
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        initUser();
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlAuto = "SELECT * FROM auto INNER JOIN detail ON auto.id = detail.autoId";

        ResultSet resultSet = connection.createStatement().executeQuery(sqlAuto);
        ObservableList<Auto> cars = FXCollections.observableArrayList();

        while (resultSet.next()) {
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

        TableColumn<Auto, String> generationColumn = new TableColumn<>("Поколоение");
        generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Auto, String> priceColumn = new TableColumn<>("Цена($)");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Auto, String> transmissionColumn = new TableColumn<>("Трансмиссия");
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Auto, String> fuelColumn = new TableColumn<>("Тип типлива");
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Auto, String> yearColumn = new TableColumn<>("Год");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Auto, String> volumeEColumn = new TableColumn<>("Объем двигателя");
        volumeEColumn.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        String sql = "SELECT purchases.*, auto.*, detail.* FROM autostore.purchases " +
                "INNER JOIN autostore.auto ON (purchases.auto_id = auto.id) " +
                "INNER JOIN autostore.detail ON (auto.id = detail.autoId); ";

        ResultSet resultSet1 = connection.createStatement().executeQuery(sql);
        ObservableList<Purchases> userCars = FXCollections.observableArrayList();

        while(resultSet1.next()){
            var purchase = new Purchases(
                    resultSet1.getInt("id"),
                    resultSet1.getString("username"),
                    resultSet1.getString("brand"),
                    resultSet1.getString("model"),
                    resultSet1.getString("generation"),
                    resultSet1.getInt("price"),
                    resultSet1.getString("transmission"),
                    resultSet1.getString("fuel"),
                    resultSet1.getInt("year"),
                    resultSet1.getFloat("volumeE")
            );
            userCars.add(purchase);
        }
        userCarTable.setItems(userCars);
        userCarTable.setPlaceholder(new Label("Ни один пользователь ещё не купил автомобиль"));

        TableColumn<Purchases, String> userColumn1 = new TableColumn<>("Пользователь");
        userColumn1.setCellValueFactory(data -> (ObservableValue<String>) data.getValue().getUsername());

        TableColumn<Purchases, String> brandColumn1 = new TableColumn<>("Марка");
        brandColumn1.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Purchases, String> modelColumn1 = new TableColumn<>("Модель");
        modelColumn1.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Purchases, String> generationColumn1 = new TableColumn<>("Поколение");
        generationColumn1.setCellValueFactory(new PropertyValueFactory<>("generation"));

        TableColumn<Purchases, String> priceColumn1 = new TableColumn<>("Цена($)");
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Purchases, String> transmissionColumn1 = new TableColumn<>("Трансмиссия");
        transmissionColumn1.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        TableColumn<Purchases, String> fuelColumn1 = new TableColumn<>("Тип топлива");
        fuelColumn1.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<Purchases, String> yearColumn1 = new TableColumn<>("Год");
        yearColumn1.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Purchases, String> volumeEColumn1 = new TableColumn<>("Объем двигателя");
        volumeEColumn1.setCellValueFactory(new PropertyValueFactory<>("volumeE"));

        brandColumn.setPrefWidth(100);
        modelColumn.setPrefWidth(60);
        generationColumn.setPrefWidth(80);
        priceColumn.setPrefWidth(55);
        transmissionColumn.setPrefWidth(90);
        fuelColumn.setPrefWidth(53);
        yearColumn.setPrefWidth(52);
        volumeEColumn.setPrefWidth(90);

        userCarTable.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
        userColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        brandColumn1.setMaxWidth( 1f * Integer.MAX_VALUE * 30);
        modelColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        generationColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        priceColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        transmissionColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        fuelColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        yearColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);
        volumeEColumn1.setMaxWidth(1f * Integer.MAX_VALUE * 30);

        autoTable.getColumns().add(brandColumn);
        autoTable.getColumns().add(modelColumn);
        autoTable.getColumns().add(generationColumn);
        autoTable.getColumns().add(priceColumn);
        autoTable.getColumns().add(transmissionColumn);
        autoTable.getColumns().add(fuelColumn);
        autoTable.getColumns().add(yearColumn);
        autoTable.getColumns().add(volumeEColumn);

        userCarTable.getColumns().add(userColumn1);
        userCarTable.getColumns().add(brandColumn1);
        userCarTable.getColumns().add(modelColumn1);
        userCarTable.getColumns().add(generationColumn1);
        userCarTable.getColumns().add(priceColumn1);
        userCarTable.getColumns().add(transmissionColumn1);
        userCarTable.getColumns().add(fuelColumn1);
        userCarTable.getColumns().add(yearColumn1);
        userCarTable.getColumns().add(volumeEColumn1);

        System.out.println("Auto table is full");
        autoTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(autoTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = autoTable.getSelectionModel().getSelectedItem();
                    brandField.setText(sel.getBrand());
                    modelField.setText(sel.getModel());
                    generationField.setText(sel.getGeneration());
                    priceField.setText(String.valueOf(sel.getPrice()));
                    transmissionField.setText(sel.getTransmission());
                    fuelField.setText(sel.getFuel());
                    yearField.setText(String.valueOf(sel.getYear()));
                    volumeEField.setText(String.valueOf(sel.getVolumeE()));
                    System.out.println("Selected car " + sel.getBrand() + " " + sel.getModel() + " " + sel.getGeneration());
                }
            }
        });
    }

    public void initUser() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = FXCollections.observableArrayList();

        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlUser = "SELECT * FROM autostore.users";

        ResultSet resultSetUser = connection.createStatement().executeQuery(sqlUser);
        userTable.getColumns().clear();
        while (resultSetUser.next()) {
            var user = new User(
                    resultSetUser.getInt("id"),
                    resultSetUser.getString("email"),
                    resultSetUser.getString("username"),
                    resultSetUser.getString("password"),
                    resultSetUser.getString("role")
            );
            users.add(user);
        }
        userTable.setItems(users);
        userTable.setPlaceholder(new Label("It seems, there are no products here!"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        roleColumn.setCellValueFactory(new PropertyValueFactory<>("admin"));

        emailColumn.setPrefWidth(100);
        usernameColumn.setPrefWidth(100);
        passwordColumn.setPrefWidth(100);
        roleField.setPrefWidth(100);

        userTable.getColumns().add(emailColumn);
        userTable.getColumns().add(usernameColumn);
        userTable.getColumns().add(passwordColumn);
        userTable.getColumns().add(roleColumn);

        System.out.println("Users table is full");
        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(userTable.getSelectionModel().getSelectedItem() != null)
                {
                    var sel = userTable.getSelectionModel().getSelectedItem();
                    emailField.setText(sel.getEmail());
                    usernameField.setText(sel.getUsername());
                    passwordField.setText(sel.getPassword());
                    roleField.setText(sel.getAdmin());
                    System.out.println("Selected user " + sel.getUsername());
                }
            }
        });
    }

    public void deleteCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedCar = autoTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null){
            try {
                System.out.println("Select car " + selectedCar.getBrand());
                Main.client.oos.writeInt(11);
                Main.client.oos.writeObject(selectedCar);
                Main.client.oos.flush();

                System.out.println("Auto " + selectedCar.getBrand() + " " + selectedCar.getModel() + " " + selectedCar.getGeneration() + " deleted from database");
                }catch (Exception e){
                e.printStackTrace();
            }
        }
        updateCar(mouseEvent);
    }

    public void deleteUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException{
        var selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            try {
                System.out.println("Select user" + selectedUser.getUsername());

                Main.client.oos.writeInt(8);
                Main.client.oos.writeObject(selectedUser);
                Main.client.oos.flush();

                System.out.println("User " + selectedUser.getUsername() + " deleted from database");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        updateUser(mouseEvent);
    }

    public void addCar(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var brand = brandField.getText();
        var model = modelField.getText();
        var generation = generationField.getText();
        var price = priceField.getText();
        var transmission = transmissionField.getText();
        var fuel = fuelField.getText();
        var year = yearField.getText();
        var volumeE = volumeEField.getText();

        if(!brand.equals("") && !model.equals("") && !generation.equals("") && !price.equals("") &&
            !transmission.equals("") && !fuel.equals("") && !year.equals("") && !volumeE.equals("")){

            try{
                Auto auto = new Auto(brand, model, generation, Integer.valueOf(price), transmission, fuel, Integer.valueOf(year), Float.valueOf(volumeE));
                Main.client.oos.writeInt(10);
                Main.client.oos.writeObject(auto);

                System.out.println("Auto " + brand + " " + model + " " + generation + " added to database");

                brandField.clear();;
                modelField.clear();
                generationField.clear();
                priceField.clear();
                transmissionField.clear();
                fuelField.clear();
                yearField.clear();
                volumeEField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(brand);
                alert.setHeaderText(null);
                alert.setContentText("Added " + brand + " " + model + " " + generation + " to database");
                alert.showAndWait();

            } catch (Exception e){
                e.printStackTrace();
            }

        }
        updateCar(mouseEvent);
    }

    public void addUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var email = emailField.getText();
        var username = usernameField.getText();
        var password = passwordField.getText();
        var role = roleField.getText();


        if(!email.equals("") && !username.equals("") && !password.equals("") && !role.equals("")){
            try{
                User user = new User(email, username, password, role);
                Main.client.oos.writeInt(9);
                Main.client.oos.writeObject(user);

                System.out.println("User " + username + " added to database");

                emailField.clear();;
                usernameField.clear();
                passwordField.clear();
                roleField.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(username);
                alert.setHeaderText(null);
                alert.setContentText("Added " + username + " to database");
                alert.showAndWait();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        updateUser(mouseEvent);
    }

    public void editUser(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedUser = userTable.getSelectionModel().getSelectedItem();
        var email = emailField.getText();
        var username = usernameField.getText();
        var password = passwordField.getText();
        var role = roleField.getText();
        if (selectedUser != null){
            if(!email.equals("") && !username.equals("") && !password.equals("") && !role.equals("")){
            try {
                System.out.println("Select user " + selectedUser.getUsername());
                DatabaseConnection connectDB = new DatabaseConnection();
                Connection connection = connectDB.getConnection();

                String sqlUser = "UPDATE users SET email = ?, username = ?, password = ?, role = ? WHERE users.id = " + selectedUser.getId();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlUser);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, role);
                preparedStatement.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            }
        }
        updateCar(mouseEvent);
    }

    public void editAuto(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        var selectedCar = autoTable.getSelectionModel().getSelectedItem();

        var brand = brandField.getText();
        var model = modelField.getText();
        var generation = generationField.getText();
        var price = priceField.getText();
        var transmission = transmissionField.getText();
        var fuel = fuelField.getText();
        var year = yearField.getText();
        var volumeE = volumeEField.getText();

        if(!brand.equals("") && !model.equals("") && !generation.equals("") && !price.equals("") &&
                !transmission.equals("") && !fuel.equals("") && !year.equals("") && !volumeE.equals("")){
                try {
                    System.out.println("Select car " + selectedCar.getId());
                    DatabaseConnection connectDB = new DatabaseConnection();
                    Connection connection = connectDB.getConnection();

                    String sqlAuto = "UPDATE auto SET brand = ?, model = ?, generation = ? WHERE auto.id = " + selectedCar.getId();
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlAuto);
                    preparedStatement.setString(1, brand);
                    preparedStatement.setString(2, model);
                    preparedStatement.setString(3, generation);
                    preparedStatement.executeUpdate();

                    String sqlDetail = "UPDATE detail SET price = " + price + ", transmission = '" + transmission + "', fuel = '" +
                            fuel + "', year = " + year + ", volumeE = " + volumeE + " WHERE detail.autoId = " + selectedCar.getId();
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sqlDetail);
                        preparedStatement1.executeUpdate();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        updateCar(mouseEvent);
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        System.out.println("Admin logOut");
        borderPane.getChildren().removeAll();
        borderPane.getChildren().setAll(root);
    }

    public void showDiagram(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectDB = new DatabaseConnection();
        Connection connection = connectDB.getConnection();

        String sqlAuto = "SELECT auto.brand, purchases.auto_id FROM autostore.purchases " +
                "INNER JOIN autostore.auto ON purchases.auto_id = auto.id";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlAuto);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<String> autos = new ArrayList<>();
        while (resultSet.next()) {
            autos.add(resultSet.getString("auto.brand"));
        }
        Stage primaryStage = new Stage();

        primaryStage.setTitle("My diagram");

        PieChart pieChart = new PieChart();
        Set<String> str = new HashSet<>(autos);
        autos.clear();
        autos.addAll(str);
        for (int i = 0; i < autos.size(); i++){
            pieChart.getData().add(new PieChart.Data(autos.get(i), countAuto(autos.get(i))));
        }
        Label lbl = new Label("");
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            lbl.setPrefWidth(20);
                            lbl.toFront();
                            lbl.setTranslateX(40);
                            lbl.setTranslateY(40);
                            lbl.setText(String.valueOf(data.getPieValue()));
                        }
                    });
        }

        pieChart.setLegendSide(Side.LEFT);

        primaryStage.setTitle("Статистика продаж");
        FlowPane pane = new FlowPane(lbl, pieChart);
        StackPane root1 = new StackPane(pane);

        Scene scene = new Scene(root1, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public int countAuto(String brand) throws SQLException, ClassNotFoundException {
        int number = 0;
        try{
            DatabaseConnection connectDB = new DatabaseConnection();
            Connection connection = connectDB.getConnection();

            String sqlAuto = "SELECT COUNT(auto.brand), purchases.auto_id FROM autostore.purchases " +
                    "INNER JOIN autostore.auto ON purchases.auto_id = auto.id " +
                    "WHERE auto.brand = '" + brand + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlAuto);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                number = resultSet.getInt("COUNT(auto.brand)");
            }
            System.out.println("Count auto brand - " + brand + " - " + number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return number;
    }
}
