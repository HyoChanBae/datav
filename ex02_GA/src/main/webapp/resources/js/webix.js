webix.ready(function() {
    webix.ui({
        view: "dashboard",
        id: "dashboard",
        gridColumns: 4,
        gridRows: 4,
        cellHeight: 150,
        cellWidth: 200,
        cells: [
            {
                view: "panel",
                id: "panel1",
                x: 0, y: 0, dx: 1, dy: 1,
                resize: true,
                css: "my_panel",
                body: {
                    template: "Panel 1"
                }
            },
            {
                view: "panel",
                id: "panel2",
                x: 1, y: 0, dx: 2, dy: 2,
                resize: true,
                css: "my_panel",
                body: {
                    template: "Panel 2"
                }
            },
            {
                view: "panel",
                id: "panel3",
                x: 0, y: 1, dx: 1, dy: 1,
                resize: true,
                css: "my_panel",
                body: {
                    template: "Panel 3"
                }
            }
        ]
    });

    $$("dashboard").getChildViews().forEach(panel => {
        panel.getNode().onmousedown = function(e) {
            if (e.target.className.includes("webix_panel_head") || e.target.className.includes("webix_drag_handle")) {
                // Make panel draggable
                panel.$drag(panel, e);
            }
        };
    });
});
