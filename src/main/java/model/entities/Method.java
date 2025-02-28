package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Method {
     private String name;
     private List<MethodSet> methodSets = new ArrayList<>();
     private Ticker ticker;
     private Long id;

     public Method(String name) {
          this.name = name;
     }

     // Геттеры/сеттеры
     public String getName() {
          return name;
     }
     public void setName(String name) {
          this.name = name;
     }

     public List<MethodSet> getSets() {
          return methodSets;
     }
     public void setSets(List<MethodSet> sets) {
          this.methodSets = sets;
     }

     public Ticker getTicker() {
          return ticker;
     }
     public void setTicker(Ticker ticker) {
          this.ticker = ticker;
     }

     public Long getId() {
          return id;
     }
     public void setId(Long id) {
          this.id = id;
     }
}
