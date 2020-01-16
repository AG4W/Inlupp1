package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Scanner;

//Axel Gustafsson axgu8924
public class Main {
    private Scanner scanner;
    private DogSorter sorter;

    private boolean shouldExit;

    private ArrayList<Dog> dogs;
    private ArrayList<User> users;
    private Hashtable<Dog, Auction> auctions;
    private int auctionIndex = 0;

    private void processInput(String input){
        for (Command command : Command.values()){
            for (String tag : command.getTags()){
                if(tag.equalsIgnoreCase(input)){
                    execute(command);
                    return;
                }
            }
        }

        System.out.println("Error: '" + input + "' is not a valid command. Type 'help' or 'h' to list all available commands.");
    }
    private void execute(Command command){
        switch (command){
            case REGISTER_NEW_DOG:
                registerDog();
                break;
            case REGISTER_NEW_USER:
                registerUser();
                break;
            case GIVE_DOG:
                giveDog();
                break;
            case REMOVE_DOG:
                removeDog();
                break;
            case REMOVE_USER:
                removeUser();
                break;
            case START_AUCTION:
                startAuction();
                break;
            case CLOSE_AUCTION:
                closeAuction();
                break;
            case MAKE_BID:
                makeBid();
                break;
            case LIST_DOGS:
                listDogs();
                break;
            case LIST_USERS:
                listUsers();
                break;
            case LIST_AUCTIONS:
                listAuctions();
                break;
            case LIST_BIDS:
                listBids();
                break;
            case INCREASE_AGE:
                increaseAge();
                break;
            case HELP:
                help();
                break;
            case EXIT:
                exit();
                break;
        }

        printLastCommand(command);
    }

    private void registerDog(){
        String name, breed;
        int age, weight;

        System.out.print("Name?>");
        name = scanner.nextLine().trim().toLowerCase();

        while(!validateStringInput(name))
            name = scanner.nextLine().trim().toLowerCase();

        System.out.print("Breed?>");
        breed = scanner.nextLine().trim().toLowerCase();

        while(!validateStringInput(breed))
            breed = scanner.nextLine().trim().toLowerCase();

        System.out.print("Age?>");
        age = scanner.nextInt();

        System.out.print("Weight?>");
        weight = scanner.nextInt();

        scanner.nextLine();

        dogs.add(new Dog(name, breed, age, weight));
        dogs = sorter.sort(dogs);

        System.out.println(dogs.get(dogs.size() - 1).getName() + " added to the register.");
    }
    private void registerUser(){
        String name;

        System.out.println("Name?>");
        name = scanner.nextLine().trim().toLowerCase();

        while(!validateStringInput(name))
            name = scanner.nextLine().trim().toLowerCase();

        users.add(new User(name));

        System.out.println(users.get(users.size() - 1).getName() + " added to the register.");
    }
    private void giveDog(){
        Dog dog = null;
        User user = null;

        dog = requestDogFromInput();

        if(dog == null)
            return;
        if(dog.hasOwner()) {
            System.out.println("Error: the dog already has an owner.");
            return;
        }

        user = requestUserFromInput();

        if(user == null)
            return;

        user.addDog(dog);
    }
    private void removeDog(){
        Dog d = requestDogFromInput();

        if(d == null)
            return;

        dogs.remove(d);
        dogs = sorter.sort(dogs);

        if(d.hasOwner())
            d.getOwner().removeDog(d);

        if(auctions.containsKey(d))
            auctions.remove(d);

        System.out.println(d.getName() + " is removed from the register.");
    }
    private void removeUser(){
        User u = requestUserFromInput();

        if(u == null)
            return;

        users.remove(u);

        for (Dog d : u.getDogs()) {
            d.setOwner(null);
            dogs.remove(d);
        }
        for (Auction a : auctions.values()){
            a.onUserRemoved(u);
        }

        System.out.println(u.getName() + " is removed from the register.");
    }
    private void startAuction(){
        Dog d = requestDogFromInput();

        if(d == null)
            return;

        if(auctions.containsKey(d)){
            System.out.println("Error: this dog is already up for auction.");
            return;
        }
        else if(d.hasOwner()){
            System.out.println("Error: this dog already has an owner.");
            return;
        }

        auctionIndex++;
        auctions.put(d, new Auction(d, auctionIndex));
        System.out.println(d.getName() + " has been put for auction in auction #" + auctions.get(d).getIndex());
    }
    private void closeAuction(){
        Dog d = null;

        d = requestDogFromInput();

        if(d == null)
            return;

        if(!auctions.containsKey(d)){
            System.out.println("Error: this dog is not up for auction");
            return;
        }

        Auction a = auctions.get(d);

        if(a.getBidsSorted().isEmpty()){
            System.out.println("The auction is closed. No bids where made for " + a.getDog().getName());
            return;
        }

        Bid winner = a.getWinningBid();
        auctions.remove(d);
        winner.getBidder().addDog(d);

        System.out.println("The auction is closed. The winning bid was " + winner.getAmount() + " kr and was made by " + winner.getBidder().getName());
    }
    private void makeBid(){
        Dog dog = null;
        User user = null;
        int amount;

        user = requestUserFromInput();

        if(user == null)
            return;

        dog = requestDogFromInput();

        if(dog == null)
            return;
        if(!auctions.containsKey(dog)){
            System.out.println("Error: this dog is not up for auction.");
            return;
        }

        amount = requestBidAmountFromInput(auctions.get(dog).getMinimumBidAmount());
        auctions.get(dog).addBid(new Bid(dog, user, amount));
        System.out.println("Bid made!");
    }
    private void listDogs(){
        if(dogs.isEmpty()){
            System.out.println("Error: no dogs in register");
            return;
        }

        System.out.println("Smallest tail length to display?>");
        double minTailLength = scanner.nextDouble();
        scanner.nextLine();

        for (Dog d : dogs){
            if(d.getTailLength() >= minTailLength)
                System.out.println(d.toString());
        }
    }
    private void listUsers(){
        if(users.isEmpty()){
            System.out.println("Error: no users in the register.");
            return;
        }

        for (User u : users)
            System.out.println(u.toString());
    }
    private void listAuctions(){
        if(auctions.isEmpty()){
            System.out.println("Error: no auctions in progress");
            return;
        }

        ArrayList<Auction> list = new ArrayList<>(auctions.values());
        list.sort(Comparator.comparing(Auction::getIndex));

        for (Auction a : list)
            System.out.println(a.toString());
    }
    private void listBids(){
        Dog d = null;

        d = requestDogFromInput();

        if(d == null)
            return;

        if(!auctions.containsKey(d)){
            System.out.println("Error: this dog is not up for auction.");
            return;
        }

        Auction a = auctions.get(d);

        if(a.getBidsSorted().isEmpty()){
            System.out.println("No bids registered yet for this auction.");
            return;
        }

        for (Bid b : a.getBidsSorted())
            System.out.println(b.toString());
    }
    private void increaseAge() {
        Dog d = requestDogFromInput();

        if(d == null)
            return;

        d.incrementAge();
        dogs = sorter.sort(dogs);
        System.out.println(d.getName() + " is now one year older.");
    }
    private void help(){
        System.out.println("Available commands:");

        for (Command c : Command.values()){
            System.out.println(c.toString());
        }
    }
    private void exit(){
        shouldExit = true;
        System.out.println("Goodbye!");
    }


    private User requestUserFromInput(){
        String userName;
        User user = null;

        System.out.println("Enter the name of the user?>");
        userName = scanner.nextLine().trim().toLowerCase();

        while(!validateStringInput(userName))
            userName = scanner.nextLine().trim().toLowerCase();

        for (User u : users) {
            if(u.getName().equalsIgnoreCase(userName)){
                user = u;
                break;
            }
        }

        if(user == null)
            System.out.println("Error: no user with that name.");

        return user;
    }
    private Dog requestDogFromInput(){
        String dogName;
        Dog dog = null;

        System.out.println("Enter the name of the dog?>");
        dogName = scanner.nextLine().trim().toLowerCase();

        while(!validateStringInput(dogName))
            dogName = scanner.nextLine().trim().toLowerCase();

        for (Dog d : dogs) {
            if(d.getName().equalsIgnoreCase(dogName)){
                dog = d;
                break;
            }
        }

        if(dog == null)
            System.out.println("Error: no dog with that name.");

        return dog;
    }
    private int requestBidAmountFromInput(int threshold){
        int amount;

        System.out.println("Amount to bid (min " + threshold + ")?>");
        amount = scanner.nextInt();
        scanner.nextLine();

        while(amount < threshold){
            System.out.println("Error: too low bid!");
            System.out.println("Amount to bid (min " + threshold + ")?>");
            amount = scanner.nextInt();
            scanner.nextLine();
        }

        return amount;
    }

    private boolean validateStringInput(String input){
        if(input.isBlank() || input.isEmpty()){
            System.out.println("Error: invalid input.");
            return false;
        }

        return true;
    }
    private void printLastCommand(Command command){
        System.out.println("Last command: " + command.getFormattedName());
    }

    public Main(){
        scanner = new Scanner(System.in);
        sorter = new DogSorter();

        dogs = new ArrayList<Dog>();
        users = new ArrayList<User>();

        auctions = new Hashtable<Dog, Auction>();

        while(!shouldExit){
            System.out.print("?>");
            processInput(scanner.nextLine().toLowerCase());
        }
    }
    public static void main(String[] args) {
	    // write your code here
        new Main();
    }
}
