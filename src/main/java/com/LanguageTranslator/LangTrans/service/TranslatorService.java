package com.LanguageTranslator.LangTrans.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslateTextRequest;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class TranslatorService {

    // You can get this from your Google Cloud project dashboard
    private static final String GOOGLE_PROJECT_ID = "langtrans-472204";
    private static final String GOOGLE_LOCATION = "global";

    // Path to your downloaded JSON key file
    // Make sure this file is in src/main/resources/
    private static final String CREDENTIALS_PATH = "src/main/resources/LangTrans.json";

    public String translateText(String text, String source, String target) throws IOException {
        try (TranslationServiceClient client = TranslationServiceClient.create(
                TranslationServiceSettings.newBuilder()
                        .setCredentialsProvider(com.google.api.gax.core.FixedCredentialsProvider.create(GoogleCredentials.fromStream(new FileInputStream(CREDENTIALS_PATH))))
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