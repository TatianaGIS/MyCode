package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);

        final String fileName = "letter.xml";

        try {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));

            String text = "Зарегистрирован новый пожар со статусом 'действующий'." + "\n";
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    //System.out.println(xmlr.getLocalName());
                } else if (xmlr.isEndElement()) {
                    //System.out.println("/" + xmlr.getLocalName());
                } else if (xmlr.hasText() && xmlr.getText().trim().length() > 0) {
                    //System.out.println("   " + xmlr.getText());
                    text = text + xmlr.getText() + "\n";
                }
            }
            //System.out.println(text);
            bot.sendText(5005278759L, text);
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }

        //bot.sendText(5005278759L, "Hello World");
    }
}

