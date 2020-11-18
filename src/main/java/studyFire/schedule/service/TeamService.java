package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.ChatMessage;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.Team;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.MessageRepository;
import studyFire.schedule.repository.ScheduleRepository;
import studyFire.schedule.repository.TeamRepository;

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

    @Transactional
    public Long save(Team team) {
        teamRepository.save(team); //Chat도 저장됨
        return team.getId();
    }

    //초대기능
    @Transactional
    public void invite(Member member, Team team) {
        //이미 팀에 들어가있는지 확인
        Member mem = memberRepository.findOne(member.getId());
        if (mem.getTeam() != null) {
            throw new IllegalStateException("이미 팀이 있는 회원입니다.");
        }

        mem.changeTeam(team); //dirty checking
    }

    //강퇴기능
    @Transactional
    public void kickOut(Member member) {
        Member mem = memberRepository.findOne(member.getId());
        mem.changeTeam(null);
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

}
