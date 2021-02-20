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
    }

    open() {
        this.updateHistory();
        this.openForm(this.form.querySelectorAll("div.item").length > 0);
    }

    updateHistory() {
        const divs = this.form.querySelectorAll("div.result>div.item");
        for (const d of divs) {
            d.remove();
        }

        const listHistory = this.serviceHistory.getHistory();
        if (listHistory) {

            const div = this.form.querySelector("div.result");
            div.hidden = false;
            for (const i of listHistory) {
                div.insertAdjacentHTML("beforeend", this.getRow(i));
            }

            this.addRemoveHandler();
            this.addDownLoadHandler();
            this.addUpdateItemHandler();
        }

        if (this.form.querySelectorAll("div.item").length === 0) {
            this.form.querySelector("div.result").hidden = true;
            if (!this.form.querySelector("h3.clear")) {
                this.form.insertAdjacentHTML("beforeend", `<h3 class="text-center clear">История пуста</h3>`);
            }
        } else {
            const h = this.form.querySelector("h3.clear");
            if (h) h.remove();
        }
    }

    addRemoveHandler() {
        this.form.querySelectorAll("i.remove").forEach(i => {
            i.onclick = (e) => {
                this.serviceHistory.removeItem(e.target.getAttribute("data-id"));
            }
        })
    }

    addDownLoadHandler() {
        this.form.querySelectorAll("i.download").forEach(i => {
            i.onclick = (e) => {
                console.log(e.target.getAttribute("data-id"));
            }
        })
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
        <div class="col-5">${name}</div>
        <div class="col-1">${countRecord}</div>
        <div class="col-1">${checkStatus}</div>
        <div class="col-1">${downLoad}</div>
        <div class="col-1"><i style="cursor:pointer" class="fas fa-trash-alt remove" title="Удалить запись" data-id="${taskId}"></i></div>
    </div>`;
    }

    getColorRow(status) {
        if (status === "COMPLETED") return "bg-success";
        if (status === "START") return "bg-warning";
        if (status === "ERROR") return "bg-danger"
    }

    getIconStatus(status) {
        if (status === "COMPLETED") return "fas fa-check";
        if (status === "START") return "fas fa-clock";
        if (status === "ERROR") return "fas fa-exclamation-triangle"
    }

    getColor(status) {
        if (status === "START") return "green";
        if (status === "ERROR") return "red";
        if (status === "STOP") return "black";
        if (status === "NO_CONNECT") return "blue";
    }
}