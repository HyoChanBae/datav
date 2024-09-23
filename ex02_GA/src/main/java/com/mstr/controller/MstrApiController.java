package com.mstr.controller;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.mstr.domain.LoginVO;
import com.mstr.domain.MstrLoginVO;
import com.mstr.service.MstrApiService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @packageName : com.oss.controller
* @fileName : MstrApiController.java
* @author : km.kang
* @date : 2021-11-09 ?ò§?†Ñ 10:58
* description : MSTR APIÎ•? ?ôú?ö©?ïò?äî Action Controller
* ===========================================================
* DATE AUTHOR NOTE
* -----------------------------------------------------------
* 2021-11-09 km.kang ÏµúÏ¥à ?Éù?Ñ±
*/
@Controller
@RequestMapping("/mstrapi")
public class MstrApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MstrApiController.class);



    @Value("#{system['MSTR.CONFIG.DEFAULT.ROOT.REPORT.FOLDERID']}")
    private String rootReportFolder;

    @Value("#{system['MSTR.CONFIG.DEFAULT.ROOT.DOSSIER.FOLDERID']}")
    private String dossierReportFolder;

    @Value("#{system['MSTR.CONFIG.DEFAULT.ROOT.ANALYSIS.FOLDERID']}")
    private String analysisReportFolder;

    @Value("#{system['MSTR.CONFIG.PROJECT.ID']}")
    private String mstrProjectId;

    @Value("#{system['MSTR.CONFIG.CASTOR.SERVER.CONFIGURATION.ID']}")
    private String configurationProjectId;

    @Value("#{web['date.config.year.range']}")
    private String yearRange;

    @Resource(name = "mstrApiService")
    private MstrApiService mstrApiService;

    @RequestMapping("getMenuList")
    @ResponseBody
    public Map<String, Object> getMenuList(HttpServletRequest request, HttpSession session, String folderId) {
        Map<String, Object>             param = new ReqData(request).getParams();
        Map<String, Object>             map = new HashMap<String, Object>();
        WebObjectsFactory               objFactory = WebObjectsFactory.getInstance();
        WebIServerSession               isess = objFactory.getIServerSession();
        WebObjectSource                 objectSource = objFactory.getObjectSource();
        MstrLoginVO                         mstrLoginVO = (MstrLoginVO) session.getAttribute("loginVO");
        List<Map<String, Object>>       folderListSession = null;
        Map<String, Object>             folderMap = null;
        List<Map<String, Object>>       folderList = null;
        List<Integer>                   eisIncluedType = new ArrayList<Integer>();
        String                          folderObjectId = null;

        folderListSession = (List<Map<String, Object>>) session.getAttribute("folder_"+folderId);
        if(folderListSession != null){
            map.put("status", "S");
            map.put("list", folderListSession);
            return map;
        }
        String sessionState = null;
        sessionState = mstrLoginVO.getMstrSession();
        if (sessionState != null) {
            isess.restoreState(sessionState);
        }
        
        eisIncluedType.add(EnumDSSXMLObjectTypes.DssXmlTypeFolder);
        eisIncluedType.add(EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition);
        eisIncluedType.add(EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition);
        eisIncluedType.add(EnumDSSXMLObjectTypes.DssXmlTypeShortcut);
        folderObjectId = folderId;

        folderMap = mstrApiService.getFolderList(objectSource, folderObjectId, eisIncluedType, false, true);
        folderList = (List<Map<String, Object>>) folderMap.get("children");
        session.setAttribute("folder_" + folderId, folderList); //

        map.put("status", "S");
        map.put("list", folderList);
        return map;
    }


    private static final class ReqData {
        private final Map<String, Object> params = new HashMap<String, Object>();

        public ReqData(HttpServletRequest request) {
            Map<String, String[]> map = request.getParameterMap();

            for (String key : map.keySet()) {
                String[] val = map.get(key);

                if (val.length > 0) {
                    params.put(key, val[0]);
                }
            }
        }

        public Map<String, Object> getParams() {
            return params;
        }
    }

    

}
