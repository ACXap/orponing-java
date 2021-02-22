"use strict"
import TabWithResultLoad from "./TabWithResultLoad.js";
export default class TabClipboard extends TabWithResultLoad {
    serviceOrponing;

    constructor(serviceOrponingClipboard) {
        super();
        this.serviceOrponing = serviceOrponingClipboard;
        this.tab = document.querySelector("#tab-orponing-clipboard");
        this.form = document.querySelector("#div-form-clipboard");

        this.addTab(this);
        this.tab.onclick = () => this.openForm();

        this.form.querySelector("#input-clipboard").onclick = async () => {
            if (navigator.clipboard) {
                const data = await navigator.clipboard.readText();

                if (data) {
                    const result = this.serviceOrponing.initListAddress(data);
                    if (result.error) {
                        this.notifyError(result.error);
                    } else {
                        this.setPreview(result.previewList);
                    }

                    this.form.querySelector("div.count-address").textContent = "Всего записей: " + result.count;
                } else {
                    this.notifyError("В буфере обмена нет подходящих данных");
                }
            }
        }

        this.form.querySelector("button.start").onclick = async () => {
            this.orponing(() => this.serviceOrponing.orponing());
        };
    }
}