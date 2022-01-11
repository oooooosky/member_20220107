package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    // MemberEntity -> MemberDetailDTO
    // 리턴타입 : MemberDetailDTO
    // 매개변수타입 : MemberEntity
    public static MemberDetailDTO toMemberDetailDTO(MemberEntity memberEntity) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        return memberDetailDTO;
    }

    public static List<MemberDetailDTO> change(List<MemberEntity> memberEntityList) {
        List<MemberDetailDTO> memberDetailDTOList = new ArrayList<>();
        for (MemberEntity m: memberEntityList) {
            memberDetailDTOList.add(toMemberDetailDTO(m));
        }
        return memberDetailDTOList;
    }

}
