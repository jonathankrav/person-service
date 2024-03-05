package telran.java51.person.service;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {

	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);
	
	Iterable<PersonDto> findByCity(String city);
	
	Iterable<PersonDto> findByAge(int ageFrom, int ageTo);
	
	PersonDto updateNameById(Integer id, String newName);
	
	Iterable<PersonDto> findByName(String name);
	
	PersonDto updateAddressById(Integer id, AddressDto newAddress);
	
	PersonDto removePersonById(Integer id);

}
