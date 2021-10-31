package br.com.franca.repository;

import br.com.franca.domain.People;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PeopleRepository {
    private HashMap<Long, People> repository;

    public PeopleRepository() {
         repository = new HashMap<Long, People>();
    }

    public void save(People people) {
        repository.put(1l,people);
    }

    public People find(Long id) {
        return repository.get(id);
    }

    public HashMap<Long, People> getRepository() {
        return this.repository;
    }

    public List<People> findAll() {
        return this.getRepository().values()
                .stream().collect(Collectors.toList());
    }

    public void delete(Long id) {
        getRepository().remove(id);
    }
}
