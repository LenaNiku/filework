import java.io.*;

public class Basket implements Serializable {
    private int[] basket = new int[3];
    private int[] prices;
    private String[] products;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
    }
    public void addToCart(int productNum, int amount){
        basket[productNum - 1] += amount;


    };
   public void printCart() {
       int totalPrice = 0;
       for (int i = 0; i < basket.length; i++) {
           int priceOfProduct = basket[i] * prices[i];
           totalPrice += priceOfProduct;
           if (basket[i] > 0) {
               System.out.println("продукт " + products[i] + " количество " + basket[i] + " стоимость " + priceOfProduct);
           }
       }
       System.out.println("общая цена " + totalPrice);

   };

    public void saveTxt(File textFile) throws IOException {

        try (PrintWriter out = new PrintWriter(textFile)) {
            for (int i = 0; i < basket.length; i++) {

                // (long e : longArrInField)
                out.print(basket[i] + " ");
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    static Basket loadFromTxtFile(String fileName, String[] products, int[] prices)  throws IOException {

        Basket basket = new Basket(products, prices);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))  {
            String value = reader.readLine();
            String[] amounts = value.split(" ");
            int i = 1;
            for (String a: amounts) {
                basket.addToCart(i, Integer.parseInt(a));
                i++;
            }
        }

        /*try (FileInputStream inputStream = new FileInputStream(pathToFile)) {
            String everything = inputStream.toString();
            String[] amounts = everything.split(" ");
            int i = 0;
            for (String a: amounts) {
                basket.addToCart(i, Integer.parseInt(a));
                i++;
            }
        }*/
        catch (IOException e) {
            System.out.println(e.toString());
        }

        return basket;

    }

    public void saveBin(String fileName) throws IOException {
      FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    static Basket loadFromBinFile(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream
                = new FileInputStream(fileName);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Basket basket = (Basket) objectInputStream.readObject();
        objectInputStream.close();
        return basket;
    }
}
