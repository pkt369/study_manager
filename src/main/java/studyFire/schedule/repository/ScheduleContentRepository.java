package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;
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

    public List<ScheduleContent> findAllByScheduleDate(Schedule schedule, LocalDate date) {
        return em.createQuery("select c from ScheduleContent c join fetch c.schedule s where s = :schedule and s.date = :date",
                ScheduleContent.class)
                .setParameter("schedule", schedule)
                .setParameter("date", date)
                .getResultList();
    }


    public void changeSchedule_isEnd(List<String> checkArr, List<String> uncheckArr) {
        for (String check : checkArr) {
            long id = Long.parseLong(check);
            ScheduleContent content = em.find(ScheduleContent.class, id);
            content.changeIsEnd("true");
        }
        for (String uncheck : uncheckArr) {
            long id = Long.parseLong(uncheck);
            ScheduleContent content = em.find(ScheduleContent.class, id);
            content.changeIsEnd("false");
        }
    }

    public List<ScheduleContent> findScheduleByDate(LocalDate date) {
        return em.createQuery("select c from ScheduleContent c join fetch c.schedule s where s.date = :date",ScheduleContent.class)
                .setParameter("date", date)
                .getResultList();
    }

    /*public void changeIsEnd(String boo, String bringId) {
        long id = Long.parseLong(bringId);
        ScheduleContent content = em.find(ScheduleContent.class, id);
        content.changeIsEnd(boo);
    }*/

//    public List<ScheduleContent> findContextWithMemberDate(Member member, Date date)

}
