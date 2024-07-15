package uz.pdp.frontEnd.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.backend.service.bot.MainBotService;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("working");
        TelegramBotsApi register =new TelegramBotsApi(DefaultBotSession.class);
        register.registerBot(MainBotService.getInstance());

    }
}