package org.example.hellofx.ui.theme;

import javafx.scene.Scene;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;

public interface UserInformationScene {

    /**
     * @return the  scene of your application
     */
    public Scene getUserInformationScene(Account profile, Resident resident);
}

