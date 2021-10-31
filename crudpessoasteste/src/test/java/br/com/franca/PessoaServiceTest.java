package br.com.franca;

import br.com.franca.domain.People;
import br.com.franca.exception.InvalidPeopleException;
import br.com.franca.exception.PeopleNotFoundException;
import br.com.franca.repository.PeopleRepository;
import br.com.franca.service.PeopleService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class PessoaServiceTest {

    private People people;
    private PeopleService peopleService;
    private PeopleRepository peopleRepository;

    @Before
    public void setup(){
        // cenário
        peopleRepository = new PeopleRepository();
        peopleService = new PeopleService(peopleRepository);
        people =  buildPeople();
    }

    // antes de testar um cenário que salva uma pessoa com sucesso,
    // primeiro precisamos de um repositorio de pessoas vazio.
    @Test
    public void createPeopleRepository_shouldCreateRepositoryEmpty_whenStart(){
        // verificação
        Assertions
                .assertThat(peopleRepository.getRepository())
                .isEmpty();
    }

    @Test
    public void save_shouldSavePeople_whenValidPeople(){
        // execução
        peopleService.save(people);
        //verificacao
        Assertions
                .assertThat(peopleRepository.getRepository())
                .isNotEmpty()
                .hasSize(1)
                .containsValue(people)
                .containsKey(1l);
    }

    @Test(expected = InvalidPeopleException.class)
    public void exceptionSave_shouldThrowException_whenInvalidPeople(){
        // execução
        peopleService.save(new People());
    }

    @Test
    public void delete_shouldRemovePeople_whenRepositoryIsNotEmpty(){
        //cenário
        peopleService.save(people);
        // execucao
        peopleService.delete(1l);
        // verificação
        Assertions.assertThat(peopleRepository.getRepository())
                .isEmpty();
    }

    @Test(expected = PeopleNotFoundException.class)
    public void exceptionDelete_shouldThrowException_whenNoPeopleToDelete(){
        //cenário
        peopleService.save(people);
        // execução
        peopleService.delete(2l);
        // verificação

    }

    public People buildPeople() {
        People people = new People();
        people.setCpf("12345678912");
        people.setName("DonaNeves");
        people.setAge(70);
        return people;
    }

}
