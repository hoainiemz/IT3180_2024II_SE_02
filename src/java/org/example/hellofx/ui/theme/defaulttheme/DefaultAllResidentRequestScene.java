package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.example.hellofx.controller.AllResidentRequestController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.AllResidentRequestScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultAllResidentRequestScene implements AllResidentRequestScene {
    @Autowired
    AllResidentRequestController allResidentRequestController;
    @Autowired
    private DataBaseService dataBaseService;

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<Account> masterData;
    private TableView<Account> table;
    private Pagination pagination;
    private VBox mainContent;
    private Scene scene;
    private Notification info;

    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    void reloadTable() {
        String condition = "r.user_id IS NULL";
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();
        if (searchFilter.getText() != null && !searchFilter.getText().isEmpty()) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "(LOWER(a.email) LIKE LOWER('%" + searchFilter.getText() + "%') or LOWER(a.phone) LIKE LOWER('%" + searchFilter.getText() + "%') or LOWER(a.username) LIKE LOWER('%" + searchFilter.getText() + "%'))";
        }
        String query = "SELECT a.* FROM account a LEFT JOIN resident r ON a.user_id = r.user_id";
        if (!condition.isEmpty()) {
            query += " WHERE " + condition;
        }
        query += ';';
//        TableView<Account> table = (TableView) scene.lookup("#resident-table");
        table.getItems().clear();
        masterData = FXCollections.observableArrayList(dataBaseService.nativeAccountQuery(query));
        System.out.println(masterData);
        resetPagination();
    }

    @Override
    public Scene getAllResidentRequestScene() {
        reset();
        scene = JavaFxApplication.getCurrentScene();
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
        HBox searchBar = new HBox(new TextFlow(new Text("Các yêu cầu gia nhập:")));
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
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));

        ((TextAndTextField) scene.lookup("#searchFilter")).getTextField().setOnAction(event -> {
            reloadTable();
        });

        createTable();
        reloadTable();

        mainContent.getChildren().addAll(table, pagination);

        return scene;
    }

    private void createTable () {

        if (table == null) {
            table = new TableView<Account>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        var col0 = new TableColumn<Account, String>("Tên tài khoản");
        col0.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getUsername())
        );
        var col1 = new TableColumn<Account, String>("Địa chỉ email");
        col1.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getEmail())
        );
        var col2 = new TableColumn<Account, String>("Số điện thoại");
        col2.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getPhone())
        );

        var col3 = new TableColumn<Account, Void>("Hoạt động");
        col3.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button("Chấp nhận yêu cầu");

            {
                actionButton.setOnAction(event -> {
                    Account account = getTableView().getItems().get(getIndex());
                    Resident newResident = new Resident();
                    newResident.setUserId(account.getUserId());
                    allResidentRequestController.acceptButtonClicked(newResident);
                    reloadTable();
                    showPopUpMessage("OK!", "Đã chấp nhận yêu cầu tham gia của cư dân mới!");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actionButton);
                }
            }
        });

        table.getColumns().setAll(col0, col1, col2, col3);
        table.getSelectionModel().selectFirst();
        table.setId("resident-table");
        table.setRowFactory(tv -> {
            TableRow<Account> row = new TableRow<>();
            return row;
        });
        Styles.toggleStyleClass(table, Styles.STRIPED);
        if (allResidentRequestController.getProfile().getRole() == AccountType.Admin || allResidentRequestController.getProfile().getRole() == AccountType.Client) {
            table.setEditable(true);
        }
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
        ObservableList<Account> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }

    private void showPopUpMessage(String state, String message) {
        StackPane content = (StackPane) scene.lookup("#content");
        if (info == null) {
            info = new Notification(message);
            info.getStyleClass().add(Styles.ELEVATED_1);
            StackPane.setAlignment(info, Pos.BOTTOM_RIGHT);
            StackPane.setMargin(info, new Insets(10, 10, 30, 10));
            info.setMaxHeight(100);
        }
        else {
            info.setMessage(message);
            try {
                content.getChildren().remove(info);
            }
            catch (NullPointerException e) {
            }
        }
        try {
            info.getStyleClass().remove(Styles.WARNING);
        }
        catch (NullPointerException e) {}
        try {
            info.getStyleClass().remove(Styles.SUCCESS);
        }
        catch (NullPointerException e) {}
        if (state.equals("Error changing password")) {
            info.getStyleClass().add(Styles.WARNING);
            info.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE));
        }
        else {
            info.getStyleClass().add(Styles.SUCCESS);
            info.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE));
        }
        info.setOnClose(event -> {
            content.getChildren().remove(info);
        });
        content.getChildren().add(info);
    }
}
