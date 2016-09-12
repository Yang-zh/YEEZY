package com.fangzhich.yeezy.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TagFormat
 * Created by Khorium on 2016/9/9.
 */
public class TagFormat {

    public static TagFormat from(String format) {
        return new TagFormat(format);
    }

    private final String format;
    private final Map<String, Object> tags = new LinkedHashMap<String, Object>();

    private TagFormat(String format) {
        this.format = format;
    }

    public TagFormat with(String key, Object value) {
        tags.put("\\{" + key + "\\}", value);
        return this;
    }

    public String format() {
        String formatted = format;
        for (Map.Entry<String, Object> tag : tags.entrySet()) {
            // bottleneck, creating temporary String objects!
            formatted = formatted.replaceAll(tag.getKey(), tag.getValue().toString());
        }
        return formatted;
    }
}
