package sessions.s03.tests;

import sessions.s03.oop.BirdCu;
import sessions.s03.oop.FishHeo;
import sessions.s03.oop.Lion;
import sessions.s03.oop.StandardAnimal;

public class Main {
    public static void main(String[] args) {
//        StandardAnimal fishHeo = new FishHeo();
//        StandardAnimal birdCu = new BirdCu();
//        StandardAnimal lion = new Lion();

        StandardAnimal fishHeo = new FishHeo("FishHeo",1,"male","small fishes","Fish");
        StandardAnimal birdCu = new BirdCu("BirdCu",2,"female","worm","Bird");
        StandardAnimal lion = new Lion("Lion",3,"female","meat","Wild Animals");

        animalFunctions(fishHeo,birdCu,lion);
    }

    public static void animalFunctions(StandardAnimal... animals){
        for(StandardAnimal a : animals){
            a.getName();
            a.speak();
            a.eat();
            a.flyAble();
            a.runAble();
            a.swimAble();
            a.getAge();
            a.getGender();
            a.getAnimalClass();
            System.out.println("------------------");
        }
    };
}
