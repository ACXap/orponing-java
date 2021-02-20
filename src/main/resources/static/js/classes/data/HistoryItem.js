"use strict"
export default class HistoryItem {
    /**
     * 
     * @param {Number} id 
     * @param {string} status 
     * @param {string} name 
     * @param {string} taskId 
     * @param {int} countRecord 
     * @param {Date} date 
     */
    constructor(id, status, name, taskId, countRecord, date) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.taskId = taskId;
        this.countRecord = countRecord;
        this.date = date;
    }
}