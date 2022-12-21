import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        ClientLog clientLog = new ClientLog();
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        String fileName = "basket.txt";
        String jsonFileName = "basket.json";

        File textFile = new File(fileName);
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }
        //int[] basket = new int[3];
        //Basket basket = new Basket(products, prices);
        //Basket basket = Basket.loadFromTxtFile(new File(fileName));
        Basket basket = Basket.loadFromJsonFile(new File(jsonFileName));
        System.out.println("Ваша корзина:");
        basket.printCart();

        while (true) {

            System.out.println("Выберите товар и количество или введите `end`");
            String line = scanner.nextLine();
            if ("end".equals(line)) {
                clientLog.exportAsCSV(new File("log.scv"));
                System.out.println("Программа завершена!");
                break;
            }
            String[] numbers = line.split(" ");
            if (numbers.length > 1) {
                byte number = Byte.parseByte(numbers[0]);
                byte amount = Byte.parseByte(numbers[1]);
                basket.addToCart(number, amount);
                clientLog.log(number, amount);
                basket.saveTxt(textFile);
                basket.saveToJson(new File(jsonFileName));
            }

            basket.printCart();
            basket.saveTxt(textFile);
        }
    }
}
