"use strict"
export default class ServiceOrponingFile {
    _serviceOrponing;
    _listAddress = [];
    _fileName = "";

    constructor(serviceOrponing) {
        this._serviceOrponing = serviceOrponing;
    }

    async orponing(callBack) {
        if (this._listAddress.length === 0) {
            callBack();
            return;
        }

        this._serviceOrponing.orponingListAddress(this._listAddress, callBack, this._fileName);
    }

    initListAddress(file, callBack) {
        if (!this._isValidFile(file)) {
            const message = !file ? "А кто файл то будет добавлять?" : "Неверный тип файла. Допускается только *.txt и *.csv";
            callBack({ count: 0, error: message })
            return;
        }
        this._fileName = file.name;
        this._readFileUtfEncoding(file, callBack);
    }

    _readFileUtfEncoding(file, callBack) {
        try {
            const reader = new FileReader();
            reader.readAsBinaryString(file);

            reader.onload = (e) => {
                try {
                    const data = decodeURIComponent(escape(e.target.result));
                    const result = this._convertFileDataToAddress(data);
                    callBack(result);
                } catch (error) {
                    this._readFileOtherEncoding(file, callBack);
                    return;
                }
            }
        } catch (e) {
            callBack({ count: 0, error: e });
        }
    }

    _readFileOtherEncoding(file, callBack) {
        try {
            const reader = new FileReader();
            reader.readAsText(file, "windows-1251");
            reader.onload = e => {
                const result = this._convertFileDataToAddress(e.target.result);
                callBack(result);
            }
        } catch (e) {
            callBack({ count: 0, error: e });
        }
    }

    _convertFileDataToAddress(data) {
        this._listAddress.length = 0;

        try {
            this._listAddress.push(...this._serviceOrponing.convertStringToAddress(data));
            return { count: this._listAddress.length, error: null, previewList: this._listAddress.slice(0, 10) };
        } catch (e) {
            return { count: 0, error: e };
        }
    }

    _isValidFile(file) {
        return file && (file.type === "text/plain" || (file.type === "application/vnd.ms-excel" && file.name.includes(".csv")));
    }
}