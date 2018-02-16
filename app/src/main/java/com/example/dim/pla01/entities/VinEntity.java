package com.example.dim.pla01.entities;

import java.io.Serializable;
import java.util.Date;

public class VinEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private Integer vinId;
    private String vinLibelle;
    private String vinDomaine;
    private Integer vinAnnee;
    private Date vinMiseBouteille;
    private TypeVinEntity vinType;
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
                ", vinType=" + vinType.toString() +
                ", vinVigneron=" + vinVigneron.toString() +
                '}';
    }

    public Integer getVinId() {
        return vinId;
    }

    public void setVinId(Integer vinId) {
        this.vinId = vinId;
    }

    public String getVinLibelle() {
        return vinLibelle;
    }

    public void setVinLibelle(String vinLibelle) {
        this.vinLibelle = vinLibelle;
    }

    public String getVinDomaine() {
        return vinDomaine;
    }

    public void setVinDomaine(String vinDomaine) {
        this.vinDomaine = vinDomaine;
    }

    public Integer getVinAnnee() {
        return vinAnnee;
    }

    public void setVinAnnee(Integer vinAnnee) {
        this.vinAnnee = vinAnnee;
    }

    public Date getVinMiseBouteille() {
        return vinMiseBouteille;
    }

    public void setVinMiseBouteille(Date vinMiseBouteille) {
        this.vinMiseBouteille = vinMiseBouteille;
    }

    public TypeVinEntity getVinType() {
        return vinType;
    }

    public void setVinType(TypeVinEntity vinType) {
        this.vinType = vinType;
    }

    public VigneronEntity getVinVigneron() {
        return vinVigneron;
    }

    public void setVinVigneron(VigneronEntity vinVigneron) {
        this.vinVigneron = vinVigneron;
    }
}
