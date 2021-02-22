"use strict"
import TabWithPreview from "./TabWithPreview.js";

export default class TabWithResultLoad extends TabWithPreview {
    constructor() {
        super();
    }

    getDownloadFile() {
        const downloadFile = document.createElement("a");
        downloadFile.className = "btn btn-primary";
        downloadFile.download = `load.csv`;
        downloadFile.textContent = "Скачать";
        return downloadFile;
    }

    addDownLoadLink(data) {
        const downloadFile = this.getDownloadFile();
        downloadFile.href = data;

        const a = this.form.querySelector("div.result>a");
        if (a) a.remove();

        this.form.querySelector("div.result").hidden = false;
        this.form.querySelector("div.result").appendChild(downloadFile);
    }

    async orponing(execute) {
        this.startProcessing();

        const result = await execute();
        if (result.error) {
            this.notifyError(result.error);
        } else {
            this.addDownLoadLink(result.data);
            this.removePreview();
        }

        this.stopProcessing();
    }
}