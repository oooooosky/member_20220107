package com.icia.member.service;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        // JPARepository는 무조건 Entity 타입만 받음.
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);
//        Long memberId = mr.save(memberEntity).getId();
//        return memberId;
        // 위 두줄과 아래 한줄은 같은 코드
        return mr.save(memberEntity).getId();
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if (memberEntity!=null) {
            if(memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {
        List<MemberEntity> memberEntityList = mr.findAll();
        List<MemberDetailDTO> memberDetailDTOList = MemberDetailDTO.change(memberEntityList);
        return memberDetailDTOList;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
//        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
//        MemberEntity memberEntity = memberEntityOptional.get();
//        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
//        return memberDetailDTO;
        return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberId).get());
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        // update 처리 시 save 메서드 호출
        // 기존에 있는 정보면 알아서 업데이트 쿼리 수행
        // MemberDetailDTO -> MemberEntity로 변환해줘야함.
        // 바꾸고자 하는 대상 클래스에 해당 메서드가 생성되어야함.
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getId();

        return memberId;
    }

}
