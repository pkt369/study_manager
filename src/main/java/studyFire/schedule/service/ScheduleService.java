package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.ScheduleContentRepository;
import studyFire.schedule.repository.ScheduleRepository;
import studyFire.schedule.repository.TeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository contentRepository;

    @Transactional
    public Long save(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    //스케줄 내용 저장
    @Transactional
    public Long contentSave(ScheduleContent content) {
        contentRepository.save(content);
        return content.getId();
    }


}
