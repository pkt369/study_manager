package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Team;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;

    public void save(Team team) {
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
}
