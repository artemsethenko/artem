package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {
    private long id;
    private String title;
    private String text;
    private Instant date;
    private String category;

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": " + id +
                ",\n\"title\": " + title +
                ",\n\"text\": " + text +
                ",\n\"date:\" " + date +
                ",\n\"category:\" " + category +
                "\n}";
    }
}
