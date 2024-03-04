package telran.java51.person.dao;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	Stream <Person> finByCityIgnoreCase(String city);
	
	Stream <Person> findByAges(int ageFrom, int ageTo);
	
	Stream <Person> findByNameIgnoreCase(String name);
	
	

}
