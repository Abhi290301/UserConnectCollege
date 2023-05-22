package com.abhishek.collegecu.Ui.About;

public class CourseModel {
    private int img;
    private String ctitle,description;
    public CourseModel(){

    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public CourseModel(int img, String ctitle, String description) {
        this.img = img;
        this.ctitle = ctitle;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
