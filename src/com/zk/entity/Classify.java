package com.zk.entity;

public class Classify {

    private Integer id;
    private String name;
    private String table;

    public Classify() {
    }

    public Classify(Integer id, String name, String table) {
        this.id = id;
        this.name = name;
        this.table = table;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", table='" + table + '\'' +
                '}';
    }
}
