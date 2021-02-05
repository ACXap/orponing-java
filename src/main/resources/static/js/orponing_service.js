async function init() {
    try {
        const result = await apiGetListServices();

        result.forEach(el => {
            getElement("main").insertAdjacentHTML('afterbegin', getBlock(el));
            if (el.isStartable) addStartButton(el.id);
            loadStatus(el.id);
        });
    } catch (e) {
        notifyError(e);
    } finally {
        getElement("loadcomp").remove();
    }
}

function getBlock({ id, icon, name, description }) {
    return `<div class="col" id=${id}>
                <div class="card shadow-sm">
                    <i class="fas fa-${icon} fa-5x m-5" style="color:black" title="$Обработка"></i>
                    <div class="card-body">
                        <p class="card-text" title="${description}">${name}</p>
                        <div>
                            <b>Статус: </b><span title="Обработка">Обработка</span>
                            <br>
                            <b>Дата: </b><span>${new Date()}</span>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row">
                            <div class="col-2"><i class="fas fa-sync m-3" title="Обновить статус" onClick="loadStatus('${id}')" style="cursor:pointer"></i></div>
                        </div>
                    </div>
                </div>
            </div>`;
}

function getColor(status) {
    if (status === "START") return "green";
    if (status === "ERROR") return "red";
    if (status === "STOP") return "black";
}

function addStartButton(idService) {
    getElement(idService).querySelector("div.row")
        .innerHTML += `<div class="col-8"><i class="fas fa-play m-3" title="Запустить сервис" onClick="startService('${idService}')" style="cursor:pointer"></i></div>`;
}

async function startService(idService) {
    updateStatus(idService, async () => apiStartService(idService));
}

async function loadStatus(idService) {
    updateStatus(idService, async () => apiGetStatusService(idService));
}

async function updateStatus(idService, getStatus) {
    const divMain = getElement(idService);
    const iconService = divMain.querySelectorAll("i")[1];
    iconService.classList.add("fa-spin");

    try {
        const status = await getStatus();

        divMain.querySelectorAll("span")[0].title = status.status;
        divMain.querySelectorAll("span")[0].textContent = status.message;
        divMain.querySelectorAll("span")[1].textContent = new Date(status.dateStatus);
        divMain.querySelectorAll("i")[0].style = `color:${getColor(status.status)}`;
    } catch (e) {
        notifyError(e);

        divMain.querySelectorAll("span")[0].title = "Нет связи со службой тестирования";
        divMain.querySelectorAll("span")[0].textContent = "Нет соединения";
        divMain.querySelectorAll("span")[1].textContent = "";
        divMain.querySelectorAll("i")[0].style = "color:blue";
    } finally {
        iconService.classList.remove("fa-spin");
    }
}

init();
setActiveLink();