package com.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.service.FinancialService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "FinancialController", urlPatterns = {"/api/calculate"})
public class FinancialController extends HttpServlet {

    private final FinancialService financialService = new FinancialService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            // Parse inputs from request parameters
            BigDecimal principal = new BigDecimal(req.getParameter("principal"));
            BigDecimal monthly = new BigDecimal(req.getParameter("monthly"));
            double rate = Double.parseDouble(req.getParameter("rate"));
            int years = Integer.parseInt(req.getParameter("years"));

            var result = financialService.calculateCompoundGrowth(principal, monthly, rate, years);
            
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), result);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}