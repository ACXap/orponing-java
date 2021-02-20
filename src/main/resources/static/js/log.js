"use strict"
const commonUi = new CommonUi();
const api = new Api();
// пока будем получать весь, потом подумаем как получать только обновления
commonUi.getElement("loadLog").onclick = loadLog;
commonUi.getElement("clearArchive").onclick = clearArchive;

async function loadLog() {
    try {
        const log = await api.apiGetLog();
        setTextLog(log);
    } catch (e) {
        commonUi.notifyError(e);
    }
}

async function clearArchive() {
    const password = window.prompt("Укажите пароль для операции:");
    if (password) {
        const result = await api.apiClearArchive(password);

        if (result.status === "COMPLETED") {
            commonUi.getElement("archive").innerHTML = "";
        } else {
            commonUi.notifyError(result.message);
        }
    }
}

function setTextLog(log) {
    const t = commonUi.getElement("floatingTextarea");
    t.value = log
    t.scrollTop = Number.MAX_SAFE_INTEGER;
}

async function readLog(file) {
    try {
        const log = await api.apiGetLogFile(file);
        setTextLog(log);
    } catch (e) {
        commonUi.notifyError(e);
    }
}

function setActionButtonLog() {
    document.querySelectorAll("button.b-log").forEach(but => but.onclick = () => readLog(but.textContent));
}

loadLog();
commonUi.setActiveLink();
setActionButtonLog();