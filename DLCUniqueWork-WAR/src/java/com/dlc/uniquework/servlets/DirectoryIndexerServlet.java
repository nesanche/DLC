/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.servlets;

import com.dlc.uniquework.services.Indexer;
import com.dlc.uniquework.utils.PropertyProvider;
import com.dlc.uniquework.utils.ServletConstants;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fasaloni
 */
public class DirectoryIndexerServlet extends HttpServlet {

    //private static final String DOCUMENTS_URL = PropertyProvider.getInstance().getProperty("documents_folder");
    private static final String DOCUMENTS_URL = "C:\\Users\\juancruz\\Desktop\\DocumentosTP1";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request 
     *                  Servlet request
     * @param response 
     *                  Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(ServletConstants.CONTENT_TYPE);
        try (PrintWriter out = response.getWriter()) {
            File directory = new File(DOCUMENTS_URL);
            String[] files = directory.list(new Filter(".txt"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (String file : files) {
                        Indexer indexer;
                        indexer = new Indexer(DOCUMENTS_URL+file);
                        System.out.println(indexer.doIndexation());                        
                    }
                     
                }
            }).start();
            out.print("Procesando solicitud, por favor espere...");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request 
     *                Servlet request
     * @param response 
     *                Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request 
     *                Servlet request
     * @param response 
     *                Servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private class Filter implements FilenameFilter {

        String extension;

        Filter(String extension) {
            this.extension = extension;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }

}
