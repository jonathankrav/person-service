package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	@Query("select p from Person p where p.address.city=:cityName")
	Stream <Person> findByAddressCityIgnoreCase(@Param("cityName") String city);
	
	
	@Query("select p from Person p where p.name=?1")
	Stream <Person> findByNameIgnoreCase(String name); 
	
	default Stream<Person> findByAgeBetween(int ageFrom, int ageTo) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDateFrom = currentDate.minusYears(ageTo);
        LocalDate birthDateTo = currentDate.minusYears(ageFrom);
        return findByBirthDateBetween(birthDateFrom, birthDateTo);
    }

	Stream<Person> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate);
	
	@Query("select new telran.java51.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();
	
}
