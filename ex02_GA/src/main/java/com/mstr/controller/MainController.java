package com.mstr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microstrategy.web.objects.WebDisplayUnits;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.webapi.EnumDSSXMLFolderNames;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.mstr.domain.LoginVO;
import com.mstr.service.MstrSessionService;


@Controller
public class MainController {

	@RequestMapping("/main.do")
	public String mainPage(HttpServletRequest request, HttpSession session, ModelMap model) {
		    LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
		    WebFolder folder = null;
	        String objectType = "34";
	        String sharedReportsFolderID = "D3C7D461F69C4610AA6BAA5EF51F4125";

	        //Create a session using
	        WebIServerSession serverSession = MstrSessionService.getSession(sharedReportsFolderID, sharedReportsFolderID, sharedReportsFolderID);
	        WebObjectSource objSource = serverSession.getFactory().getObjectSource();

	        List<String> stringList = new ArrayList<>();
	        try {
	            //Obtain ID for Shared Reports Folder and populate WebFolder
	            //Setting
	            sharedReportsFolderID = objSource.getFolderID(EnumDSSXMLFolderNames.DssXmlFolderNamePublicReports);
	            folder = (WebFolder) objSource.getObject(sharedReportsFolderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);

	            //Set number of elements to fetch
	            folder.setBlockBegin(1);
	            folder.setBlockCount(50);

	            //Fetch Contents from Intelligence Server
	            folder.populate();

	            //If the folder has any contents, display them
	            if (folder.size() > 0) {
	                //Extract folder contents
	                WebDisplayUnits units = folder.getChildUnits();

	                //Print folder contents
	                System.out.println("\n\n\nPrinting folder contents:");
	                if (units != null) {
	                    for (int i = 0; i < units.size(); i++) {
	                        switch (units.get(i).getDisplayUnitType()) {
	                        case EnumDSSXMLObjectTypes.DssXmlTypeFolder:
	                            objectType = "Folder";
	                            break;

	                        case EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition:
	                            objectType = "Report";
	                            break;

	                        case EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition:
	                            objectType = "Document";
	                            break;

	                        case EnumDSSXMLObjectTypes.DssXmlTypeFilter:
	                            objectType = "Filter";
	                            break;

	                        case EnumDSSXMLObjectTypes.DssXmlTypeTemplate:
	                            objectType = "Template";
	                            break;
	                        }
	                        stringList.add(units.get(i).getDisplayName());
	                        System.out.println("\t" + objectType + ": " + units.get(i).getDisplayName());
	                    }
	                    for (String data : stringList) {
	                        System.out.println("데이터"+data);
	                    }
	                }
	            }
	        } catch (WebObjectsException ex) {
	            System.out.println("\nError while fetching folder contents: " + ex.getMessage());
	        }
	        System.out.println("스트링리스트"+stringList.get(0));
	        //Close the session to clean up resources on the Intelligence Server
	        model.addAttribute("menu", stringList);
		
		return "/mstr/left";
	}
	
}
