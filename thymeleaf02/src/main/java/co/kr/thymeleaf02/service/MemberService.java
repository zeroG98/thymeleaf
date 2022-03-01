package co.kr.thymeleaf02.service;

import co.kr.thymeleaf02.entity.Member;
import co.kr.thymeleaf02.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
// 비즈니스 로직 처리(서비스 계층)에 Transactional 어노테이션을 선언하여
// 로직 실행중 에러가 발생하면 롤백 처리되도록 한다.
@Transactional
@RequiredArgsConstructor // final이 붙은 필드를 생성자 주입을 해주는 어노테이션
public class MemberService {

    private final MemberRepository memberRepository;

    //    @Autowired // 생성자 주입
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다!!");
        }
    }


}
