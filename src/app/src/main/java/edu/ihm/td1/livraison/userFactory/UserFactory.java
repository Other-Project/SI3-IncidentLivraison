package edu.ihm.td1.livraison.userFactory;

public class UserFactory {
    public User getUser(String userType){
        if(userType == null){
            return null;
        }
        if(userType.equalsIgnoreCase("CLIENT")){
            return new Client();

        } else if(userType.equalsIgnoreCase("LIVREUR")){
            return new Livreur();

        } else if(userType.equalsIgnoreCase("SAV")){
            return new Sav();
        }

        return null;
    }
}
