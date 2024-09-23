//메인페이지 이동

/* MSTR 리포트 리스트 가져오기 */
function getMenuList(menuId,folderId) {
    const url = ctx + "/mstrapi/getMenuList.do";
    $.ajax({
        type    : "POST",
        data    : "folderId=" + folderId,
        async   : false,
        dataType: "json",
        url     : url,
        success : function (response) {
            let $rootObj = $("[data-key='"+menuId+"']");

            let depth_num = 0;
            if($rootObj.hasClass("depth2")) {
                depth_num = 2;
            }else if($rootObj.hasClass("depth3")){
                depth_num = 3;
            }

            renderMenuList(depth_num, $rootObj, response.list)

        }
    });
}

//좌측 트리 메뉴 렌더링 (리포트 제외 일반메뉴)
function authMenuRender(menuAuthList, execMenuId){
    $.each(menuAuthList, function (index, menu) {
        let $parent;
        let $li;
        let $span;
        if(menu.lv === 1){
            $parent = $("#gnb").find("ul.depth1");
            $li = $("<li class='menu1'></li>").appendTo($parent);
            $li.append('<i class="menuico '+menu.menuId+'"><span class="tooltiptext tooltip-right">'+menu.menuTitle+'</span></i>');
            $span = $('<span class="title folder">'+menu.menuTitle+'</span>').appendTo($li);
            $li.append('<ul class="depth2 path" title="'+menu.menuTitle+'" data-key="'+menu.menuId+'"></ul>');
        }else{
            $parent = $("ul[data-key='"+menu.upperMenuId+"']");
            if($parent.length === 0){
                if(menu.lv === 2){
                    $parent = $("#gnb").find("ul.depth1");
                }else{
                    let parentInfo = getMenuInfo(menu.upperMenuId);
                    $parent = $("ul[data-key='"+parentInfo.upperMenuId+"']");
                }
                $li = $("<li class='menu1'></li>").appendTo($parent);
                $li.append('<i class="menuico '+menu.upperMenuId+'"><span class="tooltiptext tooltip-right">'+menu.upperMenuNm+'</span></i>');
                $li.append('<span class="title folder">'+menu.upperMenuNm+'</span>');
                $li.append('<ul class="depth'+menu.lv+' path" title="'+menu.upperMenuNm+'" data-key="'+menu.upperMenuId+'"></ul>');
                $parent = $("ul[data-key='"+menu.upperMenuId+"']");
            }
            $li = $("<li></li>").appendTo($parent);
            if(menu.menuDivCd === 'F'){
                $span = $("<span title='"+menu.menuTitle+"' class='depth"+menu.lv+"_title folder'>"+menu.menuTitle+"</span>").appendTo($li);
                $li.append('<ul class="depth'+(menu.lv +1)+' path" title="'+menu.menuTitle+'" data-key="'+menu.menuId+'"></ul>');
            }else {
                $span = $("<span title='"+menu.menuTitle+"' data-key='"+menu.menuId+"' class='depth"+menu.lv+"_title'>"+menu.menuTitle+"</span>").appendTo($li);
            }
            if (typeof dataKey != "undefined" && dataKey === menu.menuId) {
                $span.addClass("on");
                //$rootObj.css("display", "block");
                let menupath = $span.parents("ul.path");

                $.each(menupath, function (index, el) {
                    $("ul.navi").append("<li>" + $(el).attr("title") + "</li>");
                })
                $("ul.navi").append("<li class='on'>" + menu.menuTitle + "</li>");
                $("#content").children("h2").html(menu.menuTitle);
            }

        }

        if(menu.menuDivCd === 'L'){ //링크방식일 경우
            $span.addClass("view");
            $span.click(function(){
                moveMenu(menu.menuId, false, '_top', menu.menuUrl);
            });
            if(execMenuId === menu.menuId){
                moveMenu(menu.menuId, false, '_top', menu.menuUrl);
            }
        }else if(menu.menuDivCd === 'P'){ // 팝업 링크
            $span.addClass("view");
            $span.click(function(){
                moveMenu(menu.menuId, true, menu.menuId, menu.menuUrl);
            })
        }
        else if(menu.menuDivCd === 'F'){ // MSTR 폴더 하위 리포트 리스트를 구
            // $li.append('<ul class="depth2 path" title="'+menu.menuTitle+'" data-key="'+menu.menuId+'"></ul>');
            getMenuList(menu.menuId, menu.menuUrl);
        }

    })

    // $("li.menu1").each(function(){
    //     if($(this).find(".depth2").find("li").length === 0){
    //         $(this).remove();
    //     }    // })
}
//mstr 화면 좌측 메뉴 렌더링
function renderMenuList(depth_num, $rootObj, list) {
    if (typeof list == "undefined" || list == null) {
        return;
    }
    let sortNumList = [];
    list.forEach(function (menu) {
        let $li = $('<li></li>').appendTo($rootObj);
        let id = menu.id;
        let title = menu.title;
        let sortNum = title.indexOf(".") > 0 ? title.substring(0, title.indexOf(".") + 1) * 1 : 0;
        let isReport = menu.folder == false ? true : false;
        let isMerge = false;
        let divNm = title.indexOf(":") > 0 ? title.split(":")[1] : title;
        divNm = divNm.indexOf(".") > -1 ? divNm.substring(divNm.indexOf(".") + 1) : divNm;
        /*
             같은 sort number를 가지는 리포트는 하나의 리포트처럼 보이도록 merge 처리 시킨다.
             ex) 23.기간별 비교분석(월별), 23.기간별 비교분석(일별), 23.기간별 비교분석(주별)
             위 2개 리포트를 (월별, 일별, 주별) 프롬프트를 임의로 생성하여 1개 리포트에서 3개리포트를 조회 할 수 있어야 한다.
             가장 첫번째로 오는 23번 리포트 월별에 일별과 주별 리포트 정보를 넘겨주도록 한다.
         */
        if (isReport && sortNum != 0) {
            // let index = sortNumList.findIndex(rpt => rpt.sort == sortNum); ie 지원안함
            let index = -1;
            sortNumList.some(function(el, i) {
                if (el.sort == sortNum) {
                    index = i;
                    return true;
                }
            });

            //같은 넘버의 리포트가 있을 경우
            if (index > 0) {
                isMerge = true;
                let mergeId = sortNumList[index].id;
                let $mergeObj = $("[data-key='" + mergeId + "']");
                let mergeTitle = $mergeObj.text();
                let mergeDivNm = mergeTitle.indexOf(":") > 0 ? mergeTitle.split(":")[1] : mergeTitle;
                mergeDivNm = mergeDivNm.indexOf(".") > -1 ? mergeDivNm.substring(mergeDivNm.indexOf(".") + 1) : mergeDivNm;
                let mergeKey;
                mergeKey = typeof $mergeObj.attr("merge-key") == "undefined" ? mergeId + ":" + mergeDivNm
                    + "," + id + ":" + divNm : $mergeObj.attr("merge-key") + "," + id + ":" + divNm
                title = mergeTitle.indexOf(":") > 0 ? mergeTitle.split(":")[0] : mergeTitle;
                $mergeObj.text(title);
                $mergeObj.attr("merge-key", mergeKey);

                $li.remove();

            } else {
                sortNumList.push({"id": id, "sort": sortNum});
            }
        }
        if (!isMerge) {
            title = title.indexOf(".") > 0 ? title.substring(title.indexOf(".") + 1) : title;
            let $span = $(
                '<span class="depth' + depth_num + '_title" data-key="' + id + '">'
                + title + '</span>').appendTo($li);

            //리포트 타입
            if (isReport) {
                $span.click(function () {
                    moveReport(menu, this); //리포트 실행 이벤트
                });
                $span.addClass("view");
                if (typeof dataKey != "undefined" && dataKey === id) {
                    $span.addClass("on");
                    //$rootObj.css("display", "block");
                    let menupath = $span.parents("ul.path");

                    $.each(menupath, function (index, el) {
                        $("ul.navi").append("<li>" + $(el).attr("title") + "</li>");
                    })
                    title = title.indexOf(":") > 0 ? title.split(":")[0] : title;
                    $("ul.navi").append("<li class='on'>" + title + "</li>");
                }
            } else {
                $span.addClass("folder");
            }

            //폴더 타입 > 하위 리스트를 가져온다
            if (!isReport && menu.children) {
                let $ul = $('<ul class="path depth' + (depth_num + 1) + '" title="' + title + '">')
                    .appendTo($li);
                renderMenuList((depth_num + 1), $ul, menu.children)
            }
        }

    })

}
//MSTR 리포트 실행
function moveReport(menu, obj) {

    let menuId = $(obj).closest(".depth2.path").attr("data-key");
    let action;
    let mergeKey;
    //putMenuAccessHist(menuId, execDiv, objectId, execUrl)

    let menupath = $(obj).parents("ul.path");
    let navi = "";

    $.each(menupath, function (index, el) {
        if(navi === ""){
            navi = $(el).attr("title");
        }else{
            navi = $(el).attr("title") + " > " + navi;
        }
    });
    console.log("navi title :: " + $(obj).text()  );

    navi += " > " + $(obj).text().indexOf(":") > -1 ? $(obj).text().split(":")[0] : $(obj).text();
    menuId = typeof menuId == "undefined" ? "teamReport": menuId;
   if(menuId === "report" || menuId === "dossier" || menuId === "admin"){
        action = ctx + "/mstrapi/promptPage.do";
        mergeKey = $(obj).attr("merge-key");
        if (typeof mergeKey == "undefined") {
            mergeKey = "";
        }
        let target = "_top";
        putMenuAccessHist(menuId, "mstr", menu.id, navi, action);
        buildFormSubmit(action, target, "post",
            {
                  "objectId"       : menu.id
                , "objectType"     : menu.type
                , "objectSubType"  : menu.subType
                , "objectTitle"    : $(obj).text()
                , "objectViewMedia": menu.viewMedia
                , "mergeKey"       : mergeKey
                , "ossMenuId"      : menu.id
            });
    }else {
        action = ctx + "/mstrapi/popup.do";
            let target = menu.id;
            putMenuAccessHist(menuId, "mstr", menu.id, navi, action);
            buildFormSubmitPopup(action, target, "post",
                {
                      "objectId"       : menu.id
                    , "objectType"     : menu.type
                    , "objectSubType"  : menu.subType
                    , "objectTitle"    : $(obj).text()
                    , "objectViewMedia": menu.viewMedia
                    , "menuDiv"        : menuId
                    , "ossMenuId"      : menu.id
                }, 1800, 800, 200, 200);
    }
}

//메뉴 이동
function moveMenu(ossMenuId, popupYn, target, url){
    let action = ctx + url;
    let data = {"ossMenuId":ossMenuId };

    putMenuAccessHist(ossMenuId, "portal", '', '', action);

    if(popupYn){ //메뉴 팝업 여부
        buildFormSubmitPopup(action, target, "post", data, 1800, 800, 200, 200);
    }else{
        buildFormSubmit(action, target, "post", data);
    }
}

//탭메뉴 이동
function moveTab(tabId, url){
    let action = ctx + (url.indexOf("?") > -1 ? url.split("?")[0] : url);
    let target = tabId + "_iframe";
    let parameter = getQuery(url);
    if(!$("#" + target).hasClass("on")){
        $("#" + target).addClass("on");
        buildFormSubmit(action, target, "post", parameter);
    }
}
//좌측메뉴닫기 (현재 사용 x hover 이벤트로 바꿈)
function closeLeftMenu() {
    if ($("#gnb").hasClass("open")) {
        $("#gnb").toggleClass("open");
        $(".depth1 > li").removeClass("on");
    }
}
//메인대시보드에서 상세 리포트 팝업 띄우기
function dashboardDetail(objectId, width, height){
    let action = ctx + "/mstrapi/popup.do";
    let target = objectId;
    if(typeof width == "undefined"){
        width = 1400;
    }
    if(typeof height == "undefined"){
        height = 750;
    }
    let param = {"ossMenuId": "dashboardDetail", "dashboardDivDt": $("#dashboardDivDt").val(), "objectId":objectId };
    buildFormSubmitPopup(action, target, "post", param, width, height, 200, 600);
}

//MSTR 페이지 호출
function moveReportPage(objectId, objectType, subType, viewMedia){
    let obj = $("#gnb").find("span.view[data-key='"+objectId+"']");
    moveReport({"id":objectId, "type":objectType, "subType":subType, "viewMedia":viewMedia }, obj);
}

//메뉴 이력 입력
function putMenuAccessHist(menuId, execDiv, objId, objNm, execUrl){
    const action = ctx + "/cmm/putMenuAccessHist.do";
    $.ajax({
        type    : "post",
        url     : action,
        data    : {"menuId": menuId, "execDiv": execDiv, "objId":objId, "objNm":objNm, "execUrl":execUrl},
        success : function (data, text, request) {
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });
}

/* form 개체 생성 >> submit 실행 */
function buildFormSubmit(action, target, method, postData) {
    if("_top" === target){
        showLoadingPopup();
    }

    let $form = $("<form action='" + action + "' target='" + target + "' method='" + method + "' ></form>");
    $.each(postData, function (key, value) {
        let $input = $("<input type='hidden' name='" + key + "' value='" + value + "' />");
        $form.append($input);
    });
    $form.appendTo('body').submit().remove();
}

/* form 개체 생성 >> 팝업 생성 >> submit 실행 */
function buildFormSubmitPopup(action, target, method, postData, popupWidth, popupHeight, topOffset, lefOffset) {
    lefOffset = ($(window).width() - popupWidth) / 2;
    let popWindow = window.open("", target, "width=" + popupWidth + ",height=" + popupHeight + ",location=no,menubar=no,titlebar=no,status=no, scroll=no,resizable=no,top=" + topOffset + ",left=" + lefOffset);

    let $form = $("<form action='" + action + "' target='" + target + "' method='" + method + "' ></form>");
    $.each(postData, function (key, value) {
        let $input = $("<input type='hidden' name='" + key + "' value='" + value + "' />");
        $form.append($input);
    });

    $form.appendTo('body').submit().remove();
    popWindow.focus();

}

