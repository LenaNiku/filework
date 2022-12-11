import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        String fileName = "basket.txt";
        String binFileName = "basket.bin";

        File textFile = new File(fileName);
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        //Basket basket = new Basket(products, prices);
        Basket basket = Basket.loadFromTxtFile(new File(fileName));
        //Basket basket = Basket.loadFromBinFile(new File(binFileName));

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

                basket.addToCart(number, amount);
                basket.saveTxt(textFile);
                basket.saveBin(new File(binFileName));
            }

            basket.printCart();
        }
    }
}
