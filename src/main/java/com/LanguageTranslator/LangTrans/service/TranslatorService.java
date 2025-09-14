package com.LanguageTranslator.LangTrans.service;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TranslatorService {

    // IMPORTANT: Change this to "http://localhost:5000/translate" if you are
    // running the local Docker container as we discussed.
    private static final String API_URL = "http://localhost:5000/translate";
    private final OkHttpClient client = new OkHttpClient();

    public String translateText(String text, String source, String target) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("q", text)
                .add("source", source)
                .add("target", target)
                .add("format", "text")
                .build();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorResponse = response.body().string();
                throw new IOException("API call failed: " + response.code() + " " + response.message() + " - " + errorResponse);
            }

            String jsonResponse = response.body().string();
            JSONObject obj = new JSONObject(jsonResponse);
            return obj.getString("translatedText");
        }
    }
}