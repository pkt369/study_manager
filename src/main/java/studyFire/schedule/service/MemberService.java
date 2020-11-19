package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.ScheduleRepository;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findByEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if(findMembers.size() != 0){
            Long id = findMembers.get(0).getId();
            return memberRepository.findOne(id);
        }
        return null;
    }


    private void validateDuplicateMember(Member member) {
        List<Member> email = memberRepository.findByEmail(member.getEmail());
        if (!email.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
}
