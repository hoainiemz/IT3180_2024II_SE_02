package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.hellofx.controller.LoginController;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.LoginScene;
import org.example.hellofx.utils.Effects;
import org.example.hellofx.utils.ScreenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.feather.Feather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

interface MyFun {
    public Node func(String a, String b, boolean c);
    public Node func(String a, String b, boolean c, String id);
};

@Component
@Primary
public class DefaultLoginScene implements LoginScene {
    @Autowired
    private LoginController loginController;

    private static final String ADVICE_API_URL = "https://api.adviceslip.com/advice";
    private Notification info;
    private Scene scene;


    public String getAdvice() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ADVICE_API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Extract advice from the JSON response
            String json = response.body();
            int adviceStart = json.indexOf("\"advice\": \"") + 11;
            int adviceEnd = json.indexOf("\"}", adviceStart);
            return json.substring(adviceStart, adviceEnd);

        } catch (Exception e) {
            return "Error fetching advice: " + e.getMessage();
        }
    }

    public Scene getLoginScene(){
        scene = JavaFxApplication.getCurrentScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/themes/default-theme/login/login.fxml"));
        try {
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException e) {
            return null;
        }
        scene.getStylesheets().add("/themes/default-theme/login/login.css");
        VBox leftFrame = (VBox) scene.lookup("#leftFrame");
        StackPane rightFrame = (StackPane) scene.lookup("#rightFrame");
        leftFrame.setPrefWidth(ScreenUtils.getScreenWidth() * 0.3);
        rightFrame.setPrefWidth(ScreenUtils.getScreenWidth() * 0.7);
        leftFrame.setOnMouseClicked(event -> {
            leftFrame.requestFocus(); // Move focus to the VBox
        });
        rightFrame.setOnMouseClicked(mouseEvent -> {
            rightFrame.requestFocus();
        });
        rightFrame.requestFocus();

        getLoginForm(scene);
        HBox rightSubFrame = new HBox();
        VBox rightPane = new VBox(rightSubFrame);
        rightSubFrame.getStyleClass().add("right-sub-frame");

        rightSubFrame.getStyleClass().add("tweet-card");

        rightFrame.setAlignment(Pos.CENTER);
        rightSubFrame.setMaxWidth(rightFrame.getPrefWidth() * 0.6);
//        rightSubFrame.setMaxHeight(ScreenUtils.getScreenHeight() * 0.6);
        // Quote Icon
        Text quoteIcon = new Text("❝");
        quoteIcon.getStyleClass().add("quote-icon");

        // Tweet Text
        Label tweetText = new Label(getAdvice());
        tweetText.setWrapText(true);
        tweetText.getStyleClass().add("tweet-text");
        rightSubFrame.getChildren().addAll(quoteIcon, tweetText);

        quoteIcon.setOnMouseClicked(event -> {
            tweetText.setText(getAdvice());
        });
        quoteIcon.getStyleClass().add("quote-icon");
        StackPane.setAlignment(rightPane, Pos.CENTER);
//        rightPane.setStyle("-fx-background-color: red;");
        rightPane.setAlignment(Pos.CENTER);
        rightFrame.getChildren().add(rightPane);
        return scene;
    }

    void getLoginForm(Scene scene) {
        VBox leftFrame = (VBox) scene.lookup("#leftFrame");
        leftFrame.setAlignment(Pos.TOP_CENTER);
        leftFrame.getChildren().clear();
        HBox huster = new HBox();
        huster.setId("huster");
        VBox bluemoon = new VBox();
        bluemoon.setId("bluemoon");
        VBox loginForm = new VBox();
        loginForm.setId("loginForm");
        VBox statusContainer = new VBox();
        statusContainer.setId("statusContainer");
        HBox buttonContainer = new HBox();
        buttonContainer.setId("buttonContainer");
        HBox shutDownContainer = new HBox();
        shutDownContainer.setId("shutDownContainer");
        HBox freeze = new HBox();
        freeze.setId("freeze");
        huster.setPrefHeight(ScreenUtils.getScreenHeight() * 0.1);
        bluemoon.setPrefHeight(ScreenUtils.getScreenHeight() * 0.3);
//        loginForm.setPrefHeight(ScreenUtils.getScreenHeight() * 0.4);
        buttonContainer.setPrefHeight(ScreenUtils.getScreenHeight() * 0.1);
        shutDownContainer.setPrefHeight(ScreenUtils.getScreenHeight() * 0.25);
//        leftFrame.setSpacing(10);

        Text logoLabel = new Text("Hanoi University of Science and Technology");
        Image image = new Image("images/logo.png");
        ImageView logo = new ImageView(image);
        logo.setPreserveRatio(true);
        logo.setFitHeight(huster.getPrefHeight() * 0.6);
//        huster.getChildren().addAll(logo, logoLabel);
        huster.setPadding(new Insets(0, 50, 0, 50));
        huster.setSpacing(10);
        huster.setAlignment(Pos.CENTER_LEFT);

        Text bluemoonLabel = new Text("Management Service");
        Image bluermoonLogoImage = new Image("images/blue-moon-cropped-logo.png");
        ImageView bluemoonLogo = new ImageView(bluermoonLogoImage);
        bluemoonLogo.setPreserveRatio(true);
        bluemoonLogo.setFitHeight(bluemoon.getPrefHeight() * 0.6);
        bluemoon.getChildren().addAll(bluemoonLogo, bluemoonLabel);
        bluemoon.setAlignment(Pos.TOP_CENTER);

        MyFun iconTextFieldMaker = new MyFun() {

            public HBox func(String imgUrl, String Prompt, boolean password) {
                HBox textFieldContainer = new HBox();
                textFieldContainer.getStyleClass().add("login-text-field");
                textFieldContainer.setPrefHeight(loginForm.getPrefHeight() * 0.15);
                textFieldContainer.setAlignment(Pos.CENTER);
                VBox.setMargin(textFieldContainer, new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));
                textFieldContainer.setPadding(new Insets(5, 10, 5, 10));
                Image img = new Image(imgUrl);
                ImageView icon = new ImageView(img);
                icon.setPreserveRatio(true);
                icon.setFitHeight(textFieldContainer.getPrefHeight() * 0.6);
                textFieldContainer.getChildren().addAll(icon);
                if (!password) {
                    TextField field = new TextField();
                    field.setPromptText(Prompt);
                    field.setMinWidth(leftFrame.getPrefWidth() * 0.8);
                    field.setPrefWidth(leftFrame.getPrefWidth() * 0.8);
                    field.setMaxWidth(leftFrame.getPrefWidth() * 0.8);
                    textFieldContainer.getChildren().addAll(field);
                }
                else {
                    PasswordField field = new PasswordField();
                    field.setPromptText(Prompt);
                    field.setMinWidth(leftFrame.getPrefWidth() * 0.8);
                    field.setPrefWidth(leftFrame.getPrefWidth() * 0.8);
                    field.setMaxWidth(leftFrame.getPrefWidth() * 0.8);
                    textFieldContainer.getChildren().addAll(field);
                }
                return textFieldContainer;
            }

            @Override
            public Node func(String a, String b, boolean c, String id) {
                return null;
            }
        };
        loginForm.setSpacing(20);
        loginForm.getChildren().add(iconTextFieldMaker.func("images/user-icon-grey.png", "Enter your username", false));
        loginForm.getChildren().add(iconTextFieldMaker.func("images/password-icon.png", "Enter your password", true));

        Text status = new Text("Invalid username or password!");
        status.setVisible(false);
        statusContainer.getChildren().add(status);
        VBox.setMargin(statusContainer, new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("register-button");
        buttonContainer.getChildren().addAll(loginButton, registerButton);
        VBox.setMargin(buttonContainer, new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));
        loginButton.setPrefWidth(leftFrame.getPrefWidth() * 0.4);
        registerButton.setPrefWidth(leftFrame.getPrefWidth() * 0.4);
        registerButton.setPrefHeight(buttonContainer.getPrefHeight() * 0.5);
        buttonContainer.setSpacing(20);
//        VBox.setMargin();
//        HBox.setMargin(buttonContainer, new Insets(200, 0, 0, 0));
//        leftFrame.setSpacing(20);

        loginButton.setOnAction(event -> {
            HBox hb = (HBox) loginForm.getChildren().get(0);
            TextField tf1 = (TextField) hb.getChildren().get(1);
            hb = (HBox) loginForm.getChildren().get(1);
            PasswordField tf2 = (PasswordField) hb.getChildren().get(1);
            try {
                String response = loginController.loginButtonClicked(tf1.getText(), tf2.getText());
                if (response == null) {
                    status.setVisible(true);
                }
            }
            catch (Exception e) {
                showPopUpMessage("Error", "Bạn cần phải được chấp nhận là một cư dân trước khi có thể sử dụng ứng dụng");
            }
        });
        registerButton.setOnMouseClicked(event -> {
            loginController.signUpButtonClicked();
        });
        registerButton.setOnMouseEntered(event -> {
            registerButton.setEffect(Effects.glowEffect);
        });
        registerButton.setOnMouseExited(event -> {
            registerButton.setEffect(null);
        });
        loginForm.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });

        Button shutDownButton = new Button("Đóng ứng dụng");
        shutDownButton.getStyleClass().addAll();
        shutDownContainer.getChildren().addAll(shutDownButton);
        shutDownContainer.setAlignment(Pos.CENTER_LEFT);
//        shutDownContainer.setStyle("-fx-background-color: yellow;");
        shutDownContainer.setPrefWidth(leftFrame.getPrefWidth() * 0.9);
        shutDownContainer.setMaxWidth(leftFrame.getPrefWidth() * 0.9);
        shutDownContainer.setMinWidth(leftFrame.getPrefWidth() * 0.9);
        shutDownButton.getStyleClass().add("shutdown-button"); // Link to CSS
        shutDownButton.setOnAction(e -> Platform.exit()); // Closes app


        VBox.setVgrow(freeze, Priority.ALWAYS);

        leftFrame.getChildren().addAll(huster, bluemoon, loginForm, statusContainer, buttonContainer, freeze, shutDownContainer);
        HBox hb = (HBox) loginForm.getChildren().get(0);
        TextField tf1 = (TextField) hb.getChildren().get(1);
        hb = (HBox) loginForm.getChildren().get(1);
        PasswordField tf2 = (PasswordField) hb.getChildren().get(1);



//        tf1.setText("tuanminhvippro");
//        tf2.setText("2005vtmvtm");
//        tf1.setText("admin");
//        tf2.setText("admin");
    }
    private void showPopUpMessage(String state, String message) {
        StackPane rightFrame = (StackPane) scene.lookup("#rightFrame");
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
                rightFrame.getChildren().remove(info);
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
        if (state.equals("Error")) {
            info.getStyleClass().add(Styles.WARNING);
            info.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE));
        }
        else {
            info.getStyleClass().add(Styles.SUCCESS);
            info.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE));
        }
        info.setOnClose(event -> {
            rightFrame.getChildren().remove(info);
        });
        rightFrame.getChildren().add(info);
    }
}

