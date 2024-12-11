package org.example.server.model;

import java.util.List;

public class Content {
    private String title;
    private String type;
    private List<String> tags;

    public Content(String title, String type, List<String> tags) {
        this.title = title;
        this.type = type;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}