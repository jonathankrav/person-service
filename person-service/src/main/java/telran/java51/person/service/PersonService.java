package telran.java51.person.service;

import java.util.List;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	PersonDto removePerson(Integer id);

	PersonDto updatePersonName(Integer id, String name);

	PersonDto updatePersonAddress(Integer id, AddressDto addressDto);
	
	List<Object> findPersonsByCity(String city);
	List<Object> findPersonsByName(String name);
	List<Object> findPersonsBetweenAge(Integer minAge, Integer maxAge);

	Iterable<CityPopulationDto> getCitiesPopulation();
	
	Iterable<ChildDto> findAllChild();
	
	Iterable<EmployeeDto> findEmployeesBySalary(Integer minSalary, Integer maxSalary);
	
	//
}
