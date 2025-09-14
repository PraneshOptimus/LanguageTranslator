package com.LanguageTranslator.LangTrans.Controller;

import com.LanguageTranslator.LangTrans.service.TranslatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class TranslatorController {

    private final TranslatorService translatorService;

    @Autowired
    public TranslatorController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/")
    public String home() {
        return "index"; // loads index.html from templates
    }

    @PostMapping("/translate")
    public String translate(@RequestParam("text") String text,
                            @RequestParam("source") String source,
                            @RequestParam("target") String target,
                            Model model) {
        try {
            String translatedText = translatorService.translateText(text, source, target);

            model.addAttribute("original", text);
            model.addAttribute("translated", translatedText);

        } catch (Exception e) {
            model.addAttribute("error", "⚠️ Translation failed: " + e.getMessage());
        }
        return "index";
    }
}