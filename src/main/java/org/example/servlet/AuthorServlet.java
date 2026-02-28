package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.example.service.AuthorService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@WebServlet("/author/*")
public class AuthorServlet extends HttpServlet {
    private final AuthorService authorService = AuthorService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String authorIdParam = req.getParameter("authorId");


        if (authorIdParam != null) {
            try {
                Optional<Author> author = authorService.findById(Long.parseLong(authorIdParam));
                if (author.isPresent()) {
                    objectMapper.writeValue(resp.getOutputStream(), author.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Author not found!");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        } else {
            List<AuthorDto> authors = authorService.findAll();
            objectMapper.writeValue(resp.getOutputStream(), authors);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String authorIdParam = req.getParameter("id");

        try {
            if (authorIdParam != null || !authorIdParam.isEmpty()) {
                Long id = Long.parseLong(authorIdParam);
                boolean isDeleted = authorService.delete(id);
                if (isDeleted) {
                    resp.getWriter().write("{\"status:\":\"success\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Maybe missing book id\"}");
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String id = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String gmail = req.getParameter("gmail");

        if (id != null && !id.isEmpty()) {
            try {
                Long idParam = Long.parseLong(id);
                Long value = Long.parseLong(phone);
                BigDecimal phoneParam = BigDecimal.valueOf(value);

                Author author = Author.builder()
                        .id(idParam)
                        .firstName(firstName)
                        .lastName(lastName)
                        .phone(phoneParam)
                        .gmail(gmail)
                        .build();

                authorService.save(author);
                objectMapper.writeValue(resp.getWriter(), author);

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String id = req.getParameter("id");
        String phone = req.getParameter("phone");
        String gmail = req.getParameter("gmail");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        if (id != null && !id.isEmpty()) {
            try {
                Long idParam = Long.parseLong(id);
                Long value = Long.parseLong(phone);
                BigDecimal phoneParam = BigDecimal.valueOf(value);

                Optional<Author> existAuthor = authorService.findById(idParam);
                if (existAuthor.isPresent()) {
                    Author newAuthor = Author.builder()
                            .id(existAuthor.get().getId())
                            .firstName(firstName)
                            .lastName(lastName)
                            .phone(phoneParam)
                            .gmail(gmail)
                            .build();

                    authorService.update(newAuthor);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("PATCH".equalsIgnoreCase(req.getMethod())) {
            doPatch(req, resp);
            return;
        }

        super.service(req, resp);
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String id = req.getParameter("id");
        String gmail = req.getParameter("gmail");
        String lastName = req.getParameter("lastName");

        if (id != null && !id.isEmpty()) {
            try {
                Long idParam = Long.parseLong(id);

                Optional<Author> existAuthor = authorService.findById(idParam);
                if (existAuthor.isPresent()) {
                    Author newAuthor = Author.builder()
                            .id(existAuthor.get().getId())
                            .firstName(existAuthor.get().getFirstName())
                            .lastName(lastName)
                            .phone(existAuthor.get().getPhone())
                            .gmail(gmail)
                            .build();

                    authorService.patch(newAuthor);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        }


        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
