package CurrencyConvert;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ApiLayerClient {
    private OkHttpClient client;
    private String apiKey;

    public ApiLayerClient(String apiKey) {
        client = new OkHttpClient();
        this.apiKey = apiKey;
    }

    public Response getExchangeRates(String baseCurrency) throws IOException {
        String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        return client.newCall(request).execute();
    }
}
