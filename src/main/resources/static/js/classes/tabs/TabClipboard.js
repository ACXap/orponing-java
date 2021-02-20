"use strict"
import TabCommon from "./TabCommon.js";
export default class TabClipboard extends TabCommon {
    tab;
    form;

    constructor(option) {
        super();
        this.tab = document.querySelector("#tab-orponing-clipboard");
        this.form = document.querySelector("#div-form-clipboard");

        this.addTab(this.tab);
        this.addForm(this.form);

        this.tab.onclick = () => this.openForm(this.tab, this.form, document.querySelector("div.result"));
    }
}