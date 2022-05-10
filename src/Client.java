import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;

    private final List<Game> purchasedGames = new ArrayList<>();
    private double totalPurchases;

    public Client(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getPurchasedGames() {
        return purchasedGames;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(double totalPurchases) {
        this.totalPurchases = totalPurchases;
    }




}
