package servlet;

import dao.AnimalDAO;
import model.Animal;

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
 * Animal Servlet
 * RESTful API for animal management
 */
public class AnimalServlet extends HttpServlet {

    private AnimalDAO animalDAO;

    @Override
    public void init() throws ServletException {
        animalDAO = new AnimalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Animal> animals = animalDAO.getAllAnimals();

            JSONArray jsonArray = new JSONArray();
            for (Animal animal : animals) {
                JSONObject jsonAnimal = new JSONObject();
                jsonAnimal.put("animalId", animal.getAnimalId());
                jsonAnimal.put("batchId", animal.getBatchId());
                jsonAnimal.put("tagNumber", animal.getTagNumber());
                jsonAnimal.put("species", animal.getSpecies());
                jsonAnimal.put("breed", animal.getBreed());
                jsonAnimal.put("birthDate", animal.getBirthDate() != null ? animal.getBirthDate().toString() : null);
                jsonAnimal.put("gender", animal.getGender());
                jsonAnimal.put("healthStatus", animal.getHealthStatus());
                jsonAnimal.put("weight", animal.getWeight());
                jsonAnimal.put("notes", animal.getNotes());
                jsonAnimal.put("lastCheckup",
                        animal.getLastCheckup() != null ? animal.getLastCheckup().toString() : null);
                jsonAnimal.put("createdAt", animal.getCreatedAt() != null ? animal.getCreatedAt().toString() : null);
                jsonArray.put(jsonAnimal);
            }

            out.print(jsonArray.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error fetching animals: " + e.getMessage());
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

            Animal animal = new Animal();
            animal.setBatchId(jsonRequest.optInt("batchId", 0));
            animal.setTagNumber(jsonRequest.getString("tagNumber"));
            animal.setSpecies(jsonRequest.getString("species"));
            animal.setBreed(jsonRequest.optString("breed", null));
            animal.setGender(jsonRequest.optString("gender", "Unknown"));
            animal.setHealthStatus(jsonRequest.optString("healthStatus", "Normal"));
            if (jsonRequest.has("weight")) {
                animal.setWeight(new java.math.BigDecimal(jsonRequest.getDouble("weight")));
            }
            animal.setNotes(jsonRequest.optString("notes", null));
            boolean added = animalDAO.addAnimal(animal);

            JSONObject jsonResponse = new JSONObject();
            if (added) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Animal added successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to add animal");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error adding animal: " + e.getMessage());
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
            int animalId = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            boolean updated = animalDAO.updateAnimalStatus(animalId, status);

            JSONObject jsonResponse = new JSONObject();
            if (updated) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Animal status updated");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to update animal status");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error updating animal: " + e.getMessage());
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
            int animalId = Integer.parseInt(request.getParameter("id"));

            boolean deleted = animalDAO.deleteAnimal(animalId);

            JSONObject jsonResponse = new JSONObject();
            if (deleted) {
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Animal deleted successfully");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Failed to delete animal");
            }

            out.print(jsonResponse.toString());

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting animal: " + e.getMessage());
            out.print(errorResponse.toString());
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }
}
