package com.reinstein.heberth.address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reinstein.heberth.address.exceptions.AddressNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<Address> findAllAddress(){
        return this.addressService.findAllAddress();
    }

    @PostMapping
    public Address saveAddress(@RequestBody Address address) throws JsonProcessingException {
        return this.addressService.save(address);
    }

    @DeleteMapping
    public void deleteAddressById(@RequestParam Long id) throws AddressNotFoundException {
        this.addressService.deleteAddressById(id);
    }

    @PutMapping
    public Address updateAddress(@RequestBody Address address) throws AddressNotFoundException, JsonProcessingException {
        return this.addressService.updateAddress(address);
    }


}
