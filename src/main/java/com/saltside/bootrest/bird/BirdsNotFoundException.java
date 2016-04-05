package com.saltside.bootrest.bird;


public class BirdsNotFoundException extends RuntimeException {

    public BirdsNotFoundException(String id) {
        super(String.format("No birds entry found with id: <%s>", id));
    }
}
