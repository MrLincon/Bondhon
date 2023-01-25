package com.matrimony.bd.Activity.QnA;

public class NewsModal {

    // string for news title and description.
    private String newsTitle;
    private String newsDesc;

    // on below line we have created a constructor.
    public NewsModal(String newsTitle, String newsDesc) {
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
    }

    // creating a getter and setter.
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

}
