package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.NotificationItem;
import org.example.hellofx.model.Resident;

import java.util.List;

public interface NotificationInformationController {
    public Account getProfile();

    public Resident getResident();

    public void saveButtonClicked(NotificationItem oldnoti, NotificationItem newNoti, List<Integer> dsIn, List<Integer> dsOut);

    public void reset(Integer id);
}
