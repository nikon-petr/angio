package com.angio.angiobackend.api.common.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FullName {

    @Column(name = "firstname", nullable = false, length = 30)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 30)
    private String lastname;

    @Column(name = "patronymic", length = 30)
    private String patronymic;

    public String getFullNameString() {
        return String.join(" ", lastname, firstname, patronymic);
    }
}
