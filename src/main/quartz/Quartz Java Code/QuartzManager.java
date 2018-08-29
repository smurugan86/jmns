package com.ghx.cc.commitment.webservices.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ghx.cc.commitment.dto.GpoDTO;
import com.ghx.cc.commitment.webservices.rest.RestConstants;

@Path("/quartz")
@Service("quartzManager")
public class QuartzManager extends BaseResource {

    @Autowired
    @Qualifier("routScheduler")
    private Scheduler routScheduler;

    @Autowired
    @Qualifier("rinScheduler")
    private Scheduler rinScheduler;

    @Autowired
    @Qualifier("rcbScheduler")
    private Scheduler rcbScheduler;

    @GET
    @Path("triggers")
    public String getAllTriggers(@Context HttpServletRequest httpServletRequest, @Context UriInfo uriInfo, @QueryParam("group") String group, @HeaderParam(RestConstants.SSL_CLIENT_CN) String sslClientCn)
        throws SchedulerException {
    	
    	ArrayList<String> triggers = new ArrayList<String>();

        if (isAuthorized(sslClientCn)) {
            Set<TriggerKey> triggerKeys = new HashSet<TriggerKey>();
            if (group == null) {
                group = "default";
            }
            switch (group.toUpperCase()) {
                case "ROUT":
                    triggerKeys = routScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    break;

                case "RIN":
                    triggerKeys = rinScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    break;

                case "RCB":
                    triggerKeys = rcbScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    break;

                default:
                    triggerKeys.addAll(routScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));
                    triggerKeys.addAll(rinScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));
                    triggerKeys.addAll(rcbScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));
                    break;
            }

            for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
                TriggerKey triggerKey = (TriggerKey) iterator.next();
                
                URI uri = uriInfo.getAbsolutePathBuilder().path(triggerKey.toString()).build();
                triggers.add(uri.toString());
            }
        }

        return StringUtils.collectionToDelimitedString(triggers, ",", "\n", "");
    }

    @DELETE
    @Path("triggers/{triggerKeyName}")
    public boolean deleteTrigger(@Context HttpServletRequest httpServletRequest, @PathParam("triggerKeyName") String triggerKeyName, @HeaderParam(RestConstants.SSL_CLIENT_CN) String sslClientCn)
        throws SchedulerException {

        Set<TriggerKey> triggerKeys = new HashSet<TriggerKey>();

        String[] triggerParts = triggerKeyName.split("\\.");
        String triggerGroup = triggerParts[0];

        if (isAuthorized(sslClientCn)) {
            switch (triggerGroup.toUpperCase()) {
                case "ROUT":
                    triggerKeys = routScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
                        TriggerKey triggerKey = (TriggerKey) iterator.next();
                        if (triggerKey.toString().equalsIgnoreCase(triggerKeyName)) {
                            return routScheduler.unscheduleJob(triggerKey);
                        }
                    }
                    break;

                case "RIN":
                    triggerKeys = rinScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
                        TriggerKey triggerKey = (TriggerKey) iterator.next();
                        if (triggerKey.toString().equalsIgnoreCase(triggerKeyName)) {
                            return rinScheduler.unscheduleJob(triggerKey);
                        }
                    }
                    break;

                case "RCB":
                    triggerKeys = rcbScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
                        TriggerKey triggerKey = (TriggerKey) iterator.next();
                        if (triggerKey.toString().equalsIgnoreCase(triggerKeyName)) {
                            return rcbScheduler.unscheduleJob(triggerKey);
                        }
                    }
                    break;
                    
                case "RECOVERING_JOBS":
                    triggerKeys = rinScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup());
                    for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
                        TriggerKey triggerKey = (TriggerKey) iterator.next();
                        if (triggerKey.toString().equalsIgnoreCase(triggerKeyName)) {
                            return rinScheduler.unscheduleJob(triggerKey);
                        }
                    }
                    break;
            }
        }

        return false;

    }

    @DELETE
    @Path("triggers")
    public boolean deleteAllTriggers(@Context HttpServletRequest httpServletRequest)
    		throws SchedulerException {

    	Set<TriggerKey> triggerKeys = new HashSet<TriggerKey>();

    	if (isNotProductionEnvironment(httpServletRequest)) {
    		triggerKeys.addAll (routScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));
    		triggerKeys.addAll(rinScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));
    		triggerKeys.addAll(rcbScheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()));

    		for (Iterator<TriggerKey> iterator = triggerKeys.iterator(); iterator.hasNext();) {
    			TriggerKey triggerKey = (TriggerKey) iterator.next();
    			String[] triggerParts = triggerKey.toString().split("\\.");
    			String triggerGroup = triggerParts[0];



    			switch (triggerGroup.toUpperCase()) {
    			case "ROUT":

    				routScheduler.unscheduleJob(triggerKey);
    				break;


    			case "RIN":
    				rinScheduler.unscheduleJob(triggerKey);
    				break;

    			case "RCB":
    				rcbScheduler.unscheduleJob(triggerKey);
    				break;

    			case "RECOVERING_JOBS":
    				rinScheduler.unscheduleJob(triggerKey);
    				break;
    				

    			}
    		}
    	}

    	return false;

    }

    
    private boolean isAuthorized(String sslClientCn) {
    	GpoDTO gpo = getGpoBySslClientCN(sslClientCn);
        return (gpo.getOrgName().equals("Juno GPO"));
    }
    
    private boolean isNotProductionEnvironment(HttpServletRequest httpServletRequest) {
        return (!httpServletRequest.getServerName().equalsIgnoreCase("ccm.ghx.com")
                && !httpServletRequest.getServerName().equalsIgnoreCase("ccm01.ghx.com") && !httpServletRequest.getServerName().equalsIgnoreCase("ccm02.ghx.com"));
    }
}