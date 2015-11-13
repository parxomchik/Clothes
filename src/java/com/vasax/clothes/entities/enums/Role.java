package com.vasax.clothes.entities.enums;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by root on 07.10.14.
 */
public enum Role {
    ADMINISTRATOR,
    DELIVERY,
    MANAGER,
    SUPERUSER, //only one user per system
    CUSTOMER
}
