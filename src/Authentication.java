import java.util.Scanner;

public class Authentication {
    Scanner enter = new Scanner(System.in);

    private String user = "";
    private String password = "";

    public void authenticate(Client client) {
        String correctUser = "admin";
        while (!user.equals(correctUser)) {
            System.out.println("Digite o usuário.");
            user = enter.nextLine();

            if (user.equals(correctUser)) {
                client.setName(user);

            }else{
                System.out.println("Usuário incorreto, tente novamente!\n");
            }
        }

        String correctPassword = "123";
        while (!password.equals(correctPassword)) {
            System.out.println("Digite a senha.");
            password = enter.nextLine();

            if (!password.equals(correctPassword)) {
                System.out.println("Senha incorreta, tente novamente!\n");
            }
        }

        System.out.printf("\n--------------------LOGIN REALIZADO COM SUCESSO \uD83D\uDE00 ! - BEM VINDO(A) %s --------------------\n", client.getName().toUpperCase());

    }

    public String getUser() {
        return user;
    }
}
