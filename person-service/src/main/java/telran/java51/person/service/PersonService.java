package telran.java51.person.service;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import telran.java51.person.dto.PersonDto;

public interface PersonService {

	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);
	
	PersonDto[] findByCity(String city);
	
	PersonDto[] findByAges(int ageFrom, int ageTo);
	
	PersonDto updateNameById(Integer id, String newName);
	
	PersonDto[] findByName(String name);
	
	PersonDto updateAddressById(Integer id, Address newAddress);
	
	PersonDto removePersonById(Integer id);

}
