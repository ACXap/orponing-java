"use strict"
import TabCommon from "./TabCommon.js";
export default class TabFile extends TabCommon {
    tab;
    form;

    constructor(option) {
        super();
        this.tab = document.querySelector("#tab-orponing-file");
        this.form = document.querySelector("#div-form-file");

        this.addTab(this.tab);
        this.addForm(this.form);

        this.tab.onclick = () => this.openForm(this.tab, this.form, document.querySelector("div.result"));
    }
}