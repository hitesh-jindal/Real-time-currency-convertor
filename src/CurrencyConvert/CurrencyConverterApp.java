package CurrencyConvert;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import okhttp3.Response;
import java.util.Set;
import java.util.Scanner;

public class CurrencyConverterApp {
    public static void main(String[] args) {
        String apiKey = "21568e2ab8b015e3e8963076"; // Declare apiKey here

        ApiLayerClient apiLayerClient = new ApiLayerClient(apiKey);

        CurrencyConverter converter = new CurrencyConverter(apiKey,apiLayerClient);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Currencies:");
        try {
            Response response = apiLayerClient.getExchangeRates("USD");

            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JSONParser parser = new JSONParser();
                JSONObject exchangeRateData = (JSONObject) parser.parse(jsonData);

                JSONObject rates = (JSONObject) exchangeRateData.get("conversion_rates");
                Set<String> currencyCodes = rates.keySet();

                for (String currencyCode : currencyCodes) {
                    System.out.println(currencyCode);
                }

                System.out.print("Enter the amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character

                System.out.print("Enter the source currency code: ");
                String fromCurrency = scanner.nextLine().toUpperCase();

                System.out.print("Enter the target currency code: ");
                String toCurrency = scanner.nextLine().toUpperCase();

                try {
                    double convertedAmount = converter.convert(amount, fromCurrency, toCurrency);
                    System.out.println(amount + " " + fromCurrency + " is " + convertedAmount + " " + toCurrency);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Failed to fetch exchange rates.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
