package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleContentRepository {

    private final EntityManager em;

    public void save(ScheduleContent content) {
        em.persist(content);
    }


    public ScheduleContent findOne(Long id) {
        return em.find(ScheduleContent.class, id);
    }

    public List<ScheduleContent> findAllBySchedule(Schedule schedule) {
        return em.createQuery("select c from ScheduleContent c join fetch c.schedule s where s = :schedule",
                ScheduleContent.class)
                .setParameter("schedule", schedule)
                .getResultList();
    }

}
