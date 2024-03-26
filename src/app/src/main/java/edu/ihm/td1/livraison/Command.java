package edu.ihm.td1.livraison;

public class Command {

    private String description;
    private int id;
    private int image;

    public Command(int id, String desc, int img){
        this.id = id;
        description = desc;
        image = img;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    public int getImage(){
        return image;
    }
}
