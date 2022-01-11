package com.icia.member.repository;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberMapperDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapperRepository {

    // mapper를 호출하는 방식
    // 회원목록 출력
    List<MemberMapperDTO> memberList();

    // 회원가입
    void save(MemberMapperDTO memberMapperDTO);

    // mapper를 호출하지 않고 여기서 쿼리까지 수행하는 방식
    @Select("select * from member_table")
    List<MemberMapperDTO> memberList2();

    @Insert("insert into member_table(member_email, member_password, member_name) values(#{member_email}, #{member_password}, #{member_name})")
    void save2(MemberMapperDTO memberMapperDTO);

}
