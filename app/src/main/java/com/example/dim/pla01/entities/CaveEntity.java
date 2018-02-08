package com.example.dim.pla01.entities;

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


@JsonRootName(value = "cave")
@JsonPropertyOrder({"cavId", "cavVin", "cav_utilisateur", "cavNombreBouteilles"})
@JsonSerializableSchema(id = "caveSchema")
public class CaveEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @JsonValue
    private Integer cavId;
    @JsonValue
    private Integer cavNombreBouteilles;
    @JsonValue
    private UtilisateurEntity cavUtilisateur;
    @JsonValue
    private VinEntity cavVin;

    public CaveEntity() {
    }

    @JsonCreator
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

    @JsonGetter
    public Integer getCavId() {
        return cavId;
    }

    @JsonSetter
    public void setCavId(Integer cavId) {
        this.cavId = cavId;
    }

    @JsonGetter
    public Integer getCavNombreBouteilles() {
        return cavNombreBouteilles;
    }

    @JsonSetter
    public void setCavNombreBouteilles(Integer cavNombreBouteilles) {
        this.cavNombreBouteilles = cavNombreBouteilles;
    }

    @JsonGetter
    public UtilisateurEntity getCavUtilisateur() {
        return cavUtilisateur;
    }

    @JsonSetter
    public void setCavUtilisateur(UtilisateurEntity cavUtilisateur) {
        this.cavUtilisateur = cavUtilisateur;
    }

    @JsonGetter
    public VinEntity getCavVin() {
        return cavVin;
    }

    @JsonSetter
    public void setCavVin(VinEntity cavVin) {
        this.cavVin = cavVin;
    }
}
