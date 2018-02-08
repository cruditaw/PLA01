package com.example.dim.pla01.entities;

import android.support.annotation.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

import java.io.Serializable;

/**
 * Created by dim on 08/02/2018.
 */

@JsonPropertyOrder({"vignId", "vignLibelle", "vignAdresse", "vignAdresse1", "vignAdresse2", "vignVille", "vignCp", "vignTel", "vignMail"})
@JsonRootName(value = "vigneron")
@JsonSerializableSchema(id = "vigneronSchema")
public class VigneronEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @JsonValue
    private Integer vignId;
    @Size(max = 100)
    @JsonValue
    private String vignLibelle;
    @Size(max = 100)
    @JsonValue
    private String vignAdresse;
    @Size(max = 100)
    @JsonValue
    private String vignAdresse1;
    @Size(max = 100)
    @JsonValue
    private String vignAdresse2;
    @Size(max = 100)
    @JsonValue
    private String vignVille;
    @Size(max = 50)
    @JsonValue
    private String vignCp;
    @Size(max = 50)
    @JsonValue
    private String vignTel;
    @Size(max = 1000)
    @JsonValue
    private String vignMail;

    public VigneronEntity() {
    }

    @JsonCreator
    public VigneronEntity(Integer vignId) {
        this.vignId = vignId;
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

    @JsonGetter
    public Integer getVignId() {
        return vignId;
    }

    @JsonSetter
    public void setVignId(Integer vignId) {
        this.vignId = vignId;
    }

    @JsonGetter
    public String getVignLibelle() {
        return vignLibelle;
    }

    @JsonSetter
    public void setVignLibelle(String vignLibelle) {
        this.vignLibelle = vignLibelle;
    }

    @JsonGetter
    public String getVignAdresse() {
        return vignAdresse;
    }

    @JsonSetter
    public void setVignAdresse(String vignAdresse) {
        this.vignAdresse = vignAdresse;
    }

    @JsonGetter
    public String getVignAdresse1() {
        return vignAdresse1;
    }

    @JsonSetter
    public void setVignAdresse1(String vignAdresse1) {
        this.vignAdresse1 = vignAdresse1;
    }

    @JsonGetter
    public String getVignAdresse2() {
        return vignAdresse2;
    }

    @JsonSetter
    public void setVignAdresse2(String vignAdresse2) {
        this.vignAdresse2 = vignAdresse2;
    }

    @JsonGetter
    public String getVignVille() {
        return vignVille;
    }

    @JsonSetter
    public void setVignVille(String vignVille) {
        this.vignVille = vignVille;
    }

    @JsonGetter
    public String getVignCp() {
        return vignCp;
    }

    @JsonSetter
    public void setVignCp(String vignCp) {
        this.vignCp = vignCp;
    }

    @JsonGetter
    public String getVignTel() {
        return vignTel;
    }

    @JsonSetter
    public void setVignTel(String vignTel) {
        this.vignTel = vignTel;
    }

    @JsonGetter
    public String getVignMail() {
        return vignMail;
    }

    @JsonSetter
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
