package com.reinstein.heberth.address;

import com.reinstein.heberth.address.exceptions.AddressNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAllAddress() {
        return this.addressRepository.findAll();
    }

    public void save(Address address) {
        this.addressRepository.save(address);
    }

    public void deleteAddressById(Long id) throws AddressNotFoundException {
        this.addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        this.addressRepository.deleteById(id);
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateAddress(Address updatedAddress) throws AddressNotFoundException {
        Address address = this.addressRepository.findById(updatedAddress.getId())
                .orElseThrow(() -> new AddressNotFoundException(updatedAddress.getId()));

        if (updatedAddress.getStreetName() != null) address.setStreetName(updatedAddress.getStreetName());
        if (updatedAddress.getNumber() != null) address.setNumber(updatedAddress.getNumber());
        if (updatedAddress.getComplement() != null) address.setComplement(updatedAddress.getComplement());
        if (updatedAddress.getNeighbourhood() != null) address.setNeighbourhood(updatedAddress.getNeighbourhood());
        if (updatedAddress.getCity() != null) address.setCity(updatedAddress.getCity());
        if (updatedAddress.getState() != null) address.setState(updatedAddress.getState());
        if (updatedAddress.getCountry() != null) address.setCountry(updatedAddress.getCountry());
        if (updatedAddress.getZipcode() != null) address.setZipcode(updatedAddress.getZipcode());
        if (updatedAddress.getLatitude() != null) address.setLatitude(updatedAddress.getLatitude());
        if (updatedAddress.getLongitude() != null) address.setLongitude(updatedAddress.getLongitude());
    }
}
