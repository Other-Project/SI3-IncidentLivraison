package edu.ihm.td1.livraison.userFactory;

public class ClientFactory extends UserFactory<ClientFactory,Client>{
    private long numTelephone;
    private Adresse adresse;

    public ClientFactory() {
        setNom("Zion");
        setPrenom("Eva");
        setImageProfile("?");
        numTelephone = 786703245;
        adresse = new Adresse("France","Nice","Promenade des anglais",11);
    }
    @Override
    public Client build() {
        return new Client(getPrenom(),getNom(),getImageProfile(),numTelephone,adresse);
    }
    public ClientFactory setNumTelephone(int numTelephone) {
        this.numTelephone = numTelephone;
        return this;
    }
    public ClientFactory setAdresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }
}
