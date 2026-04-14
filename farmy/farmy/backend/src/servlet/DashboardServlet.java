package servlet;

import dao.DashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import org.json.JSONObject;

/**
 * Dashboard Servlet
 * Aggregates and returns dashboard metrics
 */
public class DashboardServlet extends HttpServlet {

    private DashboardDAO dashboardDAO;

    @Override
    public void init() throws ServletException {
        dashboardDAO = new DashboardDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Map<String, Object> metrics = dashboardDAO.getDashboardMetrics();

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("totalAnimals", metrics.get("totalAnimals"));
            jsonResponse.put("normalCount", metrics.get("normalCount"));
            jsonResponse.put("sickCount", metrics.get("sickCount"));
            jsonResponse.put("isolatedCount", metrics.get("isolatedCount"));
            jsonResponse.put("deadCount", metrics.get("deadCount"));
            jsonResponse.put("visitorsToday", metrics.get("visitorsToday"));
            jsonResponse.put("pendingVaccinations", metrics.get("pendingVaccinations"));

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error fetching dashboard data: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
