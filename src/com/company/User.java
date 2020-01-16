package com.company;

//Axel Gustafsson axgu8924
public class User {
    private String name;
    private Dog[] dogs = new Dog[0];

    public User(String name){
        this.name = name;
    }

    public void addDog(Dog dog){
        dog.setOwner(this);

        Dog[] temp = new Dog[dogs.length + 1];

        for (int i = 0; i < dogs.length; i++) {
            temp[i] = dogs[i];
        }

        temp[dogs.length] = dog;
        dogs = temp;
    }
    public void removeDog(Dog dog){
        dog.setOwner(null);

        for (int i = 0; i < dogs.length; i++) {
            if(dogs[i] == dog && i != dogs.length - 1){
                dogs[i] = dogs[dogs.length - 1];
                dogs[dogs.length - 1] = dog;
                break;
            }
        }

        Dog[] temp = new Dog[dogs.length - 1];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = dogs[i];
        }

        dogs = temp;
    }

    public Dog[] getDogs(){
        return this.dogs;
    }
    public String getName(){
        return this.name;
    }
    @Override
    public String toString() {
        String retval = this.getName();

        if(dogs.length > 0){
            retval += " [";

            for (int i = 0; i < dogs.length; i++)
                retval += dogs[i].getName() + (i == dogs.length - 1 ? "" : ", ");

            retval += "]";
        }

        return retval;
    }
}
