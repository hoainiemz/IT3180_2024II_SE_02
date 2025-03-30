package org.example.hellofx.ui.theme.defaulttheme;

import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.hellofx.controller.SignUpController;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.SignUpScene;
import org.example.hellofx.utils.ScreenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultSignUpScene implements SignUpScene {
    @Autowired
    private SignUpController signUpController;

    private Notification info;
    private Scene scene;

    public Scene getSignUpScene() {
        scene = JavaFxApplication.getCurrentScene();
        VBox leftFrame = (VBox) scene.lookup("#leftFrame");
        leftFrame.setAlignment(Pos.TOP_CENTER);
        leftFrame.getChildren().clear();
        leftFrame.setPadding(new Insets(60, 0, 0, 0));
        VBox welcomeFrame = new VBox();
        Text welcomeText = new Text("Get Started");
        welcomeText.getStyleClass().add("welcomeText");
        Text welcomeText2 = new Text("Create a new account");
        welcomeText2.getStyleClass().add("welcomeText2");
        welcomeFrame.getChildren().addAll(welcomeText, welcomeText2);

//        welcomeFrame.setAlignment(Pos.CENTER);
        welcomeFrame.setPadding(new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));
        welcomeFrame.setSpacing(5);
        leftFrame.setSpacing(30);

        VBox loginForm = new VBox();
        loginForm.setId("loginForm");

        MyFun iconTextFieldMaker = new MyFun() {
            @Override
            public Node func(String a, String b, boolean c) {
                return null;
            }

            public VBox func(String imgUrl, String Prompt, boolean password, String id) {
                VBox vBox = new VBox();
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
                Label status = new Label("OK!");
                status.getStyleClass().add("status-text");
                status.setVisible(false);
                status.setPadding(new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));
                vBox.getChildren().addAll(textFieldContainer, status);
                vBox.setId(id);
                return vBox;
            }
        };
        loginForm.setSpacing(15);
        loginForm.getChildren().add(iconTextFieldMaker.func("images/user-icon-grey.png", "Enter your username", false, "reg-username-field"));
        loginForm.getChildren().add(iconTextFieldMaker.func("images/password-icon.png", "Enter your password", true, "reg-password-field"));
        loginForm.getChildren().add(iconTextFieldMaker.func("images/email.png", "Enter your email", false, "reg-email-field"));
        loginForm.getChildren().add(iconTextFieldMaker.func("images/phone.png", "Enter your phone number", false, "reg-phone-field"));

        HBox buttonContainer = new HBox();
        buttonContainer.setId("buttonContainer");
        buttonContainer.setPrefHeight(ScreenUtils.getScreenHeight() * 0.1);

        Button signUpButton = new Button("Register");
        signUpButton.getStyleClass().add("login-button");
        Button backToLogin = new Button("Back to login");
        backToLogin.getStyleClass().add("register-button");
        buttonContainer.getChildren().addAll(signUpButton, backToLogin);
        VBox.setMargin(buttonContainer, new Insets(0, leftFrame.getPrefWidth() * 0.2, 0, leftFrame.getPrefWidth() * 0.2));
        signUpButton.setPrefWidth(leftFrame.getPrefWidth() * 0.4);
        backToLogin.setPrefWidth(leftFrame.getPrefWidth() * 0.4);
        backToLogin.setPrefHeight(buttonContainer.getPrefHeight() * 0.5);
        buttonContainer.setSpacing(20);

        backToLogin.setOnAction(actionEvent -> {
            signUpController.backToSignInClicked();
        });

        signUpButton.setOnAction(actionEvent -> {
            String username = ((TextField) ((HBox) ((VBox) loginForm.getChildren().get(0)).getChildren().get(0)).getChildren().get(1)).getText();
            String password = ((TextField) ((HBox) ((VBox) loginForm.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getText();
            String email = ((TextField) ((HBox) ((VBox) loginForm.getChildren().get(2)).getChildren().get(0)).getChildren().get(1)).getText();
            String phone = ((TextField) ((HBox) ((VBox) loginForm.getChildren().get(3)).getChildren().get(0)).getChildren().get(1)).getText();
            List<String> res = signUpController.signUpClicked(username, password, email, phone);
            Boolean kt = true;
            for (int i = 0; i < res.size(); i++) {
                VBox container = (VBox) loginForm.getChildren().get(i);
                Label status = (Label) container.getChildren().get(1);
                if (res.get(i).equals("OK!")) {
                    status.setTextFill(Color.valueOf("#549159"));
                }
                else {
                    status.setTextFill(Color.RED);
                    kt = false;
                }
                status.setText(res.get(i));
                status.setVisible(true);
            }
            if (kt) {
                showPopUpMessage("Registration Successfully", "Chúc mừng, tài khoản của bạn đã được đăng ký thành công!");
            }
            else {
                showPopUpMessage("Registration Failed", "Có lỗi xảy ra!");
            }
        });

        leftFrame.getChildren().addAll(welcomeFrame, loginForm, buttonContainer);
        leftFrame.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                signUpButton.fire();
            }
        });
        return scene;
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
        if (state.equals("Registration Failed")) {
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
