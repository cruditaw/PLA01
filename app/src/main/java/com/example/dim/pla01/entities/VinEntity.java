package com.example.dim.pla01.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

import java.io.Serializable;
import java.util.Date;

@JsonPropertyOrder({"vinId", "vinLibelle", "vinType", "vinAnnee", "vinDomaine", "vinVigneron", "vinMiseBouteille"})
@JsonRootName(value = "vin")
@JsonSerializableSchema(id = "vinSchema")
public class VinEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @JsonValue
    private Integer vinId;
    @JsonValue
    private String vinLibelle;
    @JsonValue
    private String vinDomaine;
    @JsonValue
    private Integer vinAnnee;
    @JsonValue
    private Date vinMiseBouteille;
    @JsonValue
    private TypeVinEntity vinType;
    @JsonValue
    private VigneronEntity vinVigneron;

    public VinEntity() {
    }

    public VinEntity(Integer vinId) {
        this.vinId = vinId;
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public VinEntity clone() throws CloneNotSupportedException {
        VinEntity v = new VinEntity();
        v.setVinId(vinId);
        v.setVinAnnee(vinAnnee);
        v.setVinDomaine(vinDomaine);
        v.setVinLibelle(vinLibelle);
        v.setVinMiseBouteille(vinMiseBouteille);
        v.setVinType(vinType);
        v.setVinVigneron(vinVigneron);
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VinEntity vinEntity = (VinEntity) o;

        if (vinId != null ? !vinId.equals(vinEntity.vinId) : vinEntity.vinId != null) return false;
        if (vinLibelle != null ? !vinLibelle.equals(vinEntity.vinLibelle) : vinEntity.vinLibelle != null)
            return false;
        if (vinDomaine != null ? !vinDomaine.equals(vinEntity.vinDomaine) : vinEntity.vinDomaine != null)
            return false;
        if (vinAnnee != null ? !vinAnnee.equals(vinEntity.vinAnnee) : vinEntity.vinAnnee != null)
            return false;
        if (vinMiseBouteille != null ? !vinMiseBouteille.equals(vinEntity.vinMiseBouteille) : vinEntity.vinMiseBouteille != null)
            return false;
        if (vinType != null ? !vinType.equals(vinEntity.vinType) : vinEntity.vinType != null)
            return false;
        return vinVigneron != null ? vinVigneron.equals(vinEntity.vinVigneron) : vinEntity.vinVigneron == null;
    }

    @Override
    public int hashCode() {
        int result = vinId != null ? vinId.hashCode() : 0;
        result = 31 * result + (vinLibelle != null ? vinLibelle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VinEntity{" +
                "vinId=" + vinId +
                ", vinLibelle='" + vinLibelle + '\'' +
                ", vinDomaine='" + vinDomaine + '\'' +
                ", vinAnnee=" + vinAnnee +
                ", vinMiseBouteille=" + vinMiseBouteille +
                ", vinType=" + vinType +
                ", vinVigneron=" + vinVigneron +
                '}';
    }

    @JsonGetter
    public Integer getVinId() {
        return vinId;
    }

    @JsonSetter
    public void setVinId(Integer vinId) {
        this.vinId = vinId;
    }

    @JsonGetter
    public String getVinLibelle() {
        return vinLibelle;
    }

    @JsonSetter
    public void setVinLibelle(String vinLibelle) {
        this.vinLibelle = vinLibelle;
    }

    @JsonGetter
    public String getVinDomaine() {
        return vinDomaine;
    }

    @JsonSetter
    public void setVinDomaine(String vinDomaine) {
        this.vinDomaine = vinDomaine;
    }

    @JsonGetter
    public Integer getVinAnnee() {
        return vinAnnee;
    }

    @JsonSetter
    public void setVinAnnee(Integer vinAnnee) {
        this.vinAnnee = vinAnnee;
    }

    @JsonGetter
    public Date getVinMiseBouteille() {
        return vinMiseBouteille;
    }

    @JsonSetter
    public void setVinMiseBouteille(Date vinMiseBouteille) {
        this.vinMiseBouteille = vinMiseBouteille;
    }

    @JsonGetter
    public TypeVinEntity getVinType() {
        return vinType;
    }

    @JsonSetter
    public void setVinType(TypeVinEntity vinType) {
        this.vinType = vinType;
    }

    @JsonGetter
    public VigneronEntity getVinVigneron() {
        return vinVigneron;
    }

    @JsonSetter
    public void setVinVigneron(VigneronEntity vinVigneron) {
        this.vinVigneron = vinVigneron;
    }
}
