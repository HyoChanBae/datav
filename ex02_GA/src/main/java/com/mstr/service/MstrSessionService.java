
package com.mstr.service;


import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;


/**
 *
 * <p>Title: SessionManagementSample</p>
 * <p>Description: Helper class to manage a MicroStrategy Session. </p>
 * <p>Company: Microstrategy, Inc.</p>
 */
public class MstrSessionService {
    private static WebObjectsFactory factory = null;
    private static WebIServerSession serverSession = null;
    /**
     * Creates a new session that can be reused by other classes
     * @return WebIServerSession
     */
    
   
    public static WebIServerSession getSession(String id, String pwd, String ip) {
    	System.out.println("mstr session들어왔나");
        if (serverSession == null) {
            //create factory object
            factory = WebObjectsFactory.getInstance();
            System.out.println("factory 체크"+factory);
            serverSession = factory.getIServerSession();
            System.out.println("serverSession 체크"+serverSession);
            //Set up session properties
            serverSession.setServerName("127.0.0.1"); //Should be replaced with the name of an Intelligence Server
            serverSession.setServerPort(0);
            serverSession.setProjectName("MicroStrategy Tutorial"); //Project where the session should be created
            serverSession.setLogin(id); //User ID
            serverSession.setPassword(pwd); //Password

            //Initialize the session with the above parameters
            try {
            	serverSession.getSessionID();
                System.out.println("\nSession created with ID: " + serverSession.getSessionID());
            } catch (WebObjectsException ex) {
                handleError(null, "Error creating session:" + ex.getMessage());
            }
        }
        //Return session
        System.out.println("session값 셋팅 확인"+serverSession);
        return serverSession;
    }

    /**
     * Close Intelligence Server Session
     * @param serverSession WebIServerSession
     */
    public static void closeSession(WebIServerSession serverSession) {
        try {
            serverSession.closeSession();
        } catch (WebObjectsException ex) {
            System.out.println("Error closing session:" + ex.getMessage());
            return;
        }
        System.out.println("Session closed.");
    }

    /**
     * Print out error message, close session and abort execution
     * @param serverSession WebIServerSession
     * @param message String
     */
    public static void handleError(WebIServerSession serverSession, String message) {
        System.out.println(message);
        if (serverSession != null) {
            closeSession(serverSession);
        }
        System.exit(0);
    }

}

