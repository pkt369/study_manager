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

    private void setId(Long id) {
        this.id = id;
    }

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


    //== 생성 메서드==//
    public static ChatMessage createMessage(String message, Chat chat) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setCreateAt(LocalDateTime.now());
        chatMessage.setChat(chat);

        return chatMessage;
    }
}
