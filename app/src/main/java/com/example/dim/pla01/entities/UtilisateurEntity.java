package com.example.dim.pla01.entities;

import android.support.annotation.Size;

import java.io.Serializable;

/**
 * Created by dim on 08/02/2018.
 */
public class UtilisateurEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private Integer utrId;
    @Size(max = 100)
    private String utrNom;
    @Size(max = 100)
    private String utrPrenom;
    @Size(max = 1000)
    private String utrMail;

    public UtilisateurEntity() {
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public UtilisateurEntity clone() throws CloneNotSupportedException {
        UtilisateurEntity u = new UtilisateurEntity();
        u.setUtrId(utrId);
        u.setUtrMail(utrMail);
        u.setUtrNom(utrNom);
        u.setUtrPrenom(utrPrenom);
        return u;
    }

    public Integer getUtrId() {
        return utrId;
    }

    public void setUtrId(Integer utrId) {
        this.utrId = utrId;
    }

    public String getUtrNom() {
        return utrNom;
    }

    public void setUtrNom(String utrNom) {
        this.utrNom = utrNom;
    }

    public String getUtrPrenom() {
        return utrPrenom;
    }

    public void setUtrPrenom(String utrPrenom) {
        this.utrPrenom = utrPrenom;
    }

    public String getUtrMail() {
        return utrMail;
    }

    public void setUtrMail(String utrMail) {
        this.utrMail = utrMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UtilisateurEntity that = (UtilisateurEntity) o;

        if (utrId != null ? !utrId.equals(that.utrId) : that.utrId != null) return false;
        if (utrNom != null ? !utrNom.equals(that.utrNom) : that.utrNom != null) return false;
        if (utrPrenom != null ? !utrPrenom.equals(that.utrPrenom) : that.utrPrenom != null)
            return false;
        return utrMail != null ? utrMail.equals(that.utrMail) : that.utrMail == null;
    }

    @Override
    public int hashCode() {
        int result = utrId != null ? utrId.hashCode() : 0;
        result = 31 * result + (utrNom != null ? utrNom.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UtilisateurEntity{" +
                "utrId=" + utrId +
                ", utrNom='" + utrNom + '\'' +
                ", utrPrenom='" + utrPrenom + '\'' +
                ", utrMail='" + utrMail + '\'' +
                '}';
    }
}
