package it.unimib.icasiduso.sportrack.utils;

import android.content.Context;

public class TextParser {

    public TextParser(Context context) {
    }

    public static String parseText(String input) {
        String[] words = input.split("_");

        StringBuilder parsedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                parsedString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
            parsedString.append(" ");
        }
        return parsedString.toString().trim();
    }
}
