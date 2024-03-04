package telran.java51.person.dao;

import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

}
