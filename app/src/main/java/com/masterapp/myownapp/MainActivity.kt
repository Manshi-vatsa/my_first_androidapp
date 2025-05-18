package com.masterapp.myownapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private lateinit var translateButton: Button

    private var translator: Translator? = null

    private val allSupportedLanguages = mapOf(
        "Afrikaans" to TranslateLanguage.AFRIKAANS,
        "Arabic" to TranslateLanguage.ARABIC,
        "Bengali" to TranslateLanguage.BENGALI,
        "Chinese" to TranslateLanguage.CHINESE,
        "Czech" to TranslateLanguage.CZECH,
        "Danish" to TranslateLanguage.DANISH,
        "Dutch" to TranslateLanguage.DUTCH,
        "English" to TranslateLanguage.ENGLISH,
        "Finnish" to TranslateLanguage.FINNISH,
        "French" to TranslateLanguage.FRENCH,
        "German" to TranslateLanguage.GERMAN,
        "Gujarati" to TranslateLanguage.GUJARATI,
        "Hindi" to TranslateLanguage.HINDI,
        "Hungarian" to TranslateLanguage.HUNGARIAN,
        "Indonesian" to TranslateLanguage.INDONESIAN,
        "Italian" to TranslateLanguage.ITALIAN,
        "Japanese" to TranslateLanguage.JAPANESE,
        "Kannada" to TranslateLanguage.KANNADA,
        "Korean" to TranslateLanguage.KOREAN,
        "Latvian" to TranslateLanguage.LATVIAN,
        "Lithuanian" to TranslateLanguage.LITHUANIAN,
        "Malay" to TranslateLanguage.MALAY,
        "Marathi" to TranslateLanguage.MARATHI,
        "Norwegian" to TranslateLanguage.NORWEGIAN,
        "Polish" to TranslateLanguage.POLISH,
        "Portuguese" to TranslateLanguage.PORTUGUESE,
        "Romanian" to TranslateLanguage.ROMANIAN,
        "Russian" to TranslateLanguage.RUSSIAN,
        "Slovak" to TranslateLanguage.SLOVAK,
        "Spanish" to TranslateLanguage.SPANISH,
        "Swedish" to TranslateLanguage.SWEDISH,
        "Tamil" to TranslateLanguage.TAMIL,
        "Telugu" to TranslateLanguage.TELUGU,
        "Thai" to TranslateLanguage.THAI,
        "Turkish" to TranslateLanguage.TURKISH,
        "Ukrainian" to TranslateLanguage.UKRAINIAN,
        "Urdu" to TranslateLanguage.URDU,
        "Vietnamese" to TranslateLanguage.VIETNAMESE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)
        translateButton = findViewById(R.id.translateButton)

        translateButton.setOnClickListener { view ->
            val textToTranslate = editText.text.toString().trim()

            if (textToTranslate.isEmpty()) {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val popupMenu = PopupMenu(this, view)
            val languageNames = allSupportedLanguages.keys.sorted()

            languageNames.forEachIndexed { index, name ->
                popupMenu.menu.add(0, index, 0, name)
            }

            popupMenu.setOnMenuItemClickListener { item ->
                val selectedLanguage = item.title.toString()
                val targetLangCode = allSupportedLanguages[selectedLanguage]

                if (targetLangCode == null) {
                    Toast.makeText(this, "Selected language not supported", Toast.LENGTH_SHORT).show()
                } else {
                    translateText(textToTranslate, targetLangCode)
                }

                true
            }

            popupMenu.show()
        }
    }

    private fun translateText(text: String, targetLangCode: String) {
        translator?.close()

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(targetLangCode)
            .build()

        translator = Translation.getClient(options)

        translator?.downloadModelIfNeeded()
            ?.addOnSuccessListener {
                translator?.translate(text)
                    ?.addOnSuccessListener { translatedText ->
                        textView.text = translatedText
                    }
                    ?.addOnFailureListener {
                        Toast.makeText(this, "Translation failed", Toast.LENGTH_SHORT).show()
                    }
            }
            ?.addOnFailureListener {
                Toast.makeText(this, "Model download failed", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        translator?.close()
    }
}
