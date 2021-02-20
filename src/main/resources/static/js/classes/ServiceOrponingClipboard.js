"use strict"
export default class ServiceOrponingClipboard {
    _serviceOrponing;
    _listAddress = [];

    constructor(serviceOrponing) {
        this._serviceOrponing = serviceOrponing;
    }

    async orponing(callBack) {
        if (this._listAddress.length === 0) {
            callBack();
            return;
        }

        this._serviceOrponing.orponingListAddress(this._listAddress, callBack);
    }

    initListAddress(data) {
        this._listAddress.length = 0;

        try {
            this._listAddress.push(...this._serviceOrponing.convertStringToAddress(data));
            return { count: this._listAddress.length, error: null, previewList: this._listAddress.slice(0, 9) };
        } catch (e) {
            return { count: 0, error: e };
        }
    }
}