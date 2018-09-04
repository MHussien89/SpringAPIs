/**
 *
 */
package com.sblox.dataaccess.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Mohamed.ElBayoumy@vodafone.com
 *
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class IEntity implements Serializable {

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "modified")
    private Date modified;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "created")
    private Date created;

    /**
     * @return the modified
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

}
