package com.web.prj.dtos.request;

import lombok.Data;

@Data
public class ApplyMemberRequest {
    private String userId;
    private String memberId;
}
