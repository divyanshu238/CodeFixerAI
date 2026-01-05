package com.codefixerai.servlet;

/*
 * CodeAnalysisServlet exposes CodeFixer AI functionality
 * as a web service using Jakarta Servlets.
 *
 * This servlet runs on Apache Tomcat 10 and demonstrates:
 * - Servlet implementation
 * - Code reuse from desktop application
 * - JSON-based response
 */

import com.codefixerai.analyzer.CodeAnalyzer;
import com.codefixerai.model.Issue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/*
 * Maps this servlet to /analyze URL
 * Example:
 * http://localhost:8080/codefixer/analyze
 */
@WebServlet("/analyze")
public class CodeAnalysisServlet extends HttpServlet {

    private CodeAnalyzer analyzer;

    /*
     * Called once when servlet is loaded.
     */
    @Override
    public void init() throws ServletException {
        analyzer = new CodeAnalyzer();
    }

    /*
     * Handles POST requests with source code input.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String code = request.getParameter("code");

        if (code == null || code.trim().isEmpty()) {
            out.write("{\"error\":\"No code provided\"}");
            return;
        }

        List<Issue> issues = analyzer.analyze(code);

        StringBuilder json = new StringBuilder();
        json.append("{\"issueCount\":")
                .append(issues.size())
                .append(",\"issues\":[");

        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);

            json.append("{")
                    .append("\"type\":\"").append(issue.getType()).append("\",")
                    .append("\"message\":\"").append(issue.getMessage()).append("\",")
                    .append("\"line\":").append(issue.getLine())
                    .append("}");

            if (i < issues.size() - 1) {
                json.append(",");
            }
        }

        json.append("]}");

        out.write(json.toString());
    }

    /*
     * Handles GET requests and shows usage info.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>CodeFixer AI – Servlet API</h2>");
        out.println("<p>Send a POST request to <b>/analyze</b> with parameter <b>code</b>.</p>");
        out.println("</body></html>");
    }
}