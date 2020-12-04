package studyFire.schedule.domain.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ScheduleForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String content_header;
    private String content_body;

    private String isEnd;
    private Long id;
}
