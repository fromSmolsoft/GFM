package com.smol.gfm.model;

import lombok.Data;

import java.util.List;

@Data
public class FilterEntry {

    private String id;
    private String category;
    private String title;
    private String updated;
    private String content;

    private List<AppsProperty> properties;


    @Override
    public String toString() {
        return "\n<entry>" +
               "\n\t<id>" + id + "</id>" +
               "\n\t<category term='" + category + '\'' +
               "\n\t<title>" + title + "</title>" +
               "\n\t<updated>" + updated + "</updated>" +
               "\n\t<content>" + content + "</content>" +
               "\n\t" + properties +
               "\n</entry>";
    }
}
