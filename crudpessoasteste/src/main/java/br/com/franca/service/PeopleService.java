package br.com.franca.service;

import br.com.franca.domain.People;
import br.com.franca.exception.InvalidPeopleException;
import br.com.franca.exception.PeopleNotFoundException;
import br.com.franca.repository.PeopleRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PeopleService {
    private PeopleRepository repository;

    public void save(People people) {

        if (isInValidPeople(people)){
            throw new InvalidPeopleException("Invalid People");
        };

        repository.save(people);
    }

    public People findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public List<People> findAll() {
        return repository.findAll();
    }

    public void delete(String cpf) {
        repository.delete(cpf);
    }

    private boolean isInValidPeople(People people) {
        if (people==null || people.getName()==null || people.getName().isEmpty()
                || people.getName().isBlank() || people.getName().length()<1){
            return  true;
        }
        return  false;
    }
}
