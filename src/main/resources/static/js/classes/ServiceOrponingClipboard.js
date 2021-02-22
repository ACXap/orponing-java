"use strict"
export default class ServiceOrponingClipboard {
    serviceOrponing;
    listAddress = [];

    constructor(serviceOrponing) {
        this.serviceOrponing = serviceOrponing;
    }

    async orponing() {
        if (this.listAddress.length === 0) return;

        return await this.serviceOrponing.orponingListAddress(this.listAddress, "Буфер обмена");
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