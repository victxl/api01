package com.victxl.api01.resource;


import com.victxl.api01.event.RecursoCriadoeEvent;
import com.victxl.api01.execeptionhandler.Api01ExceptionHandler;
import com.victxl.api01.model.Lancamento;
import com.victxl.api01.repository.LancamentoRepository;
import com.victxl.api01.repository.filter.LancamentoFilter;
import com.victxl.api01.service.LancamentoService;
import com.victxl.api01.service.PessoaInexistenteOuInativaException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {

@Autowired
    private LancamentoRepository lancamentoRepository;
@Autowired
    private ApplicationEventPublisher publisher;

@Autowired
private LancamentoService lancamentoService;

@Autowired
private MessageSource messageSource;


@GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
    return lancamentoRepository.filtrar(lancamentoFilter,pageable);
}

@GetMapping("/{id}")
    public ResponseEntity<Lancamento> buscarPeloId(@PathVariable Long id){
    Optional<Lancamento> lancamento = lancamentoRepository.findById(id);
    return lancamento.isPresent()? ResponseEntity.ok(lancamento.get()):ResponseEntity.notFound().build();
}
@PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
    Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);

    publisher.publishEvent(new RecursoCriadoeEvent(this,response,lancamentoSalva.getId()));
   return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
}

@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
    String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
    String mensagemDesenvolvedor = ex.toString();
    List<  Api01ExceptionHandler.Erro> erros = Arrays.asList(new Api01ExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
    return ResponseEntity.badRequest().body(erros);
}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        this.lancamentoRepository.deleteById(id);
    }

}