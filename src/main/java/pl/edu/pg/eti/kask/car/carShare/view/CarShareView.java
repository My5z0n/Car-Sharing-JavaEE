package pl.edu.pg.eti.kask.car.carShare.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.car.service.CarService;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.model.CarShareModel;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single characters.
 */
@RequestScoped
@Named
public class CarShareView implements Serializable {

    /**
     * Service for managing characters.
     */
    private final CarShareService service;

    /**
     * Character id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Character exposed to the view.
     */
    @Getter
    private CarShareModel carShare;

    @Inject
    public CarShareView(CarShareService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<CarShare> carShare = service.find(id);
        if (carShare.isPresent()) {
            this.carShare = CarShareModel.entityToModelMapper().apply(carShare.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "CarShare not found");
        }
    }

    
}
