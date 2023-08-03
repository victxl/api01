package com.victxl.api01.repository.lancamento;

import com.victxl.api01.model.Lancamento;
import com.victxl.api01.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, org.springframework.data.domain.Pageable pageable);
}
