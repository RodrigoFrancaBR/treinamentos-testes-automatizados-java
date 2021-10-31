package br.com.franca;

import br.com.franca.domain.People;
import br.com.franca.exception.InvalidPeopleException;
import br.com.franca.exception.PeopleNotFoundException;
import br.com.franca.repository.PeopleRepository;
import br.com.franca.service.PeopleService;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PessoaServiceTest {

    private People people;
    private PeopleService peopleService;
    private PeopleRepository peopleRepository;

    @BeforeEach
    public void setup(){
        // cenário
        peopleRepository = new PeopleRepository();
        peopleService = new PeopleService(peopleRepository);
        people =  buildPeople();
    }

    // antes de testar um cenário que salva uma pessoa com sucesso,
    // primeiro precisamos de um repositorio de pessoas vazio.
    @Test
    @DisplayName("Should create empty repository when repository is not empty")
    public void createRepository_shouldCreateRepositoryEmpty_whenIsNotEmpty(){
        // verificação
        Assertions
                .assertThat(peopleRepository.getRepository())
                .isEmpty();
    }

    @Test
    @DisplayName("Should save people when a people is valid")
    public void save_shouldSave_whenAPeopleIsValid(){
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

    // @Test(expected = InvalidPeopleException.class)
    @Test
    @DisplayName("Should throw exception when is not valid people")
    public void exceptionSave_shouldThrowException_whenIsNotValidPeople(){
        // cenário
        this.people = null;
        // execução verificação
        org.junit.jupiter.api.Assertions.assertThrows(InvalidPeopleException.class, ()-> peopleService.save(people));
    }

    @Test
    @DisplayName("Should delete when repository is not empty")
    public void delete_shouldRemovePeople_whenRepositoryIsNotEmpty(){
        //cenário
        peopleService.save(people);
        // execucao
        peopleService.delete(people.getCpf());
        // verificação
        Assertions.assertThat(peopleRepository.getRepository())
                .isEmpty();
    }

    @Test
    @DisplayName("should throw exception when not people to delete")
    public void exceptionDelete_shouldThrowException_whenNoPeopleToDelete(){
        //cenário
        people.setCpf("78945612378");
        // execução verificação
        org.junit.jupiter.api.Assertions.assertThrows(PeopleNotFoundException.class, ()->peopleService.delete(people.getCpf()));
    }

    public People buildPeople() {
        People people = new People();
        people.setCpf("12345678912");
        people.setName("DonaNeves");
        people.setAge(70);
        return people;
    }

}
