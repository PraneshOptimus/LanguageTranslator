package com.LanguageTranslator.LangTrans.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;

@Service
public class TranslatorService {
    private static final String GOOGLE_PROJECT_ID = "langtrans-472204";
    private static final String GOOGLE_LOCATION = "global";

    public String translateText(String text, String source, String target) throws IOException {
        String credentialsJson = System.getenv("GOOGLE_CREDENTIALS");

        if (credentialsJson == null || credentialsJson.isEmpty()) {
            throw new IOException("Google credentials environment variable is not set.");
        }

        try (TranslationServiceClient client = TranslationServiceClient.create(
                TranslationServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(GoogleCredentials.fromStream(new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8)))))
                        .build())) {

            LocationName parent = LocationName.of(GOOGLE_PROJECT_ID, GOOGLE_LOCATION);

            TranslateTextRequest request = TranslateTextRequest.newBuilder()
                    .setParent(parent.toString())
                    .setMimeType("text/plain")
                    .setSourceLanguageCode(source)
                    .setTargetLanguageCode(target)
                    .addContents(text)
                    .build();

            return client.translateText(request).getTranslations(0).getTranslatedText();
        }
    }
}