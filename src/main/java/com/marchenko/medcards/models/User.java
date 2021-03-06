package com.marchenko.medcards.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Column(nullable = false,unique = true, length = 50)
    @NotBlank
    @Length(max = 50)
    protected String login;

    @Column(nullable = false)
    @NotBlank
    @Length(max = 50)
    protected String password;

    @Transient
    protected Role role;

    protected User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
