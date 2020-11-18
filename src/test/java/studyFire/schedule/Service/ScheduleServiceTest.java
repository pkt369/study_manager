package studyFire.schedule.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.domain.Team;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.ScheduleService;
import studyFire.schedule.service.TeamService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ScheduleServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TeamService teamService;
    @Autowired
    ScheduleService scheduleService;

    @Test
    public void 스케줄_내용포함_만들기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Schedule schedule = Schedule.createSchedule(member);
        ScheduleContent content = ScheduleContent.createContent(schedule, "세준코딩", "세준 코딩잘해...");

        //when
        memberService.join(member);
        scheduleService.save(schedule);
        scheduleService.contentSave(content);

        //then
        assertThat(member.getSchedules().size()).isEqualTo(1);
        assertThat(schedule.getMember()).isSameAs(member);
        assertThat(content.getSchedule()).isSameAs(schedule);
        assertThat(content.getContent_header()).isEqualTo("세준코딩");

    }


}