package com.example.dim.pla01.entities;

import java.io.Serializable;

/**
 * Created by dim on 08/02/2018.
 */


public class CaveEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private Integer cavId;

    private Integer cavNombreBouteilles;

    private UtilisateurEntity cavUtilisateur;

    private VinEntity cavVin;

    public CaveEntity() {
    }


    public CaveEntity(Integer cavId) {
        this.cavId = cavId;
    }

    @Override
    @SuppressWarnings("CloneDoesntCallSuperClone")
    public CaveEntity clone() throws CloneNotSupportedException {
        CaveEntity cave = new CaveEntity();
        cave.setCavId(cavId);
        cave.setCavNombreBouteilles(cavNombreBouteilles);
        cave.setCavUtilisateur(cavUtilisateur);
        cave.setCavVin(cavVin);
        return cave;
    }


    public Integer getCavId() {
        return cavId;
    }

    public void setCavId(Integer cavId) {
        this.cavId = cavId;
    }

    public Integer getCavNombreBouteilles() {
        return cavNombreBouteilles;
    }

    public void setCavNombreBouteilles(Integer cavNombreBouteilles) {
        this.cavNombreBouteilles = cavNombreBouteilles;
    }

    public UtilisateurEntity getCavUtilisateur() {
        return cavUtilisateur;
    }

    public void setCavUtilisateur(UtilisateurEntity cavUtilisateur) {
        this.cavUtilisateur = cavUtilisateur;
    }

    public VinEntity getCavVin() {
        return cavVin;
    }

    public void setCavVin(VinEntity cavVin) {
        this.cavVin = cavVin;
    }
}
