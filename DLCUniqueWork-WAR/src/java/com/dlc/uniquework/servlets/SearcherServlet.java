/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.servlets;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.model.Ranking;
import com.dlc.uniquework.services.Searcher;
import com.dlc.uniquework.utils.PropertyProvider;
import com.dlc.uniquework.utils.ServletConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class in charge of search the specified word in the searcher and rank it. 
 * @author fasaloni
 */
public class SearcherServlet extends HttpServlet {

    public static final String STRING_PARAMETER = "cadena";
    public static final String RESULT_PARAMETER = "resultado";
    public static final String RANK_PARAMETER = "rank";
    public static final String REAL_RESULTS_PARAMETER = "cantidadRealResultados";
    public static final String COUNT_PARAMETER = "cantidad";
    public static final String REDIRECT_TO = "Index.jsp";
    public static final String DOCUMENTS_PATH = "documents_path";
    
    private static final int RANKING_LIMIT = 500;

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
            Searcher searcher = new Searcher(request.getParameter(STRING_PARAMETER));
            List<Ranking> rankingList = new ArrayList<>();
            rankingList = searcher.search();
            String[] result = new String[rankingList.size()];
            double[] rank = new double[rankingList.size()];
            int count = 0;
            for (Ranking ranking : rankingList) {
                if (count <= RANKING_LIMIT) {
                    rank[count] = Math.floor((double) ranking.getRank() * 100) / 100;
                    result[count] = DataAccess.getInstance().getLocation((int) ranking.getId());
                    count++;
                } else {
                    break;
                }
            }
            request.setAttribute(RESULT_PARAMETER, result);
            request.setAttribute(RANK_PARAMETER, rank);
            request.setAttribute(STRING_PARAMETER, request.getParameter(STRING_PARAMETER));
            request.setAttribute(REAL_RESULTS_PARAMETER, "Resultados: " + searcher.getCount());
            request.setAttribute(COUNT_PARAMETER, count);
            request.setAttribute(DOCUMENTS_PATH, PropertyProvider.getInstance().getProperty("documents_folder"));
            RequestDispatcher rd = null;
            rd = request.getRequestDispatcher(REDIRECT_TO);
            rd.forward(request, response);
        }
    }
}
