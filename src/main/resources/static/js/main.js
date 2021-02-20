//import Api from '../js/classes/Api.js';
import Api from '../../test/Api.js';

import ServiceOrponing from "../js/classes/ServiceOrponing.js";
import ServiceOrponingAddress from "../js/classes/ServiceOrponingAddress.js";
import ServiceOrponingFile from "../js/classes/ServiceOrponingFile.js";
import ServiceOrponingClipboard from "../js/classes/ServiceOrponingClipboard.js";
import TabCommon from "../js/classes/tabs/TabCommon.js";
import TabAddress from "../js/classes/tabs/TabAddress.js";
import TabFile from "../js/classes/tabs/TabFile.js";
import TabClipboard from "../js/classes/tabs/TabClipboard.js";
import TabHistory from "../js/classes/tabs/TabHistory.js";
import ServiceHistory from '../js/classes/ServiceHistory.js';

const api = new Api();
const serviceOrponing = new ServiceOrponing(api);
const serviceHistory = new ServiceHistory(serviceOrponing);
const serviceOrponingAddress = new ServiceOrponingAddress(serviceOrponing);
const serviceOrponingFile = new ServiceOrponingFile(serviceOrponing);
const serviceOrponingClipboard = new ServiceOrponingClipboard(serviceOrponing);

const tabAddress = new TabAddress(serviceOrponingAddress);
const tabFile = new TabFile(serviceOrponingFile);
const tabClipboard = new TabClipboard(serviceOrponingClipboard);
const tabHistory = new TabHistory(serviceHistory);
TabCommon.openLastTab();