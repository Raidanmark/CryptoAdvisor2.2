package bot.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Ticker {
    private String name;
    private List<Timeframe> timeframes = new ArrayList<>();
    private List<Method> methods = new ArrayList<>();
    private Long id;


    public Ticker(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
