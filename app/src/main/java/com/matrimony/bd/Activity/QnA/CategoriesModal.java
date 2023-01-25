package com.matrimony.bd.Activity.QnA;

import java.util.ArrayList;

public class CategoriesModal {

    // creating variable for our category,
    // news array list and expanded boolean.
    private String newsCategory;
    private ArrayList<NewsModal> newsModalArrayList;
    private boolean expanded;

    // creating a constructor.
    public CategoriesModal(String newsCategory, ArrayList<NewsModal> newsModalArrayList) {
        this.newsCategory = newsCategory;
        this.newsModalArrayList = newsModalArrayList;

        // default we are setting expanded as false.
        this.expanded = false;
    }

    // getter and setter methods.
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public ArrayList<NewsModal> getNewsModalArrayList() {
        return newsModalArrayList;
    }

    public void setNewsModalArrayList(ArrayList<NewsModal> newsModalArrayList) {
        this.newsModalArrayList = newsModalArrayList;
    }

}
