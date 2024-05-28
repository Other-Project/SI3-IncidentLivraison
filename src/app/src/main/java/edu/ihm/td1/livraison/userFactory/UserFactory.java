package edu.ihm.td1.livraison.userFactory;

public abstract class UserFactory<T extends UserFactory, U extends User> {
    private String prenom;
    private String nom;
    private String imageProfile;

    public abstract U build();

    public String getPrenom() {
        return prenom;
    }

    public T setPrenom(String prenom) {
        this.prenom = prenom;
        return (T) this;
    }

    public String getNom() {
        return nom;
    }

    public T setNom(String nom) {
        this.nom = nom;
        return (T) this;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public T setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
        return (T) this;
    }
}
