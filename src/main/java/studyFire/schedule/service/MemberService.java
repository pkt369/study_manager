package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Role;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.changePassword(passwordEncoder.encode(member.getPassword()));

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

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        List<Member> userEntityWrapper = memberRepository.findByEmail(userEmail);
        Member userEntity = userEntityWrapper.get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(userEntity.getEmail(), userEntity.getPassword(), authorities);
    }
}
