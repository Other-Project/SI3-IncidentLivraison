package edu.ihm.td1.livraison;

public class Command {

    private String description;
    private int id;
    private int image;
    private Boolean delivered;

    public Command(int id, String desc, int img, boolean del){
        this.id = id;
        description = desc;
        image = img;
        delivered = del;
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

    public Boolean getDelivered() {
        return delivered;
    }
}
