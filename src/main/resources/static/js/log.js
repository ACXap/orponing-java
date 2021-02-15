// пока будем получать весь, потом подумаем как получать только обновления
getElement("loadLog").onclick = loadLog;
getElement("clearArchive").onclick = clearArchive;

async function loadLog() {
    try {
        const log = await apiGetLog();
        setTextLog(log);
    } catch (e) {
        notifyError(e);
    }
}

async function clearArchive() {
    if (window.prompt("Укажите пароль для операции:")) {
        const result = await apiClearArchive(password);

        if (result.status === "COMPLETED") {
            getElement("archive").innerHTML = "";
        } else {
            alert(result.message);
        }
    }
}

function setTextLog(log) {
    const t = getElement("floatingTextarea");
    t.value = "";
    t.value = log
    t.focus();
    t.scrollTop = Number.MAX_SAFE_INTEGER;
}

async function readLog(file) {
    try {
        const log = await apiGetLogFile(file);
        setTextLog(log);
    } catch (e) {
        notifyError(e);
    }
}

function setActionButtonLog() {
    document.querySelectorAll("button.b-log").forEach(but => but.onclick = () => readLog(but.textContent));
}


loadLog();
setActiveLink();
setActionButtonLog();
