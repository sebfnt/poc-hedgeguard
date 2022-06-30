package com.example.poc.raising;

import java.util.List;

public class RaisingRequest {

    private List<String> fields;
    private String mode;
    private Integer rowLimit;
    private String url;

    public RaisingRequest(List<String> fields, String mode, Integer rowLimit, String url) {
        this.fields = fields;
        this.mode = mode;
        this.rowLimit = rowLimit;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public Integer getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(Integer rowLimit) {
        this.rowLimit = rowLimit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
