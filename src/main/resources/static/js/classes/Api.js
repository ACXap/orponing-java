"use strict"
export default class Api {

    async _getJson(response) {
        if (response.status == 200) {
            return await response.json();
        }

        throw new Error(response.statusText);
    }

    async _getText(response) {
        if (response.status == 200) {
            return await response.text();
        }

        throw new Error(response.statusText);
    }

    _getHeadersPost() {
        return {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept-Encoding': 'gzip, deflate, br',
            'Content-Encoding': 'gzip, deflate, br'
        };
    }

    async apiGetLog() {
        const response = await fetch("/log", {
            method: 'POST',
            headers: this._getHeadersPost()
        });
        return await this._getText(response);
    }

    async apiGetLogFile(file) {
        const response = await fetch("/log/read?file=" + file, {
            method: 'GET',
            headers: this._getHeadersPost()
        });
        return this._getText(response);
    }

    async apiClearArchive(password) {
        const response = await fetch("/log/clear?password=" + password);
        return this._getJson(response);
    }

    async apiOrponingAddress(address) {
        const response = await fetch("/api/get_global_id?address=" + address);
        return this._getJson(response);
    }

    async apiGetListServices() {
        const response = await fetch("/orponing_service/all_services");
        return this._getJson(response);
    }

    async apiStartService(id) {
        const response = await fetch(`/orponing_service/${id}/start`);
        return this._getJson(response);
    }

    async apiGetStatusService(id) {
        const response = await fetch("/orponing_service/status?service=" + id);
        return this._getJson(response);
    }

    async apiOrponingListAddress(listAddress) {
        const response = await fetch("/api/get_global_id", {
            method: 'POST',
            headers: this._getHeadersPost(),
            body: JSON.stringify(listAddress)
        });
        return this._getText(response);
    }

    async apiGetStatusTask(id) {
        const response = await fetch("/api/get_global_id/status?id=" + id);
        return this._getJson(response);
    }

    async apiGetResultTask(id) {
        const response = await fetch("/api/get_global_id/result?id=" + id);
        return this._getJson(response);
    }
}