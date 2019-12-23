package ru.basejava.iliketobreathe.web;

import ru.basejava.iliketobreathe.Config;
import ru.basejava.iliketobreathe.model.Resume;
import ru.basejava.iliketobreathe.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {

    private Storage storage = Config.get().getStorage();

    @Override
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        writer.println("<body>");
        writer.println("<table border=\"1\">");
        writer.println("<tr>");
        writer.println("<th>UUID</th>");
        writer.println("<th>Full name</th>");
        writer.println("</tr>");

        for (Resume r : storage.getAllSorted()) {
            writer.println("<tr>");
            writer.println("<td>" + r.getUuid() + "</td>");
            writer.println("<td>" + r.getFullName() + "</td>");
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
