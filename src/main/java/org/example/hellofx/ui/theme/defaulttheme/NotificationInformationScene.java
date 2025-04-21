package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import org.example.hellofx.controller.NotificationInformationController;
import org.example.hellofx.model.Noticement;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.model.enums.NotificationType;
import org.example.hellofx.repository.NoticementRepository;
import org.example.hellofx.repository.NotificationItemRepository;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.*;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class NotificationInformationScene extends Notificable{
    @Autowired
    private NotificationInformationController notificationInformationController;

    private Scene scene;

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<Resident> masterData;
    private TableView<Resident> table;

    private Pagination pagination;
    private VBox mainContent;
    private ScrollPane scrollPane;
    private Map<Integer, SimpleBooleanProperty> selectedMapUpdater;
    private Map<Integer, SimpleStringProperty> selectedMap;
    private NotificationItem noti;
    private List<Noticement> oldData;

    void reset() {
        masterData = null;
        selectedMap = null;
        selectedMapUpdater = null;

        table = null;
        pagination = null;
        scrollPane = null;
        mainContent = null;
    }

    protected Scene getCurrentScene() {
        return scene;
    }

    void reloadTable(Scene scene) {
        String condition = "";
        ComboBox<String> houseIdFilter = ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#houseIdFilter"));
        ComboBox<String> groupFilter = ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#groupFilter"));
        ComboBox<AccountType> roleFilter = ((ComboBox<AccountType>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#roleFilter"));
        TextField searchFilter = ((TextAndTextField) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#searchFilter")).getTextField();
        if (houseIdFilter.getValue() != null && !houseIdFilter.getValue().isEmpty()) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "r.house_id = '" + houseIdFilter.getValue() + "'";
        }
        if (notificationInformationController.getProfile().getRole() == AccountType.Resident) {
            if (!condition.isEmpty()) {
                condition += " and ";
            }
            condition = condition + "r.house_id = '" + notificationInformationController.getResident().getHouseId() + "'";
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
        masterData = notificationInformationController.residentQuery(query);
        resetPagination();
//        table.setItems(FXCollections.observableArrayList(dataBaseService.nativeResidentQuery(query)));
    }

    @Transactional
    public Scene getScene(Integer notiId) {
        reset();
        noti = notificationInformationController.getNotificationItemById(notiId);
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
//        mainContent.setPrefHeight(content.getPrefHeight());
        mainContent.setMinHeight(content.getPrefHeight());
//        mainContent.setMaxHeight(content.getPrefHeight());

        scrollPane.setPrefWidth(content.getPrefWidth());
        scrollPane.setMinWidth(content.getPrefWidth());
        scrollPane.setMaxWidth(content.getPrefWidth());
        scrollPane.setPrefHeight(content.getPrefHeight());
        scrollPane.setMinHeight(content.getPrefHeight());
        scrollPane.setMaxHeight(content.getPrefHeight());

        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setId("main-content");
        TextFlow section = new TextFlow(new Text("Thông tin hông báo:"));
        section.getStyleClass().add("big-text");
        mainContent.getChildren().addAll(section);
        mainContent.setPadding(new Insets(20, 20, 10, 20));

        VBox notificationInfo = new VBox();
        mainContent.getChildren().addAll(notificationInfo);
//        notificationInfo.setPrefHeight(mainContent.getPrefHeight() * 0.2);
//        VBox.setVgrow(notificationInfo, Priority.ALWAYS);
        notificationInfo.setMinWidth(mainContent.getPrefWidth() * 0.9);
        notificationInfo.setPrefWidth(mainContent.getPrefWidth() * 0.9);
        notificationInfo.setMaxWidth(mainContent.getPrefWidth() * 0.9);
        notificationInfo.setAlignment(Pos.CENTER_LEFT);
        notificationInfo.setPadding(new Insets(20, 50, 20, 50));
        notificationInfo.getStyleClass().add("public-profile");
        mainContent.setSpacing(20);
        notificationInfo.setSpacing(20);

//        ComboBox<NotificationType> notiType = new ComboBox<>(FXCollections.observableArrayList(NotificationType.values()));
//        notiType.getSelectionModel().select(NotificationType.Info);
//        notiType.setEditable(false);
        List<Badge> badges = new ArrayList<>();
        badges.add(new Badge("Info", MaterialDesignI.INFORMATION_OUTLINE, Color.valueOf("#0969da")));
        badges.add(new Badge("Success", MaterialDesignC.CHECK_CIRCLE_OUTLINE, Color.valueOf("#1f823b")));
        badges.add(new Badge("Warning", MaterialDesignA.ALERT_OUTLINE, Color.valueOf("#9a6801")));
        badges.add(new Badge("Danger", MaterialDesignA.ALERT_CIRCLE_OUTLINE, Color.valueOf("#d2313c")));
        ComboBox<Badge> notiType = new ComboBox<>(FXCollections.observableArrayList(badges));
        notiType.setButtonCell(new BadgeCell()); // Set button appearance
        notiType.setCellFactory(c -> new BadgeCell()); // Set dropdown appearance
        for (int i = 0; i < badges.size(); i++) {
            if (badges.get(i).text().equals(noti.getType())) {
                notiType.getSelectionModel().select(i);
            }
        }
        notificationInfo.getChildren().add(new VerticleTextAndComboBox("Loại: ", notiType, "Enter the type of notification", "noti-type-info", true));
        notificationInfo.getChildren().add(new VerticleTextAndTextField("Tiêu đề:", noti.getTitle(), "enter the title of the notification", "noti-title-info", true));
        notificationInfo.getChildren().add(new VerticleTextAndTextArea("Nội dung thông baáo: ", noti.getMessage(), "enter the message of the notification", "noti-message-info", true));

        mainContent.getChildren().add(new Separator(Orientation.HORIZONTAL));
        TextFlow section2 = new TextFlow(new Text("Đối tượng được thông báo:"));
        section2.getStyleClass().add("big-text");
        mainContent.getChildren().addAll(section2);


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

        filter.getChildren().add(new TextComboBox<String>("Theo phòng: ", notificationInformationController.getAllHouseIds(), true, 100, "houseIdFilter"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Theo nhóm: ", FXCollections.observableArrayList(), true, 100, "groupFilter"));
        if (notificationInformationController.getProfile().getRole() != AccountType.Resident) {
            TextComboBox<AccountType> role = new TextComboBox<AccountType>("Theo quyền: ", FXCollections.observableArrayList(AccountType.values()), false, 140, "roleFilter", true);
            role.getComboBox().setValue(AccountType.Resident);
            filter.getChildren().add(new Separator(Orientation.VERTICAL));
            filter.getChildren().add(role);
            ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#roleFilter")).setOnAction(event -> {
                reloadTable(scene);
            });
        }
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));


        ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#houseIdFilter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((ComboBox<String>) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#groupFilter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((TextAndTextField) ((ScrollPane) scene.lookup("ScrollPane")).getContent().lookup("#searchFilter")).getTextField().setOnAction(event -> {
            reloadTable(scene);
        });

        HBox actionContainer = new HBox();
        actionContainer.setId("action-container");
        actionContainer.getChildren().addAll(new Button("Thêm"), new Button("Gỡ"));
        actionContainer.getChildren().get(0).getStyleClass().add("action-button");
        actionContainer.getChildren().get(0).setId("add-all-button");
        actionContainer.getChildren().get(0).setStyle("-fx-background-color: rgba(164,42,0,255);");
        actionContainer.getChildren().get(1).getStyleClass().add("action-button");
        actionContainer.getChildren().get(1).setStyle("-fx-background-color: rgba(55,95,173,255);");
        actionContainer.getChildren().get(1).setId("rm-all-button");
        actionContainer.setSpacing(20);
        actionContainer.setPrefWidth(mainContent.getPrefWidth() * 0.9);
        actionContainer.setMaxWidth(mainContent.getPrefWidth() * 0.9);
        actionContainer.setMinWidth(mainContent.getPrefWidth() * 0.9);

        mainContent.getChildren().addAll(actionContainer);

        createTable();
        reloadTable(scene);

        Styles.toggleStyleClass(table, Styles.STRIPED);
        table.setPrefWidth(notificationInfo.getPrefWidth());
        table.setMaxWidth(notificationInfo.getPrefWidth());
        table.setMinWidth(notificationInfo.getPrefWidth());
        mainContent.getChildren().addAll(table, pagination);

        HBox createButtonContainer = new HBox();
        Button savebutton = new Button("Tạo thông báo");
        savebutton.setId("save-button");
        createButtonContainer.getChildren().add(savebutton);
        createButtonContainer.setPrefWidth(mainContent.getPrefWidth() * 0.9);
        createButtonContainer.setMaxWidth(mainContent.getPrefWidth() * 0.9);
        createButtonContainer.setMinWidth(mainContent.getPrefWidth() * 0.9);
        savebutton.setStyle("-fx-background-color: #4abc96 !important;");
        mainContent.getChildren().add(new Separator(Orientation.HORIZONTAL));
        mainContent.getChildren().addAll(createButtonContainer);
        savebutton.setOnAction(actionEvent -> {
            String title = ((VerticleTextAndTextField) mainContent.lookup("#noti-title-info")).getTextField().getText();
            if (title == null) {
                showPopUpMessage("Error", "Tiêu đề thông báo không được để trống!");
                return;
            }
            String message = ((VerticleTextAndTextArea) mainContent.lookup("#noti-message-info")).getTextArea().getText();
            if (message == null) {
                showPopUpMessage("Error", "Nội dung thông báo không được để trống!");
                return;
            }
            NotificationType type = NotificationType.valueOf(notiType.getValue().text());

            NotificationItem newNoti = new NotificationItem(title, message, notiType.getValue().text());
            newNoti.setId(noti.getId());

            List<Integer> dsIn = new ArrayList<>();
            List<Integer> dsOut = new ArrayList<>();
            TreeSet<Integer> lonn = new TreeSet<>();

            for (Noticement noticement : oldData) {
                lonn.add(noticement.getResidentId());
            }
            selectedMap.forEach((k, v) -> {
                if (v.getValue().equals("Nhận")) {
                    if (!lonn.contains(k)) {
                        dsIn.add(k);
                    }
                }
            });

            for (Noticement noticement : oldData) {
                Integer residentId = noticement.getResidentId();
                if (!selectedMap.get(residentId).getValue().equals("Nhận")) {
                    dsOut.add(noticement.getNoticementId());
                }
            }

            notificationInformationController.saveButtonClicked(noti, newNoti, dsIn, dsOut);

            reset();
            notificationInformationController.reset(noti.getId());
            showPopUpMessage("Thành công", "Cập nhật thông báo thành công!");
        });
        return scene;
    }


    private void createTable () {
        var selectAll = new CheckBox();

        selectedMapUpdater = new TreeMap<>();
        selectedMap = new TreeMap<>();
        oldData = notificationInformationController.getNoticementsById(noti.getId());


        for (int i = 0; i < oldData.size(); i++) {
            selectedMap.computeIfAbsent(oldData.get(i).getResidentId(), k -> new SimpleStringProperty("Nhận"));
        }
        var col0 = new TableColumn<Resident, Boolean>();
        col0.setGraphic(selectAll);
        col0.setSortable(false);
        col0.setCellValueFactory(celldata -> {
            Integer id = celldata.getValue().getResidentId();
            return selectedMapUpdater.computeIfAbsent(id, k -> new SimpleBooleanProperty(false));
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

        var col3 = new TableColumn<Resident, String>("Phòng");
        col3.setCellValueFactory(
                c -> {
                    if (c.getValue().getHouseId() == null) {
                        return null;
                    }
                    return new SimpleStringProperty(c.getValue().getHouseId());
                }
        );
        var col4 = new TableColumn<Resident, String>("Thông báo");
        col4.setCellValueFactory(celldata -> {
            Integer id = celldata.getValue().getResidentId();
            return selectedMap.computeIfAbsent(id, k -> new SimpleStringProperty("Không nhận"));
        });
//        col4.setCellFactory(CheckBoxTableCell.forTableColumn(col4));
//        col4.setPrefWidth(60);

        if (table == null) {
            table = new TableView<Resident>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3, col4);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
        table.getSelectionModel().selectFirst();
        table.setId("resident-table");
        table.setRowFactory(tv -> {
            TableRow<Resident> row = new TableRow<>();
            return row;
        });
//        Styles.toggleStyleClass(table, Styles.STRIPED);
        if (notificationInformationController.getProfile().getRole() == AccountType.Admin || notificationInformationController.getProfile().getRole() == AccountType.Client) {
            table.setEditable(true);
        }
        else {
            selectAll.setDisable(true);
        }
        selectAll.setOnAction(event -> {
            table.getItems().forEach(item -> {
                selectedMapUpdater.get(item.getResidentId()).set(selectAll.isSelected());
            });
        });
        ((Button) mainContent.lookup("#add-all-button")).setOnAction(event -> {
            table.getItems().forEach(item -> {
                if (selectedMapUpdater.get(item.getResidentId()).getValue()) {
                    selectedMap.get(item.getResidentId()).setValue("Nhận");
                    selectedMapUpdater.get(item.getResidentId()).setValue(false);
                }
            });
            selectAll.setSelected(false);
        });
        ((Button) mainContent.lookup("#rm-all-button")).setOnAction(event -> {
            table.getItems().forEach(item -> {
                if (selectedMapUpdater.get(item.getResidentId()).getValue()) {
                    selectedMap.get(item.getResidentId()).set("Không nhận");
                    selectedMapUpdater.get(item.getResidentId()).setValue(false);
                }
            });
            selectAll.setSelected(false);
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
