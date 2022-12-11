import java.io.*;

public class Basket implements Serializable {
    private int[] basket = new int[3];
    private String[] products = new String[3];
    private int[] prices = new int[3];

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
    }

    public Basket() {

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

            for (int i = 0; i < products.length; i++) {
                out.print(products[i] + " ");
            }
            out.println("");
            for (int i = 0; i < prices.length; i++) {
                out.print(prices[i] + " ");
            }
            out.println("");
            for (int i = 0; i < basket.length; i++) {
                out.print(basket[i] + " ");
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    static Basket loadFromTxtFile(File textFile)  throws IOException {

        Basket basket = new Basket();

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile)))  {
            //products
            String value = reader.readLine();
            String[] products_name = value.split(" ");
            int i = 0;
            for (String a: products_name) {
                basket.products[i] = a;
                i++;
            }

            //prices
            value = reader.readLine();
            String[] prices_name = value.split(" ");
            i = 0;
            for (String a: prices_name) {
                basket.prices[i] = Integer.parseInt(a);
                i++;
            }

            //basket
            value = reader.readLine();
            String[] amounts = value.split(" ");
            i = 1;
            for (String a: amounts) {
                basket.addToCart(i, Integer.parseInt(a));
                i++;
            }
        }

        catch (IOException e) {
            System.out.println(e.toString());
        }

        return basket;

    }

    public void saveBin(File fileName) throws IOException {
      FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    static Basket loadFromBinFile(File fileName) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream
                = new FileInputStream(fileName);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Basket basket = (Basket) objectInputStream.readObject();
        objectInputStream.close();
        return basket;
    }
}
