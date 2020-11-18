package studyFire.schedule.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Member;
import studyFire.schedule.service.MemberService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa.com", "aaa", "세준", 24);

        //when
        Long id = memberService.join(member);
        Member mem = memberService.findOne(id);

        //then
        assertThat(member).isSameAs(mem);

    }

    @Test
    public void 중복회원체크() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa.com", "aaa", "세준", 24);
        Member member1 = Member.createMember("aaa@aaa.com", "aaa", "gg", 24);

        //when
        memberService.join(member);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member1));

    }
}