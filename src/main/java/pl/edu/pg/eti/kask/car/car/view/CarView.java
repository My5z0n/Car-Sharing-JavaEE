package pl.edu.pg.eti.kask.car.car.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.car.model.CarsModel;
import pl.edu.pg.eti.kask.car.car.service.CarService;


import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single characters.
 */
@ViewScoped
@Named
public class CarView implements Serializable {

    /**
     * Service for managing characters.
     */
    private final CarService service;

    /**
     * Character id.
     */
    @Setter
    @Getter
    private String id;

    /**
     * Character exposed to the view.
     */
    @Getter
    private CarModel car;

    @Inject
    public CarView(CarService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public String delete()  {
        System.out.println("delete");
        return "car_list?faces-redirect=true";

    }

    public void init() throws IOException {
        Optional<Car> car = service.find(id);
        if (car.isPresent()) {
            this.car = CarModel.entityToModelMapper().apply(car.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Car not found");
        }
    }

    
}
