package com.marchenko.medcards.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@MappedSuperclass
public class User {

    @Column(unique = true)
    protected String login;

    protected String password;

    @Transient
    protected Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
