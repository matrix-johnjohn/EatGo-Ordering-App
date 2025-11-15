import axios from 'axios';

export const instance = axios.create({
    baseURL: 'http://192.168.163.1:8080',
    timeout: 5000,
    headers: { 'X-Custom-Header': 'foobar' }
});