package com.icia.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberMapperDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.repository.MemberMapperRepository;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*; // 예외처리하는 assert

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Autowired
    private MemberMapperRepository mmr;

    // unit test : 메서드별로 테스트

    // 현재 쓰는 테스트코드 : junit

    @Test
    @DisplayName("회원 데이터 생성")
    public void newMembers() {
        IntStream.rangeClosed(1,15).forEach(i -> {
            ms.save(new MemberSaveDTO("email"+i, "pw"+1,"name"+i));
        });
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인 테스트")
    public void loginTest() {

        MemberSaveDTO memberSaveDTO = new MemberSaveDTO("로그인용이메일", "로그인용비밀번호", "로그인용이름");
        ms.save(memberSaveDTO);

        MemberLoginDTO memberLoginDTO = new MemberLoginDTO("로그인용이메일", "로그인용비밀번호");
        boolean loginResult = ms.login(memberLoginDTO);

        System.out.println(loginResult);

        assertThat(loginResult).isEqualTo(true);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("조회 테스트")
    public void memberListTest() {

        for (int i=1; i<=3; i++) {
            ms.save(new MemberSaveDTO("조회용이메일"+i, "조회용비밀번호"+i,"조회용이름"+i));
        }

        List<MemberDetailDTO> memberDetailDTOList = ms.findAll();

        assertThat(ms.findAll().size()).isEqualTo(3);
    }
    
    /*
        회원삭제 테스트코드 만들기
        회원삭제 시나리오를 작성해보고 코드 작성, 11시 15분 마감
     */
    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제 테스트")
    public void memberDeleteTest() {

        // 회원 추가 및 회원번호 찾기
        Long memberId = ms.save(new MemberSaveDTO("삭제용이메일", "삭제용비밀번호", "삭제용이름"));
        System.out.println(ms.findById(memberId));
        // 삭제
        ms.deleteById(memberId);
        // System.out.println(ms.findById(memberId));
        // 위 출력문을 돌리면 NoSuchElementException 발생
        // 삭제한 회원의 id로 조회를 시도했을 때 null 이어야 테스트 통과
        // NoSuchElementException은 무시하고 테스트를 수향
        assertThrows(NoSuchElementException.class, () -> {
            assertThat(ms.findById(memberId)).isNull(); // 삭제회원 id 조회결과가 null이면 테스트 통과
        });

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원수정 테스트")
    public void memberUpdateTest() {

        // 회원 추가 및 회원번호 찾기
//        Long memberId = ms.save(new MemberSaveDTO("수정용이메일", "수정용비밀번호", "수정전이름"));

        // DetailDTO 가져오기 및 값 변경
//        MemberDetailDTO memberDetailDTO = ms.findById(memberId), memberUpdateDetailDTO = ms.findById(memberId);
//        memberUpdateDetailDTO.setMemberName("수정후이름");

        // 수정
//        memberUpdateDetailDTO = ms.findById(ms.update(memberUpdateDetailDTO));

        // 수정 후 비교
//        System.out.println("수정 전 DB 데이터 : "+memberDetailDTO.getMemberName());
//        System.out.println("수정 후 DB 데이터 : "+memberUpdateDetailDTO.getMemberName());
//        assertThat(memberDetailDTO.getMemberName()).isNotEqualTo(ms.findById(ms.update(memberUpdateDetailDTO)).getMemberName());

        // 요약
        Long memberId = ms.save(new MemberSaveDTO("수정용이메일", "수정용비밀번호", "수정전이름"));
        MemberDetailDTO memberDetailDTO = ms.findById(memberId), memberUpdateDetailDTO = ms.findById(memberId);
        memberUpdateDetailDTO.setMemberName("수정후이름");
        assertThat(memberDetailDTO.getMemberName()).isNotEqualTo(ms.findById(ms.update(memberUpdateDetailDTO)).getMemberName());

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원수정 테스트 선생님.ver")
    public void memberUpdateTeacherTest() {

        /*
            1. 신규 회원 등록
            2. 신규 등록한 회원에 대한 이름 수정
            3. 신규 등록 시 사용한 이름과 DB에 저장된 이름이 일치하는지 판단
            4. 일치하지 않아야 테스트 통과
         */

        // 1.
        String memberEmail = "수정회원1";
        String memberPassword = "수정비번1";
        String memberName = "수정전이름";
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO(memberEmail, memberPassword, memberName);
        Long saveMemberId = ms.save(memberSaveDTO);

        // 가입 후 DB에서 이름 조회
        String saveMemberName = ms.findById(saveMemberId).getMemberName();

        // 2.
        String updateName = "수정후이름";
        MemberDetailDTO updateMember = new MemberDetailDTO(saveMemberId, memberEmail, memberPassword, updateName);
        ms.update(updateMember);

        // 수정 후 DB에서 이름조회
        String updateMemberName = ms.findById(saveMemberId).getMemberName();

        // 3. 4.
        assertThat(saveMemberName).isNotEqualTo(updateMemberName);

    }

    @Test
    @DisplayName("mybatis 목록 출력 테스트")
    public void mybatisMemberListTest() {
        List<MemberMapperDTO> memberDetailDTOList = mmr.memberList();
        for (MemberMapperDTO m: memberDetailDTOList) {
            System.out.println("m = " + m.toString());
        }
    }

    @Test
    @DisplayName("mybatis 회원가입 테스트")
    public void memberSaveTest() {
        MemberMapperDTO memberMapperDTO = new MemberMapperDTO("mybatis회원이메일", "mybatis회원비밀번호", "mybatis회원이름");
        mmr.save(memberMapperDTO);
    }

    @Test
    @DisplayName("mybatis 목록 출력 테스트")
    public void mybatisMemberListTest2() {
        List<MemberMapperDTO> memberDetailDTOList = mmr.memberList2();
        for (MemberMapperDTO m: memberDetailDTOList) {
            System.out.println("m = " + m.toString());
        }
    }

    @Test
    @DisplayName("mybatis 회원가입 테스트")
    public void memberSaveTest2() {
        MemberMapperDTO memberMapperDTO = new MemberMapperDTO("mybatis회원이메일", "mybatis회원비밀번호", "mybatis회원이름");
        mmr.save2(memberMapperDTO);
    }

}
