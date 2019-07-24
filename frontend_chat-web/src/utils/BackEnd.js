import Promise from 'promise-polyfill';
import axios from 'axios';
import axiosDefaults from 'axios/lib/defaults';
import { JsonDateDecoder } from './JsonDateDecoder';

if (!window.Promise) {
    window.Promise = Promise;
}

var cloneDeep = require('lodash/cloneDeep');

/**
 * Convert parameter information to correct Get request.
 *
 * @param parameter - parameter information
 * @returns return converted string value
 */
function convert_parameter(parameter) {
    var param_list = Object.keys(parameter);
    var param = "?";
    param_list.forEach((p) => {
        if (parameter[p] !== null && parameter[p] !== undefined) {
            if (param === "?") {
                param = param + p + "=" + parameter[p];
            }
            else {
                param = param + "&" + p + "=" + parameter[p];
            }
        }
    });
    return param;
}

/**
 * Server class.
 * Handler communication with server.
 */
class BackEnd {
    createHeader() {
        if(localStorage.getItem("accessToken")) {
            var headers = {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + localStorage.getItem("accessToken")
                        };
        } else {
            var headers = {'Content-Type': 'application/json'};
        }
        return headers;
    }
    login(account, onOK, onError) {
        axios.post("/api/auth/login/",
            JSON.stringify(account),
            { headers: {"Content-Type": "application/json"} })
            .then((response) => {
                onOK({
                    username: account.username,
                    accessToken: response.data.accessToken
                });
            })
            .catch((error) => {
                onError(error);
            });
    }

    getAllRooms(onOK, onError) {
        let headers = this.createHeader();
        axios.get("http://localhost:8080/api/rooms",
            {
                headers: headers,
                transformResponse: [JsonDateDecoder]
            })
            .then((response) => {
                onOK(response.data);
            })
            .catch((error) => {
                onError(error);
            });
    }

    getAllMessagesOfRoom(roomID, onOK, onError) {
        let headers = this.createHeader();
        axios.get("http://localhost:8080/api/messagesOfRoomID/" + roomID,
            {
                headers: headers,
                transformResponse: [JsonDateDecoder]
            })
            .then((response) => {
                onOK(response.data);
            })
            .catch((error) => {
                onError(error);
            });
    }
}

const backend = new BackEnd();
export default backend;
