Ext.onReady(function () {
  // Ext.tip.QuickTipManager.init();

  var reload = function () {
    userStore.load();
  };

  var userStore = Ext.create('MyExt.Component.SimpleJsonStore', {
    dataUrl: '../demo/searchDemoPaging.do',
    rootFlag: 'data',
    pageSize: 10,
    remoteSort: true,
    sorters: [{
      property: 'id',
      direction: 'DESC'
    }],
    fields: ['id', 'name', 'comment']
  });

  userStore.on('beforeload', function (store, options) {
    options.params = Ext.apply(options.params || {}, { "simpleSearch": searchForm.getForm().getValues() });
  });


  var searchForm = Ext.create('Ext.form.Panel', {
    region: 'north',
    margin: '0 0 5 0',
    frame: false,
    height: 60,
    bodyStyle: {
      padding: '15px 0px 0px 10px',
      background: 'rgb(223,233,246)'
    },
    fieldDefaults: {
      labelWidth: 50
    },
    defaults: {
      width: 300
    },
    defaultType: 'textfield',
    buttonAlign: 'left',
    items: [{
      fieldLabel: '搜索',
      width: 400,
      emptyText: 'name',
      name: 'simpleSearch',
      enableKeyEvents: true,
      listeners: {
        keypress: function (thiz, e) {
          if (e.getKey() == Ext.EventObject.ENTER) {
            userGrid.getPageToolbar().moveFirst();
          }
        }
      }
    }]
  });

  var userGrid = Ext.create('MyExt.Component.GridPanel', {
    region: 'center',
    title: '用户列表',
    store: userStore,
    columns: [{
      dataIndex: 'id',
      header: 'ID',
      width: 320
    }, {
      dataIndex: 'name',
      header: "用户名",
      width: 200
    }, {
      dataIndex: 'comment',
      header: "备注",
      flex: 1
    }],
    tbar: []
  });


  Ext.create('Ext.container.Viewport', {
    layout: 'border',
    items: [searchForm, userGrid]
  });
  reload();

})