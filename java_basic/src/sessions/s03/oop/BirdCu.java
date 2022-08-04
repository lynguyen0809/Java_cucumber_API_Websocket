package sessions.s03.oop;

public class BirdCu extends StandardAnimal implements IFlyable {

    public BirdCu(){};

    public BirdCu(String name, Integer age, String gender, String eat, String animalClass){
        super(name,age,gender,eat,animalClass);
    }

    public BirdCu(String eat, String animalClass){
        super(eat,animalClass);
    };

    @Override
    public void speak(){
        System.out.println("BirdCu sounds hip..me..hip..me");
    };

    @Override
    public void flyAble() {
        System.out.println("BirdCu can fly");
    }
}
