package com.angio.angiobackend.api.user.dto.push;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GreetingPushDto {

    private String firstname;
    private String lastname;
}
