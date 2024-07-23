package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.sql.SqlStorage;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset-UTF-8");
        writeAllResumes(response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    private void writeAllResumes(PrintWriter out) {
        SqlStorage storage = com.urise.webapp.Config.getInstance().getStorage();
        out.println("<html><head" +
                "meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8>\"</head>");
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("<th>uuid</th>");
        out.println("<th>FullName</th>");
        out.println("</tr>");
        for (Resume resume : storage.getAllSorted()) {
            out.println("<tr>");
            out.println("<th>" + resume.getUuid() + "</th>");
            out.println("<th>" + resume.getFullName() + "</th>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</html>");
    }
}
