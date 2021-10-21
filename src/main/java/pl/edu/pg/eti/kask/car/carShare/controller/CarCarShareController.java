package pl.edu.pg.eti.kask.car.carShare.controller;


import pl.edu.pg.eti.kask.car.car.controller.CarController;
import pl.edu.pg.eti.kask.car.car.dto.CreateCarRequest;
import pl.edu.pg.eti.kask.car.car.dto.GetCarResponse;
import pl.edu.pg.eti.kask.car.car.dto.GetCarsResponse;
import pl.edu.pg.eti.kask.car.car.dto.UpdateCarRequest;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.service.CarService;
import pl.edu.pg.eti.kask.car.carShare.dto.CreateCarShareRequest;
import pl.edu.pg.eti.kask.car.carShare.dto.GetCarShareResponse;
import pl.edu.pg.eti.kask.car.carShare.dto.GetCarSharesResponse;
import pl.edu.pg.eti.kask.car.carShare.dto.UpdateCarShareRequest;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Path("/cars/{plate}/shares")
public class CarCarShareController {
    /**
     * Service for managing professions.
     */
    private CarShareService service;
    private CarService carService;

    /**
     * JAX-RS requires no-args constructor.
     */
    public CarCarShareController() {
    }

    /**
     * @param service service for managing professions
     */
    @Inject
    public void setService(CarShareService service) {
        this.service = service;
    }

    @Inject
    public void setCarService(CarService service) {
        this.carService = service;
    }

    /**
     * @return response with available professions
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShares(@PathParam("plate") String plate) {
        Optional<Car> cars = carService.find(plate);
        if (cars.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            List<CarShare> carShares= service.findAllByCar(plate);
            return Response.ok(GetCarSharesResponse.entityToDtoMapper().apply(carShares)).build();
        }
    }



    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") Long id,@PathParam("plate") String plate) {
        Optional<CarShare> car = service.findAllByCarAndId(plate,id);
        if (car.isPresent()) {
            return Response.ok(GetCarShareResponse.entityToDtoMapper().apply(car.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarShares(@PathParam("plate") String plate) {
        Optional<Car> cars = carService.find(plate);
        if (cars.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            List<CarShare> carShares= service.findAllByCar(plate);
            if (carShares.isEmpty())
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            else {
                service.deleteShares(carShares);
                return Response.status(Response.Status.OK).build();
            }
        }
    }
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarshare(@PathParam("plate") String plate, @PathParam("id") Long id) {
        Optional<CarShare> car = service.findAllByCarAndId(plate,id);
        if (car.isPresent()) {
            service.delete(car.get().getId());
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCar(@PathParam("plate") String plate,CreateCarShareRequest request) {
        Optional<Car> car = carService.find(plate);
        if(car.isPresent())
        {
            CarShare carShare = CreateCarShareRequest
                    .dtoToEntityMapper()
                    .apply(request);

            carShare.setCar(car.get());
            // service.createForCallerPrincipal(car);
            if (service.create(carShare)) {
                return Response.created(UriBuilder.fromResource(CarController.class).path("{plate}/shares/{id}")
                        .build(plate,carShare.getId())).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUserCharacter(@PathParam("plate") String plate, @PathParam("id") Long id, UpdateCarShareRequest request) {
        Optional<CarShare> car = service.findAllByCarAndId(plate,id);

        if (car.isPresent()) {
            UpdateCarShareRequest.dtoToEntityUpdater().apply(car.get(), request);
            service.update(car.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

