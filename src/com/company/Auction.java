package com.company;

import java.util.*;

//Axel Gustafsson axgu8924
public class Auction {
    private Dog dog;
    private Hashtable<User, Bid> bids;
    private int index;

    public Auction(Dog dog, int index){
        this.dog = dog;
        this.bids = new Hashtable<>();
        this.index = index;
    }

    public void addBid(Bid bid){
        bids.put(bid.getBidder(), bid);
    }

    public ArrayList<Bid> getBidsSorted(){
        ArrayList<Bid> l = new ArrayList<Bid>(bids.values());
        l.sort(Comparator.comparing(Bid::getAmount).reversed());

        return l;
    }
    public int getMinimumBidAmount(){
        if(bids.isEmpty())
            return 1;
        else{
            return this.getBidsSorted().get(0).getAmount() + 1;
        }
    }
    public Bid getWinningBid(){
        return this.getBidsSorted().get(0);
    }
    public int getIndex(){
        return this.index;
    }
    public Dog getDog(){ return this.dog; }

    public void onUserRemoved(User user){
        if(bids.containsKey(user))
            bids.remove(user);
    }

    @Override
    public String toString() {
        String s = "Auction #" + this.getIndex() + ": " + dog.getName() + ". Top bids: [";

        for (int i = 0; i < (this.getBidsSorted().size() > 3 ? 3 : this.getBidsSorted().size()); i++)
            s += this.getBidsSorted().get(i).toString() + (i == (this.getBidsSorted().size() > 3 ? 2 : this.getBidsSorted().size() - 1) ? "" : ", ");

        s += "]";

        return s;
    }
}
