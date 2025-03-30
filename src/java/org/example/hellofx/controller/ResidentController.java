package org.example.hellofx.controller;

import org.example.hellofx.model.Resident;

import java.util.List;

public interface ResidentController {

    /**
     * query the resident list
     * @return a list of residents
     */
    public List<Resident> getResidentList();

    /**
     * serr more information of user with id
     * @param id
     */
    public void seeMoreInformation(int id);
}
