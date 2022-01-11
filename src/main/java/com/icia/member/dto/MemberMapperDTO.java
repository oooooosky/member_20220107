package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberMapperDTO {

    private Long member_id;
    private String member_email;
    private String member_password;
    private String member_name;

    public MemberMapperDTO (String member_email, String member_password, String member_name) {
        this.member_email = member_email;
        this.member_password = member_password;
        this.member_name = member_name;
    }
}
