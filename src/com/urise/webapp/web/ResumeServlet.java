package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.sql.SqlStorage;
import com.urise.webapp.util.DateUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.SectionType.OBJECTIVE;
import static com.urise.webapp.model.SectionType.PERSONAL;

public class ResumeServlet extends HttpServlet {
    private SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume resume;

        switch (action) {
            case "create":
                resume = new Resume();
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
        request.setAttribute("resume", resume);
        request.setAttribute("action", action);
        request.getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        final String uuid = request.getParameter("uuid");
        final String action = request.getParameter("action");
        final Resume r = action.equals("create") ? new Resume() : storage.get(uuid);
        boolean addCompany = false;
        boolean addPeriod = false;
        r.setFullName(request.getParameter("fullName"));
        for (ContactType type : ContactType.values()) {
            final String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.putContact(type, value);
            } else r.getContacts().remove(type);
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE, PERSONAL, ACHIEVEMENTS, QUALIFICATIONS:
                    final String value = request.getParameter(type.name());
                    if (value != null && !value.trim().isEmpty()) {
                        r.putSection(type, (type == OBJECTIVE || type == PERSONAL) ?
                                new TextSection(value) :
                                new ListSection(Arrays.stream(value.split("\n")).filter(p -> !p.trim().isEmpty()).toList()));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE, EDUCATION:
                    final List<Company> companies = new ArrayList<>();
                    if (request.getParameter("addCompanyTo" + type.name()) != null) {
                        addCompany = true;
                        companies.add(new Company("", "", new Company.Period("", "", DateUtil.NOW)));
                    }
                    final String[] companyNames = request.getParameterValues(type.name() + "_name");
                    final String[] companyWebsites = request.getParameterValues(type.name() + "_website");
                    if (companyNames != null) {
                        for (int i = 0; i < companyNames.length; i++) {
                            if (!companyNames[i].trim().isEmpty()) {
                                List<Company.Period> periods = new ArrayList<>();
                                if (request.getParameterValues("addPeriodTo" + type.name() + i) != null) {
                                    addPeriod = true;
                                    periods.add(new Company.Period());
                                }
                                final String prefix = type.name() + i + "_period";
                                final String[] periodStarts = request.getParameterValues(prefix + "Start");
                                final String[] periodEnds = request.getParameterValues(prefix + "End");
                                final String[] periodTitles = request.getParameterValues(prefix + "Title");
                                final String[] periodDescriptions = request.getParameterValues(prefix + "Description");
                                if (periodTitles != null) {
                                    for (int j = 0; j < periodTitles.length; j++) {
                                        if (!periodTitles[j].trim().isEmpty()) {
                                            periods.add(new Company.Period(periodTitles[j], periodDescriptions[j],
                                                    DateUtil.ofMMyyyy(periodStarts[j]),
                                                    DateUtil.ofMMyyyy(periodEnds[j].isBlank() ? "01/3000" : periodEnds[j])));
                                        }
                                    }
                                }
                                companies.add(new Company(companyNames[i], companyWebsites[i], periods));
                            }
                        }
                    }
                    if (!companies.isEmpty()) {
                        r.putSection(type, new CompanySection(companies));
                    } else {
                        r.getSections().remove(type);
                    }
                    break;
            }
        }
        if (addCompany || addPeriod) {
            request.setAttribute("resume", r);
            request.setAttribute("action", action);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            return;
        }
        if (action.equals("create")) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}
