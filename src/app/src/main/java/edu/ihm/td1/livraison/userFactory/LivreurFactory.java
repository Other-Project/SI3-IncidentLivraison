package edu.ihm.td1.livraison.userFactory;

public class LivreurFactory extends UserFactory<LivreurFactory,Livreur>{
    private long numTelephone;

    public LivreurFactory() {
        setNom("Zion");
        setPrenom("Eva");
        setImageProfile("?");
        numTelephone = 786703245;
    }

    @Override
    public Livreur build() {
        return new Livreur(getPrenom(),getNom(),getImageProfile(),numTelephone);
    }
    public LivreurFactory setNumTelephone(int numTelephone) {
        this.numTelephone = numTelephone;
        return this;
    }
}
