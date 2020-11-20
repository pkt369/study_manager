package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.*;
import studyFire.schedule.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;
    private final MessageRepository messageRepository;
    private final JoinTeamMemberRepository joinTeamMemberRepository;

    @Transactional
    public Long save(Team team, Member member) {
        teamRepository.save(team, member); //Chat도 저장됨
        return team.getId();
    }

    //초대기능
    @Transactional
    public void invite(Member member, Team team) {
        JoinTeamMember joinTeamMember = JoinTeamMember.createJoinTeamMember(member, team);
        joinTeamMemberRepository.joinTeam(joinTeamMember);
    }

    //강퇴기능
    @Transactional
    public void kickOut(Member member, Team team) {
        //멤버 강퇴 코드
        List<JoinTeamMember> teamMember = joinTeamMemberRepository.findTeamMember(member, team);
        joinTeamMemberRepository.deleteTeamMember(teamMember.get(0));

        //나가는 사람이 팀장이고 혼자였으면 방을 폭파
        //팀장이고 두명 이상이면 팀장을 자동으로 바꿔줌
//        CheckCaptainAndChange(member, team);


    }



    //조장 변경기능
    @Transactional
    public void changeCaptainMember(Member member, Team team) {
        joinTeamMemberRepository.changeCaptainMember(member, team);
    }

    // 스케줄 공유 기능
    public List<Schedule> bringScheduleList(Team team) {
        //멤버에게서 가져온 스케줄 //스케줄을 날짜별로 리스트를 만들어 선택하도록 만들기

        return teamRepository.findSchedules(team);
    }

    //채팅 이전에 했던것들 다가져오기
    public List<ChatMessage> bringMessage(Team team) {
        List<ChatMessage> chatting = messageRepository.findByChat(team.getChat());
        return chatting;
    }

    public List<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    public List<JoinTeamMember> findJoinTeamMemberList(Team team) {
        return joinTeamMemberRepository.findTeamMemberList(team);
    }

    public List<JoinTeamMember> findJoinTeamMember(Member member, Team team) {
        return joinTeamMemberRepository.findTeamMember(member, team);
    }

    //나가는 사람이 팀장이면 그다음사람으로 변경
    @Transactional
    private void CheckCaptainAndChange(Member member, Team team) {
        //나간사람이 방장이 아니면 메서드 지나치기
        if(member != team.getCaptain_member()){
            return;
        }
        List<JoinTeamMember> teamMemberList = joinTeamMemberRepository.findTeamMemberList(team);
        //방에 아무도 없으면 폭파
        if(teamMemberList.size() == 0){
            teamRepository.deleteTeam(team);
            return;
        }

        //이미 방장이 나가서 그다음사람이 방장역할을 맡게됨
        joinTeamMemberRepository.changeCaptainMember(teamMemberList.get(0).getMember(), team);

    }
}
