package com.sblox.dataaccess.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Mostafa Hussien
 */
@Entity
public class PasswordResetTrial extends IEntity {

    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date trialTime;

    public PasswordResetTrial() {
    }

    public PasswordResetTrial(Long userId) {
        this.userId = userId;
        this.trialTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(Date trialTime) {
        this.trialTime = trialTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
