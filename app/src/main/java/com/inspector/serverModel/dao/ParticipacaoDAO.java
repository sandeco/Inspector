package com.inspector.serverModel.dao;

import com.inspector.model.Ministracao;
import com.inspector.model.Participacao;

import java.util.List;

public interface ParticipacaoDAO {

	List<Participacao> listarParticipacao();
	
	boolean inserirParticipacao(Participacao participacao);
	
	boolean removerParticipacao(Participacao participacao);
	
	boolean updateParticipacao(Participacao participacao);
		
	Participacao buscarParticipacaoPorInscricaoMinistracao(int inscricao, Ministracao ministracao);

}
