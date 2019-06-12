package com.daap.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class ResultDocument {
    private XWPFDocument doc;
    private XWPFTable table;
    private String name;

    public ResultDocument(String name) {
//        this.doc = doc;
//        this.table = table;


        this.name = name;
        doc = new XWPFDocument();
        table = doc.createTable();
        table.getRow(0).getCell(0).setText("Sr. No");
        table.getRow(0).createCell().setText("Class");
        table.getRow(0).createCell().setText("Path");
    }

    public void setDoc(XWPFDocument doc) {
        this.doc = doc;
    }

    public void setTable(XWPFTable table) {
        this.table = table;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XWPFDocument getDoc() {
        return doc;
    }

    public XWPFTable getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ResultDocument{" +
                "doc=" + doc +
                ", table=" + table +
                ", name='" + name + '\'' +
                '}';
    }
}
