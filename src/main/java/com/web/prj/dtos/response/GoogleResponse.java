package com.web.prj.dtos.response;

import lombok.Data;

@Data
public class GoogleResponse {
    private String access_token;

    private String name;
    private String email;
    private String picture;
}
