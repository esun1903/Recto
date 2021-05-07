package com.ssafy.recto.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ssafy.recto.config.MattermostProperties;
import com.ssafy.recto.dto.MattermostMessage.Attachment;
import com.ssafy.recto.dto.MattermostMessage.Attachments;

@Component
@RequiredArgsConstructor
public class MattermostSender {

    private Logger log = LoggerFactory.getLogger(MattermostSender.class);

    @Value("${notification.mattermost.enabled}")
    private boolean mmEnabled;
    @Value("https://meeting.ssafy.com/hooks/eu6us6pjbt8978736w9t9iu9pr")
    private String webhookUrl;

    @Autowired
    private final RestTemplate restTemplate;
    private final MattermostProperties mmProperties;

    public void sendMessage(Exception exception, String uri, String params) {
        if (!mmEnabled) {
            return;
        }

        try {
            Attachment attachment = Attachment.builder()
                    .channel(mmProperties.getChannel())
                    .authorIcon(mmProperties.getAuthorIcon())
                    .authorName(mmProperties.getAuthorName())
                    .color(mmProperties.getColor())
                    .pretext(mmProperties.getPretext())
                    .title(mmProperties.getTitle())
                    .text(mmProperties.getText())
                    .footer(mmProperties.getFooter())
                    .build();

            attachment.addExceptionInfo(exception, uri, params);
            Attachments attachments = new Attachments(attachment);
            attachments.addProps(exception);
            String payload = new Gson().toJson(attachments);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(webhookUrl, entity, String.class);

        } catch (Exception e) {
            log.error("#### ERROR!! Notification Manager : {}", e.getMessage());
        }
    }
}