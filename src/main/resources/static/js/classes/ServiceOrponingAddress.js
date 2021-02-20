"use strict"
export default class ServiceOrponingAddress {
    _serviceOrponing;

    constructor(serviceOrponing) {
        this._serviceOrponing = serviceOrponing;
    }

    async orponing(address) {
        const json = await this._serviceOrponing.orponingAddress(address);
        return json;
    }
}