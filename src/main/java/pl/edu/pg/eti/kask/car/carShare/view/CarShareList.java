package pl.edu.pg.eti.kask.car.carShare.view;




import pl.edu.pg.eti.kask.car.carShare.model.CarSharesModel;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionScoped;
import java.io.Serializable;
import java.util.Objects;

/**
 * View bean for rendering list of characters.
 */
@ViewScoped
@Named
public class CarShareList implements Serializable {

    /**
     * Service for managing characters.
     */
    private final CarShareService service;

    /**
     * Characters list exposed to the view.
     */
    private CarSharesModel carShares;

    private String plate="";

    @Inject
    public CarShareList(CarShareService service) {
        this.service = service;
    }

    public void init(String pate) {
        this.plate=new String(pate);
        System.out.println("Init XX");
        carShares = CarSharesModel.entityToModelMapper().apply(service.findAllByCar(plate));
    }
    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached using
     * lazy getter.
     *
     * @return all characters
     */
    //public CarSharesModel getCarShares() {
    //            return carShares;
//
    //    }



    /**
     * Action for clicking delete action.
     *
     * @param id character to be removed
     * @return navigation case to list_characters
     */
    public String deleteAction(Long id) {
        service.delete(id);
        System.out.println(id);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
       // throw  new IllegalArgumentException("aa");
    }


    public CarSharesModel getcarShares(String id) {
        plate = id;
        if (carShares == null) {
            System.out.println("This:  "+plate);
            if (!Objects.equals(plate, "")){
                carShares = CarSharesModel.entityToModelMapper().apply(service.findAllByCar(plate));
                return carShares;
            }
            else {
                carShares = CarSharesModel.entityToModelMapper().apply(service.findAll());
                return null;


            }

        }
        return carShares;
    }

    public void getCarShares() {
    }
}
