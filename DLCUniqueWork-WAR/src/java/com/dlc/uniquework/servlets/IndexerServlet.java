/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.servlets;

import com.dlc.uniquework.data.DataConstants;
import com.dlc.uniquework.data.FileAccess;
import com.dlc.uniquework.services.Indexer;
import static com.dlc.uniquework.servlets.SearcherServlet.REDIRECT_TO;
import com.dlc.uniquework.utils.ServletConstants;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class in charge of index all the words.
 * @author fasaloni
 */
public class IndexerServlet extends HttpServlet {
 
    private static final String URL_PARAMETER = "url";
    
    private static final String PAGE_TO_REDIRECT = "Status.jsp";
    
    private static final String REDIRECT_TO = "Indexer.jsp";
    
    public static final String DOCUMENTS_PATH = "documents_folder";

    
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
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
     * @param request servlet request
     * @param response servlet response
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(ServletConstants.CONTENT_TYPE);
        try (PrintWriter out = response.getWriter()) {
            Indexer indexer = new Indexer(request.getParameter(URL_PARAMETER));
            RequestDispatcher rd = null;
            rd = request.getRequestDispatcher(REDIRECT_TO);
            rd.forward(request, response);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = indexer.doIndexation(); 
                    FileAccess.save(result);
                }
            }).start();            
            response.sendRedirect(PAGE_TO_REDIRECT);

        }
    }
}
