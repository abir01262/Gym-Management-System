package com.gymapp.net;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    public MessageType type;
    public Role fromRole;
    public String fromId;
    public String fromName;
    public String toId;           // "broadcast" or specific id
    public long ts;

    // Simple payloads
    public String text;           // chat or notice text

    // Assignment payloads
    public String taskName;       // task name
    public String taskStatus;     // status when member updates
    public Integer slotIndex;     // 0..2 for coach's member slot (optional helper)
}
