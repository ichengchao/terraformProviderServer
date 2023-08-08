Ext.onReady(function () {
    Ext.tip.QuickTipManager.init();

    var store = Ext.create('Ext.data.TreeStore', {
        root: {
            expanded: true,
            children: [
                {
                    text: "<font style='font-weight: bold;'>Demo</font>",
                    iconCls: 'MyExt-menu-sub',
                    expanded: true,
                    children: [
                        {
                            text: "grid",
                            leaf: true,
                            href: 'demo/grid.html',
                            hrefTarget: 'mainFrame'
                        }, {
                            text: "grid paging",
                            leaf: true,
                            href: 'demo/grid_paging.html',
                            hrefTarget: 'mainFrame'
                        }
                    ]
                }, {
                    text: "<font style='font-weight: bold;'>Extjs</font>",
                    iconCls: 'MyExt-menu-sub',
                    expanded: true,
                    children: [
                        {
                            text: "component",
                            leaf: true,
                            href: 'extjs/component.html',
                            hrefTarget: 'mainFrame'
                        }, {
                            text: "message",
                            leaf: true,
                            href: 'extjs/message.html',
                            hrefTarget: 'mainFrame'
                        }, {
                            text: "layout",
                            leaf: true,
                            href: 'extjs/layout/layout.html',
                            hrefTarget: 'mainFrame'
                        }
                    ]
                }, {
                    text: "<font style='font-weight: bold;'>Bootstrap</font>",
                    iconCls: 'MyExt-menu-sub',
                    expanded: true,
                    children: [
                        {
                            text: "layout",
                            leaf: true,
                            href: 'bootstrap/boot_index.html',
                            hrefTarget: 'mainFrame'
                        }
                    ]
                }
            ]
        }
    });

    var tree_panel = Ext.create('Ext.tree.Panel', {
        frame: false,
        border: false,
        region: "center",
        store: store,
        rootVisible: false
    });

    var header_panel = Ext
        .create(
            'Ext.panel.Panel',
            {
                region: "north",
                height: 68,
                frame: false,
                border: false,
                bodyStyle: 'background:rgb(223,233,246)',
                html: 'loading...'
            });

    var main_panel = Ext.create('Ext.panel.Panel', {
        // title : 'Hello',
        frame: false,
        border: false,
        region: "center",
        html: '<iframe name="mainFrame" frameborder="0" width="100%" height="100%" src="welcome/welcome.html"/>'
    });

    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        items: [{
            layout: 'border',
            region: 'west',
            // frame : false,
            // border : false,
            split: true,
            width: 160,
            margins: '0 0 0 0',
            items: [header_panel, tree_panel]
        }, main_panel]
    });

    MyExt.util.Ajax("system/getIndexLogoPage.do", null, function (data) {
        header_panel.body.update(data.data);
    });

});