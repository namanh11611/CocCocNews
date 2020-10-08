package com.namanh.coccocnews.model;

import com.namanh.coccocnews.network.DescriptionConverter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name = "item", strict = false)
public class Article {

    @Element
    private String title;

    @Element(name="description", data = true)
    @Convert(DescriptionConverter.class)
    private DescriptionData descriptionData;

    @Element
    private String pubDate;

    @Element
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return descriptionData.getDescription();
    }

    public String getImageUrl() {
        return descriptionData.getImageUrl();
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
