/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlc.uniquework.servlets;

import com.dlc.uniquework.data.DataAccess;
import com.dlc.uniquework.model.Ranking;
import com.dlc.uniquework.model.Searcher;
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
 *
 * @author fasaloni
 */
public class SearcherServlet extends HttpServlet {
    
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Searcher b = new Searcher(request.getParameter("cadena"));
            List<Ranking> map = new ArrayList<>();
            map = b.search();
            String[] resultado = new String[map.size()];
            double[] rank = new double[map.size()];
            int i = 0;
            for(Ranking m : map){
                if(i<51){
                    rank[i] = Math.floor((double)m.getRank()*100)/100;
                    resultado[i] = DataAccess.getInstance().getLocation((int)m.getId());
                    i++;
                }
                else break;
            }
            
            
            
            request.setAttribute("resultado", resultado);
            request.setAttribute("rank", rank);
            request.setAttribute("cadena", request.getParameter("cadena"));
            request.setAttribute("cantidadRealResultados", "Resultados: "+b.getCount());
            request.setAttribute("cantidad", i);
            RequestDispatcher rd = null;
            rd=request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }
}
