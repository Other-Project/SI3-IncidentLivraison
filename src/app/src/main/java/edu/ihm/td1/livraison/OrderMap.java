package edu.ihm.td1.livraison;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class OrderMap {
    private static final HashMap<Integer, Order> ORDERS = new HashMap<>();

    static {
        addOrder(new Order(0, "Sac à dos Dora", R.drawable.sac, false, "435 Rue du Chemin, Valbonne 06560", new GregorianCalendar(2024, 5, 18, 9, 0).getTimeInMillis()));
        addOrder(new Order(1, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510", new GregorianCalendar(2024, 5, 25, 9, 0).getTimeInMillis()));
        addOrder(new Order(2, "Sac Dora Taille Enfant", R.drawable.sac, false, "1 bis rue de l’étoile, Antibes 06600", new GregorianCalendar(2024, 12, 18, 9, 0).getTimeInMillis()));
        addOrder(new Order(3, "Sac Dora Taille Enfant", R.drawable.sac, false, "1 rue Bicon, Antibes 06600", new GregorianCalendar(2024, 7, 14, 9, 0).getTimeInMillis()));
        addOrder(new Order(4, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510", new GregorianCalendar(2024, 9, 28, 9, 0).getTimeInMillis()));
        addOrder(new Order(5, "Sac à dos Dora Taille Adulte", R.drawable.sac, true, "14 Chem. de Saint-Bernard, Valbonne 06560", new GregorianCalendar(2024, 8, 1, 9, 0).getTimeInMillis()));
        addOrder(new Order(6, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "1913 route de Cannes, Valbonne 06560", new GregorianCalendar(2024, 8, 25, 9, 0).getTimeInMillis()));
        addOrder(new Order(7, "Sac Dora Edition limitée Taille Adulte", R.drawable.sac, true, "2 chemin de Ronde, Biot 06510", new GregorianCalendar(2024, 8, 18, 9, 0).getTimeInMillis()));
        addOrder(new Order(8, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "11 avenue Saint-Exupéry, Antibes 06600", new GregorianCalendar(2024, 8, 8, 9, 0).getTimeInMillis()));
    }

    public static void addOrder(Order order) {
        ORDERS.put(order.getId(), order);
    }

    public static Order getOrder(int id) {
        return ORDERS.get(id);
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(ORDERS.values());
    }
}
