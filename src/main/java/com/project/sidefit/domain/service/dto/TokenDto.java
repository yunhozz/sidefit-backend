package com.project.sidefit.domain.service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

//    private String grantType;
    private String accessToken;
    private String refreshToken;
//    private Long accessTokenExpireDate;
}
