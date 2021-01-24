package com.marchenko.medcards.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


public interface User {
    String getLogin();
    String getPassword();
    Role getRole();
}
