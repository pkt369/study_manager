package studyFire.schedule.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Schedule;
import studyFire.schedule.domain.ScheduleContent;
import studyFire.schedule.repository.MemberRepository;
import studyFire.schedule.repository.ScheduleContentRepository;
import studyFire.schedule.repository.ScheduleRepository;
import studyFire.schedule.repository.TeamRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleContentRepository contentRepository;

    @Transactional
    protected Long save(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    //스케줄 내용 저장
    @Transactional
    public Long contentSave(ScheduleContent content, Member member, LocalDate date) {
        //멤버에게 그날짜 스케줄이 없으면 생성 있으면 아무일 없음.
//        Schedule schedule = isExistScheduleAndSave(member, date);
        List<Schedule> existSchedule = scheduleRepository.isExistSchedule(member, date);
        if (existSchedule.isEmpty()) {
            Schedule schedule = Schedule.createSchedule(member, date);
            scheduleRepository.save(schedule);
            content.setSchedule(schedule);
        }else{
            content.setSchedule(existSchedule.get(0));
        }

        contentRepository.save(content);
        return content.getId();
    }

    public List<ScheduleContent> findContent(Schedule schedule) {
        return contentRepository.findAllBySchedule(schedule);
    }

    //사용 X
    @Transactional
    protected Schedule isExistScheduleAndSave(Member member, LocalDate date) {
        //같은 날짜에 만들어졌는지 확인하기
        List<Schedule> existSchedule = scheduleRepository.isExistSchedule(member, date);
        if (existSchedule.isEmpty()) {
            Schedule schedule = Schedule.createSchedule(member,date);
            scheduleRepository.save(schedule);
            return schedule;
        }
        return null;
    }

    public List<Schedule> findAllByMember(Member member) {
        return scheduleRepository.findAllByMember(member);
    }

    public List<ScheduleContent> findAllByScheduleDate(Schedule schedule, LocalDate date) {
        return contentRepository.findAllByScheduleDate(schedule, date);
    }

    public List<Schedule> findAllByMemberDate(Member member, LocalDate date) {
        return findAllByMemberDate(member, date);
    }

    public List<ScheduleContent> todaySchedule(Member member) {
        return scheduleRepository.todaySchedule(member);
    }

    @Transactional
    public void changeSchedule_isEnd(List<String> checkArr, List<String> uncheckArr) {
        contentRepository.changeSchedule_isEnd(checkArr, uncheckArr);
    }

    /*@Transactional
    public void changeIsEnd(String boo, String id) {
        contentRepository.changeIsEnd(boo, id);
    }*/

    public List<ScheduleContent> findScheduleByDate(LocalDate date) {
        return contentRepository.findScheduleByDate(date);
    }

    @Transactional
    public void deleteSchedule(String id) {
        long parseId = Long.parseLong(id);
        contentRepository.deleteSchedule(parseId);
    }

    @Transactional
    public void ChangeSchedule(List<Map<String, String>> mapList) {
        for (int i = 0; i < mapList.size(); i++) {
            ScheduleContent content = contentRepository.findOne(Long.parseLong(mapList.get(i).get("id")));
            content.changeScheduleContent(mapList.get(i).get("header"), mapList.get(i).get("body"), mapList.get(i).get("isEnd"));
        }
    }




}
