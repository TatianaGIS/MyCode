package org.example;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyThread extends Thread {

    @Override
    public void run() {
        File directory = new File("directory"); //определяем каталог исходных данных
        File dest_dir = new File("archive"); // определяем каталог, куда переместить

        // бесконечный цикл
        while (true) {
            // проверка папки на наличие файлов
            if (directory.isDirectory() && dest_dir.isDirectory()) {
                File arr[] = directory.listFiles(); // создаем массив строк, где храним имена файлов
                for (File elem : arr) {
                    if (elem.isFile()) {
                        // запускаем парсинг файла и отправку сообщения
                        for (int i = 0; i < arr.length; i++) {
                            // берем файлы из каталога по очереди
                            String fileName = arr[i].toString();
                            String fileNameto = dest_dir + "/" + arr[i].getName();

                            // парсинг
                            String message = ParseXML(fileName).CreateMsg();

                            // отправка сообщения
                            Bot bot = new Bot();
                            bot.sendText(Long.parseLong(bot.getBotUsername()), message);

                            // перемещаем в другую папку
                            moveFile(fileName, fileNameto);
                        }
                    }
                }
            } else {
                System.out.println("не могу найти каталог");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // обработка одного файла - парсинг xml
    public static Msg ParseXML(String myFile) {
        String text = "";
        Msg newMsg = new Msg(); // создаем экземпляр класса сообщения
        // отслеживание событий
        boolean LinkFlag = false;
        boolean NumberFlag = false;
        boolean DateFlag = false;
        boolean RegionFlag = false;
        boolean DistanceFlag = false;
        boolean ForestryFlag = false;
        boolean DforestryFlag = false;
        boolean KvFlag = false;
        boolean AreaFlag = false;

        try {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(myFile, new FileInputStream(myFile));
            int event = xmlr.getEventType(); // первое событие
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        //отмечаем флаг
                        if (xmlr.getLocalName().equals("Link")) {
                            LinkFlag = true;
                        } else if (xmlr.getLocalName().equals("Number")) {
                            NumberFlag = true;
                        } else if (xmlr.getLocalName().equals("Date")) {
                            DateFlag = true;
                        } else if (xmlr.getLocalName().equals("Region")) {
                            RegionFlag = true;
                        } else if (xmlr.getLocalName().equals("Distance")) {
                            DistanceFlag = true;
                        } else if (xmlr.getLocalName().equals("Forestry")) {
                            ForestryFlag = true;
                        } else if (xmlr.getLocalName().equals("Dforestry")) {
                            DforestryFlag = true;
                        } else if (xmlr.getLocalName().equals("Kv")) {
                            KvFlag = true;
                        } else if (xmlr.getLocalName().equals("Area")) {
                            AreaFlag = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (LinkFlag) {
                            newMsg.setLink(xmlr.getText());
                            LinkFlag = false;
                        } else if (NumberFlag) {
                            newMsg.setNumber(Integer.parseInt(xmlr.getText()));
                            NumberFlag = false;
                        } else if (DateFlag) {
                            newMsg.setDate(xmlr.getText());
                            DateFlag = false;
                        } else if (RegionFlag) {
                            newMsg.setRegion(xmlr.getText());
                            RegionFlag = false;
                        } else if (DistanceFlag) {
                            newMsg.setDistance(xmlr.getText());
                            DistanceFlag = false;
                        } else if (ForestryFlag) {
                            newMsg.setForestry(xmlr.getText());
                            ForestryFlag = false;
                        } else if (DforestryFlag) {
                            newMsg.setDforestry(xmlr.getText());
                            DforestryFlag = false;
                        } else if (KvFlag) {
                            newMsg.setKv(Integer.parseInt(xmlr.getText()));
                            KvFlag = false;
                        } else if (AreaFlag) {
                            newMsg.setArea(Float.parseFloat(xmlr.getText()));
                            AreaFlag = false;
                        }
                        break;
                }
                if (!xmlr.hasNext()) {
                    break;
                }
                event = xmlr.next();
            }
        } catch (FileNotFoundException | XMLStreamException ex) {
            ex.printStackTrace();
        }
        // возвращаем сообщение из файла
        return newMsg;
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
