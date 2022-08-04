package sessions.s03.oop;

public class Lion extends StandardAnimal implements IRunable {

    public Lion(){};
    public Lion(String name, Integer age, String gender, String eat, String animalClass){
        super(name,age,gender,eat,animalClass);
    }

    @Override
    public void speak(){
        System.out.println("Lion sounds Roarrr...Roarr...Roarr");
    };

    @Override
    public void runAble(){
        System.out.println("Lion can run");
    }
}
