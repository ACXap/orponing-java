"use strict"
export default class ServiceOrponing {
    _api;

    onStartTask;
    onCompletedTask;

    constructor(api) {
        this._api = api;
    }

    async orponingAddress(address) {
        const json = await this._api.apiOrponingAddress(address);
        return json;
    }

    async orponingListAddress(list, name) {
        try {
            const idTask = await this._api.apiOrponingListAddress(list);
            this.onStartTask({ status: "START", name: name, taskId: idTask, countRecord: list.length, date: new Date() });

            while (true) {
                await this._delay(100);
                let result = await this.getStatus(idTask);
                if (result.status === "COMPLETED") {
                    this.onCompletedTask({ status: result.status, taskId: idTask, date: new Date() });
                    result = await this.getResult(idTask);
                    return { data: this.convertAddressInfoToString(result, list), error: null }
                }
            }
        } catch (e) {
            this.onStartTask({ status: "ERROR", name: name, taskId: "", countRecord: list.length, date: new Date() });
            return { data: "", error: e.message };
        }
    }

    _delay(delay) {
        return new Promise(resolve => setTimeout(resolve, delay));
    }

    async getStatus(idTask) {
        return await this._api.apiGetStatusTask(idTask);
    }

    async getResult(idTask) {
        return await this._api.apiGetResultTask(idTask);
    }

    convertAddressInfoToString(addressInfo, list) {
        let dataForSave = "data:application/txt;charset=utf-8,%EF%BB%BF";

        const data = [];
        data.push("id;Address;GlobalId;AddressOrpon;ParsingLevelCode;QualityCode;UnparsedParts;Error");

        addressInfo.forEach(el => {
            data.push(`${el.Id};${list.find(e => e.Id == el.Id).Address};${el.GlobalId ?? ""};${el.AddressOrpon ?? ""};${el.ParsingLevelCode ?? ""};${el.QualityCode ?? ""};${el.UnparsedParts ?? ""};${el.Error ?? ""}`);
        });

        dataForSave += encodeURIComponent(data.join("\r\n"));

        return dataForSave;
    }

    convertStringToAddress(data) {
        const list = [];

        if (data) {
            const rows = data.split(/\r\n|\n/);

            if (rows[0].split(";").length > 1) {
                for (const row of rows) {
                    const items = row.split(";");
                    list.push({ Id: items[0], Address: items[1] });
                }
            } else {
                let index = 1;
                for (const row of rows) {
                    list.push({ Id: index++, Address: row });
                }
            }
        }

        return list;
    }
}