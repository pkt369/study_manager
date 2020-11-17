package studyFire.schedule.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;

import javax.persistence.EntityManager;
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
        return em.createQuery("select s from Schedule s fetch join member m on m.id = :memberId where m.id = s.member",
                Schedule.class)
                .setParameter("memberId", member.getId())
                .getResultList();
    }
}
