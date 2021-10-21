package pl.edu.pg.eti.kask.car.car.controller;

import pl.edu.pg.eti.kask.car.car.dto.CreateCarRequest;
import pl.edu.pg.eti.kask.car.car.dto.GetCarResponse;
import pl.edu.pg.eti.kask.car.car.dto.GetCarsResponse;
import pl.edu.pg.eti.kask.car.car.dto.UpdateCarRequest;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.service.CarService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Path("/cars")
public class CarController {
    /**
     * Service for managing professions.
     */
    private CarService service;

    /**
     * JAX-RS requires no-args constructor.
     */
    public CarController() {
    }

    /**
     * @param service service for managing professions
     */
    @Inject
    public void setService(CarService service) {
        this.service = service;
    }

    /**
     * @return response with available professions
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars() {
        return Response.ok(GetCarsResponse.entityToDtoMapper().apply(service.findAll())).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCars() {
        List<Car> cars = service.findAll();

        if (cars.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            service.deleteAll();
            return Response.status(Response.Status.OK).build();
        }
    }

    /**
     * @param name name of the profession
     * @return response with selected profession or 404 status code
     */
    @GET
    @Path("{plate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("plate") String name) {
        Optional<Car> car = service.find(name);
        if (car.isPresent()) {
            return Response.ok(GetCarResponse.entityToDtoMapper().apply(car.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{plate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("plate") String name) {
        Optional<Car> car = service.find(name);
        if (car.isPresent()) {
            service.delete(car.get().getPlate());
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCar(CreateCarRequest request) {
        Car car = CreateCarRequest
                .dtoToEntityMapper()
                .apply(request);

        // service.createForCallerPrincipal(car);
        if (service.create(car)) {
            return Response.created(UriBuilder.fromResource(CarController.class).path("{plate}")
                    .build(car.getPlate())).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    @PUT
    @Path("{plate}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUserCharacter(@PathParam("plate") String plate, UpdateCarRequest request) {
        Optional<Car> car = service.find(plate);

        if (car.isPresent()) {
            UpdateCarRequest.dtoToEntityUpdater().apply(car.get(), request);

            service.update(car.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

