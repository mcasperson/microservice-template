package com.matthewcasperson.domain;

import com.yahoo.elide.annotation.ComputedAttribute;
import com.yahoo.elide.annotation.ReadPermission;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Auotgenerated JPA entity, with some additions to support Elide.
 * Notably we have added the @ReadPermission annotation, which will
 * only allow entities that pass the rule we have called
 * "Client supplied secret".
 */
@ReadPermission(expression = "Client supplied secret")
@Entity
@Table(name = "Microservice", schema = "MicroserviceDatabase")
public class MicroserviceKeyValue {

    private Integer id;
    private String key;
    private String value;
    private String secret;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MicroserviceKeyValue that = (MicroserviceKeyValue) o;

        if (id != that.id) {
            return false;
        }
        if (key != null ? !key.equals(that.key) : that.key != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Transient
    @ComputedAttribute
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
