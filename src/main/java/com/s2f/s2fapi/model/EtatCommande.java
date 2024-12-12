package com.s2f.s2fapi.model;

import lombok.Getter;

@Getter
public enum EtatCommande {
    ENCOURS,
    TERMINER,
    LIVRER,
    ANNULER;
}
