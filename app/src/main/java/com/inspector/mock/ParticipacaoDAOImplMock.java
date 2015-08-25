package com.inspector.mock;

import com.inspector.dao.ParticipacaoDAO;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.model.Participacao;
import com.inspector.model.Participante;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParticipacaoDAOImplMock implements ParticipacaoDAO {

	public ParticipacaoDAOImplMock() {
		
	}

	@Override
	public List<Participacao> listarParticipacao() {
		
		List<Participacao> participacoes = new ArrayList<Participacao>();
		/*
		Ministracao m = new Ministracao(0, new Palestra("Desenvolvimento de Jogos", 0), new Date());
		
		Participacao p1 = new Participacao(m, new Participante(0100, "José Maria"), true, true);
		Participacao p2 = new Participacao(m, new Participante(0102, "Luis Henrique"), true, true);
		Participacao p3 = new Participacao(m, new Participante(0103, "Paulo"), false, true);
		
		participacoes.add(p1);
		participacoes.add(p2);
		participacoes.add(p3);
		*/

		return participacoes;
	}

	@Override
	public boolean inserirParticipacao(Participacao participacao) {
		return true;
	}

	@Override
	public boolean removerParticipacao(Participacao participacao) {
		return true;
	}

	@Override
	public Participacao buscarParticipacaoPorInscricaoMinistracao(
			int inscricao, Ministracao ministracao) {


		//Participacao p = new Participacao(ministracao, new Participante(inscricao, "José Maria"), true, true);
		
		return null;
	}

	@Override
	public boolean updateParticipacao(Participacao participacao) {
		return true;
	}

}
