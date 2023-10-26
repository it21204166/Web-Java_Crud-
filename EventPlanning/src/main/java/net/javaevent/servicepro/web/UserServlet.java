package net.javaevent.servicepro.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javaevent.servicepro.dao.UserDAO;
import net.javaevent.servicepro.model.User;



@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

   


    public UserServlet() {
        this.userDAO = new UserDAO();
    }
//dopost method
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
//doget method
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Calculate the total user count
        int totalCount = userDAO.getTotalUserCount();
        request.setAttribute("totalUserCount", totalCount);

        String action = request.getServletPath();

        if (action.equals("/new")) {
            showNewForm(request, response);
        } else if (action.equals("/insert")) {
            try {
                insertUser(request, response);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals("/delete")) {
            try {
                deleteUser(request, response);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals("/edit")) {
            try {
                showEditForm(request, response);
            } catch (SQLException | ServletException | IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals("/update")) {
            try {
                updateUser(request, response);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else if (action.equals("/search")) {
            // Handle the search action
            String search = request.getParameter("search");

            if (search != null && !search.isEmpty()) {
                List<User> users = userDAO.searchUsersByServiceType(search);
                request.setAttribute("listUser", users);
            } else {
                List<User> listUser = userDAO.selectAllUsers();
                request.setAttribute("listUser", listUser);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
            dispatcher.forward(request, response);
        } else {
            try {
                listUser(request, response);
            } catch (SQLException | IOException | ServletException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }
//get user details
    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        String businesstype = request.getParameter("businesstype");
        String email = request.getParameter("email");
        
        // The 'phoneno' should be parsed as a String
        String phoneno = request.getParameter("phoneno");
        
        String about = request.getParameter("about");

        User newUser = new User(name, businesstype, email, phoneno, about);
        userDAO.insertUser(newUser);
        response.sendRedirect("list");
    }
//delete user details
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("list");
    }
//show edit details form
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }
//update details
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String businesstype = request.getParameter("businesstype");
        String email = request.getParameter("email");
        
        // The 'phoneno' should be parsed as a String
        String phoneno = request.getParameter("phoneno");
        
        String about = request.getParameter("about");

        User user = new User(id, name, businesstype, email, phoneno, about);
        userDAO.updateUser(user);
        response.sendRedirect("list");
    }

//    list all the details
    private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }
}
