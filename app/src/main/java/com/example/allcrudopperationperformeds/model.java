package com.example.allcrudopperationperformeds;

public class model
{
    String name,Fathername,email;
    String image;
    String systemimage;


    public String getSystemimage() {
        return systemimage;
    }

    public void setSystemimage(String systemimage) {
        this.systemimage = systemimage;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public model() {
    }

    public model(String name, String fathername, String email,String image, String systemimage) {
        this.name = name;
        this.Fathername = fathername;
        this.email = email;
        this.image = image;
        this.systemimage=systemimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathername() {
        return Fathername;
    }

    public void setFathername(String fathername) {
        Fathername = fathername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
