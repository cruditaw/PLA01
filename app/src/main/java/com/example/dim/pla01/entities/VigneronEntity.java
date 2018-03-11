package com.example.dim.pla01.entities;

import android.support.annotation.Size;

import java.io.Serializable;

/**
 * Created by dim on 08/02/2018.
 */

public class VigneronEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private Integer vignId;
    @Size(max = 100)
    private String vignLibelle;
    @Size(max = 100)
    private String vignAdresse;
    @Size(max = 100)
    private String vignAdresse1;
    @Size(max = 100)
    private String vignAdresse2;
    @Size(max = 100)
    private String vignVille;
    @Size(max = 50)
    private String vignCp;
    @Size(max = 50)
    private String vignTel;
    @Size(max = 1000)
    private String vignMail;

    public VigneronEntity() {
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public VigneronEntity clone() throws CloneNotSupportedException {
        VigneronEntity v = new VigneronEntity();
        v.setVignAdresse(vignAdresse);
        v.setVignAdresse1(vignAdresse1);
        v.setVignAdresse2(vignAdresse2);
        v.setVignCp(vignCp);
        v.setVignId(vignId);
        v.setVignLibelle(vignLibelle);
        v.setVignMail(vignMail);
        v.setVignTel(vignTel);
        v.setVignVille(vignVille);
        return v;
    }

    public Integer getVignId() {
        return vignId;
    }

    public void setVignId(Integer vignId) {
        this.vignId = vignId;
    }

    public String getVignLibelle() {
        return vignLibelle;
    }

    public void setVignLibelle(String vignLibelle) {
        this.vignLibelle = vignLibelle;
    }

    public String getVignAdresse() {
        return vignAdresse;
    }

    public void setVignAdresse(String vignAdresse) {
        this.vignAdresse = vignAdresse;
    }

    public String getVignAdresse1() {
        return vignAdresse1;
    }

    public void setVignAdresse1(String vignAdresse1) {
        this.vignAdresse1 = vignAdresse1;
    }

    public String getVignAdresse2() {
        return vignAdresse2;
    }

    public void setVignAdresse2(String vignAdresse2) {
        this.vignAdresse2 = vignAdresse2;
    }

    public String getVignVille() {
        return vignVille;
    }

    public void setVignVille(String vignVille) {
        this.vignVille = vignVille;
    }

    public String getVignCp() {
        return vignCp;
    }

    public void setVignCp(String vignCp) {
        this.vignCp = vignCp;
    }

    public String getVignTel() {
        return vignTel;
    }

    public void setVignTel(String vignTel) {
        this.vignTel = vignTel;
    }

    public String getVignMail() {
        return vignMail;
    }

    public void setVignMail(String vignMail) {
        this.vignMail = vignMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VigneronEntity that = (VigneronEntity) o;

        if (vignId != null ? !vignId.equals(that.vignId) : that.vignId != null) return false;
        if (vignLibelle != null ? !vignLibelle.equals(that.vignLibelle) : that.vignLibelle != null)
            return false;
        if (vignAdresse != null ? !vignAdresse.equals(that.vignAdresse) : that.vignAdresse != null)
            return false;
        if (vignAdresse1 != null ? !vignAdresse1.equals(that.vignAdresse1) : that.vignAdresse1 != null)
            return false;
        if (vignAdresse2 != null ? !vignAdresse2.equals(that.vignAdresse2) : that.vignAdresse2 != null)
            return false;
        if (vignVille != null ? !vignVille.equals(that.vignVille) : that.vignVille != null)
            return false;
        if (vignCp != null ? !vignCp.equals(that.vignCp) : that.vignCp != null) return false;
        if (vignTel != null ? !vignTel.equals(that.vignTel) : that.vignTel != null) return false;
        return vignMail != null ? vignMail.equals(that.vignMail) : that.vignMail == null;
    }

    @Override
    public int hashCode() {
        int result = vignId != null ? vignId.hashCode() : 0;
        result = 31 * result + (vignLibelle != null ? vignLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VigneronEntity{" +
                "vignId=" + vignId +
                ", vignLibelle='" + vignLibelle + '\'' +
                ", vignAdresse='" + vignAdresse + '\'' +
                ", vignAdresse1='" + vignAdresse1 + '\'' +
                ", vignAdresse2='" + vignAdresse2 + '\'' +
                ", vignVille='" + vignVille + '\'' +
                ", vignCp='" + vignCp + '\'' +
                ", vignTel='" + vignTel + '\'' +
                ", vignMail='" + vignMail + '\'' +
                '}';
    }
}
