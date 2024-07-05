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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);

        // запуск потока
        MyThread myThread = new MyThread();
        myThread.start();
    }

    // !!!функции
    // обработка одного файла - парсинг xml
    public static String Msg(String myfile) {
        String text = "";
        try {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(myfile, new FileInputStream(myfile));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    switch (xmlr.getLocalName()) {
                        case "Fire":
                            text = text + "Зарегистрирован новый пожар со статусом 'действующий':" + "\n";
                            break;
                        case "Link":
                            text = text + "Ссылка: ";
                            break;
                        case "Number":
                            text = text + "Номер пожара: ";
                            break;
                        case "Date":
                            text = text + "Дата обнаружения: ";
                            break;
                        case "Region":
                            text = text + "Район: ";
                            break;
                        case "Distance":
                            text = text + "Расстояние до ближайшего н.п.: ";
                            break;
                        case "Forestry":
                            text = text + "Лесничество: ";
                            break;
                        case "District_forestry":
                            text = text + "Участковое лесничество: ";
                            break;
                        case "Kv":
                            text = text + "Квартал: ";
                            break;
                        case "Area":
                            text = text + "Площадь, га: ";
                            break;
                    }
                } else if (xmlr.hasText() && !xmlr.getText().trim().isEmpty()) {
                    text = text + xmlr.getText() + "\n";
                }
            }

        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        // возвращаем сообщение из файла
        return text;
    }

    // тестовая отправка сообщения
    public static void sendTG(String file1, String file2) throws TelegramApiException {
        //final TelegramClient tgClient = new OkHttpTelegramClient("7487634886:AAHiInqfpMGL-V9wV2yaZy1VRKQ9RIxbeSQ");
        String var1 = file1;
        String var2 = file2;
        SendMessage sendMessage = new SendMessage("-1002219488636", "Hello");
        // перемещение файла
        //moveFile("directory/" +var1, "archive/" + var2);
    }

    // перемещение файла
    public static void moveFile(String src, String dest) {
        Path result = null;
        try {
            result =  Files.move(Paths.get(src), Paths.get(dest));
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(result != null) {
            System.out.println("File moved successfully.");
        }
        else{
            System.out.println("File movement failed.");
        }
    }

}