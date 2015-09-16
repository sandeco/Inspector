package com.inspector.newimport.teste;

import com.android.volley.Request;
import com.inspector.com.inspector.modelCom.PalestraCom;
import com.inspector.modelcom.EventoCom;
import com.inspector.modelcom.MinistracaoCom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanderson on 16/09/2015.
 */
public class ProxyRest {

    Generic2 generic;

    public void requisicoes(){
        List<Requisicao> requisicoes = new ArrayList<Requisicao>();

        requisicoes.add(new Requisicao<EventoCom>("localhost:8080/inspector/evento/", Request.Method.GET));
        requisicoes.add(new Requisicao<PalestraCom>());
        requisicoes.add(new Requisicao<MinistracaoCom>());

    }
}
