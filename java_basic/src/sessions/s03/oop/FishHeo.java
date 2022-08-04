package sessions.s03.oop;

public class FishHeo extends StandardAnimal implements ISwimable {

    public FishHeo(){};

    public FishHeo(String name, Integer age, String gender, String eat, String animalClass){
        super(name,age,gender,eat,animalClass);
    }

    @Override
    public void speak(){
        System.out.println("FishHeo sounds sos...SOS...Ét Ô Ét");
    };

    @Override
    public void swimAble() {
        System.out.println("FishHeo can swim");
    }
}
