"use strict"
const api = new Api();
const commonUi = new CommonUi();

async function init() {
    try {
        const result = await api.apiGetListServices();

        result.forEach(el => {
            commonUi.getElement("main").insertAdjacentHTML('afterbegin', getBlock(el));
            if (el.isStartable) addStartButton(el.id);
            loadStatus(el.id);
        });
    } catch (e) {
        commonUi.notifyError(e);
    } finally {
        commonUi.getElement("loadcomp").remove();
    }
}

function getBlock({ id, icon, name, description }) {
    return `<div class="col" id=${id}>
                <div class="card shadow-sm">
                    <i class="fas fa-${icon} fa-5x m-5" style="color:black" title="${name}"></i>
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
    if (status === "NO_CONNECT") return "blue";
}

function addStartButton(idService) {
    commonUi.getElement(idService).querySelector("div.row")
        .innerHTML += `<div class="col-8"><i class="fas fa-play m-3" title="Запустить сервис" onClick="startService('${idService}')" style="cursor:pointer"></i></div>`;
}

async function startService(idService) {
    updateStatus(idService, async () => api.apiStartService(idService));
}

async function loadStatus(idService) {
    updateStatus(idService, async () => api.apiGetStatusService(idService));
}

async function updateStatus(idService, getStatus) {
    const iconService = commonUi.getElement(idService).querySelectorAll("i")[1];
    iconService.classList.add("fa-spin");

    try {
        const status = await getStatus();
        setStatus(idService, status.status, status.message, status.dateStatus)
    } catch (e) {
        commonUi.notifyError(e);
        setStatus(idService, "NO_CONNECT", "Нет связи со службой тестирования", new Date);
    } finally {
        iconService.classList.remove("fa-spin");
    }
}

function setStatus(idService, status, message, date) {
    const divMain = commonUi.getElement(idService);
    divMain.querySelectorAll("span")[0].title = status;
    divMain.querySelectorAll("span")[0].textContent = message;
    divMain.querySelectorAll("span")[1].textContent = new Date(date);
    divMain.querySelectorAll("i")[0].style = `color:${getColor(status)}`;
}

init();
commonUi.setActiveLink();