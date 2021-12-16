package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional  //test에 있으면 test 끝나고 롤백을 해버림.
    @Rollback(false)  //rollback false
    public void testMember() throws Exception{

        Member member = new Member();
        member.setUsername("memberA");

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 영속성 때문에 하나의 트랜잭션 안에서는 같은 값 .. jpa 기본편을 듣자

    }

}