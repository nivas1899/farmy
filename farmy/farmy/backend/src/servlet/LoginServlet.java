package servlet;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

/**
 * Login Servlet
 * Handles user authentication
 */
public class LoginServlet extends HttpServlet {

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

            // Authenticate user
            User user = userDAO.authenticate(username, password);

            JSONObject jsonResponse = new JSONObject();

            if (user != null) {
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                jsonResponse.put("success", true);
                jsonResponse.put("message", "Login successful");
                jsonResponse.put("username", user.getUsername());

            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid username or password");
            }

            // Write JSON response
            PrintWriter out = response.getWriter();
            out.write(jsonResponse.toString());
            out.close();

        } catch (Exception e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error processing login: " + e.getMessage());

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
