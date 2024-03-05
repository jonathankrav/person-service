package telran.java51.person.service;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Person;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;


	@Override
	public Boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public Iterable<PersonDto> findByCity(String city) {
		return personRepository.findByAddressCityIgnoreCase(city)
				.filter(p -> p.getAddress().getCity().equalsIgnoreCase(city))
				.map(p->modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Iterable<PersonDto> findByAge(int ageFrom, int ageTo) {
		return personRepository.findByAgeBetween(ageFrom, ageTo)
				.map(p->modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PersonDto updateNameById(Integer id, String newName) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (newName != null) {
			person.setName(newName);
		}
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional
	public Iterable<PersonDto> findByName(String name) {
		return personRepository.findByNameIgnoreCase(name)
				.map(p->modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PersonDto updateAddressById(Integer id, AddressDto newAddress) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		if (newAddress.getCity() !=null && newAddress.getStreet() != null && newAddress.getBuilding() !=null) {
			person.setAddress(modelMapper.map(newAddress, Address.class));;
		}
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto removePersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		personRepository.delete(person);
		return modelMapper.map(person, PersonDto.class);
	}

}
