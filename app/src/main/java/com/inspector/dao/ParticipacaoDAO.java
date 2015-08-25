package com.inspector.dao;

import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;

import java.util.List;

public interface ParticipacaoDAO {

	public List<Participacao> listarParticipacao();
	
	public boolean inserirParticipacao(Participacao participacao);
	
	public boolean removerParticipacao(Participacao participacao);
	
	public boolean updateParticipacao(Participacao participacao);
		
	public Participacao buscarParticipacaoPorInscricaoMinistracao(int inscricao, Ministracao ministracao);

}
