package studyFire.schedule.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    private String message;

    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private void setMessage(String message) {
        this.message = message;
    }

    private void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    //== 편의 메서드 ==//
    private void setChat(Chat chat) {
        this.chat = chat;
        chat.getMessages().add(this);
    }

    private void setMember(Member member) {
        this.member = member;
        member.getMessages().add(this);
    }


    //== 생성 메서드==//
    public static ChatMessage createMessage(String message, Chat chat, Member member) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setCreateAt(LocalDateTime.now());
        chatMessage.setChat(chat);
        chatMessage.setMember(member);

        return chatMessage;
    }
}
