package pl.edu.pg.eti.kask.car.user.servlet;


import pl.edu.pg.eti.kask.car.servlet.HttpHeaders;
import pl.edu.pg.eti.kask.car.servlet.MimeTypes;
import pl.edu.pg.eti.kask.car.servlet.ServletUtility;
import pl.edu.pg.eti.kask.car.user.dto.CreateAvatarRequest;
import pl.edu.pg.eti.kask.car.user.entity.Avatar;
import pl.edu.pg.eti.kask.car.user.entity.User;
import pl.edu.pg.eti.kask.car.user.service.AvatarService;
import pl.edu.pg.eti.kask.car.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = AvatarServlet.Paths.AVARARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {


    private final AvatarService avatarService;
    private final UserService userService;

    @Inject
    public AvatarServlet(AvatarService avatarService, UserService userService) {
        this.avatarService = avatarService;
        this.userService = userService;
    }


    public static class Paths {
        public static final String AVARARS = "/api/avatars";
    }

    public static class Patterns {


        public static final String AVATAR = "^/[0-9]+/?$";
    }


    public static class Parameters {

        public static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVARARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVARARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVARARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        Optional<Avatar> avatar = avatarService.find(id);

        if (user.isPresent() && avatar.isPresent()) {
            avatarService.delete(avatar.get());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        Optional<Avatar> avatar = avatarService.find(id);

        if (user.isPresent()) {
            Part portrait = request.getPart(Parameters.AVATAR);
            if (avatar.isPresent()) {
                if (portrait != null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                if (portrait != null) {
                    var bytes = portrait.getInputStream().readAllBytes();
                    CreateAvatarRequest requestAvatar = CreateAvatarRequest.builder().id(id).image(bytes).build();
                    avatarService.create(CreateAvatarRequest.dtoToEntityMapper().apply(requestAvatar));
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        Optional<Avatar> avatar = avatarService.find(id);

        if (user.isPresent()) {
            Part portrait = request.getPart(Parameters.AVATAR);
            if (avatar.isPresent()) {
                if (portrait != null) {
                    avatarService.update(id, portrait.getInputStream());
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } else {
                if (portrait != null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }

            }


        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        Optional<Avatar> avatar = avatarService.find(id);

        if (user.isPresent() && avatar.isPresent()) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(avatar.get().getImage().length);
            response.getOutputStream().write(avatar.get().getImage());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
