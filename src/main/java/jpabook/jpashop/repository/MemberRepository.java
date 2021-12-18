package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //그래들에서 설정할때 엔티티매니저 생성 자동으로 해줌

    public void save(Member member){
        em.persist(member); //insert query 날라감
    }

    public Member findOne(Long id){
        return  em.find(Member.class, id); //단건 조회
    }

    public List<Member> findAll(){ // jpaql 사용,
      return  em.createQuery("select m from Member m", Member.class)
                 .getResultList();
    }

    public List<Member> findByName(String name){ //파라미터 바인딩해서 특정 이름에 대해 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
