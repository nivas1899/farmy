package servlet;

import dao.VisitorDAO;
import model.Visitor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Visitor Servlet
 * RESTful API for visitor biosecurity tracking
 */
public class VisitorServlet extends HttpServlet {

    private VisitorDAO visitorDAO;

    @Override
    public void init() throws ServletException {
        visitorDAO = new VisitorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String filter = request.getParameter("filter");
            List<Visitor> visitors;

            if ("today".equalsIgnoreCase(filter)) {
                visitors = visitorDAO.getVisitorsToday();
            } else {
                visitors = visitorDAO.getAllVisitors();
            }

            JSONArray jsonArray = new JSONArray();
            for (Visitor visitor : visitors) {
                JSONObject jsonVisitor = new JSONObject();
                jsonVisitor.put("visitorId", visitor.getVisitorId());
                jsonVisitor.put("visitorName", visitor.getVisitorName());
                jsonVisitor.put("organization", visitor.getOrganization());
                jsonVisitor.put("visitDate", visitor.getVisitDate().toString());
                jsonVisitor.put("biosecurityCompliance", visitor.getBiosecurityCompliance());
                jsonVisitor.put("notes", visitor.getNotes());
                jsonArray.put(jsonVisitor);
            }

            out.print(jsonArray.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error fetching visitors: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Read JSON request body
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonRequest = new JSONObject(sb.toString());
            String visitorName = jsonRequest.getString("visitorName");
            String organization = jsonRequest.optString("organization", "");
            String purpose = jsonRequest.optString("purpose", "Visit");
            String notes = jsonRequest.optString("notes", null);

            Visitor visitor = new Visitor();
            visitor.setVisitorName(visitorName);
            visitor.setOrganization(organization);
            visitor.setPurpose(purpose);
            visitor.setVisitDate(new java.sql.Timestamp(System.currentTimeMillis()));
            visitor.setBiosecurityCompliance(jsonRequest.optString("biosecurityCompliance", "Yes"));
            visitor.setNotes(notes);

            boolean added = visitorDAO.addVisitor(visitor);

            JSONObject jsonResponse = new JSONObject();
            if (added) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Visitor logged successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to log visitor");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error logging visitor: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
