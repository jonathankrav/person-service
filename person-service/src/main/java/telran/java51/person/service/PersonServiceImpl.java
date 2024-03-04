package telran.java51.person.service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
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
	public PersonDto[] findByCity(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto[] findByAges(int ageFrom, int ageTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto updateNameById(Integer id, String newName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto[] findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto updateAddressById(Integer id, Address newAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDto removePersonById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
