package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.theme.Styles;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.example.hellofx.controller.NotificationManagementController;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.ThemeScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.Badge;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.BadgeCell;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextComboBox;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationManagementScene implements ThemeScene {
    @Autowired
    private NotificationManagementController notificationManagementController;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<NotificationItem> masterData;
    private TableView<NotificationItem> table;
    private Pagination pagination;
    private VBox mainContent;


    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    void reloadTable(Scene scene) {
        ComboBox<Badge> typeFilter = ((ComboBox<Badge>) scene.lookup("#type-filter"));
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();
        table.getItems().clear();
        for (int i = 0; i < 10; i++) {
            try {
                masterData = notificationManagementController.getNotifications(typeFilter.getValue().text(), searchFilter.getText());
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
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
        HBox searchBar = new HBox(new TextFlow(new Text("Danh sách các thông báo:")));
        searchBar.getStyleClass().add("big-text");
        mainContent.setPadding(new Insets(20, 50, 10, 50));
//        HBox mainContent = new HBox();
        searchBar.setMaxHeight(container.getPrefHeight() * 0.1);
        mainContent.getChildren().addAll(searchBar);
        ((TextFlow) searchBar.getChildren().get(0)).setPrefWidth(mainContent.getPrefWidth() * 0.7);

        HBox filter = new HBox();
        filter.setId("filter");
        Text boLoc = new Text("Bộ lọc:");
        boLoc.setStyle("-fx-font-weight: bold;");
        filter.getChildren().addAll(boLoc, new Separator(Orientation.VERTICAL));
        mainContent.getChildren().add(filter);
        filter.setPrefHeight(container.getPrefHeight() * 0.03);
        filter.setAlignment(Pos.CENTER_LEFT);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setSpacing(20);

        List<Badge> badges = new ArrayList<>();
        badges.add(new Badge("All", MaterialDesignF.FLAG_OUTLINE, Color.BLACK));
        badges.add(new Badge("Info", MaterialDesignI.INFORMATION_OUTLINE, Color.valueOf("#0969da")));
        badges.add(new Badge("Success", MaterialDesignC.CHECK_CIRCLE_OUTLINE, Color.valueOf("#1f823b")));
        badges.add(new Badge("Warning", MaterialDesignA.ALERT_OUTLINE, Color.valueOf("#9a6801")));
        badges.add(new Badge("Danger", MaterialDesignA.ALERT_CIRCLE_OUTLINE, Color.valueOf("#d2313c")));
        ComboBox<Badge> notiType = new ComboBox<>(FXCollections.observableArrayList(badges));
        notiType.setButtonCell(new BadgeCell()); // Set button appearance
        notiType.setCellFactory(c -> new BadgeCell()); // Set dropdown appearance
        notiType.getSelectionModel().selectFirst(); // Default selection
        filter.getChildren().add(new TextComboBox<Badge>("Loại: ", notiType, false, "type-filter", false));
//        filter.getChildren().add(new TextComboBox<String>("Loại: ", FXCollections.observableArrayList("Tất cả", "Info", "Success", "Warning", "Danger"), false, 200, "type-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));


        ((ComboBox<String>) scene.lookup("#type-filter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((TextAndTextField) scene.lookup("#searchFilter")).getTextField().setOnAction(event -> {
           reloadTable(scene);
        });

        createTable();
        reloadTable(scene);
//
        mainContent.getChildren().addAll(table, pagination);

        return scene;
    }

    private void createTable () {
        var selectAll = new CheckBox();

        var col0 = new TableColumn<NotificationItem, String>("Thời điểm tạo(yyyy-MM-dd HH:mm)");

        col0.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getCreatedAt() != null ? c.getValue().getCreatedAt().format(formatter) : ""
                )
        );


        var col1 = new TableColumn<NotificationItem, String>("Loại");
        col1.setCellValueFactory(
                c -> {
                    return new SimpleStringProperty(c.getValue().getType());
                }
        );

        var col2 = new TableColumn<NotificationItem, String>("Tiêu đề");
        col2.setCellValueFactory(
                c -> {
                    return new SimpleStringProperty(c.getValue().getTitle());
                }
        );

        var col3 = new TableColumn<NotificationItem, String>("Nội dung");
        col3.setCellValueFactory(
                c -> {
                    if (c.getValue().getMessage() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getMessage());
                }
        );

        if (table == null) {
            table = new TableView<NotificationItem>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        table.setRowFactory(tv -> {
            TableRow<NotificationItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    NotificationItem clickedNotificationItem = row.getItem();
                    notificationManagementController.seeNotificationItemInformation(clickedNotificationItem.getId());
//                    System.out.println("Clicked on: " + clickedResident.getFirstName());
                }
            });
            return row;
        });
        Styles.toggleStyleClass(table, Styles.STRIPED);
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
        ObservableList<NotificationItem> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }
}
