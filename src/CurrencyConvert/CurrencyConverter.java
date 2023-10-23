package CurrencyConvert;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import okhttp3.Response;

import java.math.BigDecimal;
import java.io.IOException;

public class CurrencyConverter {
    private ApiLayerClient apiLayerClient;
    private String apiKey;

    public CurrencyConverter(String apiKey, ApiLayerClient apiLayerClient) {
        this.apiLayerClient = apiLayerClient;
        this.apiKey = apiKey;
    }

    public double convert(double amount, String fromCurrency, String toCurrency) throws Exception {
        Response response = apiLayerClient.getExchangeRates(fromCurrency);

        if (response.isSuccessful()) {
            String jsonData = response.body().string();
            JSONParser parser = new JSONParser();

            try {
                JSONObject exchangeRateData = (JSONObject) parser.parse(jsonData);

                // Continue with your code to access JSON data within exchangeRateData.

                // Example:
                JSONObject rates = (JSONObject) exchangeRateData.get("conversion_rates");

                if (rates.containsKey(fromCurrency) && rates.containsKey(toCurrency)) {
                    double fromRate = ((Number) rates.get(fromCurrency)).doubleValue();
                    double toRate = ((Number) rates.get(toCurrency)).doubleValue();

                    BigDecimal result = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(toRate / fromRate));

                    return result.doubleValue();
                } else {
                    throw new IllegalArgumentException("Currency not supported.");
                }
            } catch (ParseException e) {
                throw new IOException("Failed to parse exchange rate data.");
            }
        } else {
            throw new IOException("Failed to fetch exchange rates.");
        }
    }
}
