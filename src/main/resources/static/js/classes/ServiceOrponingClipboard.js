"use strict"
export default class ServiceOrponingClipboard {
    serviceOrponing;
    listAddress = [];

    constructor(serviceOrponing) {
        this.serviceOrponing = serviceOrponing;
    }

    async orponing(callBack) {
        if (this.listAddress.length === 0) {
            callBack();
            return;
        }

        this.serviceOrponing.orponingListAddress(this.listAddress, callBack, "Буфер обмена");
    }

    initListAddress(data) {
        this.listAddress.length = 0;

        try {
            this.listAddress.push(...this.serviceOrponing.convertStringToAddress(data));
            return { count: this.listAddress.length, error: null, previewList: this.listAddress.slice(0, 9) };
        } catch (e) {
            return { count: 0, error: e };
        }
    }
}