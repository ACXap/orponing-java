db = "db";
dnName = "БД для хранения разобранных адресов";
orponing = "orponing";

orponingName = "Сервис для разбора адресов";
orponingService = "orponing-service";
orponingServiceName = "Фоновый сервис орпонизации";

iconServer = "fa-server";
iconDb = "fa-database";

function getElement(id) {
    return document.querySelector(`#${id}`);
}

async function init() {
    addBlock(iconDb, dnName, db);
    addBlock(iconServer, orponingName, orponing);
    await addBlock(iconServer, orponingServiceName, orponingService);

    addStartButton(orponingService);
}

async function addBlock(iconServer, nameService, idService) {
    const result = await getStatus(idService);

    getElement("main").innerHTML += getBlock(iconServer, idService, nameService, result.Status, result.DateStatus, result.Message);
}

async function getStatus(idService) {
    const response = await fetch("/orponing_service/status?service=" + idService);
    const result = await response.json();
    return result;
}

function getBlock(icon, idService, nameService, statusService, dateService, messageService) {
    return `<div class="col" id=${idService}>
                <div class="card shadow-sm">
                    <i class="fas ${icon} fa-5x m-5" style="color:${getColor(statusService)}"></i>
                    <div class="card-body">
                        <p class="card-text">${nameService}</p>
                        <div>
                            <b>Статус: </b><span title="${statusService}">${messageService}</span>
                            <br>
                            <b>Дата: </b><span>${dateService}</span>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row">
                            <div class="col-2" style="cursor:pointer"><i class="fas fa-sync m-3" onClick="updateStatus('${idService}')"></i></div>
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
    getElement(idService).querySelector("div.row").innerHTML += `<div class="col-8"><i class="fas fa-play m-3"></i></div>`;
}

async function updateStatus(idService) {
    const divMain = getElement(idService);
    const iconService = divMain.querySelectorAll("i")[1];
    iconService.classList.add("fa-spin");


    try {
        const result = await getStatus(idService);
        divMain.querySelectorAll("span")[0].title = result.Status;
        divMain.querySelectorAll("span")[0].textContent = result.Message;

        divMain.querySelectorAll("span")[1].textContent = result.DateStatus;

        divMain.querySelectorAll("i")[0].style = `color:${getColor(result.Status)}`;
        iconService.classList.remove("fa-spin");
    } catch (e) {
        iconService.classList.remove("fa-spin");
        console.error(e);
    }
}

init();