package bot.data.model;

import java.util.LinkedList;


public class Ticker {
    private final String symbol;
    


    public void addClosePrice(double newPrice, int maxSize) {
        if (close.size() >= maxSize) {
            close.removeFirst(); // Удаляем самое старое значение
        }
        close.addLast(newPrice); // Добавляем новое значение в конец
    }
}

