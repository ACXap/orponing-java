"use strict"
import TabWithResultLoad from "./TabWithResultLoad.js";
export default class TabFile extends TabWithResultLoad {
    tab;
    form;
    serviceOrponing;

    constructor(serviceOrponingFile) {
        super();
        this.serviceOrponing = serviceOrponingFile;

        this.tab = document.querySelector("#tab-orponing-file");
        this.form = document.querySelector("#div-form-file");
        this.addTab(this);
        this.tab.onclick = () => this.open();

        this.form.ondragover = (e) => {
            e.stopPropagation();
            e.preventDefault();
        };

        this.form.ondrop = (e) => {
            e.stopPropagation();
            e.preventDefault();

            const files = e.dataTransfer.files;
            this.serviceOrponing.initListAddress(files[0], (result) => {
                if (result.error) {
                    this.notifyError(result.error);
                    this.form.querySelector("input").value = "";
                } else {
                    this.form.querySelector("input").files = files;
                }

                this.setPreview(result.previewList);
                this.form.querySelector("div.count-address").textContent = "Всего записей: " + result.count;
            });
        };

        this.form.querySelector("input").onchange = (e) => {
            const file = e.currentTarget.files[0];

            this.serviceOrponing.initListAddress(file, (result) => {
                if (result.error) {
                    this.notifyError(result.error);
                    this.form.querySelector("input").value = "";
                } else {
                    this.setPreview(result.previewList);
                }
                this.form.querySelector("div.count-address").textContent = "Всего записей: " + result.count;
            });
        }

        this.form.querySelector("button.start").onclick = () => {
            if (this.form.querySelector("input").value) {
                this.orponingData((c) => this.serviceOrponing.orponing(c));
            }
        }
    }

    open() {
        this.openForm(this.form.querySelector("div.result>a"));
    }
}