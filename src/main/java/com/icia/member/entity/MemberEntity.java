package com.icia.member.entity;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name="member_table")
public class MemberEntity {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    @Column(name = "member_id") // 컬럼 이름 지정
    private Long id;

    @Column(length = 50, unique = true)
    private String memberEmail;
    // Entity에서는 _(언더바) 절대 금물. 차후에 오류 발생 가능성 높음.

    @Column(length = 20)
    private String memberPassword;

    @Column()
    private String memberName;

    // MemberSaveDTO -> MemberEntity 객체로 변환하기 위한 메서드
    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        // MemberEntity타입의 객체를 보내기 위해 memberEntity라는 객체 선언
        MemberEntity memberEntity = new MemberEntity();

        // memberEntity 객체에 memberSaveDTO 객체 안에 담긴 각 요소를 담아줌.
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return memberEntity;
    }

    // MemberDetailDTO -> MemberEntity 객체로 변환하기 위한 메서드
    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        // MemberEntity타입의 객체를 보내기 위해 memberEntity라는 객체 선언
        MemberEntity memberEntity = new MemberEntity();

        // memberEntity 객체에 memberSaveDTO 객체 안에 담긴 각 요소를 담아줌.
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return memberEntity;
    }
}
