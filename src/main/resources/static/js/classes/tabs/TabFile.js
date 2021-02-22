"use strict"
import TabWithResultLoad from "./TabWithResultLoad.js";
export default class TabFile extends TabWithResultLoad {
    serviceOrponing;

    constructor(serviceOrponingFile) {
        super();
        this.serviceOrponing = serviceOrponingFile;

        this.tab = document.querySelector("#tab-orponing-file");
        this.form = document.querySelector("#div-form-file");
        this.addTab(this);
        this.tab.onclick = () => this.openForm();

        this.initDragDrop()

        this.form.querySelector("input").onchange = async (e) => {
            const file = e.currentTarget.files[0];

            const result = await this.serviceOrponing.initListAddress(file);
            if (result.error) {
                this.notifyError(result.error);
                this.form.querySelector("input").value = "";
            } else {
                this.setPreview(result.previewList);
            }

            this.form.querySelector("div.count-address").textContent = "Всего записей: " + result.count;
        }

        this.form.querySelector("button.start").onclick = async () => {
            if (this.form.querySelector("input").value) {
                this.orponing(() => this.serviceOrponing.orponing());
            }
        }
    }

    initDragDrop() {
        this.form.ondragover = (e) => {
            e.stopPropagation();
            e.preventDefault();
            this.form.classList.add("bg-secondary");
        };

        this.form.ondragleave = (e) => {
            e.stopPropagation();
            e.preventDefault();
            this.form.classList.remove("bg-secondary");
        };

        this.form.ondrop = async (e) => {
            e.stopPropagation();
            e.preventDefault();
            this.form.classList.remove("bg-secondary");

            const files = e.dataTransfer.files;
            const result = await this.serviceOrponing.initListAddress(files[0]);
            if (result.error) {
                this.notifyError(result.error);
                this.form.querySelector("input").value = "";
            } else {
                this.form.querySelector("input").files = files;
            }

            this.setPreview(result.previewList);
            this.form.querySelector("div.count-address").textContent = "Всего записей: " + result.count;
        };
    }
}