import axios ,{AxiosError,AxiosResponse } from 'axios';
import { AUTH_LOCALSTORAGE, BASE_URL } from './constants';
type Method = 'get' | 'post';
const timeout = 60_000;


async function createRequest<RequestDataType, ResponseType>(
    method: Method,
    endpoint: string,
    data?: RequestDataType,
    contentType = 'application/json'
  ) {
    const dataUser = localStorage.getItem(AUTH_LOCALSTORAGE);
    const token = JSON.parse(dataUser as string)?.token ?? ''
    const api = axios.create({
      baseURL: BASE_URL,
      headers: {
        accept: 'application/json',
        'Content-Type': contentType,
        Authorization: token ? `Bearer ${token}` : false,
      },
      timeout: timeout,
    })
  
    try {
      const response = await api.request<
        RequestDataType,
        AxiosResponse<ResponseType>
      >({ method, url: endpoint, data: data ? data : {} })
  
      return response.data
    } catch (error) {
      const axiosAbort = error as AxiosError
    }
  }
  export const get = async <ResponseType, RequestDataType = never>(
    endpoint: string
  ) => await createRequest<RequestDataType, ResponseType>('get', endpoint)
  
  export const post = async <RequestDataType, ResponseType>(
    endpoint: string,
    data: RequestDataType
  ) => await createRequest<RequestDataType, ResponseType>('post', endpoint, data)
  
