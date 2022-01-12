package com.reinstein.heberth.address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reinstein.heberth.address.exceptions.AddressNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock private AddressRepository addressRepository;

    private AddressService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AddressService(addressRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canFindAllAddress() {
           //when
           underTest.findAllAddress();
           //then
           verify(addressRepository).findAll();
    }

    @Test
    void canSave() throws JsonProcessingException {
        //given
        Address address = new Address();
        address.setStreetName("Street Name");
        address.setNeighbourhood("Neighbourhood");
        address.setCity("City");
        address.setState("State");
        address.setCountry("Country");
        address.setZipcode("00000");
        address.setLatitude("0");
        address.setLongitude("0");

        //when
        underTest.save(address);

        //
        ArgumentCaptor<Address> argumentCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(argumentCaptor.capture());
        Address capAddress = argumentCaptor.getValue();
        assertThat(capAddress).isEqualTo(address);

    }

    @Test
    void canDeleteAddressById() throws AddressNotFoundException {
        //given
        Address address = new Address();
        address.setId(1L);
        address.setStreetName("Street Name");
        address.setNeighbourhood("Neighbourhood");
        address.setCity("City");
        address.setState("State");
        address.setCountry("Country");
        address.setZipcode("00000");
        address.setLatitude("0");
        address.setLongitude("0");

        given(addressRepository.findById(address.getId())).willReturn(Optional.of(address));

        //when
        underTest.deleteAddressById(address.getId());

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(addressRepository).deleteById(argumentCaptor.capture());
        Long capId = argumentCaptor.getValue();
        assertThat(capId).isEqualTo(address.getId());
    }

    @Test
    void updateAddress() throws AddressNotFoundException, JsonProcessingException {
        //given
        Address address = new Address();
        address.setId(1L);
        address.setStreetName("Street Name");
        address.setNeighbourhood("Neighbourhood");
        address.setCity("City");
        address.setState("State");
        address.setCountry("Country");
        address.setZipcode("00000");
        address.setLatitude("0");
        address.setLongitude("0");

        given(addressRepository.findById(address.getId())).willReturn(Optional.of(address));

        //when
        //then
        assertThat(underTest.updateAddress(address)).isEqualTo(address);
    }
}