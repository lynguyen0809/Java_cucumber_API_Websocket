package sessions.s03.oop;

public class StandardAnimal extends Animal implements IFlyable,IRunable, ISwimable {
    Integer age;
    String eat;
    String name;
    String gender;
    String animalClass;

    public StandardAnimal(){};

    public StandardAnimal(String name, Integer age, String gender, String eat, String animalClass) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.eat = eat;
        this.animalClass = animalClass;
    }

    public StandardAnimal(String eat, String animalClass) {
        this.eat = eat;
        this.animalClass = animalClass;
    }

    @Override
    public void sleep(){
            System.out.println("zzzZZZZ...");
        }

    @Override
    public void speak() {}

    @Override
    public void eat() {
        System.out.println(name + " eats " + eat);
    }

    @Override
    public void drink() {}

    public void getAnimalClass(){
        System.out.println("Class: " + animalClass);
    };

    public void setEat(String eat){
        this.eat = eat;
    };

    public void getAge(){
        System.out.println("Age: " + age + " years old");
    };

    public void setAge(Integer age){
        this.age = age;
    };

    public void getName(){
        System.out.println("Name: " + name);
    };

    public void setName(String name){
        this.name = name;
    };

    public void getGender(){
        System.out.println("Gender: " + gender);
    };

    public void setGender(String gender){
        this.gender = gender;
    };

    @Override
    public void flyAble() {
    }

    @Override
    public void runAble() {
    }

    @Override
    public void swimAble() {
    }
}
