package pl.edu.pg.eti.kask.car.carShare.model.conventer;

import pl.edu.pg.eti.kask.car.car.entity.Car;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.car.service.CarService;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Faces converter for {@link CarModel}. The managed attribute in {@link @FacesConverter} allows the converter
 * to be the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not
 * managed by container that is injection was not possible. As this bean is not annotated with scope the beans.xml
 * descriptor must be present.
 */
@FacesConverter(forClass = CarModel.class, managed = true)
public class CarModelConverter implements Converter<CarModel> {

    /**
     * Service for professions management.
     */
    private CarService service;

    @Inject
    public CarModelConverter(CarService service) {
        this.service = service;
    }

    @Override
    public CarModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Car> car = service.find(value);
        return car.isEmpty() ? null : CarModel.entityToModelMapper().apply(car.get());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, CarModel value) {
        return value == null ? "" : value.getPlate();
    }

}
