import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        String fileName = "basket.txt";

        File textFile = new File(fileName);
        for (int i = 0; i < products.length; i++) {
            // 1. Молоко 50 руб/шт
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        //int[] basket = new int[3];
        //Basket basket = new Basket(products, prices);
        Basket basket = Basket.loadFromTxtFile(fileName, products, prices);
        System.out.println("Ваша корзина:");
        basket.printCart();
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                System.out.println("Программа завершена!");
                break;
            }
            String[] numbers = line.split(" ");
            if (numbers.length > 1) {
                byte number = Byte.valueOf(numbers[0]);
                byte amount = Byte.valueOf(numbers[1]);
                //basket[number - 1] += amount;
                basket.addToCart(number, amount);
                basket.saveTxt(textFile);
            }
            /*
        }
        int totalPrice = 0;
        for (int i = 0; i < basket.length; i++) {
            int priceOfProduct = basket[i] * prices[i];
            totalPrice += priceOfProduct;
            if (basket[i] > 0) {
                System.out.println("продукт " + products[i] + " количество " + basket[i] + " стоимость " + priceOfProduct);
            }
        }
        System.out.println("общая цена " + totalPrice);
        scanner.close();*/
            basket.printCart();
            basket.saveTxt(textFile);
        }
    }
}
