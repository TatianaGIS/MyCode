package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class Bot extends TelegramLongPollingBot {
    //токен бота
    final private String botToken = "7487634886:AAHiInqfpMGL-V9wV2yaZy1VRKQ9RIxbeSQ";
    //id канала
    final private String botUsername = "-1002219488636";

    @Override
    public String getBotUsername() {
        //return "Fire Notification Test"; // название телеграм бота
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
    }

    public void sendText(Long who, String what){
    //public void sendText(String who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) // кому отправляем
                //.chatId(who)
                .text(what).build();    // само сообщение
            sm.enableMarkdown(true); // разметка Markdown вкл
        try {
            execute(sm);                        // отправка сообщения
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      // ошибка
        }
    }
}


