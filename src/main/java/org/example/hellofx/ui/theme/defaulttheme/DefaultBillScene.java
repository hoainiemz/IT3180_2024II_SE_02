package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.theme.Styles;
import javafx.beans.property.SimpleDoubleProperty;
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
import org.example.hellofx.controller.BillController;
import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.repository.PaymentRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.BillScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DefaultBillScene implements BillScene {
    @Autowired
    private BillController billController;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<ResidentBillPaymentDTO> masterData;
    private TableView<ResidentBillPaymentDTO> table;
    private Pagination pagination;
    private VBox mainContent;
    @Autowired
    private PaymentRepository paymentRepository;


    public void reset() {
        masterData = null;
        table = null;
        pagination = null;
        mainContent = null;
    }

    void reloadTable(Scene scene) {
        ComboBox<String> stateFilter = ((ComboBox<String>) scene.lookup("#state-filter"));
        ComboBox<String> requireFilter = ((ComboBox<String>) scene.lookup("#require-filter"));
        ComboBox<String> dueFilter = ((ComboBox<String>) scene.lookup("#due-filter"));
        TextField searchFilter = ((TextAndTextField) scene.lookup("#searchFilter")).getTextField();
        table.getItems().clear();
        int kt1 = 0, kt2 = 0, kt3 = 0;
        if (stateFilter.getValue().equals("Tất cả")) {
            kt1 = 0;
        }
        else {
            if (stateFilter.getValue().equals("Đã đóng")) {
                kt1 = 1;
            }
            else {
                kt1 = -1;
            }
        }
        if (requireFilter.getValue().equals("Tất cả")) {
            kt2 = 0;
        }
        else {
            if (requireFilter.getValue().equals("Bắt buộc")) {
                kt2 = 1;
            }
            else {
                kt2 = -1;
            }
        }
        if (dueFilter.getValue().equals("Tất cả")) {
            kt3 = 0;
        }
        else {

            if (dueFilter.getValue().equals("Đã quá hạn")) {
                kt3 = -1;
            }
            else {
                kt3 = 1;
            }
        }
//        masterData = FXCollections.observableArrayList(dataBaseService.getResidentPayments(showBillController.getResident().getResidentId()));
        masterData = FXCollections.observableArrayList(paymentRepository.findResidentBillsWithResidentFilters(billController.getResident().getResidentId(), kt1, kt2, kt3, searchFilter.getText()));
//        masterData = FXCollections.observableArrayList(repositoryImpl.executeRawSql2(query, ResidentBillPaymentDTO.class));
        resetPagination();
    }

    @Override
    public Scene getBillScene() {
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
        HBox searchBar = new HBox(new TextFlow(new Text("Danh sách các khoản thu:")));
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
        Text boLoc = new Text("Bộ lọc:");
        boLoc.setStyle("-fx-font-weight: bold;");
        filter.getChildren().addAll(boLoc, new Separator(Orientation.VERTICAL));
        mainContent.getChildren().add(filter);
        filter.setPrefHeight(container.getPrefHeight() * 0.03);
        filter.setAlignment(Pos.CENTER_LEFT);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setSpacing(20);

//        filter.getChildren().add(new TextComboBox<AccountType>("Theo trạng thái user: ", FXCollections.observableArrayList(AccountType.Admin, AccountType.Client, AccountType.Resident), false, 150));
//        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Trạng thái: ", FXCollections.observableArrayList("Tất cả", "Đã đóng", "Chưa đóng"), false, 150, "state-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Loại: ", FXCollections.observableArrayList("Tất cả", "Bắt buộc", "Không bắt buộc"), false, 200, "require-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Hạn: ", FXCollections.observableArrayList("Tất cả", "Đã quá hạn", "Chưa quá hạn"), false, 180, "due-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", null, "Enter the search keyword", "searchFilter", true));


        ((ComboBox<String>) scene.lookup("#state-filter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((ComboBox<String>) scene.lookup("#require-filter")).setOnAction(event -> {
            reloadTable(scene);
        });

        ((ComboBox<String>) scene.lookup("#due-filter")).setOnAction(event -> {
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

        var col0 = new TableColumn<ResidentBillPaymentDTO, String>("Hạn nộp(yyyy-MM-dd HH:mm)");

        col0.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDueDate() != null ? c.getValue().getDueDate().format(formatter) : ""
                )
        );

        var col1 = new TableColumn<ResidentBillPaymentDTO, String>("Loại");
        col1.setCellValueFactory(
                c -> {
                    if (c.getValue().getRequired().booleanValue()) {
                        return new SimpleStringProperty("Bắt buộc");
                    }
                    return new SimpleStringProperty("Không bắt buộc");
                }
        );

        var col2 = new TableColumn<ResidentBillPaymentDTO, String>("Nội dung khoản thu");
        col2.setCellValueFactory(
                c -> {
                    return new SimpleStringProperty(c.getValue().getContent());
                }
        );

        var col3 = new TableColumn<ResidentBillPaymentDTO, Double>("Số tiền(vnđ)");
        col3.setCellValueFactory(
                c -> {
                    if (c.getValue().getFee() == null) {
                        return null;
                    }
                    return new SimpleDoubleProperty(c.getValue().getFee()).asObject();
                }
        );

        var col4 = new TableColumn<ResidentBillPaymentDTO, String>("Trạng thái");
        col4.setCellValueFactory(
                c -> {
                    if (c.getValue().getPayTime() == null) {
                        return new SimpleStringProperty("Chưa đóng");
                    }
                    return new SimpleStringProperty("Đã đóng");
                }
        );

        if (table == null) {
            table = new TableView<ResidentBillPaymentDTO>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3, col4);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );
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
        ObservableList<ResidentBillPaymentDTO> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }
}
