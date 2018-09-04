package com.sblox.common.dto;

/**
 *
 * @author Mostafa Hussien
 */
public enum OrganizationStatus {

    ACTIVE(1), DEACTIVE(-1), PAYMENT_SUSPENDED(3);

    private int id;

    private OrganizationStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
