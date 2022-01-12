package com.reinstein.heberth.address.exceptions;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(Long id){
        super("Address with id = " + id + " not found");
    }
}
