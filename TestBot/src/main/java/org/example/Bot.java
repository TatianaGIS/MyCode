package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        //return "Fire Notification Test"; // название телеграм бота
        return "-1002219488636"; // id канала
    }

    @Override
    public String getBotToken() {
        return "7487634886:AAHiInqfpMGL-V9wV2yaZy1VRKQ9RIxbeSQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
    }

    public void sendText(Long who, String what){
    //public void sendText(String who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                //.chatId(who)
                .text(what).build();    //Message content
            sm.enableMarkdown(true); // редактирование тг сообщений
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}


