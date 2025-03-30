package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;

import java.util.List;

public interface NotificationCreationController {
    public Resident getResident();

    public Account getProfile();

    public void reset();

    public void createNotificationClicked(NotificationItem notification, List<Integer> ds);
}
