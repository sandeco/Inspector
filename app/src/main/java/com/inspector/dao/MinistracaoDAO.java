package com.inspector.dao;

import com.inspector.model.Ministracao;

import java.util.List;

public interface MinistracaoDAO {

	public List<Ministracao> listarMinistracoesDeHoje();
	
	public List<Ministracao> listarMinistracoes();
	
	public boolean inserirMinistracao(Ministracao m);
	
	public boolean removerMinistracao(Ministracao m);
	
	public Ministracao buscarMinistracaoPorId(int id);
}
