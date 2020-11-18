package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;

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
//        LocalDate date = LocalDate.now();
        //오늘 날짜만 가져올수있게 함.
        return em.createQuery("select s from Schedule s join fetch s.member m where m.id = :memberId",
                Schedule.class)
                .setParameter("memberId", member.getId())
                .getResultList();
    }
}
