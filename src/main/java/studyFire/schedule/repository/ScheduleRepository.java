package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public void save(Schedule schedule) {
        em.persist(schedule);
    }

    public Schedule findOne(Long id) {
        return em.find(Schedule.class, id);
    }

    public List<Schedule> findAllByMember(Member member) {
        return em.createQuery("select s from Schedule s join fetch s.member m where m.id = :memberId",
                Schedule.class)
                .setParameter("memberId", member.getId())
                .getResultList();
    }

    public List<Schedule> isExistSchedule(Member member, LocalDate date) {
        return em.createQuery("select s from Schedule s where s.member = :member and s.date = :date",
                Schedule.class)
                .setParameter("member", member)
                .setParameter("date", date)
                .getResultList();
    }

    public List<Schedule> findAllByMemberDate(Member member, LocalDate date) {
        return em.createQuery("select s from Schedule s join fetch s.member m where m.id = :memberId and s.date = :date",
                Schedule.class)
                .setParameter("memberId", member.getId())
                .setParameter("date", date)
                .getResultList();
    }

    public List<ScheduleContent> todaySchedule(Member member) {
        return em.createQuery("select c from ScheduleContent c join fetch c.schedule s " +
                "where s.member = :member and s.date = :date", ScheduleContent.class)
                .setParameter("member", member)
                .setParameter("date", LocalDate.now())
                .getResultList();
    }

}
