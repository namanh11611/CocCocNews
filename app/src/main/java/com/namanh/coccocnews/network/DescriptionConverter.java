package com.namanh.coccocnews.network;

import com.namanh.coccocnews.model.DescriptionData;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescriptionConverter implements Converter<DescriptionData> {

    private static final String IMAGE_ERROR = "https://media-exp1.licdn.com/dms/image/C561BAQH9TyOj9sKOiA/company-background_10000/0?e=2159024400&v=beta&t=Ef5-Mro3XcUCpfq0JOOh5xlLGs4SgP_0bMCbaf3vkbc";

    @Override
    public DescriptionData read(InputNode node) throws Exception {
        final String IMG_URL_REG_EX = "src=\"([^>]+)\"";
        final String HTML_TAG_REG_EX = "</?[^>]+>";

        String nodeText = node.getValue();
        Pattern imageUrlPattern = Pattern.compile(IMG_URL_REG_EX);
        Matcher matcher = imageUrlPattern.matcher(nodeText);

        String url = null;
        while (matcher.find()) {
            url = matcher.group(1);
        }

        if (url == null) url = IMAGE_ERROR;
        String text = nodeText.replaceFirst(IMG_URL_REG_EX, "").replaceAll(HTML_TAG_REG_EX, "");
        return new DescriptionData(url, text);
    }

    @Override
    public void write(OutputNode node, DescriptionData value) throws Exception {

    }
}
