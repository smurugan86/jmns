package com.ghx.cc.commitment.webservices.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ghx.cc.commitment.webservices.rest.exception.RESTRuntimeExceptionMapper;
import com.ghx.cc.commitment.webservices.rest.filter.AsynchronousFilter;
import com.ghx.cc.commitment.webservices.rest.filter.AuthorizationFilter;
import com.ghx.cc.commitment.webservices.rest.resource.CallbackV1;
import com.ghx.cc.commitment.webservices.rest.resource.ContractV1;
import com.ghx.cc.commitment.webservices.rest.resource.JobV1;
import com.ghx.cc.commitment.webservices.rest.resource.MigrationContractV1;
import com.ghx.cc.commitment.webservices.rest.resource.MigrationOfferV1;
import com.ghx.cc.commitment.webservices.rest.resource.OfferV1;
import com.ghx.cc.commitment.webservices.rest.resource.PingV1;
import com.ghx.cc.commitment.webservices.rest.resource.QuartzManager;
import com.ghx.cc.commitment.webservices.rest.resource.ServiceInfo;
import com.ghx.cc.commitment.webservices.rest.validation.ContractUpdateCommandsValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.ContractValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.MigrationContractValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.MigrationOfferValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.MigrationRelationshipsValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.OfferUpdateCommandsValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.OfferValidatingReader;
import com.ghx.cc.commitment.webservices.rest.validation.OffersUpdateCommandsValidatingReader;

public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        addResources(classes);
        addProviders(classes);
        addFilters(classes);
        return classes;
    }

    private void addResources(Set<Class<?>> classes) {
        classes.add(QuartzManager.class);
        classes.add(PingV1.class);
        classes.add(ServiceInfo.class);
        classes.add(ContractV1.class);
        classes.add(OfferV1.class);
        classes.add(JobV1.class);
        classes.add(MigrationContractV1.class);
        classes.add(CallbackV1.class);
        classes.add(MigrationOfferV1.class);
    }

    private void addProviders(Set<Class<?>> classes) {
        classes.add(RESTRuntimeExceptionMapper.class);
        classes.add(ContractValidatingReader.class);
        classes.add(OfferValidatingReader.class);
        classes.add(MigrationContractValidatingReader.class);
        classes.add(ContractUpdateCommandsValidatingReader.class);
        classes.add(MigrationOfferValidatingReader.class);
        classes.add(OfferUpdateCommandsValidatingReader.class);
        classes.add(OffersUpdateCommandsValidatingReader.class);
        classes.add(MigrationRelationshipsValidatingReader.class);
    }

    /**
     * @param classes
     */
    private void addFilters(Set<Class<?>> classes) {
        classes.add(AsynchronousFilter.class);
        classes.add(AuthorizationFilter.class);
    }
}
