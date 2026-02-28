package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.service.BookService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


@WebServlet("/book/*")
public class BookServlet extends HttpServlet {
    private final BookService bookService = BookService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String bookIdParam = req.getParameter("bookId");
        String firstNameParam = req.getParameter("firstName");

        if (bookIdParam != null) {
            try {
                Optional<Book> book = bookService.findById(Long.parseLong(bookIdParam));
                if (book.isPresent()) {
                    objectMapper.writeValue(resp.getOutputStream(), book.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Book not found!");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        } else if (firstNameParam != null) {
            try {
                Optional<Book> book = bookService.findByName(firstNameParam);
                if (book.isPresent()) {
                    objectMapper.writeValue(resp.getOutputStream(), book.get());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().print("Book not found!");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        } else {
            List<BookDto> books = bookService.findAll();
            objectMapper.writeValue(resp.getOutputStream(), books);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String bookIdParam = req.getParameter("Id");

        try {
            if (bookIdParam != null || !bookIdParam.isEmpty()) {
                Long id = Long.parseLong(bookIdParam);
                boolean isDeleted = bookService.delete(id);
                if (isDeleted) {
                    resp.getWriter().write("{\"status:\":\"success\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Maybe missing book id\"}");
                }
            }
        } catch (NumberFormatException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String id = req.getParameter("id");
        String bookTitle = req.getParameter("bookTitle");
        String yearOfPublication = req.getParameter("yearOfPublication");
        String genre = req.getParameter("genre");
        String authorId = req.getParameter("authorId");

        if (id != null && !id.isEmpty()){
            try {
                Long idParam = Long.parseLong(id);
                Integer yearOfPublicationParam = Integer.parseInt(yearOfPublication);
                Long authorIdParam = Long.parseLong(authorId);

                Book book = Book.builder()
                        .id(idParam)
                        .bookTitle(bookTitle)
                        .yearOfPublication(yearOfPublicationParam)
                        .genre(genre)
                        .authorId(authorIdParam)
                        .build();

                bookService.save(book);
                objectMapper.writeValue(resp.getWriter(), book);


            } catch (NumberFormatException e){
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
        String bookTitle = req.getParameter("bookTitle");
        String genre = req.getParameter("genre");


        if (id != null && !id.isEmpty()){
            try {
                Long idParam = Long.parseLong(id);

                Optional<Book> existBook = bookService.findById(idParam);
                if (existBook.isPresent()) {
                    Book newBook = Book.builder()
                            .id(existBook.get().getId())
                            .bookTitle(bookTitle)
                            .yearOfPublication(existBook.get().getYearOfPublication())
                            .genre(genre)
                            .authorId(existBook.get().getAuthorId())
                            .build();

                    bookService.update(newBook);

                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
                }
            } catch (NumberFormatException e){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error:\":\"Something went wrong! Invalid id\"}");
            }
        }
    }
}
