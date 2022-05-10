import java.util.List;

public class Main {
    public static void main(String[] args) {

        Authentication authentication = new Authentication();
        Client client = new Client(authentication.getUser());
        ManageFile manageFile = new ManageFile("./Data.txt", "./exitData.txt");

        authentication.authenticate(client);

        List<Game> games = manageFile.loadGames();

        MenuInteraction menu = new MenuInteraction(games, client, manageFile);
        menu.menu();









    }
}
