package com.angio.angiobackend.api.user.dto.email;

import com.angio.angiobackend.api.common.dto.AbstractEmailDto;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationEmailDto extends AbstractEmailDto {

    private String email;
    private String password;
    private String loginLink;
}
