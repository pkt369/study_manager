package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.JoinTeamMember;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    //처음 팀이 만들어지면 바로 만든 사람이 조장
    public void save(Team team, Member member) {
        team.ChangeCaptainMember(member);
        em.persist(team);
    }

    public Team findOne(Long id) {
        return em.find(Team.class, id);
    }

    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public List<Team> findByName(String name) {
        return em.createQuery("select t from Team t where t.name = :name", Team.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Schedule> findSchedules(Team team) {
        return em.createQuery("select s from Schedule s join fetch s.team t where t = :team", Schedule.class)
                .setParameter("team", team)
                .getResultList();
    }

    public void deleteTeam(Team team) {
        em.remove(team);
    }






}
