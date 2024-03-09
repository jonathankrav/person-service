package telran.java51.person.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		if (personDto instanceof ChildDto childDto) {
			personRepository.save(modelMapper.map(childDto, Child.class));
		} else if (personDto instanceof EmployeeDto employeeDto) {
			personRepository.save(modelMapper.map(employeeDto, Employee.class));
		} else {
			personRepository.save(modelMapper.map(personDto, Person.class));
		}
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		System.out.println(person.getClass().getSimpleName());
		if (person instanceof Child child) {
			return modelMapper.map(child, ChildDto.class);
		}
		if (person instanceof Employee employee) {
			return modelMapper.map(employee, EmployeeDto.class);
		}
		return modelMapper.map(person, PersonDto.class);
	}

	@Transactional
	@Override
	public PersonDto removePerson(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		if (person instanceof Child child) {
			personRepository.delete(child);
			return modelMapper.map(child, ChildDto.class);
		} else if (person instanceof Employee employee) {
			personRepository.delete(employee);
			return modelMapper.map(employee, EmployeeDto.class);
		} else {
			personRepository.delete(person);
			return modelMapper.map(person, PersonDto.class);
		}
	}

	@Transactional
	@Override
	public PersonDto updatePersonName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setName(name);
		if (person instanceof Child child) {
			return modelMapper.map(child, ChildDto.class);
		} else if (person instanceof Employee employee) {
			return modelMapper.map(employee, EmployeeDto.class);
		} else {
			return modelMapper.map(person, PersonDto.class);
		}
	}

	@Transactional
	@Override
	public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
		Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());
		person.setAddress(modelMapper.map(addressDto, Address.class));
		if (person instanceof Child child) {
			return modelMapper.map(child, ChildDto.class);
		} else if (person instanceof Employee employee) {
			return modelMapper.map(employee, EmployeeDto.class);
		} else {
			return modelMapper.map(person, PersonDto.class);
		}
	}

//	//TODO
//	@Transactional(readOnly = true)
//	@Override
//	public Iterable<PersonDto> findPersonsByCity(String city) {
//		return personRepository.findByAddressCityIgnoreCase(city).map(p -> modelMapper.map(p, PersonDto.class))
//				.collect(Collectors.toList());
//	}

//	// TODO
//	@Transactional(readOnly = true)
//	@Override
//	public Iterable<PersonDto> findPersonsByName(String name) {
//		return personRepository.findByNameIgnoreCase(name).map(p -> modelMapper.map(p, PersonDto.class))
//				.collect(Collectors.toList());
//	}

//	// TODO
//	@Transactional(readOnly = true)
//	@Override
//	public Iterable<PersonDto> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
//		LocalDate from = LocalDate.now().minusYears(maxAge);
//		LocalDate to = LocalDate.now().minusYears(minAge);
//		return personRepository.findByBirthDateBetween(from, to).map(p -> modelMapper.map(p, PersonDto.class))
//				.collect(Collectors.toList());
//	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> findPersonsByName(String name) {
		List<Object> list = personRepository.findByNameIgnoreCase(name).collect(Collectors.toList());
		List<Object> res = new ArrayList<>();
		for (Object obj : list) {
			if (obj instanceof Child) {
				res.add(modelMapper.map(obj, ChildDto.class));
				continue;
			} else if (obj instanceof Employee) {
				res.add(modelMapper.map(obj, EmployeeDto.class));
				continue;
			} else {
				res.add(modelMapper.map(obj, PersonDto.class));
			}
		}
		return res;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object> findPersonsByCity(String city) {
		List<Object> list = personRepository.findByAddressCityIgnoreCase(city).collect(Collectors.toList());
		return mapper(list);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Object> findPersonsBetweenAge(Integer minAge, Integer maxAge) {
		LocalDate from = LocalDate.now().minusYears(maxAge);
		LocalDate to = LocalDate.now().minusYears(minAge);
		List<Object> list = personRepository.findByBirthDateBetween(from, to).collect(Collectors.toList());
		return mapper(list);
	}

	private List<Object> mapper (List<Object> list) {
		List<Object> res = new ArrayList<>();
		for (Object obj : list) {
			if (obj instanceof Child) {
				res.add(modelMapper.map(obj, ChildDto.class));
				continue;
			} else if (obj instanceof Employee) {
				res.add(modelMapper.map(obj, EmployeeDto.class));
				continue;
			} else {
				res.add(modelMapper.map(obj, PersonDto.class));
			}
		}	
		return res;
	}
	
	
	@Override
	public Iterable<CityPopulationDto> getCitiesPopulation() {
		return personRepository.getCitiesPopulation();
	}

	@Override
	public Iterable<ChildDto> findAllChild() {
		return personRepository.findAllChild().stream()
				.map(p -> modelMapper.map(p, ChildDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<EmployeeDto> findEmployeesBySalary(Integer minSalary, Integer maxSalary) {
		return personRepository.findEmployeesBySalary(minSalary, maxSalary).stream()
				.map(p->modelMapper.map(p, EmployeeDto.class))
				.collect(Collectors.toList());

//		Ver 2
//		return personRepository.findAllEmployees().stream()
//				.map(p -> modelMapper.map(p, EmployeeDto.class))
//				.filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary)
//				.collect(Collectors.toList());
	}
	

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
					new Address("Tel Aviv", "Ben Gvirol", 81));
			Child child = new Child(2000, "Mosche", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sarah", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Herzl", 7), "Motorola", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}

	}

}