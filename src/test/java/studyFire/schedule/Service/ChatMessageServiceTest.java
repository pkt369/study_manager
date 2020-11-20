package studyFire.schedule.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import studyFire.schedule.domain.ChatMessage;
import studyFire.schedule.domain.Member;
import studyFire.schedule.domain.Team;
import studyFire.schedule.service.ChatMessageService;
import studyFire.schedule.service.MemberService;
import studyFire.schedule.service.TeamService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatMessageServiceTest {


    @Autowired
    ChatMessageService chatMessageService;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;

    @Test
    public void 채팅보내기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Team team = Team.createTeam("팀1");
        teamService.save(team, member);

        ChatMessage message = ChatMessage.createMessage("지금보내는건 1", team.getChat(), member);

        //when
        Long messageId = chatMessageService.sendMessage(message);
        ChatMessage findMessage = chatMessageService.findOne(messageId);

        //then
        assertThat(messageId).isEqualTo(1L);
        assertThat(team.getChat()).isSameAs(message.getChat());
        assertThat("지금보내는건 1").isEqualTo(message.getMessage());
        assertThat(member).isSameAs(findMessage.getMember());
    }

    @Test
    @Rollback(value = false)
    public void 채팅가져오기() throws Exception {
        //given
        Member member = Member.createMember("aaa@aaa", "aaa", "세준", 24);
        Team team = Team.createTeam("팀1");
        ChatMessage message = ChatMessage.createMessage("안뇽", team.getChat(), member);
        ChatMessage message1 = ChatMessage.createMessage("안뇽2", team.getChat(), member);

        //when
        Long teamId = teamService.save(team, member);
        Long memberId = memberService.join(member);
        Long messageId = chatMessageService.sendMessage(message);
        chatMessageService.sendMessage(message1);

        List<ChatMessage> messageInChat = chatMessageService.findMessageInChat(team.getChat());


        //then
        assertThat(messageInChat.size()).isEqualTo(2);
        assertThat(messageInChat.get(0).getMessage()).isSameAs("안뇽");
    }

}