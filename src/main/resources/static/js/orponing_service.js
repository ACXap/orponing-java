function getElement(id) {
    return document.querySelector(`#${id}`);
}

async function init() {
    const response = await fetch("/orponing_service/all_status");
    const result = await response.json();

    result.forEach(el => getElement("main").innerHTML += getBlock(el.icon, el.id, el.name, el.status.status, el.status.dateStatus, el.status.message));

    // тут пока вручную смотрим кому надо кнопку старт
    addStartButton(result.find(el => el.id == "orponing-service").id);

    getElement("loadcomp").remove();
}

function getBlock(icon, idService, nameService, statusService, dateService, messageService) {
    return `<div class="col" id=${idService}>
                <div class="card shadow-sm">
                    <i class="fas fa-${icon} fa-5x m-5" style="color:${getColor(statusService)}"></i>
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
                            <div class="col-2" style="cursor:pointer"><i class="fas fa-sync m-3" onClick="loadStatus('${idService}')"></i></div>
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
        .innerHTML += `<div class="col-8" style="cursor:pointer"><i class="fas fa-play m-3" onClick="startService('${idService}')"></i></div>`;
}

async function startService(idService) {
    updateStatus(idService, async () => {
        const response = await fetch("/orponing_service/start");
        return response.json();
    })
}

async function loadStatus(idService) {
    updateStatus(idService, async () => {
        const response = await fetch("/orponing_service/status?service=" + idService);
        return response.json();
    });
}


async function updateStatus(idService, getStatus) {
    const divMain = getElement(idService);
    const iconService = divMain.querySelectorAll("i")[1];
    iconService.classList.add("fa-spin");

    try {
        const status = await getStatus();

        divMain.querySelectorAll("span")[0].title = status.status;
        divMain.querySelectorAll("span")[0].textContent = status.message;
        divMain.querySelectorAll("span")[1].textContent = status.dateStatus;
        divMain.querySelectorAll("i")[0].style = `color:${getColor(status.status)}`;
    } catch (e) {
        console.error(e);

        divMain.querySelectorAll("span")[0].title = "Нет связи с со службой тестирования";
        divMain.querySelectorAll("span")[0].textContent = "Нет соединения";
        divMain.querySelectorAll("span")[1].textContent = "";
        divMain.querySelectorAll("i")[0].style = `color:blue`;
    } finally {
        iconService.classList.remove("fa-spin");
    }
}

init();