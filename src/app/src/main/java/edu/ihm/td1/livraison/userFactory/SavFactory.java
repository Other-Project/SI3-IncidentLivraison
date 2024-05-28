package edu.ihm.td1.livraison.userFactory;

public class SavFactory extends UserFactory<SavFactory,Sav>{
    public SavFactory() {
        setNom("Ponsable");
        setPrenom("Thérèse");
        setImageProfile("?");
    }

    @Override
    public Sav build() {
        return new Sav(getPrenom(),getNom(),getImageProfile());
    }
}
