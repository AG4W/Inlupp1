package com.company;

import java.text.DecimalFormat;

//Axel Gustafsson axgu8924
public class Dog {
    private String name;
    private String breed;

    private int age;
    private int weight;

    private DecimalFormat tailFormat = new DecimalFormat("#.##");

    private User owner;

    public Dog(String name, String breed, int age, int weight){
        this.name = name;
        this.breed = breed;

        this.age = age;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }
    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }
    public int getWeight() {
        return weight;
    }

    public double getTailLength(){
        return this.getBreed().equalsIgnoreCase("tax") || this.getBreed().equalsIgnoreCase("dachshund") ? 3.7 : this.getAge() * (this.getWeight() / 10.0);
    }

    public boolean hasOwner(){
        return owner != null;
    }
    public User getOwner() { return owner; }

    public void incrementAge(){
        age++;
    }
    public void setOwner(User owner){
        this.owner = owner;
    }

    @Override
    public String toString() {
        return name + ", " + breed + ", " + age + ", " + weight + ", " + tailFormat.format(this.getTailLength()) + (this.hasOwner() ? ", owned by " + this.owner.getName() : "");
    }

    public int compareTo(Dog otherDog){
        if(Double.compare(this.getTailLength(), otherDog.getTailLength()) == 0)
            return this.getName().compareToIgnoreCase(otherDog.getName()) < 0 ? -1 : (this.getName().compareToIgnoreCase(otherDog.getName()) > 0 ? 1 : 0);

        return Double.compare(this.getTailLength(), otherDog.getTailLength());
    }
}
