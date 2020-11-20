package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.JoinTeamMember;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JoinTeamMemberRepository {

    private final EntityManager em;

    //팀에 가입시키기
    public Long joinTeam(JoinTeamMember joinTeamMember) {
        em.persist(joinTeamMember);
        return joinTeamMember.getId();
    }

    //조장
    public void changeCaptainMember(Member member, Team team) {
        Team findTeam = em.find(Team.class, team.getId());
        team.ChangeCaptainMember(member);
    }

    //팀원 한명 찾기 기능
    public List<JoinTeamMember> findTeamMember(Member member, Team team) {
        return em.createQuery("select j from JoinTeamMember j where j.member = :member and j.team = :team", JoinTeamMember.class)
                .setParameter("member", member)
                .setParameter("team", team)
                .getResultList();
    }

    public List<JoinTeamMember> findTeamMemberList(Team team) {
        return em.createQuery("select j from JoinTeamMember j where j.team = :team", JoinTeamMember.class)
                .setParameter("team", team)
                .getResultList();
    }

    //팀원 삭제 기능
    public void deleteTeamMember(JoinTeamMember joinTeamMember) {
        JoinTeamMember member = em.find(JoinTeamMember.class, joinTeamMember.getId());
        em.remove(member);
    }
}
