package view;

import java.util.Scanner;

public class ProductView {

    Scanner sc = new Scanner(System.in);

    public int getProductId() {
        System.out.print("Enter product ID: ");
        return sc.nextInt();
    }

    public String getTitle() {
        System.out.print("Enter product title: ");
        sc.nextLine();
        return sc.nextLine();
    }

    public String getDescription() {
        System.out.print("Enter description: ");
        return sc.nextLine();
    }

    public double getPrice() {
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        return price;
    }

    public String getCondition() {
        System.out.print("Enter condition: ");
        return sc.nextLine();
    }

    public String getCategory() {
        System.out.print("Enter category: ");
        return sc.nextLine();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}