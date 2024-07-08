package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Msg {
    private String Link;
    private int Number;
    private String Date;
    private String Region;
    private String Distance;
    private String Forestry;
    private String Dforestry;
    private int Kv;
    private float Area;

    // обработка объекта Msg в текстовое сообщение
    // !!! включен жирный шрифт на номере пожара
    public String CreateMsg() {
        return "Зарегистрирован новый пожар со статусом 'действующий':" + "\n" +
                "Ссылка: [Перейти на карту](" + Link + ")\n" +
                "Дата обнаружения: " + Date + "\n" +
                "Номер пожара: " + "*" + Number + "*" + "\n" +
                "Район: " + Region + "\n" +
                "Расстояние до ближайшего н.п.: " + Distance + "\n" +
                "Лесничество: " + Forestry + "\n" +
                "Участковое лесничество: " + Dforestry + "\n" +
                "Квартал: " + Kv + "\n" +
                "Площадь, га: " + Area;
    }
}
