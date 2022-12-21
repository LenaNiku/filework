import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Basket {
    private final int[] basket = new int[3];
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
    }

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

   }

    public void saveTxt(File textFile) {

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
            System.out.println(e);
        }

    }

    static Basket loadFromTxtFile(File textFile) {

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
            System.out.println(e);
        }

        return basket;

    }

    public void saveToJson(File jsonFile) {

        JSONObject basketJson = new JSONObject();

        JSONArray productsArrayJson = new JSONArray();
        JSONArray pricesArrayJson = new JSONArray();
        JSONArray basketArrayJson = new JSONArray();

        //products
        for (String product : products) {
            JSONObject productElementJson = new JSONObject();
            productElementJson.put("id", product);
            productsArrayJson.add(productElementJson);
        }

        for (int price : prices) {
            JSONObject priceElementJson = new JSONObject();
            priceElementJson.put("id", price);
            pricesArrayJson.add(priceElementJson);
        }

        for (int j : basket) {
            JSONObject basketElementJson = new JSONObject();
            basketElementJson.put("id", j);
            basketArrayJson.add(basketElementJson);
        }

        basketJson.put("products", productsArrayJson);
        basketJson.put("prices", pricesArrayJson);
        basketJson.put("basket", basketArrayJson);

        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(basketJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Basket loadFromJsonFile(File jsonFile) {

        Basket basket = new Basket();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(jsonFile));

            JSONObject basketParsedJson = (JSONObject) obj;

            JSONArray productsJson = (JSONArray)basketParsedJson.get("products");
            int i = 0;
            for (Object o : productsJson) {
                JSONObject productJson = (JSONObject) o;
                basket.products[i] = (String) productJson.get("id");
                i++;
            }
            JSONArray pricesJson = (JSONArray)basketParsedJson.get("prices");
            i = 0;
            for (Object o : pricesJson) {
                JSONObject priceJson = (JSONObject) o;
                basket.prices[i] = Math.toIntExact((long) priceJson.get("id"));
                i++;
            }
            JSONArray basketJson = (JSONArray)basketParsedJson.get("basket");
            i = 1;
            for (Object o : basketJson) {
                JSONObject basketElementJson = (JSONObject) o;
                basket.addToCart(i, Math.toIntExact((long) basketElementJson.get("id")));
                i++;
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return basket;
    }
}
