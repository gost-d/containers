package com.example.publisher.publisher;

import com.example.publisher.config.MessageConfig;
import com.example.publisher.dto.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserPubTest {

    private UserPub subject;
    private RabbitTemplate rabbitTemplateMock;

    @BeforeAll
    public void setUp() {
        this.rabbitTemplateMock = Mockito.mock(RabbitTemplate.class);
        this.subject = new UserPub();
    }

    @Test
    public void testSendMessage(){
        User user = new User("3243242","User","Test","mail","973297897","Place");
        assertThatCode(() -> subject.sendMessage(user,rabbitTemplateMock)).doesNotThrowAnyException();
        Mockito.verify(rabbitTemplateMock)
                .convertAndSend(eq(MessageConfig.EXCHANGE),eq(MessageConfig.ROUTING_KEY), eq(user));
    }
}