package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.Enumeration.MessageRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessage {
    Long id;
    ChatSession chatSession;
    MessageRole messageRole;
    String content;
    String toolCalls;
    Integer tokensUsed;
    Instant createdDate;
}
