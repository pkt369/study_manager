package studyFire.schedule.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.*;
import studyFire.schedule.repository.JoinTeamMemberRepository;
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
        teamService.save(team, member);
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
        memberService.join(member);
        teamService.save(team, member);
        chatMessageService.sendMessage(message1);
        chatMessageService.sendMessage(message2);

        //then
        assertThat(message1.getMember()).isSameAs(member);
        assertThat(message1.getMember()).isSameAs(message2.getMember());
        assertThat(team.getChat().getMessages().size()).isEqualTo(2);

    }

    @Test
    public void 멤버초대() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Member member2 = Member.createMember("bbb@aaa", "aaa", "챔", 25);
        Team team = Team.createTeam("팀1");


        //when
        memberService.join(member);
        memberService.join(member2);
        teamService.save(team, member); //조장 member


        //팀에서 멤버 찾기
        Member mem = memberService.findByEmail("aaa@aaa");
        teamService.invite(mem, team);
        teamService.invite(member2, team);


        //멤버가 팀찾기
        List<Team> findTeam = teamService.findByName("팀1");
//        teamService.invite(member, findTeam.get(0));

        List<JoinTeamMember> teamMemberList = teamService.findJoinTeamMemberList(team);

        //then
        assertThat(team.getName()).isEqualTo("팀1");
        //팀 조장이 누구인지
        assertThat(team.getCaptain_member()).isSameAs(mem);
        //제대로 팀 회원이 생겼는지
        assertThat(teamMemberList.size()).isEqualTo(2);
        //조장변경
        teamService.changeCaptainMember(member2, team);
        assertThat(team.getCaptain_member()).isSameAs(member2);


    }

    @Test
    @Rollback(value = false)
    public void 멤버강퇴() throws Exception { 
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Member member2 = Member.createMember("bbb@aaa", "aaa", "챔", 25);
        Team team = Team.createTeam("팀1");

        //when
        memberService.join(member);
        memberService.join(member2);
        teamService.save(team, member);
        teamService.invite(member, team);

        //then
        teamService.kickOut(member, team);
        //방 폭파되었는지 확인
        List<JoinTeamMember> teamMemberList = teamService.findJoinTeamMemberList(team);

        assertThat(teamMemberList.size()).isEqualTo(0);

        //다시 넣고
        teamService.save(team, member);
        teamService.invite(member, team);
        teamService.invite(member2, team);

        //조장 강퇴후 새로운 조장이 되었을 경우
        teamService.kickOut(member, team);

        List<JoinTeamMember> teamMemberList2 = teamService.findJoinTeamMemberList(team);

        assertThat(teamMemberList2.size()).isEqualTo(1);
        assertThat(teamMemberList2.get(0).getMember()).isSameAs(member2);

    }

    @Test
    public void 메세지다읽어오기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Team team = Team.createTeam("팀1");
        ChatMessage message1 = ChatMessage.createMessage("안뇽", team.getChat(), member);
        ChatMessage message2 = ChatMessage.createMessage("안뇽2", team.getChat(), member);

        //when
        memberService.join(member);
        teamService.save(team, member);
        chatMessageService.sendMessage(message1);
        chatMessageService.sendMessage(message2);

        List<ChatMessage> chatMessages = teamService.bringMessage(team);



        //then
        assertThat(chatMessages.size()).isEqualTo(2);
        assertThat(member).isSameAs(message1.getMember());
    }


}