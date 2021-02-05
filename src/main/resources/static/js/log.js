// пока будем получать весь, потом подумаем как получать только обновления
getElement("loadlog").onclick = loadLog;

async function loadLog() {
    try {
        const t = getElement("floatingTextarea");
        t.value = "";
        t.value = await apiGetLog();
        t.focus();
        t.scrollTop = Number.MAX_SAFE_INTEGER;
    } catch (e) {
        notifyError(e);
    }
}

loadLog();
setActiveLink();