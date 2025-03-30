package org.example.hellofx.ui;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.hellofx.SpringBootFxApplication;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.ui.theme.*;
import org.example.hellofx.ui.theme.NotificationInformationScene;
import org.example.hellofx.utils.ScreenUtils;

public class JavaFxApplication extends Application {
    private static Stage currentStage;

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static Scene getCurrentScene() {
        return currentStage.getScene();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        currentStage = stage;
//        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        currentStage.setTitle("Hello World");
        LoginScene theme = SpringBootFxApplication.context.getBean(LoginScene.class);
        showLoginScene();
        currentStage.setWidth(ScreenUtils.getScreenWidth());
        currentStage.setHeight(ScreenUtils.getScreenHeight());

        currentStage.initStyle(StageStyle.UNDECORATED); // Remove the upper bar
        currentStage.setFullScreen(true);
        currentStage.setFullScreenExitHint(""); // Disable the ESC message
        currentStage.setFullScreenExitKeyCombination(null); // Disable ESC key for exit

        currentStage.show();
        currentStage.show();
    }

    public static void showHomeScene(){
        HomeScene theme = SpringBootFxApplication.context.getBean(HomeScene.class);
        currentStage.setScene(theme.getHomeScene());
    }

    public static void showLoginScene(){
        LoginScene theme = SpringBootFxApplication.context.getBean(LoginScene.class);
        currentStage.setScene(theme.getLoginScene());
    }

    public static void showPasswordChangeScene(){
        PasswordChangeScene theme = SpringBootFxApplication.context.getBean(PasswordChangeScene.class);
        currentStage.setScene(theme.getPasswordChangeScene());
    }

    public static void showSignUpScene(){
        SignUpScene theme = SpringBootFxApplication.context.getBean(SignUpScene.class);
        currentStage.setScene(theme.getSignUpScene());
    }

    public static void showResidentListScene(){
        ResidentScene theme = SpringBootFxApplication.context.getBean(ResidentScene.class);
        currentStage.setScene(theme.getResidentScene());
    }

    public static void showUserInformationScene() {
        UserInformationScene theme = SpringBootFxApplication.context.getBean(UserInformationScene.class);
        ProfileController profileController = SpringBootFxApplication.context.getBean(ProfileController.class);
        currentStage.setScene(theme.getUserInformationScene(profileController.getProfile(), profileController.getResident()));
    }

    public static void showUserInformationScene(Account profile, Resident resident) {
        UserInformationScene theme = SpringBootFxApplication.context.getBean(UserInformationScene.class);
        currentStage.setScene(theme.getUserInformationScene(profile, resident));
    }

    public static void showBilCreationScene() {
        BillCreationScene theme = SpringBootFxApplication.context.getBean(BillCreationScene.class);
        currentStage.setScene(theme.getBillCreationScene());
    }

    public static void showAllResidentRequestScene() {
        AllResidentRequestScene theme = SpringBootFxApplication.context.getBean(AllResidentRequestScene.class);
        currentStage.setScene(theme.getAllResidentRequestScene());
    }

    public static void showBillScene() {
        BillScene theme = SpringBootFxApplication.context.getBean(BillScene.class);
        currentStage.setScene(theme.getBillScene());
    }

    public static void showBillManagerScene() {
        BillManagementScene theme = SpringBootFxApplication.context.getBean(BillManagementScene.class);
        currentStage.setScene(theme.getBillManagementScene());
    }

    public static void showBillInformationScene(Integer billId) {
        BillInformationScene theme = SpringBootFxApplication.context.getBean(BillInformationScene.class);
        currentStage.setScene(theme.getBillInformationScene(billId));
    }

    public static void showNotificationCreationScene() {
        NotificationCreationScene theme = SpringBootFxApplication.context.getBean(NotificationCreationScene.class);
        currentStage.setScene(theme.getNotificationCreationScene());
    }

    public static void showNotificationManagementScene() {
        NotificationManagementScene theme = SpringBootFxApplication.context.getBean(NotificationManagementScene.class);
        currentStage.setScene(theme.getNotificationManagementScene());
    }

    public static void showNotificationInformationScene(Integer notiId) {
        NotificationInformationScene theme = SpringBootFxApplication.context.getBean(NotificationInformationScene.class);
        currentStage.setScene(theme.getNotificationInformationScene(notiId));
    }
}
