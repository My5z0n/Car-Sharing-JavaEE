package pl.edu.pg.eti.kask.car.car.view;



import pl.edu.pg.eti.kask.car.car.model.CarsModel;
import pl.edu.pg.eti.kask.car.car.service.CarService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * View bean for rendering list of characters.
 */
@RequestScoped
@Named
public class CarList implements Serializable {

    /**
     * Service for managing characters.
     */
    private final CarService service;

    /**
     * Characters list exposed to the view.
     */
    private CarsModel cars;

    @Inject
    public CarList(CarService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all characters
     */
    public CarsModel getCars() {
        if (cars == null) {
            cars = CarsModel.entityToModelMapper().apply(service.findAll());
        }
        return cars;
    }

    /**
     * Action for clicking delete action.
     *
     * @param car character to be removed
     * @return navigation case to list_characters
     */
    public String deleteAction(CarsModel.Car car) {
        service.delete(car.getPlate());
        return "car_list?faces-redirect=true";
    }

}
