package com.s2f.s2fapi.dto.response;

import com.s2f.s2fapi.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserConnectDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private boolean passwordChanged;
    private String role;

    public void toUserConnectDto(Utilisateur user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getPrenom();
        this.lastname = user.getNom();
        this.passwordChanged = user.isPasswordChanged();
        this.role = user.getRole().name();
    }
}
