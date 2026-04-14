package servlet;

import dao.VaccinationDAO;
import model.Vaccination;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Vaccination Servlet
 * RESTful API for vaccination management
 */
public class VaccinationServlet extends HttpServlet {

    private VaccinationDAO vaccinationDAO;

    @Override
    public void init() throws ServletException {
        vaccinationDAO = new VaccinationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String filter = request.getParameter("filter");
            List<Vaccination> vaccinations;

            if ("pending".equalsIgnoreCase(filter)) {
                vaccinations = vaccinationDAO.getPendingVaccinations();
            } else {
                vaccinations = vaccinationDAO.getAllVaccinations();
            }

            JSONArray jsonArray = new JSONArray();
            for (Vaccination vaccination : vaccinations) {
                JSONObject jsonVaccination = new JSONObject();
                jsonVaccination.put("vaccinationId", vaccination.getVaccinationId());
                jsonVaccination.put("animalId", vaccination.getAnimalId());
                jsonVaccination.put("vaccineName", vaccination.getVaccineName());
                jsonVaccination.put("vaccinationDate", vaccination.getVaccinationDate().toString());
                jsonVaccination.put("status", vaccination.getStatus());
                jsonVaccination.put("administeredDate",
                        vaccination.getAdministeredBy() != null ? vaccination.getAdministeredBy().toString()
                                : null);
                jsonVaccination.put("notes", vaccination.getNotes());
                jsonArray.put(jsonVaccination);
            }

            out.print(jsonArray.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error fetching vaccinations: " + e.getMessage());
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
            int animalId = jsonRequest.getInt("animalId");
            String vaccineName = jsonRequest.getString("vaccineName");
            String vaccinationDate = jsonRequest.getString("vaccinationDate");
            String notes = jsonRequest.optString("notes", null);

            Vaccination vaccination = new Vaccination(animalId, vaccineName, Date.valueOf(vaccinationDate));
            vaccination.setNotes(notes);

            boolean added = vaccinationDAO.addVaccination(vaccination);

            JSONObject jsonResponse = new JSONObject();
            if (added) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Vaccination scheduled successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to schedule vaccination");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error scheduling vaccination: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int vaccinationId = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            boolean updated = vaccinationDAO.updateVaccinationStatus(vaccinationId, status);

            JSONObject jsonResponse = new JSONObject();
            if (updated) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Vaccination status updated");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to update vaccination status");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating vaccination: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int vaccinationId = Integer.parseInt(request.getParameter("id"));

            boolean deleted = vaccinationDAO.deleteVaccination(vaccinationId);

            JSONObject jsonResponse = new JSONObject();
            if (deleted) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Vaccination deleted successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to delete vaccination");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting vaccination: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
