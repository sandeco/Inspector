package com.inspector.newimport;

import com.inspector.newimport.request.ObjectRequest;
import com.inspector.persistencia.dao.GenericDAO;
import com.inspector.persistencia.sqlite.GenericSqliteDAO;

import java.io.Serializable;
import java.util.List;

public class PersistData {

    private GenericDAO dao;

    public PersistData() {}

    public List<ObjectRequest> persist(List<ObjectRequest> requests) throws Exception {

        for (ObjectRequest request : requests) {

            List<Serializable> list = request.getObjects();

            if (isEmptyOrNullList(list))
                continue; //lista vazia, próxima requisição

            dao = GenericSqliteDAO.getGenericDAO(list);

            if (dao == null)
                throw new Exception("Factory return a GenericDAO null.");

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
