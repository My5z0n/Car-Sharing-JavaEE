package pl.edu.pg.eti.kask.car.controller;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Global config for JAX-RS REST services prefix.
 */
@ApplicationPath("/restapi")
public class Config extends Application {
}
