function getElement(id) {
    return document.querySelector(`#${id}`);
}

async function init() {
    const response = await fetch("/orponing_service/all_status");
    const result = await response.json();

    result.forEach(el => getElement("main").innerHTML += getBlock(el));

    result.filter(el => el.isStartable).forEach(e => {
        addStartButton(e.id);
    })

    getElement("loadcomp").remove();
}

function getBlock(service) {
    const st = service.status;
    const d = new Date(st.dateStatus);
    return `<div class="col" id=${service.id}>
                <div class="card shadow-sm">
                    <i class="fas fa-${service.icon} fa-5x m-5" style="color:${getColor(st.status)}" title="${st.status}"></i>
                    <div class="card-body">
                        <p class="card-text" title="${service.description}">${service.name}</p>
                        <div>
                            <b>Статус: </b><span title="${st.status}">${st.message}</span>
                            <br>
                            <b>Дата: </b><span>${d}</span>
                        </div>
                    </div>
                    <div class="container">
                        <div class="row">
                            <div class="col-2"><i class="fas fa-sync m-3" title="Обновить статус" onClick="loadStatus('${service.id}')" style="cursor:pointer"></i></div>
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
    updateStatus(idService, async () => {
        const response = await fetch(`/orponing_service/${idService}/start`);
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