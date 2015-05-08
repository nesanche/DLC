/*
 * To change this license header, choose License HeaderesultSet in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.data;

import com.dlc.uniquework.model.Post;
import com.dlc.uniquework.model.Word;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 
 * @author fasaloni
 */
public class DataAccess implements IDataAccess {

    public static DataAccess singletonInstance;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * Constructor of the class.
     */
    private DataAccess() {
        try {
            Class.forName(DataConstants.DRIVER_NAME);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        resetAttributes();
    }

    /**
     * Method used for getting the instance of the class (pattern singleton).
     * @return the instance of the class.
     */
    public static DataAccess getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new DataAccess();
        }
        return singletonInstance;
    }

    /**
     * Method used for resetting all the conection attributes to null
     */
    private void resetAttributes() {
        this.connection = null;
        this.statement = null;
        this.resultSet = null;
    }

    /**
     * Method used for opening a new connection of a SQLite database.
     * @throws SQLException in case of any SQLError occurs during the conection.
     */
    private void openConnection() throws SQLException {
        this.connection = DriverManager.getConnection(DataConstants.CONNECTION_STRING);
        this.connection.setAutoCommit(false);
        this.statement = this.connection.createStatement();
    }

    /**
     * Method used for closing an existing connection of SQLite database.
     */
    private void closeConnection() {
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if (this.connection != null) {
            try {
                this.connection.commit();
                this.connection.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public List getDocuments() {
        resetAttributes();
        List<String> resultList = null;
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select %s from %s;", DataConstants.DOCUMENTS_TABLE_NAME_COLUMN, DataConstants.DOCUMENTS_TABLE));
            resultList = new ArrayList();
            while (resultSet.next()) {
                resultList.add(this.resultSet.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return resultList;
    }

    @Override
    public List getDocumentsById(int id) {
        resetAttributes();
        List<String> resultList = null;
        try {
            openConnection();
            resultSet = this.statement.executeQuery(String.format("select d.%s from %s d inner join %s p on d.%s = p.%s where p.%s = %s;", DataConstants.DOCUMENTS_TABLE_NAME_COLUMN, DataConstants.DOCUMENTS_TABLE, DataConstants.WORD_REPEATED_DOCUMENT_TABLE, DataConstants.DOCUMENTS_TABLE_ID_COLUMN, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_DOCUMENT_ID_COLUMN, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_WORD_ID_COLUMN, id));
            resultList = new ArrayList();
            while (resultSet.next()) {
                resultList.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return resultList;
    }

    @Override
    public boolean checkDocument(String file) {
        resetAttributes();
        boolean checked = false;
        try {
            openConnection();
            resultSet = this.statement.executeQuery(String.format("select %s from %s where %s = '%s';",DataConstants.DOCUMENTS_TABLE_ID_COLUMN, DataConstants.DOCUMENTS_TABLE, DataConstants.DOCUMENTS_TABLE_NAME_COLUMN, file));
            checked = resultSet.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            checked = false;
        } finally {
            closeConnection();
        }
        return checked;
    }

    @Override
    public boolean insertTable(HashMap map, String file) {
        resetAttributes();
        int idWord, idFile;
        boolean passed = false;
        try {
            openConnection();
            this.statement.executeUpdate(String.format("INSERT INTO %s(%s) VALUES ('%s');",DataConstants.DOCUMENTS_TABLE, DataConstants.DOCUMENTS_TABLE_NAME_COLUMN, file));
            this.resultSet = this.statement.executeQuery("SELECT last_insert_rowid();");
            idFile = this.resultSet.getInt(1);
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                this.resultSet = this.statement.executeQuery(String.format("select %s,%s from %s where %s = '%s';",DataConstants.WORD_TABLE_ID_COLUMN, DataConstants.WORD_TABLE_COUNT_COLUMN, DataConstants.WORD_TABLE, DataConstants.WORD_TABLE_WORD_COLUMN, entry.getKey()));
                if (this.resultSet.next()) {
                    idWord = this.resultSet.getInt(1);
                    int wordCounter = this.resultSet.getInt(2) + (int) entry.getValue();
                    this.statement.executeUpdate(String.format("update %s set %s = %s where %s = '%s';", DataConstants.WORD_TABLE, DataConstants.WORD_TABLE_COUNT_COLUMN, wordCounter, DataConstants.WORD_TABLE_WORD_COLUMN, entry.getKey()));
                } else {
                    this.statement.executeUpdate(String.format("insert into %s(%s,%s) values ('%s',%s);",DataConstants.WORD_TABLE,DataConstants.WORD_TABLE_WORD_COLUMN,DataConstants.WORD_TABLE_COUNT_COLUMN, entry.getKey(), entry.getValue()));
                    this.resultSet = this.statement.executeQuery("SELECT last_insert_rowid();");
                    idWord = this.resultSet.getInt(1);
                }
                this.statement.executeUpdate(String.format("insert into %s values (%s,%s,%s);", DataConstants.WORD_REPEATED_DOCUMENT_TABLE, idWord, idFile, (int)entry.getValue()));
            }
            passed = true;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            passed = false;
        } finally {
            closeConnection();
        }
        return passed;
    }

    @Override
    public List<Word> getList(String word) {
        resetAttributes();
        List<Word> resultList = null;
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select %s,%s,%s from %s where %s LIKE '%s" + "%" + "';", DataConstants.WORD_TABLE_ID_COLUMN, DataConstants.WORD_TABLE_WORD_COLUMN, DataConstants.WORD_TABLE_COUNT_COLUMN, DataConstants.WORD_TABLE, DataConstants.WORD_TABLE_WORD_COLUMN, word));
            resultList = new ArrayList();
            while (this.resultSet.next()) {
                resultList.add(new Word(this.resultSet.getInt(1), this.resultSet.getString(2), this.resultSet.getInt(3)));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return resultList;
    }

    @Override
    public List<Post> getPostList(String word) {
        resetAttributes();
        List<Post> resultList = null;
        ResultSet resultSetAux;
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select %s from %s where %s like '%s';", DataConstants.WORD_TABLE_ID_COLUMN, DataConstants.WORD_TABLE, DataConstants.WORD_TABLE_WORD_COLUMN, word));
            if (this.resultSet.next()) {
                int id = this.resultSet.getInt(1);
                closeConnection();
                openConnection();
                resultSetAux = this.statement.executeQuery(String.format("select %s, %s from %s where %s = %s order by %s desc;", DataConstants.WORD_REPEATED_DOCUMENT_TABLE_DOCUMENT_ID_COLUMN, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_COUNT_COLUMN, DataConstants.WORD_REPEATED_DOCUMENT_TABLE, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_WORD_ID_COLUMN, id, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_COUNT_COLUMN));
                resultList = new ArrayList();
                while (resultSetAux.next()) {
                    Post post = new Post(resultSetAux.getInt(1), resultSetAux.getInt(2));
                    resultList.add(post);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return resultList;
    }

    @Override
    public int getDocumentsCount() {
        resetAttributes();
        int count = 0;
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select COUNT(*) from %s;",DataConstants.DOCUMENTS_TABLE));
            if (this.resultSet.next()) {
                count = this.resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return count;
    }

    @Override
    public int getWordAppearance(String word) {
        resetAttributes();
        int count = 0;
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select %s from %s where %s like '%s';", DataConstants.WORD_TABLE_ID_COLUMN, DataConstants.WORD_TABLE, DataConstants.WORD_TABLE_WORD_COLUMN, word ));
            if (this.resultSet.next()) {
                int id = this.resultSet.getInt(1);
                closeConnection();
                openConnection();
                this.resultSet = this.statement.executeQuery(String.format("select COUNT(*) from %s where %s=%s;", DataConstants.WORD_REPEATED_DOCUMENT_TABLE, DataConstants.WORD_REPEATED_DOCUMENT_TABLE_WORD_ID_COLUMN, id));
                if (this.resultSet.next()) {
                    count = this.resultSet.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return count;
    
    }

    @Override
    public String getLocation(int id) {
        resetAttributes();
        String location = "";
        try {
            openConnection();
            this.resultSet = this.statement.executeQuery(String.format("select %s from %s where %s=%s;", DataConstants.DOCUMENTS_TABLE_NAME_COLUMN, DataConstants.DOCUMENTS_TABLE, DataConstants.DOCUMENTS_TABLE_ID_COLUMN, id));
            if (this.resultSet.next()) {
                location = this.resultSet.getString(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            closeConnection();
        }
        return location;
    }
}
