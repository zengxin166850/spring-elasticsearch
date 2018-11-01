/*
 *  全部系统url配置管理
 *  author: tter
 * 	date: 2018-08-08
 *
 * */

define(function () {

    return {
        "assets": {
            msgServer: "192.168.1.105:15674",
            userinfo: "/assets/data/userinfo.json",
            //menu: "/assets/data/menu.json"
            menu: "/assets/data/menu1.json"
        },
        "monitor": {

            type: "/monitor/data/type.json",
            status: "/monitor/data/status.json",
            sssj_table: "/monitor/data/sssj_table.json",
            tree_table: "/monitor/data/treetable.json",
            abnormal_data: "/abnormal",
            transfer_status: "/transfer",
            leader_config: "/emp",
            //data_stats: "/monitor/data/data_stats.json",
            sensor:"/monitorserver/sensor",
            //section: "/monitor/data/section.json",
            data_dictionary: "/monitorserver/const/getConstData",
            //org: "http://202.98.201.114:7070/monitor/open/organization_tree?organizationid=bafe95b8-a1ee-11e8-a2cb-80d4a5088870",
            org: "/monitor/data/org.json",
            changeDepGroup: "/monitorserver/emp/person",
            realtime: {
                data: "/monitorserver/realtimedata/page",
                curve: "/monitorserver/realtimedata/curveList",
                //transfer: "/monitor/data/sssj_table.json",  //模拟数据
                transfer: "/monitorserver/transfer",  //接口
                abnormity: "/monitorserver/warndata",
                personrtdata: "/monitorserver/personrtdata/page",
                personrtStatus: "/monitorserver/personrtdata/personrtStatus",
                personRtStat: "/monitorserver/personrtstat/minelist",
                personRtStation: "/monitorserver/personrtstat/stationInfo",
                cancelWarn:"/monitorserver/warndata/eliminate",
            },
            leader: {
                list: "/monitorserver/emp/list",
                set: "/monitorserver/emp/setleader"
            },
            station: {
                list: "/monitorserver/sensor",
                set: "/monitorserver/sensor/set",
                batch: "/monitorserver/sensor/batchSet",
                changelist:"/monitorserver/sensor/changelist",
                namelist:"/monitorserver/station/namelist"
            },
            underMine: {
                overman: "/monitorserver/overperson/rtData",
                overmanhis: "/monitorserver/overperson/history",
                overtime: "/monitorserver/overtime/init",
                overtimehis: "/monitorserver/overtime/history",
                personHisData:"/monitorserver/personrtstat/personhisdata",
                personHisStat:"/monitorserver/personrtstat/personhisstat"
            },
            history:{
                daily:"/monitorserver/monitor/daily",
                warnDaily:"/monitorserver/monitor/warnDaily",
                powerCutDaily:"/monitorserver/monitor/powerCutDaily",
                feedDaily:"/monitorserver/monitor/feedDaily",
                switchWarnDaily:"/monitorserver/monitor/switchWarnDaily",
                switchPowerCutDaily:"/monitorserver/monitor/switchPowerCutDaily",
                switchFeedDaily:"/monitorserver/monitor/switchFeedDaily",
                abnormity: "/monitorserver/warndata/history",
                historyCurve:"/monitorserver/history/historyCurve"
            }
        }
    }
});



