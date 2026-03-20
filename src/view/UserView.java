package view;

import java.util.Scanner;

public class UserView {

    Scanner sc = new Scanner(System.in);

    public String getEmail() {
        System.out.print("Enter email: ");
        return sc.nextLine();
    }

    public String getPassword() {
        System.out.print("Enter password: ");
        return sc.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}