package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.theme.Styles;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.example.hellofx.controller.BillResidentController;
import org.example.hellofx.dto.PaymentProjection;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.example.hellofx.model.enums.AccountType;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.ThemeScene;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextAndTextField;
import org.example.hellofx.ui.theme.defaulttheme.myhandmadenodes.TextComboBox;
import org.example.hellofx.utils.ScreenUtils;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Component
public class BillResidentScene implements ThemeScene {
    @Autowired
    private BillResidentController controller;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final int ITEMS_PER_PAGE = 9;
    private ObservableList<PaymentProjection> masterData;
    private TableView<PaymentProjection> table;
    private Pagination pagination;
    private VBox mainContent;
    private Scene scene;
    private Stage popupStage;


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
        masterData = controller.getPaymentByResidentFilters(kt1, kt2, kt3, searchFilter.getText());
        resetPagination();
    }

    public Scene getScene(Scene scene) {
        reset();
        this.scene = scene;
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

        filter.getChildren().add(new TextComboBox<String>("Trạng thái: ", FXCollections.observableArrayList("Tất cả", "Đã đóng", "Chưa đóng"), false, 150, "state-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Loại: ", FXCollections.observableArrayList("Tất cả", "Bắt buộc", "Không bắt buộc"), false, 200, "require-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextComboBox<String>("Hạn: ", FXCollections.observableArrayList("Tất cả", "Đã quá hạn", "Chưa quá hạn"), false, 180, "due-filter", false, "Tất cả"));
        filter.getChildren().add(new Separator(Orientation.VERTICAL));
        filter.getChildren().add(new TextAndTextField("Theo từ khóa: ", "", "Enter the search keyword", "searchFilter", true));


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

        var col0 = new TableColumn<PaymentProjection, String>("Hạn nộp(yyyy-MM-dd HH:mm)");

        col0.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDueDate() != null ? c.getValue().getDueDate().format(formatter) : ""
                )
        );

        var col1 = new TableColumn<PaymentProjection, String>("Phòng");

        col1.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getDueDate() != null ? c.getValue().getApartmentName() : ""
                )
        );

        var col2 = new TableColumn<PaymentProjection, String>("Loại");
        col2.setCellValueFactory(
                c -> {
                    if (c.getValue().getRequired().booleanValue()) {
                        return new SimpleStringProperty("Bắt buộc");
                    }
                    return new SimpleStringProperty("Không bắt buộc");
                }
        );

        var col3 = new TableColumn<PaymentProjection, String>("Nội dung khoản thu");
        col3.setCellValueFactory(
                c -> {
                    return new SimpleStringProperty(c.getValue().getContent());
                }
        );

        var col4 = new TableColumn<PaymentProjection, BigDecimal>("Số tiền(vnđ)");
        col4.setCellValueFactory(
                c -> {
                    return new SimpleObjectProperty<>(c.getValue().getAmount());
                }
        );

        var col5 = new TableColumn<PaymentProjection, String>("Trạng thái");
        col5.setCellValueFactory(
                c -> {
                    if (c.getValue().getPayTime() == null) {
                        return new SimpleStringProperty("Chưa đóng");
                    }
                    return new SimpleStringProperty("Đã đóng");
                }
        );

        var col6 = new TableColumn<PaymentProjection, HBox>("Thao tác");
        col6.setCellValueFactory(
                c -> {
                    FontIcon pencilIcon = new FontIcon(MaterialDesignP.PENCIL);
                    pencilIcon.setIconSize(16);        // size 16px
                    pencilIcon.setStyle("-fx-icon-color: " + "#5fa7fc" + ";");

                    FontIcon trashIcon = new FontIcon(MaterialDesignT.TRASH_CAN);
                    trashIcon.setIconSize(16);
                    trashIcon.setStyle("-fx-icon-color: " + "#fa4547" + ";");

                    Button btnEdit   = new Button("", pencilIcon);
                    Button btnDelete = new Button("", trashIcon);

                    btnEdit.getStyleClass().add("btn-edit");
                    btnDelete.getStyleClass().add("btn-delete");

                    HBox hbox = new HBox(5, btnEdit);

                    btnEdit.setOnAction(event -> {
                        showInfoPopup(JavaFxApplication.getCurrentStage(), c.getValue().getBillId());
                    });

                    return new SimpleObjectProperty(hbox);
                }
        );

        if (table == null) {
            table = new TableView<PaymentProjection>();
            pagination = new Pagination();
            //        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
            masterData = FXCollections.observableArrayList();
        }
        table.getColumns().setAll(col0, col1, col2, col3, col4, col5, col6);
        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
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
        ObservableList<PaymentProjection> pageData = FXCollections.observableArrayList(
                masterData.subList(fromIndex, toIndex));

        table.setItems(pageData);
    }


    private void showInfoPopup(Stage ownerStage, Integer billId) {
        // Tạo một stage mới cho popup
        popupStage = new Stage();

        // Áp dụng hiệu ứng blur cho nội dung chính
        GaussianBlur blur = new GaussianBlur(10); // Độ mờ có thể điều chỉnh
        mainContent.setEffect(blur);

        // Panel chứa nội dung popup
        StackPane popupContent = new StackPane();
        popupContent.setPrefWidth(ScreenUtils.getScreenWidth() * 0.8);
        popupContent.setPrefHeight(ScreenUtils.getScreenHeight() * 0.8);
        popupContent.setAlignment(Pos.TOP_CENTER);
        popupContent.setStyle("-fx-background-color: white; -fx-padding: 20px; -fx-background-radius: 10px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0.5, 0.0, 0.0);");
        popupContent.setId("content");

        // Lớp overlay bao phủ toàn màn hình
        AnchorPane overlay = new AnchorPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Màu đen với độ trong suốt 50%
        overlay.getChildren().add(popupContent);


        popupContent.translateXProperty().bind(
                overlay.widthProperty()
                        .subtract(popupContent.widthProperty())
                        .divide(2)
        );
        popupContent.translateYProperty().bind(
                overlay.heightProperty()
                        .subtract(popupContent.heightProperty())
                        .divide(2)
        );

        // Scene cho popup
        overlay.setId("container");
        Scene popupScene = controller.getBillInfoScene(new Scene(overlay), billId);
        popupScene.setFill(Color.TRANSPARENT);

        String popupCssPath = getClass().getResource("/themes/default-theme/home/home.css").toExternalForm();
        popupScene.getStylesheets().add(popupCssPath);

        // Cấu hình stage cho popup
        popupStage.initOwner(ownerStage);
        popupStage.initModality(Modality.APPLICATION_MODAL); // Chặn tương tác với cửa sổ chính
        popupStage.initStyle(StageStyle.TRANSPARENT);
        popupStage.setScene(popupScene);

        // Đảm bảo popup có kích thước giống với cửa sổ chính
        popupStage.setX(ownerStage.getX());
        popupStage.setY(ownerStage.getY());
        popupStage.setWidth(ownerStage.getWidth());
        popupStage.setHeight(ownerStage.getHeight());

        // Xử lý sự kiện đóng popup
        ((Button) ((ScrollPane) overlay.lookup("ScrollPane")).getContent().lookup("#close")).setOnAction(e -> {
            popupStage.close();
            reloadTable(scene);
            mainContent.setEffect(null); // Xóa hiệu ứng blur khi đóng popup
        });

        // Xử lý khi đóng popup bằng cách khác (X, Alt+F4)
        popupStage.setOnCloseRequest(e -> {
            mainContent.setEffect(null);
        });

        overlay.requestFocus();
        // Hiển thị popup
        popupStage.show();
    }
}
