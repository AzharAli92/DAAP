package com.daap.model;

import com.daap.util.Constants;

public class Layout {
    private String path;
    private String name;
    private Constants.XmlAntiPattern xmlAntiPattern;

    public Layout(String path, String fileName, Constants.XmlAntiPattern xmlAntiPattern) {
        this.path = path;
        this.name = fileName;
        this.xmlAntiPattern = xmlAntiPattern;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setXmlAntiPattern(Constants.XmlAntiPattern xmlAntiPattern) {
        this.xmlAntiPattern = xmlAntiPattern;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Constants.XmlAntiPattern getXmlAntiPattern() {
        return xmlAntiPattern;
    }

    @Override
    public String toString() {
        return "Layout{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", xmlAntiPattern=" + xmlAntiPattern +
                '}';
    }
}
