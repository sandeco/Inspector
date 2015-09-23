package com.inspector.newimport;

import com.inspector.model.Evento;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.newimport.request.ObjectRequest;
import com.inspector.persistencia.dao.EventoDAO;
import com.inspector.persistencia.dao.GenericDAO;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.EventoSqliteDAO;
import com.inspector.persistencia.sqlite.GenericSqliteDAO;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;

import java.io.Serializable;
import java.util.List;

public class PersistData {

    private GenericDAO dao;

    public PersistData() {



    }

    public List<ObjectRequest> persist(List<ObjectRequest> requests) {

        for (ObjectRequest request : requests) {

            List<Serializable> list = request.getObjects();

            if (isEmptyOrNullList(list))
                continue; //lista vazia, próxima requisição


            dao = GenericSqliteDAO.getGenericDAO(list);

            for (Serializable object : list) {
                  dao.create(object);
            }
            dao.close();

        }



        return requests;
    }

    private boolean isEmptyOrNullList(List<?> list) {
        return (list == null || list.size() <= 0);
    }
}
