"use strict"

import HistoryItem from "./data/HistoryItem.js";

export default class ServiceHistory {
    serviceOrponing;
    listHistory = new Map();
    index = 1;
    handlerUpdateHistory;

    constructor(serviceOrponing) {
        this.serviceOrponing = serviceOrponing;
        this.serviceOrponing.handlerStartTask = (item) => {
            this.addItem(item);
        }
        this.updateList();
    }

    addItem(item) {
        this.updateList();
        this.listHistory.set(item.taskId, new HistoryItem(this.index++, item.status, item.name, item.taskId, item.countRecord, item.date));

        window.localStorage.setItem("history", JSON.stringify(Array.from(this.listHistory.entries())));
        if (this.handlerUpdateHistory) this.handlerUpdateHistory();
    }

    removeItem(taskId) {
        this.updateList();
        console.log(this.listHistory.has(taskId));
        this.listHistory.delete(taskId);

        window.localStorage.setItem("history", JSON.stringify(Array.from(this.listHistory.entries())));
        this.updateList();
        if (this.handlerUpdateHistory) this.handlerUpdateHistory();
    }

    async updateItem(taskId) {
        this.updateList();

        const result = await this.serviceOrponing.getStatus(taskId);
        if (result) {
            const t = this.listHistory.get(taskId);
            t.status = result.status;
        }

        window.localStorage.setItem("history", JSON.stringify(Array.from(this.listHistory.entries())));
        if (this.handlerUpdateHistory) this.handlerUpdateHistory();
    }

    updateList() {
        try {
            const history = new Map(JSON.parse(window.localStorage.getItem("history")));

            if (history) {
                const list = new Map();
                this.index = 1;

                for (const item of history.values()) {
                    list.set(item.taskId, new HistoryItem(this.index++, item.status, item.name, item.taskId, item.countRecord, item.date));
                }

                this.listHistory.clear();
                list.forEach(i => this.listHistory.set(i.taskId, i));
            }
        } catch (e) {
            console.error(e);
        }
    }

    getHistory() {
        return this.listHistory.values();
    }
}