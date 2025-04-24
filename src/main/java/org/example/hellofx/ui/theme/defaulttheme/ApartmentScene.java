package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.theme.Styles;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.example.hellofx.controller.ApartmentController;
import org.example.hellofx.controller.ResidentController;
import org.example.hellofx.dto.ApartmentCountProjection;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.ThemeScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;


@Component
public class ApartmentScene implements ThemeScene {
    @Autowired
    ApartmentController controller;

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<ApartmentCountProjection> masterData;
    private TableView<ApartmentCountProjection> table;
    private Pagination pagination;
    private VBox mainContent;

    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    void reloadTable(Scene scene) {
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();

        TableView<Resident> table = (TableView) scene.lookup("#resident-table");
        masterData = controller.getApartmentsAndResidentCount(searchFilter.getText());
        resetPagination();
    }

    public Scene getScene() {
        reset();
        Scene scene = JavaFxApplication.getCurrentScene();
        HBox container = (HBox) scene.lookup("#container");
        StackPane content = (StackPane) scene.lookup("#content");
        content.getChildren().clear();
        if (mainContent != null) {
            content.getChildren().add(mainContent);
            return scene;
        }
        content.setAlignment(Pos.TOP_CENTER);
        mainContent = new VBox();
        content.getChildren().addAll(mainContent);
        mainContent.setPrefWidth(content.getPrefWidth());
        mainContent.setMinWidth(content.getPrefWidth());
        mainContent.setMaxWidth(content.getPrefWidth());
        mainContent.setPrefHeight(content.getPrefHeight());
        mainContent.setMinHeight(content.getPrefHeight());
        mainContent.setMaxHeight(content.getPrefHeight());

        mainContent.setAlignment(Pos.TOP_CENTER);

        mainContent.getChildren().clear();
        mainContent.getStyleClass().clear();
        mainContent.getStyleClass().add("doi-mat-khau");
        HBox searchBar = new HBox(new TextFlow(new Text("Danh sách căn hộ:")));
        searchBar.getStyleClass().add("big-text");
        mainContent.setPadding(new Insets(20, 50, 10, 50));
        searchBar.setMaxHeight(container.getPrefHeight() * 0.1);
        mainContent.getChildren().addAll(searchBar);
        ((TextFlow) searchBar.getChildren().get(0)).setPrefWidth(mainContent.getPrefWidth() * 0.7);

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

        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));

        ((TextAndTextField) scene.lookup("#searchFilter")).getTextField().setOnAction(event -> {
            reloadTable(scene);
        });

        createTable();
        reloadTable(scene);

        mainContent.getChildren().addAll(table, pagination);

        return scene;
    }

    private void createTable () {
        var selectAll = new CheckBox();

        Map<Integer, SimpleBooleanProperty> selectedMap = new TreeMap<>();
        var col0 = new TableColumn<ApartmentCountProjection, Boolean>();
        col0.setGraphic(selectAll);
        col0.setSortable(false);
        col0.setCellValueFactory(celldata -> {
            Integer id = celldata.getValue().getApartmentId();
            return selectedMap.computeIfAbsent(id, k -> new SimpleBooleanProperty(false));
        });
        col0.setCellFactory(CheckBoxTableCell.forTableColumn(col0));
        col0.setEditable(true);

        var col1 = new TableColumn<ApartmentCountProjection, Integer>("Id căn hộ");
        col1.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getApartmentId()));

        var col2 = new TableColumn<ApartmentCountProjection, String>("Tên căn hộ");
        col2.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getApartmentName())
        );

        var col3 = new TableColumn<ApartmentCountProjection, Long>("Số dân");
        col3.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getResidentCount()));

        if (table == null) {
            table = new TableView<ApartmentCountProjection>();
            pagination = new Pagination();
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        table.setPrefWidth(mainContent.getPrefWidth() * 0.9);
        col0.setPrefWidth(table.getPrefWidth() * 0.05);
        col0.setMaxWidth(table.getPrefWidth() * 0.05);
        table.getSelectionModel().selectFirst();
        table.setId("resident-table");
        table.setRowFactory(tv -> {
            TableRow<ApartmentCountProjection> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    ApartmentCountProjection clicked = row.getItem();
                    controller.seeMoreInformation(clicked.getApartmentId());
//                    System.out.println("Clicked on: " + clickedResident.getFirstName());
                }
            });
            return row;
        });
        Styles.toggleStyleClass(table, Styles.STRIPED);
        if (controller.getProfile().getRole() == AccountType.Admin || controller.getProfile().getRole() == AccountType.Client) {
            table.setEditable(true);
        }
        else {
            selectAll.setDisable(true);
        }
        selectAll.setOnAction(event -> {
            table.getItems().forEach(item -> {
                selectedMap.get(item.getApartmentId()).set(selectAll.isSelected());
            });
        });
    }

    void resetPagination() {
        int pageCount = (masterData.size() / ITEMS_PER_PAGE) + ((masterData.size() % ITEMS_PER_PAGE != 0) ? 1 : 0);

        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public javafx.scene.Node call(Integer pageIndex) {
                updateTable(pageIndex);
                return new Label(); // This label is not used visually
            }
        });
    }

    private void updateTable(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, masterData.size());

        // Create a sublist for the current page
        ObservableList<ApartmentCountProjection> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }
}
