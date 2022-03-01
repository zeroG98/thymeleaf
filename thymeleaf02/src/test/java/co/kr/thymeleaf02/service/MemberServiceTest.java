package co.kr.thymeleaf02.service;

import co.kr.thymeleaf02.dto.MemberFormDto;
import co.kr.thymeleaf02.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 테스트 클래스에 @Transactional을 선언하면 롤백처리되어 실제 반영이 되지 않는다.
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원정보를 입력한 테스트용 member 엔티티 만들기
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@gmail.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("대전시 서구 둔산동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복된 회원 가입테스트")
    public void savedDuplicateMemberTest() {
        Member member01 = createMember();
        Member member02 = createMember();

        memberService.saveMember(member01);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member02);
        });                                                                    

        assertEquals("이미 가입된 회원입니다!!", exception.getMessage());
    }
}