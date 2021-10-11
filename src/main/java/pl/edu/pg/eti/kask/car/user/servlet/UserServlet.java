package pl.edu.pg.eti.kask.car.user.servlet;

import pl.edu.pg.eti.kask.car.servlet.HttpHeaders;
import pl.edu.pg.eti.kask.car.servlet.MimeTypes;
import pl.edu.pg.eti.kask.car.servlet.ServletUtility;
import pl.edu.pg.eti.kask.car.servlet.UrlFactory;
import pl.edu.pg.eti.kask.car.user.dto.CreateUserRequest;
import pl.edu.pg.eti.kask.car.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.car.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.car.user.dto.UpdateUserRequest;
import pl.edu.pg.eti.kask.car.user.entity.User;
import pl.edu.pg.eti.kask.car.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = UserServlet.Paths.USER + "/*")
public class UserServlet extends HttpServlet {


    private final UserService service;


    @Inject
    public UserServlet(UserService service) {
        this.service = service;

    }


    public static class Paths {
        public static final String USER = "/api/user";
    }

    public static class Patterns {

        public static final String USERS = "^/?$";

        public static final String USER = "^/[0-9]+/?$";

    }


    private final Jsonb jsonb = JsonbBuilder.create();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USER.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USER.equals(request.getServletPath())) {
            if (path.matches(Patterns.USERS)) {
                postUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USER.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                putUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USER.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                deleteUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(service.findAll())));
    }

    private void postUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateUserRequest requestBody = jsonb.fromJson(request.getInputStream(), CreateUserRequest.class);
        User user = CreateUserRequest
                .dtoToEntityMapper()
                .apply(requestBody);

        try {
            service.create(user);
            //When creating new resource, its location should be returned.
            response.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(request, Paths.USER, user.getId().toString()));
            //When creating new resource, appropriate code should be set.
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void putUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            UpdateUserRequest requestBody = jsonb.fromJson(request.getInputStream(),
                    UpdateUserRequest.class);

            UpdateUserRequest.dtoToEntityUpdater().apply(user.get(), requestBody);

            service.update(user.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }


    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            service.delete(user.get().getId());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
