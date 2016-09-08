package com.fangzhich.yeezy.net.Bean;

/**
 * test
 * Created by Khorium on 2016/8/30.
 */
public class TestPicEntity {
    public int id;
    public String title;
    public String description;
    public int width;
    public int height;
    public ImagesBean images;

    public static class ImagesBean {
        public Object hidpi;
        public String normal;
        public String teaser;
    }
}
