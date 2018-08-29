package com.ghx.cc.commitment.webservices.rest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.ws.rs.NameBinding;

/**
 * @author dgruber
 *
 */
@NameBinding
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Asynchronous {
}