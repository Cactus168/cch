package com.jo.cch.sql;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_learn_log")
public class LearnLog {

    @DatabaseField(columnName = "id")
    private String id;

    @DatabaseField(columnName = "no")
    private Integer no;

    @DatabaseField(columnName = "subject")
    private String subject;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "stateMgs")
    private String stateMgs;

    @DatabaseField(columnName = "startDate")
    private String startDate;

    @DatabaseField(columnName = "endDate")
    private String endDate;

    @DatabaseField(columnName = "errorMgs")
    private String errorMgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStateMgs() {
        return stateMgs;
    }

    public void setStateMgs(String stateMgs) {
        this.stateMgs = stateMgs;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getErrorMgs() {
        return errorMgs;
    }

    public void setErrorMgs(String errorMgs) {
        this.errorMgs = errorMgs;
    }
}
