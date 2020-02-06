package ru.basejava.iliketobreathe.web;

import ru.basejava.iliketobreathe.Config;
import ru.basejava.iliketobreathe.model.*;
import ru.basejava.iliketobreathe.storage.Storage;
import ru.basejava.iliketobreathe.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        boolean isExisted = (uuid.length() != 0);

        if (isExisted) {
            r = storage.get(uuid);
            r.setFullName(fullName);
        } else {
            r = new Resume(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(type, new StringSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] values = request.getParameterValues(type.name());
                        if (values.length >= 2) {
                            List<Organization> organizations = new ArrayList<>();
                            String[] orgUrls = request.getParameterValues(type.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String orgName = values[i];
                                if (orgName.trim().length() != 0) {
                                    List<Organization.Period> periods = new ArrayList<>();
                                    Link link = new Link(orgName, orgUrls[i]);
                                    String[] startDates = request.getParameterValues(type.name() + "startDate" + i);
                                    String[] endDates = request.getParameterValues(type.name() + "endDate" + i);
                                    String[] titles = request.getParameterValues(type.name() + "title" + i);
                                    String[] descriptions = request.getParameterValues(type.name() + "description" + i);
                                    for (int j = 0; j < titles.length; j++) {
                                        if (titles[j] != null || titles[j].trim().length() != 0) {
                                            periods.add(new Organization.Period(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                        }
                                    }
                                    organizations.add(new Organization(link, periods));
                                }
                            }
                            r.setSection(type, new OrganizationSection(organizations));
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                }
            } else {
                r.getSections().remove(type);
            }
        }

        if (isExisted) {
            storage.update(r);
        } else {
            storage.save(r);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (section == null) {
                                section = new StringSection("");
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = new ListSection("");
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            if (section == null) {
                                section = new OrganizationSection(new Organization("", "", new Organization.Period()));
                            } else {
                                ((OrganizationSection)section).getOrganizations().add(new Organization("", "", new Organization.Period()));
                            }
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            case "add":
                r = new Resume();
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            section = new StringSection("");
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            section = new ListSection("");
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            section = new OrganizationSection(new Organization("", "", new Organization.Period()));
                            break;
                    }
                    r.setSection(type, section);
                }
                break;

            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
