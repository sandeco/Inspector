package com.inspector.serverModel.dao;

import com.inspector.model.Ministracao;

import java.util.List;

public interface MinistracaoDAO {

	List<Ministracao> listarMinistracoesDeHoje();
	
	List<Ministracao> listarMinistracoes();
	
	boolean inserirMinistracao(Ministracao m);
	
	boolean removerMinistracao(Ministracao m);
	
	Ministracao buscarMinistracaoPorId(int id);
}
