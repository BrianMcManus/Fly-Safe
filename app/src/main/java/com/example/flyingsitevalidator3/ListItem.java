package com.example.flyingsitevalidator3;

/**
 * Created by brian on 28/06/2017.
 */

public class ListItem {
    private String title;
    private String content;

    public ListItem(String nTitle, String nContent)
    {
        title = nTitle;
        content = nContent;
    }

    public ListItem()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItem listItem = (ListItem) o;

        if (title != null ? !title.equals(listItem.title) : listItem.title != null) return false;
        return content != null ? content.equals(listItem.content) : listItem.content == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
