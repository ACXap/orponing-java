function getElement(id) {
    return document.querySelector(`#${id}`);
}

async function loadLog(){
    const t = getElement("floatingTextarea");
    t.value = "";

    const response = await fetch("/log", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept-Encoding': 'gzip, deflate, br',
            'Content-Encoding': 'gzip, deflate, br'
        }
    });

    t.value = await response.text();
    t.focus();
    t.scrollTop = 100000;
}

// пока будем получать весь, потом подумаем как получать только обновления
getElement("loadlog").onclick=()=>{
    loadLog();
};

loadLog();