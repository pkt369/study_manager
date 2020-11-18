package studyFire.schedule.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.*;
import studyFire.schedule.service.ChatMessageService;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;
import studyFire.schedule.service.TeamService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ChatMessageService chatMessageService;

    @Test
    @Rollback(value = false)
    public void 팀에_스케줄_공유및_다가져오기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Team team = Team.createTeam("팀1");
        Schedule schedule = Schedule.createSchedule(member);
        ScheduleContent content1 = ScheduleContent.createContent(schedule, "1", "11");
        ScheduleContent content2 = ScheduleContent.createContent(schedule, "2", "22");

        // 스케줄 공유
        schedule.inputSchedule(team);

        //when
        memberService.join(member);
        //팀에 공유됨.
        teamService.save(team);
        scheduleService.save(schedule);
        scheduleService.contentSave(content1);
        scheduleService.contentSave(content2);





        List<Schedule> schedules = teamService.bringScheduleList(team);

        //then
        assertThat(schedule.getMember()).isSameAs(member);
        assertThat(scheduleService.findContent(schedule).size()).isEqualTo(2);
        assertThat(content1.getSchedule()).isSameAs(schedule);
        assertThat(content2.getSchedule()).isSameAs(schedule);
        assertThat(schedules.size()).isEqualTo(1);
    }

    @Test
    public void 팀에_메세지_보내기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Team team = Team.createTeam("팀1");
        ChatMessage message1 = ChatMessage.createMessage("안뇽", team.getChat(), member);
        ChatMessage message2 = ChatMessage.createMessage("안뇽2", team.getChat(), member);

        //when

        //then

    }

    @Test
    public void 멤버초대() throws Exception {
        //given

        //when

        //then

    }

    @Test
    public void 멤버강퇴() throws Exception {
        //given

        //when

        //then

    }

    @Test
    public void 메세지다읽어오기() throws Exception {
        //given

        //when

        //then

    }


}