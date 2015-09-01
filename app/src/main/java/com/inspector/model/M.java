package com.inspector.model;

/**
 * Created by sanderson on 21/08/2015.
 */
public class M {



    /***********************************************************************************************
     * MODELO EVENTO
     */

    public static class Evento{

        public static String ENTITY_NAME = "evento";
        public static String ID = "_id";
        public static String NOME = "nome";
        public static String DATA_INICIO = "data_inicio";
        public static String DATA_FIM = "data_fim";
        public static String DATA_ALTERACAO = "data_alteracao";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID   + " INTEGER PRIMARY KEY not null, " +
                NOME + " TEXT NOT NULL, " +
                DATA_INICIO     + " TEXT, " +
                DATA_FIM        + " TEXT, " +
                DATA_ALTERACAO  + " TEXT)";

        public static String FIELDS[] = {ID,NOME,DATA_INICIO,DATA_FIM,DATA_ALTERACAO};

    }

    /***********************************************************************************************
     * MODELO INSCRICAO
     */

    public static class Inscricao{

        public static String ENTITY_NAME = "inscricao";
        public static String ID = "_id";
        public static String PALESTRA_ID = "palestra_id";
        public static String PARTICIPANTE_ID = "participante_id";
        public static String DATA_ALTERACAO = "data_alteracao";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                   ID               + " INTEGER PRIMARY KEY not null, " +
                   PALESTRA_ID      + " INTEGER NOT NULL, " +
                   PARTICIPANTE_ID  + " INTEGER NOT NULL, " +
                   DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, PALESTRA_ID, PARTICIPANTE_ID, DATA_ALTERACAO};

    }


    /***********************************************************************************************
     * MODELO MINISTRAÇÃO
     */

    public static class Ministracao{

        public static String ENTITY_NAME = "ministracao";
        public static String ID = "_id";
        public static String PALESTRA_ID = "palestra_id";
        public static String DIA_HORA = "dia_hora";
        public static String LOCAL = "local";
        public static String DATA_ALTERACAO = "data_alteracao";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID               + " INTEGER PRIMARY KEY not null, " +
                PALESTRA_ID      + " INTEGER NOT NULL, " +
                DIA_HORA         + " TEXT, " +
                LOCAL            + " TEXT, " +
                DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, PALESTRA_ID, DIA_HORA, LOCAL, DATA_ALTERACAO};

    }


    /***********************************************************************************************
     * MODELO PALESTRA
     */

    public static class Palestra{

        public static String ENTITY_NAME = "palestra";
        public static String ID = "_id";
        public static String EVENTO_ID = "evento_id";
        public static String NOME = "nome";
        public static String DATA_ALTERACAO = "data_alteracao";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID               + " INTEGER PRIMARY KEY not null, " +
                EVENTO_ID        + " INTEGER NOT NULL, " +
                NOME             + " TEXT, " +
                DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, EVENTO_ID, NOME, DATA_ALTERACAO};

    }



    /***********************************************************************************************
     * MODELO PALESTRANTE
     */

    public static class Palestrante{

        public static String ENTITY_NAME = "palestrante";
        public static String ID = "_id";
        public static String PALESTRA_ID = "evento_id";
        public static String NOME = "nome";
        public static String DATA_ALTERACAO = "data_alteracao";


        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID               + " INTEGER PRIMARY KEY not null, " +
                PALESTRA_ID      + " INTEGER NOT NULL, " +
                NOME             + " TEXT, " +
                DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, PALESTRA_ID, NOME, DATA_ALTERACAO};

    }


    /***********************************************************************************************
     * MODELO PARTICIPACAO
     */

    public static class Participacao{

        public static String ENTITY_NAME = "participacao";
        public static String ID = "_id";
        public static String MINISTRACAO_ID = "ministracao_id";
        public static String PARTICIPANTE_ID = "participante_id";
        public static String DATA_ALTERACAO = "data_alteracao";


        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID               + " INTEGER PRIMARY KEY not null, " +
                PARTICIPANTE_ID  + " INTEGER NOT NULL, " +
                MINISTRACAO_ID   + " INTEGER NOT NULL, " +
                DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, PARTICIPANTE_ID, MINISTRACAO_ID, DATA_ALTERACAO};

    }



    /***********************************************************************************************
     * MODELO PARTICIPANTE
     */

    public static class Participante{

        public static String ENTITY_NAME = "participante";
        public static String ID = "_id";
        public static String CPF = "cpf";
        public static String NOME = "nome";
        public static String DATA_ALTERACAO = "data_alteracao";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +
                ID               + " INTEGER PRIMARY KEY not null, " +
                CPF              + " TEXT, " +
                NOME             + " TEXT, " +
                DATA_ALTERACAO   + " TEXT)";

        public static String FIELDS[] = {ID, CPF, NOME, DATA_ALTERACAO};

    }

    /***********************************************************************************************
     * MODELO PARTICIPANTE
     */

    public static class Comunicacao{

        public static String ENTITY_NAME = "comunicacao";
        public static String LAST_UPDATE = "last_update";
        public static String TOKEN = "token";

        public static String CREATE = "CREATE TABLE " + ENTITY_NAME + " (" +

                LAST_UPDATE  + " TEXT, " +
                TOKEN        + " TEXT)";

        public static String FIELDS[] = {LAST_UPDATE, TOKEN};

    }


}
