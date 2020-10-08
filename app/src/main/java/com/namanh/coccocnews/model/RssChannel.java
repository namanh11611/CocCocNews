package com.namanh.coccocnews.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class RssChannel {

    @ElementList(inline = true, required = false)
    private List<Article> itemList;

    public List<Article> getItemList() {
        return itemList;
    }

    public void setItemList(List<Article> itemList) {
        this.itemList = itemList;
    }
}
