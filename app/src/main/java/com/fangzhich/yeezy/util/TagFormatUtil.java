package com.fangzhich.yeezy.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TagFormatUtil
 * Created by Khorium on 2016/9/9.
 */
public class TagFormatUtil {

    public static TagFormatUtil from(String format) {
        return new TagFormatUtil(format);
    }

    private final String format;
    private final Map<String, Object> tags = new LinkedHashMap<String, Object>();

    private TagFormatUtil(String format) {
        this.format = format;
    }

    public TagFormatUtil with(String key, Object value) {
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
