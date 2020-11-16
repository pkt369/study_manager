package studyFire.schedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate date;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleContent> contents = new ArrayList<>();

    private int achieve_rate;


    protected void setDate(LocalDate date) {
        this.date = date;
    }


    protected void setAchieve_rate(int achieve_rate) {
        this.achieve_rate = achieve_rate;
    }

    //== 편의 메서드 ==//
    protected void setMember(Member member) {
        this.member = member;
        member.getSchedules().add(this);
    }

    //=== 생성 메서드 ===/
    public static Schedule createSchedule(Member member, int achieve_rate) {
        Schedule schedule = new Schedule();
        schedule.setMember(member);
        schedule.setDate(LocalDate.now());
        schedule.setAchieve_rate(achieve_rate);

        return schedule;
    }

}
