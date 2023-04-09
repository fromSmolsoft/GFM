package com.smol.gfm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DocumentObj {
    private String            feed;
    private String            title;
    private String            id;
    private String            updated;
    private Author            author;
    private List<FilterEntry> filterEntries;

    public DocumentObj(String feed) {
        this.feed = feed;
    }

    @Override
    public String toString() {
        return "=== DocumentObj ===" +
               "\n<feed>" +
               "\n<title>" + title + "</title>" +
               "\n<id>" + id + "</id>" +
               "\n<updated>" + updated + "</updated>" +
               "\n<author>" + author + "</author>" +
               "\n" + filterEntries +
               "\n<feed>";

    }
}
