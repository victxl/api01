package com.victxl.api01.resource;

import com.victxl.api01.event.RecursoCriadoeEvent;
import com.victxl.api01.model.Pessoa;
import com.victxl.api01.repository.PessoaRepository;
import com.victxl.api01.service.PessoaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    // Endpoint para listar todas as pessoas
    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }



    // Endpoint para buscar uma pessoa pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPeloId(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        // Verifica se a pessoa foi encontrada e retorna uma resposta com o código de status correspondente.
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        this.pessoaRepository.deleteById(id);
    }



    // Endpoint para criar uma nova pessoa
    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Publica um evento (RecursoCriadoeEvent) indicando que um novo recurso foi criado.
        publisher.publishEvent(new RecursoCriadoeEvent(this, response, pessoaSalva.getId()));

        // Retorna uma resposta com o código de status 201 Created e o corpo da pessoa recém-criada.
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    // Endpoint para atualizar a propriedade "ativo" de uma pessoa
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable long id, @RequestBody Boolean ativo) {
        // Chama o serviço (pessoaService) para atualizar a propriedade "ativo" da pessoa com o ID fornecido.
        pessoaService.atualizarPropriedadeAtivo(id, ativo);
    }
}
