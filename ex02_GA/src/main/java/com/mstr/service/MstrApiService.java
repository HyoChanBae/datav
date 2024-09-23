package com.mstr.service;



import com.microstrategy.web.beans.BeanFactory;
import com.microstrategy.web.beans.UserBean;
import com.microstrategy.web.beans.WebBeanException;
import com.microstrategy.web.beans.WebException;
import com.microstrategy.web.objects.*;
import com.microstrategy.web.objects.admin.users.*;
import com.microstrategy.webapi.*;
import com.mstr.util.DisplayNameComparator;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;



@Service("mstrApiService")
public class MstrApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MstrApiService.class);



    public Map<String, Object> getFolderList(WebObjectSource objSource, String folderId, List<Integer> incluedType, boolean hidden, boolean mainMenuMode) {

        Map<String, Object> ret_val = new TreeMap<String, Object>();

        WebObjectInfo obj = null;
        WebFolder rootFolder = null;
        try {
            if (hidden) {
                objSource.setFlags(objSource.getFlags() | EnumDSSXMLObjectFlags.DssXmlObjectFindHidden);
            }
            if (folderId == null || folderId.length() == 0)
                return ret_val;
            obj = objSource.getObject(folderId, EnumDSSXMLObjectTypes.DssXmlTypeFolder);
            rootFolder = (WebFolder) obj;
            rootFolder.populate();


            ret_val.put("id", rootFolder.getID());
            ret_val.put("key", rootFolder.getID());
//            String foldertitle = rootFolder.getDisplayName().indexOf(".") > 0 ?
//                    rootFolder.getDisplayName().substring(rootFolder.getDisplayName().indexOf(".") + 1) : rootFolder.getDisplayName();
            String foldertitle = rootFolder.getDisplayName();
            ret_val.put("title", foldertitle);
            ret_val.put("text", foldertitle);
            ret_val.put("isFolder", true);
            ret_val.put("folder", true);
            ret_val.put("type", rootFolder.getType());
            ret_val.put("subType", rootFolder.getSubType());
            ret_val.put("level", rootFolder.getLevel());

            if (rootFolder.size() > 0) {
                WebDisplayUnits units = rootFolder.getChildUnits();
                List<Map<String, Object>> childList = new ArrayList<>();
                WebObjectInfo childObj = null;
                Map<String, Object> childMap = null;
                for (int i = 0; i < units.size(); i++) {
                    WebDisplayUnit child = units.get(i);
                    if (incluedType != null && !incluedType.contains(child.getDisplayUnitType())) {
                        continue;
                    }

                    switch (child.getDisplayUnitType()) {
                        case EnumDSSXMLObjectTypes.DssXmlTypeFolder:
                            WebFolder childFolder = (WebFolder) child;
                            childFolder.populate();
                            String folderName = childFolder.getDisplayName();
                            if (folderName.indexOf(".") > -1) {
                                if (Integer.parseInt(folderName.split("\\.")[0]) >= 90 || Integer.parseInt(folderName.split("\\.")[0]) == 0) {
                                    continue;
                                }
                            }
                            childList.add(getFolderList(objSource, childFolder.getID(), incluedType, hidden, mainMenuMode));
                            break;
                        case EnumDSSXMLObjectTypes.DssXmlTypeShortcut:
                            try {
                                WebObjectInfo webobjectinfo = objSource.getObject(child.getID(), 18, true);
                                WebShortcut webshortcut = (WebShortcut) webobjectinfo;
                                String shortcutTitle = webshortcut.getDisplayName();
                                WebObjectInfo target = webshortcut.getTarget();
                                childObj = objSource.getObject(target.getID(), target.getDisplayUnitType());
                                childObj.populate();
                                {
                                    String title = webobjectinfo.getDisplayName();
//                                    String title = childObj.getDisplayName().indexOf(".") > 0 ?
//                                            childObj.getDisplayName().substring(childObj.getDisplayName().indexOf(".") + 1) : childObj.getDisplayName();
                                    childMap = new TreeMap<String, Object>();
                                    childMap.put("parent", rootFolder.getID());
                                    childMap.put("id", childObj.getID());
                                    childMap.put("key", childObj.getID());
                                    childMap.put("title", title);
                                    childMap.put("text", title);
                                    childMap.put("type", childObj.getType());
                                    childMap.put("subType", childObj.getSubType());
                                    childMap.put("viewMode", childObj.getVisualizationViewMode());
                                    childMap.put("isFolder", false);
                                    childMap.put("folder", false);
                                    childMap.put("viewMedia", childObj.getViewMediaSettings().getDefaultMode()); //8192?¼ê²½ìš° ?„?”¨?—
                                    childMap.put("hidden", childObj.isHidden());
                                    childList.add(childMap);

                                }
                            } catch (WebObjectsException e1) {

                                LOGGER.debug("Exception Error!");
                            } catch (IllegalArgumentException e1) {

                                LOGGER.debug("Exception Error!");
                            }
                            break;
                        default:
                            childObj = objSource.getObject(child.getID(), child.getDisplayUnitType());
                            childObj.populate();
                            String title = childObj.getDisplayName();
//                            String title = childObj.getDisplayName().indexOf(".") > 0 ?
//                                    childObj.getDisplayName().substring(childObj.getDisplayName().indexOf(".") + 1) : childObj.getDisplayName();
                            childMap = new TreeMap<String, Object>();
                            childMap.put("parent", rootFolder.getID());
                            childMap.put("id", childObj.getID());
                            childMap.put("key", childObj.getID());
                            childMap.put("title", title);
                            childMap.put("text", title);
                            childMap.put("type", childObj.getType());
                            childMap.put("subType", childObj.getSubType());
                            childMap.put("viewMedia", childObj.getViewMediaSettings().getDefaultMode());
                            childMap.put("isFolder", false);
                            childMap.put("folder", false);
                            childMap.put("hidden", childObj.isHidden());
                            childList.add(childMap);
                        break;
                    }
                }
                Collections.sort(childList, new DisplayNameComparator());
                ret_val.put("children", childList);
            }
        } catch (WebObjectsException e) {

            LOGGER.debug("Exception Error!");
        } catch (IllegalArgumentException e) {

            LOGGER.debug("Exception Error!");
        }

        return ret_val;
    }
}
