package com.mycompany.sfwe301fall24project_team4;

/**
 * This class contains the extra user data for a pharmacy staff member.
 */
public class PharmacyStaff extends User {
    private String role; // Valid roles: Pharmacy Manager, Pharmacist, Pharmacist Technician, Cashier

    public PharmacyStaff() {

    }

    public PharmacyStaff(String name, int ID, String role) {
        this.name = name;
        this.ID = ID;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
