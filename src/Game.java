public class Game {

    private final String name;
    private boolean isMultiplayer;
    private String lauchYear;
    private double amount;
    private String genre;
    private int rating;
    private int clientRating = -1;
    private int MAXNumberOfPlayers;




    public Game(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isMultiplayer(){
        return isMultiplayer;
    }

    public String IconIsMultiplayer() {
        if (isMultiplayer){
            return "\uD83D\uDC4D";
        }else{
            return "\uD83D\uDC4E";
        }
    }

    public void setIsMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public String getLauchYear() {
        return lauchYear;
    }

    public void setLauchYear(String lauchYear) {
        this.lauchYear = lauchYear;
    }

    public String getAmountString() {
        if (amount == 0){
            return "Gratis";
        }
        return Double.toString(amount);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRatingStars(int rating) {
        return "★".repeat(Math.max(0, rating));
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getMAXNumberOfPlayers() {
        return MAXNumberOfPlayers;
    }

    public void setMAXNumberOfPlayers(int MAXNumberOfPlayers) {
        this.MAXNumberOfPlayers = MAXNumberOfPlayers;
    }

    public void setClientRating(int clientRating) {
        this.clientRating = clientRating;
    }

    public int getClientRating() {
        return clientRating;
    }
}
