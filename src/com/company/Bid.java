package com.company;

import java.util.Comparator;

//Axel Gustafsson axgu8924
public class Bid implements Comparator<Bid> {
    private Dog dog;
    private User bidder;
    private int amount;

    public Bid(Dog dog, User bidder, int amount){
        this.dog = dog;
        this.bidder = bidder;
        this.amount = amount;
    }

    public Dog getDog(){
        return this.dog;
    }
    public User getBidder(){
        return this.bidder;
    }
    public int getAmount(){
        return this.amount;
    }

    @Override
    public String toString() {
        return bidder.getName() + " " + amount + " kr";
    }

    @Override
    public int compare(Bid a, Bid b){
        return Integer.compare(a.getAmount(), b.getAmount());
    }
}
