"use strict"
import TabCommon from "./TabCommon.js";
export default class TabHistory extends TabCommon {
    tab;
    form;
    serviceHistory;

    constructor(serviceHistory) {
        super();
        this.serviceHistory = serviceHistory;
        this.serviceHistory.handlerUpdateHistory = () => this.updateHistory();

        this.tab = document.querySelector("#tab-orponing-history");
        this.form = document.querySelector("#div-form-history");

        this.addTab(this);

        this.tab.onclick = () => this.open();
        this.updateHistory();
    }

    open() {
        this.updateHistory();
        this.openForm();
    }

    updateHistory() {
        const listHistory = this.serviceHistory.getHistory();

        const divResult = this.form.querySelector("div.result");
        divResult.innerHTML = "";
        divResult.hidden = listHistory.size === 0;

        if (listHistory.size > 0) {
            divResult.insertAdjacentHTML("beforeend", this.getHeader());

            for (const i of listHistory.values()) {
                divResult.insertAdjacentHTML("beforeend", this.getRow(i));
            }
            this.addRemoveHandler();
            this.addDownLoadHandler();
            this.addUpdateItemHandler();

            if (this.form.querySelector("h3.clear")) {
                this.form.querySelector("h3.clear").remove();
            }
        } else {
            if (!this.form.querySelector("h3.clear")) {
                this.form.insertAdjacentHTML("beforeend", `<h3 class="text-center clear">История пуста</h3>`);
            }
        }
    }

    addRemoveHandler() {
        this.form.querySelectorAll("i.remove").forEach(i => {
            i.onclick = (e) => this.serviceHistory.removeItem(e.target.getAttribute("data-id"));
        });
    }

    addDownLoadHandler() {
        this.form.querySelectorAll("i.download").forEach(i => {
            i.onclick = (e) => {
                console.log(e.target.getAttribute("data-id"));
            }
        });
    }

    addUpdateItemHandler() {
        this.form.querySelectorAll("i.update").forEach(i => i.onclick = (e) => {
            e.target.classList.add("fa-spin");
            this.serviceHistory.updateItem(e.target.getAttribute("data-id"));
        });
    }

    getRow({ id, status, name, taskId, countRecord, date }) {
        const d = new Date(date);
        const downLoad = status === "COMPLETED" ? `<i style="cursor:pointer" class="fas fa-download download" title="Скачать результат" data-id="${taskId}"></i>` : "";
        const checkStatus = status != "COMPLETED" ? `<i style="cursor:pointer" class="fas fa-sync update" title="Обновить статус" data-id="${taskId}"></i>` : "";

        return `<div class="row item ${this.getColorRow(status)}">
        <div class="col-1">${id}</div>
        <div class="col-2">${d.toLocaleDateString("ru-RU")}\t${d.toLocaleTimeString("ru-RU")}</div>
        <div class="col-5" title="${status}">${name}</div>
        <div class="col-1">${countRecord}</div>
        <div class="col-1">${checkStatus}</div>
        <div class="col-1">${downLoad}</div>
        <div class="col-1"><i style="cursor:pointer" class="fas fa-trash-alt remove" title="Удалить запись" data-id="${taskId}"></i></div>
    </div>`;
    }

    getHeader() {
        return `<div class="row border">
            <div class="col-1 border-end">№</div>
            <div class="col-2 border-end">Дата</div>
            <div class="col-5 border-end">Что было</div>
            <div class="col-1 border-end">Записей</div>
            <div class="col-1 border-end">Статус</div>
            <div class="col-1 border-end">Скачать</div>
            <div class="col-1">Удалить</div>
        </div>`;
    }

    getColorRow(status) {
        if (status === "COMPLETED") return "bg-success";
        if (status === "START") return "bg-warning";
        if (status === "ERROR") return "bg-danger"
    }
}