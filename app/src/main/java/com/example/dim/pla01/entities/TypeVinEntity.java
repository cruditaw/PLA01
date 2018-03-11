package com.example.dim.pla01.entities;

import android.support.annotation.Size;

import java.io.Serializable;

/**
 * Created by dim on 08/02/2018.
 */

public class TypeVinEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private Integer tvinId;
    @Size(max = 100)
    private String tvinLibelle;

    public TypeVinEntity() {
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public TypeVinEntity clone() throws CloneNotSupportedException {
        TypeVinEntity tv = new TypeVinEntity();
        tv.setTvinId(tvinId);
        tv.setTvinLibelle(tvinLibelle);
        return tv;
    }

    public Integer getTvinId() {
        return tvinId;
    }

    public void setTvinId(Integer tvinId) {
        this.tvinId = tvinId;
    }

    public String getTvinLibelle() {
        return tvinLibelle;
    }

    public void setTvinLibelle(String tvinLibelle) {
        this.tvinLibelle = tvinLibelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeVinEntity that = (TypeVinEntity) o;

        if (tvinId != null ? !tvinId.equals(that.tvinId) : that.tvinId != null) return false;
        return tvinLibelle != null ? tvinLibelle.equals(that.tvinLibelle) : that.tvinLibelle == null;
    }

    @Override
    public int hashCode() {
        int result = tvinId != null ? tvinId.hashCode() : 0;
        result = 31 * result + (tvinLibelle != null ? tvinLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TypeVinEntity{" +
                "tvinId=" + tvinId +
                ", tvinLibelle='" + tvinLibelle + '\'' +
                '}';
    }
}
