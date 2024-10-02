package com.example.languagetranslatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class MainActivity extends AppCompatActivity {

    private EditText inputText, outputText;
    private Spinner fromSpinner, toSpinner;
    private Button translateButton;
    private LinearLayout loadingScreen;
    private ProgressBar loadingSpinner;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        fromSpinner = findViewById(R.id.fromLanguageSpinner);
        toSpinner = findViewById(R.id.toLanguageSpinner);
        translateButton = findViewById(R.id.translateButton);
        loadingScreen = findViewById(R.id.loadingScreen);
        loadingSpinner = findViewById(R.id.loadingSpinner);
        loadingText = findViewById(R.id.loadingText);

        translateButton.setOnClickListener(v -> {
            String fromLanguage = fromSpinner.getSelectedItem().toString();
            String toLanguage = toSpinner.getSelectedItem().toString();
            String input = inputText.getText().toString();

            if (!input.isEmpty()) {
                translateText(fromLanguage, toLanguage, input);
            }
        });
    }

    private void translateText(String fromLanguage, String toLanguage, String input) {
        // Show loading screen
        loadingScreen.setVisibility(View.VISIBLE);

        // Set up translation options
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(getLanguageCode(fromLanguage))
                .setTargetLanguage(getLanguageCode(toLanguage))
                .build();

        Translator translator = Translation.getClient(options);

        // Set up download conditions
        DownloadConditions conditions = new DownloadConditions.Builder()
                .build(); // Allows download over both Wi-Fi and mobile data

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Model downloaded successfully, start translating
                        translator.translate(input)
                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        // Hide loading screen
                                        loadingScreen.setVisibility(View.GONE);

                                        // Translation successful, display result
                                        outputText.setText(translatedText);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Hide loading screen
                                        loadingScreen.setVisibility(View.GONE);

                                        // Display error message
                                        outputText.setText("Translation failed: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Hide loading screen
                        loadingScreen.setVisibility(View.GONE);

                        // Display error message
                        outputText.setText("Model download failed: " + e.getMessage());
                    }
                });
    }

    // Helper method to get language code
    private String getLanguageCode(String language) {
        switch (language) {
            case "Afrikaans":
                return "af";
            case "Arabic":
                return "ar";
            case "Belarusian":
                return "be";
            case "Bulgarian":
                return "bg";
            case "Bengali":
                return "bn";
            case "Catalan":
                return "ca";
            case "Czech":
                return "cs";
            case "Welsh":
                return "cy";
            case "Danish":
                return "da";
            case "German":
                return "de";
            case "Greek":
                return "el";
            case "English":
                return "en";
            case "Esperanto":
                return "eo";
            case "Spanish":
                return "es";
            case "Estonian":
                return "et";
            case "Persian":
                return "fa";
            case "Finnish":
                return "fi";
            case "French":
                return "fr";
            case "Irish":
                return "ga";
            case "Galician":
                return "gl";
            case "Gujarati":
                return "gu";
            case "Hebrew":
                return "he";
            case "Hindi":
                return "hi";
            case "Croatian":
                return "hr";
            case "Haitian":
                return "ht";
            case "Hungarian":
                return "hu";
            case "Indonesian":
                return "id";
            case "Icelandic":
                return "is";
            case "Italian":
                return "it";
            case "Japanese":
                return "ja";
            case "Georgian":
                return "ka";
            case "Kannada":
                return "kn";
            case "Korean":
                return "ko";
            case "Lithuanian":
                return "lt";
            case "Latvian":
                return "lv";
            case "Macedonian":
                return "mk";
            case "Marathi":
                return "mr";
            case "Malay":
                return "ms";
            case "Maltese":
                return "mt";
            case "Dutch":
                return "nl";
            case "Norwegian":
                return "no";
            case "Polish":
                return "pl";
            case "Portuguese":
                return "pt";
            case "Romanian":
                return "ro";
            case "Russian":
                return "ru";
            case "Slovak":
                return "sk";
            case "Slovenian":
                return "sl";
            case "Albanian":
                return "sq";
            case "Swedish":
                return "sv";
            case "Swahili":
                return "sw";
            case "Tamil":
                return "ta";
            case "Telugu":
                return "te";
            case "Thai":
                return "th";
            case "Tagalog":
                return "tl";
            case "Turkish":
                return "tr";
            case "Ukrainian":
                return "uk";
            case "Urdu":
                return "ur";
            case "Vietnamese":
                return "vi";
            case "Chinese":
                return "zh";
            default:
                return "en"; // Default to English if language is not in the list
        }
    }

}