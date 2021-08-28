package com.sunrider.youtubeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String id;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    private String email;
}
