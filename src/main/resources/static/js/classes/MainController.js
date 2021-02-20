"use strict"
export default class MainController {
    //#region const

    TAB_FILE = "tab-orponing-file";
    FORM_FILE = "div-form-file";
    ORPONING_FILE = "orponing-file";
    INPUT_FILE = "input-file";

    TAB_CLIPBOARD = "tab-orponing-clipboard";
    FORM_CLIPBOARD = "div-form-clipboard";
    ORPONING_CLIPBOARD = "orponing-clipboard";
    INPUT_CLIPBOARD = "input-clipboard";

    DIV_RESULT = "div.result";
    LIST_FORM = [this.FORM_ADDRESS, this.FORM_FILE, this.FORM_CLIPBOARD];
    //#endregion
    _serviceOrponingAddress;
    _serviceOrponingFile;
    _serviceOrponingClipboard;
    _commonUi;

    constructor(option) {
        this._serviceOrponingAddress = option.serviceOrponingAddress;
        this._serviceOrponingFile = option.serviceOrponingFile;
        this._serviceOrponingClipboard = option.serviceOrponingClipboard;
        this._commonUi = option.commonUi;

        this._initControllers();
        //this._commonUi.getElement(window.localStorage.getItem("lastTabName") ?? this.TAB_ADDRESS).click();
    }

    _initControllers() {

        this._commonUi.getElement(this.INPUT_CLIPBOARD).onclick = async () => {
            if (navigator.clipboard) {
                const data = await navigator.clipboard.readText();

                if (data) {
                    const result = this._serviceOrponingClipboard.initListAddress(data);
                    if (result.error) {
                        this._commonUi.notifyError(result.error);
                    } else {
                        this._setPreview(this.FORM_CLIPBOARD, result.previewList);
                    }

                    this._commonUi.getElement(this.FORM_CLIPBOARD + ">div.count-address").textContent = "Всего записей: " + result.count;
                } else {
                    this._commonUi.notifyError("В буфере обмена нет подходящих данных");
                }
            }
        }

        this._commonUi.getElement(this.ORPONING_FILE).onclick = () => this._orponingData(this.FORM_FILE, (c) => this._serviceOrponingFile.orponing(c));

        this._commonUi.getElement(this.ORPONING_CLIPBOARD).onclick = () => this._orponingData(this.FORM_CLIPBOARD, (c) => this._serviceOrponingClipboard.orponing(c));

        this._commonUi.getElement(this.FORM_FILE).ondragover = (e) => {
            e.stopPropagation();
            e.preventDefault();
        };

        this._commonUi.getElement(this.FORM_FILE).ondrop = (e) => {
            e.stopPropagation();
            e.preventDefault();

            const files = e.dataTransfer.files;
            this._serviceOrponingFile.initListAddress(files[0], (result) => {
                if (result.error) {
                    this._commonUi.notifyError(result.error);
                    this._commonUi.getElement(this.INPUT_FILE).value = "";
                } else {
                    this._commonUi.getElement(this.INPUT_FILE).files = files;
                }

                this._setPreview(this.FORM_FILE, result.previewList);
                this._commonUi.getElement(this.FORM_FILE + ">div.count-address").textContent = "Всего записей: " + result.count;
            });
        };

        this._commonUi.getElement(this.INPUT_FILE).onchange = (e) => {
            const file = e.currentTarget.files[0];

            this._serviceOrponingFile.initListAddress(file, (result) => {
                if (result.error) {
                    this._commonUi.notifyError(result.error);
                    this._commonUi.getElement(this.INPUT_FILE).value = "";
                } else {
                    this._setPreview(this.FORM_FILE, result.previewList);
                }
                this._commonUi.getElement(this.FORM_FILE + ">div.count-address").textContent = "Всего записей: " + result.count;
            });
        }
    }

    _setPreview(idForm, result) {
        this._commonUi.removeElement(idForm + ">div.preview");
        if (result) {
            const header = this._getHeaderPreview();
            this._commonUi.getElement(idForm).insertAdjacentHTML("beforeend", header);
            for (const address of result) {
                const row = this._getRowPreview(address.Id, address.Address);
                this._commonUi.getElement(idForm + ">div.preview>div").insertAdjacentHTML("beforeend", row);
            }
        }
    }

    _orponingData(idForm, execute) {
        this._startProcessing(idForm, "Обработка запроса...");
        this._commonUi.hideElement(idForm + ">div.result");

        execute((data, error) => {
            if (data) {
                this._addDownLoadLink(idForm, data);
                this._commonUi.removeElement(idForm + ">div.preview");
            } else if (error) {
                this._commonUi.notifyError(error);
            }

            this._stopProcessing(idForm);
        });
    }

    _startProcessing(id, message) {
        const p = this._commonUi.getElement(id + ">div.processing");
        if (p) return;

        const proc = `<div class="processing row py-2 text-center">
                    <div class="container">
                        <h5>${message}</h5>
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                </div>`;

        this._commonUi.getElement(id).insertAdjacentHTML("beforeend", proc);
        this._commonUi.disableElement(id + ">div>button.start");
    }

    _stopProcessing(id) {
        const p = this._commonUi.getElement(id + ">div.processing");
        if (p) p.remove();
        this._commonUi.enableElement(id + ">div>button.start");
    }

    _getDownloadFile() {
        const downloadFile = document.createElement("a");
        downloadFile.className = "btn btn-primary";
        downloadFile.download = `load.csv`;
        downloadFile.textContent = "Скачать";
        return downloadFile;
    }

    _addDownLoadLink(idForm, data) {
        const downloadFile = this._getDownloadFile();
        downloadFile.href = data;
        this._commonUi.removeElement(idForm + ">div.result>a");
        this._commonUi.displayElement(idForm + ">div.result");
        this._commonUi.getElement(idForm + ">div.result").appendChild(downloadFile);
    }

    _getRowPreview(id, address) {
        const result = parseInt(id);

        let color = "";
        let title = "";

        if (!result) {
            color = "bg-danger";
            title = "Идентификатор должен быть числом";
        }

        return `<div class="row border"><div class="col-2 border-end ${color}" title="${title}">${id}</div><div class="col-10">${address}</div></div>`;
    }

    _getHeaderPreview() {
        return `<div class="preview"><h5 class="text-center">Предварительный обзор данных для обработки (первые 10 записей)</h5>
            <div class="container"><div class="row border"><div class="col-2 border-end">Идентификатор</div><div class="col-10">Адрес</div></div></div></div>`;
    }
}