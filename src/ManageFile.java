import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ManageFile {

    private final String file;
    private final String exitFile;

    BufferedWriter write = null;
    BufferedReader reader;

    public ManageFile(String file, String exitFile) {
        this.file = file;
        this.exitFile = exitFile;
    }

    public List<Game> loadGames() {
        List<Game> listGames = new ArrayList<>();


        try{
            reader = new BufferedReader((new FileReader(this.file)));
            String lineRead;

            while ((lineRead = reader.readLine()) != null){
                listGames.add(manageLineGame(lineRead));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Não foi possível localizar o arquivo");
            System.exit(1);
        } catch (IOException | ParseException e) {
            System.err.println("Falha :( " + e.getMessage());
            System.exit(1);
        }

        return listGames;
    }




    private static Game manageLineGame(String line) throws ParseException {
        String[] splitLine = line.split(";");

        if(splitLine.length != 7 && splitLine.length != 6){
            throw new ArrayIndexOutOfBoundsException("O formato da linha está fora do esperado, devem haver 6 ou 7 argumentos.");
        }

        Game game = new Game(splitLine[0]);
        game.setIsMultiplayer(Boolean.parseBoolean(splitLine[1]));
        if(splitLine.length == 6){
            game.setLauchYear(splitLine[2]);
            game.setAmount(Double.parseDouble(splitLine[3]));
            game.setGenre(splitLine[4]);
            game.setRating(Integer.parseInt(splitLine[5]));
        }else {
            game.setMAXNumberOfPlayers(Integer.parseInt(splitLine[2]));
            game.setLauchYear(splitLine[3]);
            game.setAmount(Double.parseDouble(splitLine[4]));
            game.setGenre(splitLine[5]);
            game.setRating(Integer.parseInt(splitLine[6]));
        }
        return game;
    }

    public void writeLine(String line){
        if(write == null){
            try{
                write = new BufferedWriter(new FileWriter(this.exitFile));
                write.write(line);
                write.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                write.write(line);
                write.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
