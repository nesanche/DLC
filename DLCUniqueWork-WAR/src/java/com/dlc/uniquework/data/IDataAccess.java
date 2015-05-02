/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.data;

import com.dlc.uniquework.model.Post;
import com.dlc.uniquework.model.Word;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author fasaloni
 */
public interface IDataAccess {
    List getDocuments();
    List getDocumentsById(int id);
    boolean checkDocument(String file);
    boolean insertTable(HashMap map, String file);
    List<Word> getList(String word);
    List<Post> getPostList(String word);
    int getDocumentsCount();
    int getWordAppearance(String word);
    String getLocation(int id);
}
