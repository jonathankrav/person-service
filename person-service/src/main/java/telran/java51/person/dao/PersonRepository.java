package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	Stream <Person> findByAddressCityIgnoreCase(String city);
	
	
	
	Stream <Person> findByNameIgnoreCase(String name); 
	
	default Stream<Person> findByAgeBetween(int ageFrom, int ageTo) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDateFrom = currentDate.minusYears(ageTo);
        LocalDate birthDateTo = currentDate.minusYears(ageFrom);
        return findByBirthDateBetween(birthDateFrom, birthDateTo);
    }

	Stream<Person> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);
	
//	Stream <Person> findByAges(int ageFrom, int ageTo);
	
}
