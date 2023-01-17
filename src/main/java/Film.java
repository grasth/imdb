public class Film {
    private String name;
    private int age;
    private double rate;

    public Film(String name, int age, double rate) {
        this.name = name;
        this.age = age;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getRate() {
        return rate;
    }
}
