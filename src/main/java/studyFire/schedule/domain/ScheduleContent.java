package studyFire.schedule.domain;

import lombok.Getter;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ScheduleContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String content_header;
    private String content_body;

    private String isEnd;

    private LocalDateTime createAt;

    private void setContent_header(String content_header) {
        this.content_header = content_header;
    }

    private void setContent_body(String content_body) {
        this.content_body = content_body;
    }

    private void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }



    //=== 편의 메서드 ===//
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.getContents().add(this);
    }

    //== 생성 메서드 ==//
    public static ScheduleContent createContent(Schedule schedule, String content_header, String content_body) {
        ScheduleContent content = new ScheduleContent();
        content.schedule = schedule;
        content.content_header = content_header;
        content.content_body = content_body;
        content.createAt = LocalDateTime.now();
        content.isEnd = "false";

        return content;
    }
}
