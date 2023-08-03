package com.victxl.api01.service;

import com.victxl.api01.model.Lancamento;
import com.victxl.api01.model.Pessoa;
import com.victxl.api01.repository.LancamentoRepository;
import com.victxl.api01.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    // O método salvar é responsável por salvar um lançamento após verificar se a pessoa associada ao lançamento existe e está ativa.
    // Caso a pessoa não exista ou esteja inativa, uma exceção será lançada.
    public Lancamento salvar(Lancamento lancamento) {
        // Verifica se a pessoa associada ao lançamento existe no banco de dados com base no id.
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());

        // Verifica se a pessoa não existe ou está inativa.
        if (pessoa.isEmpty() || pessoa.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }

        // Se a pessoa existe e está ativa, salva o lançamento no banco de dados.
        return lancamentoRepository.save(lancamento);
    }
}
