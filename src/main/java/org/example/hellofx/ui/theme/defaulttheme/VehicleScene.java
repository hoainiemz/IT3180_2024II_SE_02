package org.example.hellofx.ui.theme.defaulttheme;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.hellofx.controller.VehicleController;
import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Vehicle;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.model.enums.VehicleType;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.ThemeScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class VehicleScene extends Notificable implements ThemeScene {
    @Autowired
    VehicleController controller;

    private Scene scene;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<Vehicle> masterData;
    private TableView<Vehicle> table;
    private Pagination pagination;
    private VBox mainContent;
    private ScrollPane scrollPane;
    @Autowired
    private VehicleController vehicleController;

    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    @Override
    protected Scene getCurrentScene() {
        return scene;
    }

    public Scene getScene() {
        reset();
        scene = JavaFxApplication.getCurrentScene();
        HBox container = (HBox) scene.lookup("#container");
        StackPane content = (StackPane) scene.lookup("#content");
        content.getChildren().clear();
        mainContent = new VBox();

        scrollPane = new ScrollPane();
        scrollPane.setContent(mainContent);

        content.getChildren().addAll(scrollPane);
        mainContent.setPrefWidth(content.getPrefWidth());
        mainContent.setMinWidth(content.getPrefWidth());
        mainContent.setMaxWidth(content.getPrefWidth());
        mainContent.setMinHeight(content.getPrefHeight());

        scrollPane.setPrefWidth(content.getPrefWidth());
        scrollPane.setMinWidth(content.getPrefWidth());
        scrollPane.setMaxWidth(content.getPrefWidth());
        scrollPane.setPrefHeight(content.getPrefHeight());
        scrollPane.setMinHeight(content.getPrefHeight());
        scrollPane.setMaxHeight(content.getPrefHeight());

        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setId("main-content");
        TextFlow section = new TextFlow(new Text("Danh sách phương tiện:"));
        section.getStyleClass().add("big-text");
        mainContent.getChildren().addAll(section);
        mainContent.setPadding(new Insets(20, 20, 10, 20));
        mainContent.setSpacing(20);

        if (controller.getProfile().getRole() != AccountType.Resident) {
            HBox addnewContrainer = new HBox();
            Button addnew = new Button("Thêm phương tiện mới");
            addnewContrainer.getChildren().add(addnew);
            addnew.getStyleClass().add("addnew-button");
            mainContent.getChildren().add(addnewContrainer);
            addnewContrainer.setAlignment(Pos.CENTER_LEFT);
        }

        HBox filter = new HBox();
        filter.setId("filter");
        Text boLoc = new Text("BỘ LỌC");
        boLoc.setStyle("-fx-font-weight: bold;");
        filter.getChildren().addAll(boLoc, new Separator(Orientation.VERTICAL));
        mainContent.getChildren().add(filter);
        filter.setPrefHeight(container.getPrefHeight() * 0.03);
        filter.setAlignment(Pos.CENTER_LEFT);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setSpacing(20);

        filter.getChildren().add(new TextComboBox<String>("Theo phòng: ", controller.getAllApartmentName(), true, 100, "houseIdFilter"));
        filter.getChildren().addAll(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Theo loại: ", FXCollections.observableArrayList("Tất cả", VehicleType.Car.toString(), VehicleType.Motorbike.toString()), false, 150, "typeFilter"));
        filter.getChildren().addAll(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo biển số: ", "", "Enter the search keyword", "searchFilter", true));
        ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#typeFilter")).setValue("Tất cả");
        ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#houseIdFilter")).setOnAction(event -> {
            reloadTable(scene);
        });
        ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#typeFilter")).setOnAction(event -> {
            reloadTable(scene);
        });
        ((TextAndTextField) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#searchFilter")).getTextField().setOnAction(event -> {
            reloadTable(scene);
        });


        return scene;
    }

    public void reloadTable(Scene scene) {
        ComboBox<String> houseIdFilter = ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#houseIdFilter"));
        ComboBox<String> typeFilter = ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#typeFilter"));
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();
        String kt1 = houseIdFilter.getValue(), kt3 = searchFilter.getText();
        VehicleType kt2;
        switch (typeFilter.getValue()) {
            case "Car":
                kt2 = VehicleType.Car;
                break;
            case "Motorbike":
                kt2 = VehicleType.Motorbike;
                break;
            default:
                kt2 = null;
                break;
        }
        ObservableList<Vehicle> tableData = vehicleController.getVehiclesByFilter(kt1, kt2, kt3);
        System.out.println(tableData.size());
    }


}
