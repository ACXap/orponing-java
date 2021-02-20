import Api from '../js/classes/Api.js';
import CommonUi from '../js/classes/CommonUi.js';
import ServiceOrponing from "../js/classes/ServiceOrponing.js";
import ServiceOrponingAddress from "../js/classes/ServiceOrponingAddress.js";
import ServiceOrponingFile from "../js/classes/ServiceOrponingFile.js";
import ServiceOrponingClipboard from "../js/classes/ServiceOrponingClipboard.js";
import TabCommon from "../js/classes/tabs/TabCommon.js";
import TabAddress from "../js/classes/tabs/TabAddress.js";
import TabFile from "../js/classes/tabs/TabFile.js";
import TabClipboard from "../js/classes/tabs/TabClipboard.js";
import MainController from "../js/classes/MainController.js";

const api = new Api();
const commonUi = new CommonUi();
const serviceOrponing = new ServiceOrponing(api);
const serviceOrponingAddress = new ServiceOrponingAddress(serviceOrponing);
const serviceOrponingFile = new ServiceOrponingFile(serviceOrponing);
const serviceOrponingClipboard = new ServiceOrponingClipboard(serviceOrponing);
const mainController = new MainController({ serviceOrponingAddress, serviceOrponingFile, serviceOrponingClipboard, commonUi });

const tabAddress = new TabAddress(serviceOrponingAddress);
const tabFile = new TabFile();
const tabClipboard = new TabClipboard();
TabCommon.openLastTab();