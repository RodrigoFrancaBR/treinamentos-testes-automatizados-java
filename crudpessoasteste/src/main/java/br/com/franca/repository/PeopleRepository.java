package br.com.franca.repository;

import br.com.franca.domain.People;
import br.com.franca.exception.PeopleNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PeopleRepository {
    private HashMap<Long, People> repository;

    public PeopleRepository() {
         repository = new HashMap<Long, People>();
    }

    public void save(People people) {
        repository.put(1l,people);
    }
    public People findByCpf(String cpf){
        Optional<People> optionalPeople = repository.values().stream().filter((people) -> people.getCpf().equals(cpf)).findFirst();

        if (optionalPeople.isEmpty()){
            throw new PeopleNotFoundException("People not found");
        }

        return optionalPeople.get();
    }

    public HashMap<Long, People> getRepository() {
        return this.repository;
    }

    public List<People> findAll() {
        return this.getRepository().values()
                .stream().collect(Collectors.toList());
    }

    public void delete(String cpf) {
        People people = findByCpf(cpf);
        getRepository().values().remove(people);
    }
}
