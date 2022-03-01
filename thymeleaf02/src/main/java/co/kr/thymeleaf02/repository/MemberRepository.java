package co.kr.thymeleaf02.repository;

import co.kr.thymeleaf02.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일 쿼리메소드
    // 회원 가입시 중복된 회원이 있는지 검사
    Member findByEmail(String email);
}
