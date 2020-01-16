package com.company;

import java.util.Arrays;

//Axel Gustafsson axgu8924
public enum Command {
    REGISTER_NEW_DOG("register new dog", "register new dog", "rnd"),
    REGISTER_NEW_USER("register new user", "register new user", "rnu"),
    START_AUCTION("start auction", "start auction", "sa"),
    CLOSE_AUCTION("close auction", "close auction", "close", "ca"),
    MAKE_BID("make bid", "make bid", "bid", "mb"),
    INCREASE_AGE("increase age", "increase age", "ia"),
    LIST_DOGS("list dogs", "list dogs", "ld"),
    LIST_USERS("list users", "list users", "lu"),
    LIST_AUCTIONS("list auctions", "list auctions", "la"),
    LIST_BIDS("list bids", "list bids", "lb"),
    GIVE_DOG("give dog", "give dog", "gd"),
    REMOVE_DOG("remove dog", "remove dog", "rd", "rmv"),
    REMOVE_USER("remove user", "remove user", "ru"),
    EXIT("exit", "exit", "quit", "e", "q"),
    HELP("help", "help", "h");

    private String formattedName;
    private String[] tags;

    Command(String formattedName, String... tags){
        this.formattedName = formattedName;
        this.tags = tags;
    }

    public String getFormattedName() { return this.formattedName; }
    public String[] getTags() { return this.tags; };

    @Override
    public String toString() {
        return formattedName + ", valid tags: " + Arrays.toString(tags);
    }
}
