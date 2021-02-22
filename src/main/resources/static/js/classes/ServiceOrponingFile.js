"use strict"
export default class ServiceOrponingFile {
    _serviceOrponing;
    _listAddress = [];
    _fileName = "";

    constructor(serviceOrponing) {
        this._serviceOrponing = serviceOrponing;
    }

    async orponing() {
        if (this._listAddress.length === 0) return;

        return await this._serviceOrponing.orponingListAddress(this._listAddress, this._fileName);
    }

    async initListAddress(file) {
        if (!this._isValidFile(file)) {
            return { count: 0, error: !file ? "А кто файл то будет добавлять?" : "Неверный тип файла. Допускается только *.txt и *.csv" };
        }
        this._fileName = file.name;

        return await this._readFile(file);
    }

    async _readFile(file) {
        try {
            let result = await this._readFileUtfEncoding(file);

            if (result.error === "not correctly encoding") {
                result = await this._readFileOtherEncoding(file);
            }

            return result;
        } catch (e) {
            return { count: 0, error: e };
        }
    }

    async _readFileUtfEncoding(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsBinaryString(file);
            reader.onload = (event) => {
                try {
                    resolve(this._convertFileDataToAddress(decodeURIComponent(escape(event.target.result))));
                } catch (e) {
                    if (e.message === "URI malformed") {
                        resolve({ count: 0, error: "not correctly encoding" });
                    } else {
                        reject(e);
                    }
                }
            };
        });
    }

    async _readFileOtherEncoding(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsText(file, "windows-1251");
            reader.onload = (e) => {
                try {
                    resolve(this._convertFileDataToAddress(e.target.result));
                } catch (e) {
                    reject(e);
                }
            };
        });
    }

    _convertFileDataToAddress(data) {
        this._listAddress.length = 0;
        this._listAddress.push(...this._serviceOrponing.convertStringToAddress(data));
        return { count: this._listAddress.length, error: null, previewList: this._listAddress.slice(0, 10) };
    }

    _isValidFile(file) {
        return file && (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv")));
    }
}