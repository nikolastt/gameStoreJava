import java.util.*;

public class MenuInteraction {
    private final Scanner enter = new Scanner(System.in);
    private final List<Game> games;
    private final Client client;
    private final ManageFile manageFile;

    public MenuInteraction(List<Game> games, Client client, ManageFile manageFile){
        this.games = games;
        this.client = client;
        this.manageFile = manageFile;
    }

    public void menu(){
        String choice;

        label:
        while(true){
            System.out.print("""

                    ------------------------------------MENU PRINCIPAL------------------------------------
                    Escolha uma opção.
                    1-Procurar por jogos.
                    2-Comprar jogos.
                    3-Dar nota a jogos comprados.
                    4-Gerar relatório.
                    0-Sair
                    ------------------------------------MENU PRINCIPAL------------------------------------
                    """);

            choice = enter.nextLine();

            switch (choice) {
                case "1" -> {
                    String nameGame = "";
                    while (nameGame.equals("")) {
                        System.out.println("Digite o nome do jogo!");
                        nameGame = enter.nextLine();
                    }
                    Game gameFound = findGame(this.games, nameGame.toLowerCase());

                    if (gameFound == null) {
                        System.out.println("Infelizmente o jogo não foi encontrado em nosso banco de jogos!");
                    } else {
                        List<Game> listGameFound = new ArrayList<>();
                        listGameFound.add(gameFound);
                        String gameDatails = getStringGameDatails(listGameFound);
                        System.out.print(gameDatails);
                    }

                }
                case "2" -> {
                    String nameGame = "";
                    printNameGame(games);
                    while (nameGame.equals("")) {
                        System.out.println("\n\nDigite o nome do jogo que deseja comprar!");
                        nameGame = enter.nextLine();
                    }
                    Game gameFound = findGame(this.games, nameGame.toLowerCase());

                    if (gameFound == null) {
                        System.out.println("Infelizmente o jogo não foi encontrado em nosso banco de jogos!");
                    } else {
                        System.out.println("\nJogo comprado com sucesso! :) ");
                        client.getPurchasedGames().add(gameFound);
                        double totalPursheases = client.getTotalPurchases() + gameFound.getAmount();
                        client.setTotalPurchases(totalPursheases);
                    }

                }
                case "3" -> {
                    String nameGame = "";
                    System.out.print("\n----------JOGOS COMPRADOS----------\n");
                    printPurshasedGames();
                    System.out.print("\n----------JOGOS COMPRADOS----------\n");
                    if (client.getPurchasedGames().size() > 0) {
                        while (nameGame.equals("")) {
                            System.out.println("\n\nDigite o nome do jogo que deseja dar a nota!");
                            nameGame = enter.nextLine();
                        }
                        Game gameFound = findGame(client.getPurchasedGames(), nameGame.toLowerCase());

                        if (gameFound == null) {
                            System.out.println("Infelizmente o jogo não foi encontrado em sua lista de jogos comprados!");
                        } else {
                            System.out.println("Digite a nota que deseja dar para o jogo de 0 a 5!");

                            int rating = Integer.parseInt(enter.nextLine());

                            while (rating > 5 || rating < 0) {
                                System.out.println("Avaliação incorreta, digite um numero de 0 a 5!");
                                rating = enter.nextInt();
                            }
                            gameFound.setClientRating(rating);
                            System.out.println("\n:) Nota adicionada com sucesso.");
                        }
                    }
                }
                case "4" -> menuGenerateReport();
                default -> {
                    if (choice.equals("0")) {
                        break label;
                    }
                    System.out.println("Digite uma opção válida!");
                }
            }
        }
    }

    private void menuGenerateReport () {

        int chosen = -1;

        while (chosen != 0){
            System.out.print("""

                    Escolha uma opção para gerar o relatório.
                    ---------------------------------MENU RELATORIO---------------------------------
                    1-Gerar dados de um cliente.
                    2-Gerar jogos lançados em uma data específica.
                    3-Gerar jogos com um preço menor que o escolhido.
                    4-Gerar jogos de um determinado genero.
                    5-Gerar dados de todos os jogos a venda.
                    0-Voltar
                    ---------------------------------MENU RELATORIO---------------------------------
                    """);

            chosen = Integer.parseInt(enter.nextLine());
            String line;

            if(chosen == 1){
                stringClientDataGenerate(client);
            }else if(chosen == 2){
                String yearGameLaunch = "";

                while(yearGameLaunch.equals("")){
                    System.out.println("Digite o ano do jogo que deseja pesquisar.");
                    yearGameLaunch = enter.nextLine();

                    if(yearGameLaunch.length() != 4){
                        System.out.println("\n-----------------------------------Erro-----------------------------------");
                        System.out.println("Ano inserido incorreto, por favor tente novamente\nExemplo: 2022.");
                        System.out.println("-----------------------------------Erro-----------------------------------\n");
                        yearGameLaunch = "";
                    }
                }

                List<Game> gamesWithYear = getGamesWithYear(games, yearGameLaunch);
                if(gamesWithYear.size() > 0) {

                    line = String.format("\n===========================================================RELATORIO JOGOS LANÇADOS NA DATA %s===========================================================\n", yearGameLaunch);
                    manageFile.writeLine(line);
                    System.out.print(line);

                    line = getStringGameDatails(gamesWithYear);
                    manageFile.writeLine(line);
                    System.out.print(line);

                    line = String.format("\n===========================================================RELATORIO JOGOS LANÇADOS NA DATA %s===========================================================\n", yearGameLaunch);
                    manageFile.writeLine(line + "\n\n\n\n");
                    System.out.print(line);


                }else{
                    System.out.println("\n-----------------------------------Erro-----------------------------------");
                    System.out.println(" :( Não existe nenhum jogo com a data informada");
                    System.out.println("-----------------------------------Erro-----------------------------------");
                }
            }else if(chosen == 3){
                double amountGame = -1;

                while(amountGame == -1){
                    System.out.println("Digite o valor, para ver os jogos com o valor abaixo do informado.");
                    try{
                        amountGame = Double.parseDouble(enter.nextLine());
                    } catch (InputMismatchException e){
                        System.err.println("Digite apenas números!");
                        System.exit(1);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(amountGame <= 0){
                        System.out.println("Digite um valor válido maior do que 0!");
                        amountGame = -1;
                    }
                }

                List<Game> gamesWithAmount = getGamesWithAmount(games, amountGame);



                line = String.format("\n===========================================================RELATORIO JOGOS COM VALOR MENOR QUE R$ %.2f===========================================================\n", amountGame);
                manageFile.writeLine(line);
                System.out.print(line);

                line = getStringGameDatails(gamesWithAmount);
                manageFile.writeLine(line);
                System.out.print(line);

                line = String.format("\n===========================================================RELATORIO JOGOS COM VALOR MENOR QUE R$ %.2f===========================================================\n", amountGame);
                manageFile.writeLine(line + "\n\n\n\n");
                System.out.print(line);



            }else if(chosen == 4){
                String genre = "";

                List<String> uniqueGenres = getUniqueGenres(games);
                printGenres(uniqueGenres);

                while (genre.equals("")) {

                    while(!uniqueGenres.contains(genre.toLowerCase())){
                        System.out.println("\nDigite um dos generos acima para buscar jogos.");
                        genre = enter.nextLine();

                        if (!uniqueGenres.contains(genre.toLowerCase())){
                            printGenres(uniqueGenres);
                            System.out.print("\n-----------------------------------ERRO-----------------------------------\n");
                            System.out.println("O genero digitado não corresponde a nenhum genero acima.");
                            System.out.print("-----------------------------------ERRO-----------------------------------\n");
                        }
                    }

                    if (genre.length() > 0){
                        List<Game> gamesWithGenre = getGamesWithGenre(games, genre);



                        line = String.format("\n===========================================================RELATORIO JOGOS COM O GENERO %s===========================================================\n", genre.toUpperCase());
                        manageFile.writeLine(line);
                        System.out.print(line);

                         line = getStringGameDatails(gamesWithGenre);
                         manageFile.writeLine(line);
                        System.out.print(line);


                        line = String.format("\n===========================================================RELATORIO JOGOS COM O GENERO %s===========================================================\n", genre.toUpperCase());
                        manageFile.writeLine(line + "\n\n\n\n");
                        System.out.print(line);



                    }else {
                        genre = "";
                    }
                }
            }else if(chosen == 5){


                line = "\n\n\n===========================================================RELATORIO DETALHE DE TODOS OS JOGOS===========================================================\n";
                manageFile.writeLine(line);
                System.out.print(line);

                line = getStringGameDatails(games);
                manageFile.writeLine(line);
                System.out.print(line);

                line = "\n\n\n===========================================================RELATORIO DETALHE DE TODOS OS JOGOS===========================================================\n";
                manageFile.writeLine(line + "\n\n\n\n");
                System.out.print(line);


            }

        }

    }


    private String getStringGameDatails(List<Game> games){
        List<String> listString = new ArrayList<>();

        for(Game game: games) {
            listString.add(String.format("\n-------------------------%s-------------------------\n" +
                    "Nome: " + game.getName() +
                    "\nAno de lançamento: " + game.getLauchYear() +
                    "\nPreço: R$ " + game.getAmountString() +
                    "\nGenero: " + game.getGenre() +
                    "\nMédia Avaliação geral: " + game.getRatingStars(game.getRating()) +
                    "\nMultplayer: " + game.IconIsMultiplayer(), game.getName().toUpperCase()));

            if(game.isMultiplayer()){
                listString.add(String.format("\nNumero máximo de jogadores no multiplayer: %d", game.getMAXNumberOfPlayers()));
            }
            if (game.getClientRating() >= 0) {
                listString.add(String.format("\nAvaliação do(a) %s: %s", client.getName(), game.getRatingStars(game.getClientRating())));
            }
            listString.add(String.format("\n-------------------------%s-------------------------\n", game.getName().toUpperCase()));
        }

        return listString.toString().replaceAll(",","").replaceAll("\\[","").replaceAll("]", "");

    }

    private List<Game> getGamesWithGenre(List<Game> games, String genre){
        List<Game> gamesWithGenre = new ArrayList<>();

        for(Game game : games){
            if (game.getGenre().equalsIgnoreCase(genre)){
                gamesWithGenre.add(game);
            }
        }

        return gamesWithGenre;
    }

    private void printGenres(List<String> genres){
        System.out.print("\n----------GENEROS----------\n");
        for (String genre: genres ){
            String firstLtr = genre.substring(0, 1).toUpperCase();
            String restLtrs = genre.substring(1);
            System.out.printf("-%s\n", firstLtr + restLtrs);
        }
        System.out.print("----------GENEROS----------\n");
    }

    private List<String> getUniqueGenres(List<Game> games){
        List<String> uniqueGenres = new ArrayList<>();

        for (Game game : games) {
            String genre = game.getGenre().toLowerCase();


            if (uniqueGenres.contains(genre)) {

                break;
            } else {
                uniqueGenres.add(genre);
            }
        }

        return uniqueGenres;
    }

    private List<Game> getGamesWithAmount (List<Game> games, double amount){
        List<Game> gamesWithAmount = new ArrayList<>();

        for (Game game : games) {
            if (game.getAmountString().equals("Gratis")) {
                gamesWithAmount.add(game);
            } else if (Double.parseDouble(game.getAmountString()) < amount) {
                gamesWithAmount.add(game);
            }
        }
        return gamesWithAmount;
    }

    private List<Game> getGamesWithYear(List<Game> games, String yearGameLauch){
        List<Game> gamesWithYear = new ArrayList<>();

        for (Game game : games) {
            if (game.getLauchYear().equals(yearGameLauch)) {
                gamesWithYear.add(game);
            }
        }


        return gamesWithYear;
    }

    private void stringClientDataGenerate(Client client){



        String line = "\n===========================================================RELATÓRIO CLIENTE===========================================================\n";
        manageFile.writeLine(line);
        System.out.print(line);

        line = String.format("Nome do Cliente: %s", client.getName());
        manageFile.writeLine(line  );
        System.out.print(line);

        line = "\nJogos comprados: ";
        manageFile.writeLine(line );
        System.out.print(line);


        line = getNameString(client.getPurchasedGames());
        manageFile.writeLine(line );
        System.out.print(line);

        line = String.format("\nTotal gasto: R$%.2f", client.getTotalPurchases());
        manageFile.writeLine(line );
        System.out.print(line);

        line = "\n===========================================================RELATÓRIO CLIENTE===========================================================\n";
        manageFile.writeLine(line + "\n\n\n\n" );
        System.out.print(line);



    }

    private String getNameString(List<Game> purshasedGames){
        if (purshasedGames.size() > 0) {
            String purshasedGamesString;
            List<String> listPurshasedGamesString = new ArrayList<>();

            for (Game game : purshasedGames) {
                listPurshasedGamesString.add(game.getName());
            }

            purshasedGamesString = listPurshasedGamesString.toString();
            return purshasedGamesString;
        }else{
            return "";
        }
    }

    private void printPurshasedGames(){
        List<Game> purshasedGames = client.getPurchasedGames();

        if(purshasedGames.size() <= 0){
            System.out.println(" :(    Você não possui jogos comprados. ");
        }else{
           printNameGame(purshasedGames);

        }
    }


    private void printNameGame(List<Game> games){

        for (int i = 0; i < games.size(); i++){
            String gameName = games.get(i).getName();
            System.out.printf("%10s  | ", gameName);

            if((i+1) % 5 == 0){
                System.out.println();
                System.out.println();
            }
        }
    }


    private Game findGame(List<Game> games, String nameOfGame){
        Game gameFound = null;
        for (Game game : games) {
            String gameName = game.getName().toLowerCase();
            if (gameName.equals(nameOfGame)) {
                gameFound = game;
            }
        }

        return gameFound;
    }

}
