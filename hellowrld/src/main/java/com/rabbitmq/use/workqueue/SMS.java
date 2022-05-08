package com.rabbitmq.use.workqueue;

public class SMS {

    private String name;
    private String phonenumber;
    private String content;

    public SMS(String name, String phonenumber, String content) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
