package studyFire.schedule.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class domainTest {

    @Autowired
    EntityManager em;

    @Test
    public void 멤버() throws Exception {
        //given
        Member member = Member.createMember("aa@aaa.com","aaa", "세준" , 10);
        Team team = Team.createTeam("팀1");


        //when
        //멤버가 팀에 들어가기

        em.persist(member);
        em.persist(team);

        Member findMember = em.find(Member.class, 1L);

        //then
        assertThat("세준").isEqualTo(findMember.getName());

    }

    @Test
    public void 채팅() throws Exception {
        //given
        Member member = Member.createMember("aa@aaa.com", "aaa", "세준", 10);
        Team team = Team.createTeam("팀1");
        ChatMessage message = ChatMessage.createMessage("안녕", team.getChat(), member);

        //when
        em.persist(member);
        em.persist(team);
        em.persist(message);



        Chat findChat = em.find(Chat.class, team.getChat().getId());
        ChatMessage chatMessage = em.find(ChatMessage.class, message.getId());

        //then
        assertThat("안녕").isEqualTo(chatMessage.getMessage());
        assertThat(team.getChat()).isSameAs(findChat);
    }


    @Test
    @Rollback(value = false)
    public void 스케줄() throws Exception {
        //given
        Member member = Member.createMember("aa@aaa.com", "aaa", "세준", 10);
        Team team = Team.createTeam("팀1");
        Schedule schedule = Schedule.createSchedule(member);
        ScheduleContent content = ScheduleContent.createContent(schedule, "제목", "여기에 글 쓰기");

        //when
        em.persist(member);
        em.persist(team);
        em.persist(schedule);
        em.persist(content);

        //then
        Schedule findSchedule = em.find(Schedule.class, schedule.getId());

        //멤버가 가지고 있는 스케줄과 만든 스케줄의 id 같은지 확인
        assertThat(member.getSchedules().get(0).getId()).isEqualTo(schedule.getId());
        //글쓴게 스케줄에 들어갔는지 확인
        assertThat(content.getSchedule().getId()).isEqualTo(schedule.getId());
        //글쓴게 똑같은지 확인
        assertThat(content.getContent_body()).isEqualTo("여기에 글 쓰기");

    }
}
