"use strict"
import TabCommon from "./TabCommon.js";
export default class TabWithPreview extends TabCommon {

    constructor() {
        super();
    }

    setPreview(result) {
        const preview = this.form.querySelector("div.preview");
        if (preview) preview.remove();

        if (result) {
            const header = this.getHeaderPreview();
            this.form.insertAdjacentHTML("beforeend", header);
            for (const address of result) {
                const row = this.getRowPreview(address.Id, address.Address);
                this.form.querySelector("div.preview>div").insertAdjacentHTML("beforeend", row);
            }
        }
    }

    getRowPreview(id, address) {
        const result = parseInt(id);

        let color = "";
        let title = "";

        if (!result) {
            color = "bg-danger";
            title = "Идентификатор должен быть числом";
        }

        return `<div class="row border"><div class="col-2 border-end ${color}" title="${title}">${id}</div><div class="col-10">${address}</div></div>`;
    }

    removePreview() {
        const p = this.form.querySelector("div.preview");
        if (p) p.remove();
    }

    getHeaderPreview() {
        return `<div class="preview"><h5 class="text-center">Предварительный обзор данных для обработки (первые 10 записей)</h5>
            <div class="container"><div class="row border"><div class="col-2 border-end">Идентификатор</div><div class="col-10">Адрес</div></div></div></div>`;
    }
}