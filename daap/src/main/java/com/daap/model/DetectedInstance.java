package com.daap.model;

public class DetectedInstance {
    String name;
    String path;
    int count;


    public DetectedInstance(String name, String path, int count) {
        this.name = name;
        this.path = path;
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void increment() {
        this.count++;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "DetectedInstance{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", count=" + count +
                '}';
    }
}
