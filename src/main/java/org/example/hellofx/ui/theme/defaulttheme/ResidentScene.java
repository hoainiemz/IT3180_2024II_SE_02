package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.theme.Styles;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.example.hellofx.controller.ResidentController;
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
public class ResidentScene implements ThemeScene {
    @Autowired
    ResidentController residentController;

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<Resident> masterData;
    private TableView<Resident> table;
    private Pagination pagination;
    private VBox mainContent;

    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    void reloadTable(Scene scene) {
        String condition = "";
        ComboBox<String> houseIdFilter = ((ComboBox<String>) scene.lookup("#houseIdFilter"));
        ComboBox<String> groupFilter = ((ComboBox<String>) scene.lookup("#groupFilter"));
        ComboBox<AccountType> roleFilter = ((ComboBox<AccountType>) scene.lookup("#roleFilter"));
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();
        if (houseIdFilter.getValue() != null && !houseIdFilter.getValue().isEmpty()) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "r.house_id = '" + houseIdFilter.getValue() + "'";
        }
        if (residentController.getProfile().getRole() == AccountType.Resident) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "r.house_id = '" + residentController.getResident().getHouseId() + "'";
        }
        if (roleFilter != null && roleFilter.getValue() != null) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "a.role = '" + roleFilter.getValue() + "'";
        }
//        if (groupFilter.getValue() != null && !groupFilter.getValue().isEmpty()) {
//            if (!condition.isEmpty()) {
//                condition += " and ";
//            }
//            condition = condition + "groupId = '" + groupFilter.getValue() + "'";
//        }
        if (searchFilter.getText() != null && !searchFilter.getText().isEmpty()) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "(LOWER(r.first_name) LIKE LOWER('%" + searchFilter.getText() + "%') or LOWER(r.last_name) LIKE LOWER('%" + searchFilter.getText() + "%'))";
        }
        String query = "SELECT r.* FROM resident r JOIN account a ON r.user_id = a.user_id";
        if (!condition.isEmpty()) {
            query += " WHERE " + condition;
        }
        query += ';';
        TableView<Resident> table = (TableView) scene.lookup("#resident-table");
//        table.getItems().clear();
        masterData = residentController.residentQuery(query);
        resetPagination();
//        table.setItems(FXCollections.observableArrayList(dataBaseService.nativeResidentQuery(query)));
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
        HBox searchBar = new HBox(new TextFlow(new Text("Danh sách dân cư:")));
        searchBar.getStyleClass().add("big-text");
        mainContent.setPadding(new Insets(20, 50, 10, 50));
//        HBox mainContent = new HBox();
        searchBar.setMaxHeight(container.getPrefHeight() * 0.1);
        mainContent.getChildren().addAll(searchBar);
//        searchBar.getChildren().add(new IconTextField("images/search.png", "Tìm kiếm", false, searchBar.getMaxHeight() * 0.5));
        ((TextFlow) searchBar.getChildren().get(0)).setPrefWidth(mainContent.getPrefWidth() * 0.7);
//        ((HBox) searchBar.getChildren().get(1)).setPrefWidth(mainContent.getPrefWidth() * 0.3);
//        ((HBox) searchBar.getChildren().get(1)).setAlignment(Pos.CENTER_RIGHT);

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

//        filter.getChildren().add(new TextComboBox<AccountType>("Theo trạng thái user: ", FXCollections.observableArrayList(AccountType.Admin, AccountType.Client, AccountType.Resident), false, 150));
//        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Theo phòng: ", residentController.getAllHouseIds(), true, 100, "houseIdFilter"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Theo nhóm: ", FXCollections.observableArrayList(), true, 100, "groupFilter"));
        if (residentController.getProfile().getRole() != AccountType.Resident) {
            TextComboBox<AccountType> role = new TextComboBox<AccountType>("Theo quyền: ", FXCollections.observableArrayList(AccountType.Client, AccountType.Resident), false, 140, "roleFilter");
            role.getComboBox().setValue(AccountType.Resident);
            filter.getChildren().add(new Separator(Orientation.VERTICAL));
            filter.getChildren().add(role);
            ((ComboBox<String>) scene.lookup("#roleFilter")).setOnAction(event -> {
                reloadTable(scene);
            });
        }
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));


        ((ComboBox<String>) scene.lookup("#houseIdFilter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((ComboBox<String>) scene.lookup("#groupFilter")).setOnAction(event -> {
            reloadTable(scene);
        });

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
        var col0 = new TableColumn<Resident, Boolean>();
        col0.setGraphic(selectAll);
        col0.setSortable(false);
        col0.setCellValueFactory(celldata -> {
            Integer id = celldata.getValue().getResidentId();
            return selectedMap.computeIfAbsent(id, k -> new SimpleBooleanProperty(false));
        });
        col0.setCellFactory(CheckBoxTableCell.forTableColumn(col0));
        col0.setEditable(true);
        col0.setPrefWidth(60);

        var col1 = new TableColumn<Resident, String>("Họ");
        col1.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getLastName())
        );

        var col2 = new TableColumn<Resident, String>("Tên");
        col2.setCellValueFactory(
                c -> {
                    if (c.getValue().getFirstName() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getFirstName());
                }
        );

        var col3 = new TableColumn<Resident, String>("Giới tính");
        col3.setCellValueFactory(
                c -> {
                    if (c.getValue().getGender() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getGender().toString());
                }
        );

        var col4 = new TableColumn<Resident, String>("Ngày sinh");
        col4.setCellValueFactory(
                c -> {
                    if (c.getValue().getDateOfBirth() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getDateOfBirth().toString());
                }
        );

        var col5 = new TableColumn<Resident, String>("Phòng");
        col5.setCellValueFactory(
                c -> {
                    if (c.getValue().getHouseId() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getHouseId());
                }
        );

        if (table == null) {
            table = new TableView<Resident>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3, col4, col5);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        table.getSelectionModel().selectFirst();
        table.setId("resident-table");
        table.setRowFactory(tv -> {
            TableRow<Resident> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Resident clickedResident = row.getItem();
                    residentController.seeMoreInformation(clickedResident.getUserId().intValue());
//                    System.out.println("Clicked on: " + clickedResident.getFirstName());
                }
            });
            return row;
        });
        Styles.toggleStyleClass(table, Styles.STRIPED);
        if (residentController.getProfile().getRole() == AccountType.Admin || residentController.getProfile().getRole() == AccountType.Client) {
            table.setEditable(true);
        }
        else {
            selectAll.setDisable(true);
        }
        selectAll.setOnAction(event -> {
            table.getItems().forEach(item -> {
                selectedMap.get(item.getResidentId()).set(selectAll.isSelected());
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
        ObservableList<Resident> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }
}
