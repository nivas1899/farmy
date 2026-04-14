package servlet;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Register Servlet
 * Handles user registration
 */
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Read JSON request body
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonRequest = new JSONObject(sb.toString());
            String username = jsonRequest.getString("username");
            String password = jsonRequest.getString("password");

            JSONObject jsonResponse = new JSONObject();

            // Validate input
            if (username == null || username.trim().isEmpty()) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Username is required");
            } else if (password == null || password.length() < 6) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Password must be at least 6 characters");
            } else if (userDAO.usernameExists(username)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Username already exists");
            } else {
                // Register user
                User user = new User(username, password);
                boolean registered = userDAO.register(user);

                if (registered) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Registration successful. Please login.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Registration failed. Please try again.");
                }
            }

            // Write JSON response
            PrintWriter out = response.getWriter();
            out.write(jsonResponse.toString());
            out.close();

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error processing registration: " + e.getMessage());

            PrintWriter out = response.getWriter();
            out.write(errorResponse.toString());
            out.close();

            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported");
    }
}
