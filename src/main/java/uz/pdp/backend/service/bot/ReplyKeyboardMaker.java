package uz.pdp.backend.service.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMaker {


    public ReplyKeyboardMarkup replyMakerWithList(List<List<String>> buttons) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        markup.setKeyboard(keyboardRows);
        for (List<String> button : buttons) {
            KeyboardRow row = new KeyboardRow();
            keyboardRows.add(row);
            for (String s : button) {
                row.add(s);
            }
        }
        return markup;
    }
    public ReplyKeyboardMarkup replyMaker(String[][] buttons) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        markup.setKeyboard(keyboardRows);
        for (String[] button : buttons) {
            KeyboardRow row = new KeyboardRow();
            keyboardRows.add(row);
            for (String s : button) {
                row.add(s);
            }
        }
        return markup;
    }

    public ReplyKeyboardMarkup replyMaker(List<String> buttons) {
        if (!buttons.get(buttons.size()-1).equals("back")) {
            buttons.add("back");
        }
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        markup.setKeyboard(keyboardRows);
        KeyboardRow row = new KeyboardRow();
        for (int i = 0; i < buttons.size(); i++) {
            row.add(buttons.get(i));
            if (i % 2 == 1) {
                keyboardRows.add(row);
                row = new KeyboardRow();
            }
        }
        if (buttons.size()%2==1) keyboardRows.add(row);
        return markup;
    }

}
