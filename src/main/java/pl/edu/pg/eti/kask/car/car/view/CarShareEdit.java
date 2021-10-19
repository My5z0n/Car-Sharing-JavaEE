package pl.edu.pg.eti.kask.car.car.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.car.carShare.entity.CarShare;
import pl.edu.pg.eti.kask.car.carShare.model.CarShareEditModel;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;


import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * View bean for rendering single character edit form.
 */
@ViewScoped
@Named
public class CarShareEdit implements Serializable {

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
    private CarShareEditModel carShare;

    /**
     * Multipart part for uploaded portrait file.
     */
    @Getter
    @Setter
    private Part portrait;

    @Inject
    public CarShareEdit(CarShareService service) {
        this.service = service;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<CarShare> carShare = service.find(id);
        if (carShare.isPresent()) {
            this.carShare = CarShareEditModel.entityToModelMapper().apply(carShare.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }

    /**
     * Action initiated by clicking save button.
     *
     * @return navigation case to the same page
     */
    public String saveAction() {
        service.update(CarShareEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), carShare));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }



}
