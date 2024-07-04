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

import java.io.File;



public class Main {
    //final Long KanalID = Bot.getBotUsername();
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);

        //final String fileName = "letter.xml";
        //final Long KanalID = -1002219488636L;
        final Long KanalID = Long.parseLong(bot.getBotUsername());


        //public boolean isDirectoryEmpty(java.io.File File directory) {
        //    String[] files = directory.list();
        //    return files.length == 0;
        //}


        File directory = new File("directory"); //определяем каталог
        //System.out.println(isDirectoryEmpty(directory1));
        if (directory.isDirectory()) {
            // создаем массив строк, где храним имена файлов
            String arr[] = directory.list();

            //проверка на длину массива
            if (arr.length > 0) {
                //System.out.println("не пустая");
                // перебор всех файлов в каталоге
                for (int i = 0; i < arr.length; i++) {
                    //System.out.println("arr[" + i + "]=" + arr[i]);

                    // берем файлы из каталога по очереди и парсим
                    String fileName = "directory/" + arr[i];
                    // парсинг xml
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
                        //bot.sendText(5005278759L, text);

                        // отправка сообщения
                        bot.sendText(KanalID, text);

                        // здесь нужно переместить файлы в другую папку




                    } catch (FileNotFoundException | XMLStreamException ex) {
                        ex.printStackTrace();
                    }
                }


            }
            else {
                System.out.println("пустая");
            }
        }
        else {
            System.out.println("не могу найти каталог");
        }

 //       // парсинг xml
  //      try {
  //          XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
//
//            String text = "Зарегистрирован новый пожар со статусом 'действующий'." + "\n";
//            while (xmlr.hasNext()) {
 //               xmlr.next();
 //               if (xmlr.isStartElement()) {
 //                   //System.out.println(xmlr.getLocalName());
 //               } else if (xmlr.isEndElement()) {
 //                   //System.out.println("/" + xmlr.getLocalName());
 //               } else if (xmlr.hasText() && xmlr.getText().trim().length() > 0) {
  //                  //System.out.println("   " + xmlr.getText());
 //                   text = text + xmlr.getText() + "\n";
 //               }
 //           }
            //System.out.println(text);
            //bot.sendText(5005278759L, text);
            // отправка сообщения
            //bot.sendText(KanalID, text);

 //       } catch (FileNotFoundException | XMLStreamException ex) {
 //           ex.printStackTrace();
  //      }


    }
}

