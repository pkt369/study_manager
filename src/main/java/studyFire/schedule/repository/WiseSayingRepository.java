package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.WiseSaying;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WiseSayingRepository {

    private final EntityManager em;

    public void save(WiseSaying saying) {
        em.persist(saying);
    }

    public WiseSaying findOne(Long id) {
        return em.find(WiseSaying.class, id);
    }

    public List<WiseSaying> findAll() {
        return em.createQuery("select w from WiseSaying w", WiseSaying.class)
                .getResultList();
    }


}
