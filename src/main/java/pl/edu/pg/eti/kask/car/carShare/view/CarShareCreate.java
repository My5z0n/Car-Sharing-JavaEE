package pl.edu.pg.eti.kask.car.carShare.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.car.car.model.CarModel;
import pl.edu.pg.eti.kask.car.car.service.CarService;
import pl.edu.pg.eti.kask.car.carShare.model.CarShareCreateModel;
import pl.edu.pg.eti.kask.car.carShare.service.CarShareService;


import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * View bean for rendering single character create form. Creating a character is divided into number of steps where
 * each step is separate JSF view. In order to use single bean, conversation scope is used.
 */
@ConversationScoped
@Named
@NoArgsConstructor
public class CarShareCreate implements Serializable {

    /**
     * Service for managing characters.
     */
    private CarShareService service;

    /**
     * Service for managing professions.
     */
    private CarService carService;

    /**
     * Character id.
     */
    @Setter
    @Getter
    private Long id;

    /**
     * Multipart part for uploaded portrait file.
     */
    @Getter
    @Setter
    private Part portrait;

    /**
     * Character exposed to the view.
     */
    @Getter
    private CarShareCreateModel carShare;

    /**
     * Available professions.
     */
    @Getter
    private List<CarModel> cars;


    /**
     * Injected conversation.
     */
    private Conversation conversation;

    @Inject
    public CarShareCreate(CarShareService service, CarService carService, Conversation conversation) {
        this.service = service;
        this.carService = carService;
        this.conversation = conversation;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached wihitn
     * field and initialized during init of the view.
     */
    @PostConstruct
    public void init() {
        cars = carService.findAll().stream()
                .map(CarModel.entityToModelMapper())
                .collect(Collectors.toList());
        carShare = CarShareCreateModel.builder().build();
        conversation.begin();
    }


    public String cancelAction() {
        conversation.end();
        return "/car/car_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {

        return "/carShare/carShare_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        service.create(CarShareCreateModel.modelToEntityMapper(
                carShare -> carService.find(carShare).orElseThrow()).apply(carShare));
        conversation.end();
        return "/car/car_list.xhtml?faces-redirect=true";
    }



    public String getConversationId() {
        return conversation.getId();
    }

}
