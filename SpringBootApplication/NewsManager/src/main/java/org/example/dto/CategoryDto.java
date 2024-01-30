package org.example.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private long id;
    private String title;
    //private List<NewsDto> news = new ArrayList<>();

    @Override
    public String toString() {
        return  "{\n\"id\": \"" + id +
                "\"\n\"title\": \"" + title +
                "\"\n}";
    }
}
