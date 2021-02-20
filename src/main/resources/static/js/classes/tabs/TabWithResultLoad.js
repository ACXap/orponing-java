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

    orponingData(execute) {
        this.startProcessing("Обработка запроса...");
        this.form.querySelector("div.result").hidden = true;

        execute((data, error) => {
            if (data) {
                this.addDownLoadLink(data);
                this.removePreview();
            } else if (error) {
                this.notifyError(error);
            }

            this.stopProcessing();
        });
    }
}