package com.jeesite.modules.history.entity;

import com.jeesite.modules.history.form.FormData;

public class HistoryForm {
    private HtHistory htHistory;        //历史表数据
    private FormData formData;          //关联的Form表

    public HistoryForm(HtHistory htHistory, FormData formData) {
        this.htHistory = htHistory;
        this.formData = formData;
    }

    public HtHistory getHtHistory() {
        return htHistory;
    }

    public void setHtHistory(HtHistory htHistory) {
        this.htHistory = htHistory;
    }

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        return "HistoryForm{" +
                "htHistory=" + htHistory +
                ", formData=" + formData +
                '}';
    }
}
