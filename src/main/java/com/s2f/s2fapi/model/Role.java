package com.s2f.s2fapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
public enum Role {
    ADMIN,
    USER,
    CLIENT,
    REVENDEUR
}
