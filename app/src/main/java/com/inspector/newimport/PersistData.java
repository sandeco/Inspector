package com.inspector.newimport;

import com.inspector.model.Evento;
import com.inspector.model.Ministracao;
import com.inspector.model.Palestra;
import com.inspector.newimport.request.ObjectRequest;
import com.inspector.persistencia.dao.EventoDAO;
import com.inspector.persistencia.dao.MinistracaoDAO;
import com.inspector.persistencia.dao.PalestraDAO;
import com.inspector.persistencia.sqlite.EventoSqliteDAO;
import com.inspector.persistencia.sqlite.MinistracaoSqliteDAO;
import com.inspector.persistencia.sqlite.PalestraSqliteDAO;

import java.util.List;

public class PersistData {

    private PalestraDAO palestraDAO;
    private EventoDAO eventoDAO;
    private MinistracaoDAO ministracaoDAO;

    public PersistData() {

        palestraDAO = new PalestraSqliteDAO();
        eventoDAO = new EventoSqliteDAO();
        ministracaoDAO = new MinistracaoSqliteDAO();
    }

    public List<ObjectRequest> persist(List<ObjectRequest> requests) {

        for (ObjectRequest request : requests) {

            List list = request.getObjects();

            if (isEmptyList(list))
                continue; //lista vazia, próxima requisição

            for (Object object : list) {

                if (object instanceof Palestra)
                    palestraDAO.create((Palestra) object);
                else if (object instanceof Evento)
                    eventoDAO.create((Evento) object);
                else if (object instanceof Ministracao)
                    ministracaoDAO.create((Ministracao) object);

            }

        }

        ministracaoDAO.close();
        palestraDAO.close();
        eventoDAO.close();

        return requests;
    }

    private boolean isEmptyList(List<?> list) {
        return (list == null || list.size() <= 0);
    }
}
