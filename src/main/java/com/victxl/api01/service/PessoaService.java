package com.victxl.api01.service;

import com.victxl.api01.model.Pessoa;
import com.victxl.api01.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long id, Pessoa pessoa) {
        Pessoa pessoaSalva = buscarPessoaPeloId(id);

        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
        return pessoaRepository.save(pessoaSalva);
    }


    public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaPeloId(id);
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);
    }

    private Pessoa buscarPessoaPeloId(Long id) {
        Pessoa pessoaSalva =  pessoaRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        return pessoaSalva;
    }
}
