package com.reinstein.heberth.address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reinstein.heberth.address.exceptions.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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

    public void save(Address address) throws JsonProcessingException {
        if (address.getLatitude() == null || address.getLongitude() == null)
            address = this.getLatitudeLongitudeFromGoogle(address);
        this.addressRepository.save(address);
    }

    public void deleteAddressById(Long id) throws AddressNotFoundException {
        this.addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
        this.addressRepository.deleteById(id);
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateAddress(Address updatedAddress) throws AddressNotFoundException, JsonProcessingException {
        final Long id = updatedAddress.getId();

        Address address = this.addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        if (updatedAddress.getLatitude() == null || updatedAddress.getLongitude() == null)
        updatedAddress = this.getLatitudeLongitudeFromGoogle(updatedAddress);

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

    public Address getLatitudeLongitudeFromGoogle(Address address) throws JsonProcessingException {
        final String uri = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + address.toString()
                + "&key=AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(forEntity.getBody());

        JsonNode location = jsonNode.get("results").get(0).get("geometry").get("location");

        address.setLatitude(location.get("lat").asText());
        address.setLongitude(location.get("lng").asText());

        return address;
    }
}
