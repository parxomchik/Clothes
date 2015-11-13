package com.vasax.clothes.util;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;

/**
 * Created by root on 19.12.14.
 */
@Named
@Singleton
public class PassEncoder implements Serializable{

    public String encode(String pass){
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
        return shaPasswordEncoder.encodePassword(pass, null);
    }
}
