package com.mycompany.sfwe301fall24project_team4;

/**
 * This is the abstract base class for a registered user of the PMS.
 */
public abstract class User {
    protected String name;
    protected int ID;

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
