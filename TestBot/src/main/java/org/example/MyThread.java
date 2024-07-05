package org.example;

import java.io.File;

public class MyThread extends Thread {

    @Override
    public void run() {
        // бесконечный цикл
        while (true) {
            // проверка папки на наличие файлов
            File directory = new File("directory"); //определяем каталог исходных данных
            File dest_dir = new File("archive"); // определяем каталог, куда переместить

            if (directory.isDirectory() && dest_dir.isDirectory()) {
                File arr[] = directory.listFiles(); // создаем массив строк, где храним имена файлов

                for (File elem : arr) {
                    if (elem.isFile()) {
                        // запускаем парсинг файла и отправку сообщения
                        System.out.println("функция парсинга и перемещения");
                        for (int i = 0; i < arr.length; i++) {
                            // берем файлы из каталога по очереди и парсим
                            //String fileName = directory + "/" + arr[i];
                            String fileName = arr[i].toString();
                            String fileNameto = dest_dir + "/" + arr[i].getName();

                            String message = "";
                            message = Main.Msg(fileName);

                            // отправка сообщения
                            if (!message.isEmpty()) {
                                Bot bot = new Bot();
                                bot.sendText(Long.parseLong(bot.getBotUsername()), message);
                                //System.out.println(message);
                            }
                            // перемещаем в другую папку
                            Main.moveFile(fileName, fileNameto);
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
}
