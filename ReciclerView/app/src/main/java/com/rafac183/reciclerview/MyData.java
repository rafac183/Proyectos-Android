package com.rafac183.reciclerview;

import java.util.ArrayList;

public class MyData {

    static ArrayList<SuperHero> arrayHero = new ArrayList<>();

    public static ArrayList<SuperHero> getSuperHeroes() {

        arrayHero.add(new SuperHero("Spider-Man", "Marvel", "Telarañas y agilidad", "https://i.ibb.co/6sZc3r3/spiderman.png"));
        arrayHero.add(new SuperHero("Superman", "DC Comics", "Súper fuerza y vuelo", "https://i.ibb.co/C2hdmqK/superman.png"));
        arrayHero.add(new SuperHero("Wonder Woman", "DC Comics", "Amazona y lucha", "https://i.ibb.co/8DQcJxc/wonderwoman.png"));
        arrayHero.add(new SuperHero("Iron Man", "Marvel", "Tecnología y armadura", "https://i.ibb.co/PrNP41n/ironman.png"));
        arrayHero.add(new SuperHero("Captain America", "Marvel", "Fuerza y escudo", "https://i.ibb.co/LvVXVNb/captainamerica.png"));
        arrayHero.add(new SuperHero("Thor", "Marvel", "Martillo y control del trueno", "https://i.ibb.co/pv6F6Cm/thor.png"));
        arrayHero.add(new SuperHero("Black Widow", "Marvel", "Espionaje y combate", "https://i.ibb.co/yq46QQQ/blackwidow.png"));
        arrayHero.add(new SuperHero("Hulk", "Marvel", "Fuerza descomunal", "https://i.ibb.co/dWfLBPL/hulk.png"));
        arrayHero.add(new SuperHero("Aquaman", "DC Comics", "Control del agua y tridente", "https://i.ibb.co/H70TdBf/aquaman.png"));
        arrayHero.add(new SuperHero("Black Panther", "Marvel", "Tecnología y combate", "https://i.ibb.co/92Z00L8/blackpanther.png"));

        return arrayHero;
    }
}
