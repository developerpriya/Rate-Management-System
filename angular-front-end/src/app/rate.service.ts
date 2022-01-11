import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rate } from './rate';



@Injectable({
  providedIn: 'root',
})
export class RateService {
  private baseURL = 'http://localhost:9002/';

  token: any;

  constructor(private httpClient: HttpClient) {}


  generateToken(request: any) {
    return this.httpClient.post<any>(`${this.baseURL}authenticate`, request, {
      responseType: 'text' as 'json',
    });
  }


  getRateList(): Observable<Rate[]> {
    let tokens = window.localStorage.getItem('token');
    let tokenStr = 'Bearer ' + tokens;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.get<Rate[]>(`${this.baseURL}rates`, {
      headers,
    });
  }

  // createRate(rate: Rate): Observable<any> {
  //   return this.httpClient.post(`${this.baseURL}rates`, rate, {
  //     responseType: 'text',
  //   });
  // }
  createRate(rate: Rate): Observable<any> {
    let tokens = window.localStorage.getItem('token');
    let tokenStr = 'Bearer ' + tokens;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.post(`${this.baseURL}rates`, rate, {
      headers,
      responseType: 'text' as 'json',
    });
  }

  // getRateById(id: number): Observable<any> {
  //   return this.httpClient.get<any>(`${this.baseURL}rates/${id}`);
  // }

  getRateById(id: number): Observable<any> {
    let tokens = window.localStorage.getItem('token');
    let tokenStr = 'Bearer ' + tokens;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.get<any>(`${this.baseURL}rates/${id}`, {
      headers,
    });
  }

  // updateRate(id: number, rate: Rate): Observable<any> {
  //   return this.httpClient.put(`${this.baseURL}rates/${id}`, rate, {
  //     responseType: 'text',
  //   });
  // }
  updateRate(id: number, rate: Rate): Observable<any> {
    let tokens = window.localStorage.getItem('token');
    let tokenStr = 'Bearer ' + tokens;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.put(`${this.baseURL}rates/${id}`, rate, {
      headers,
      responseType: 'text',
    });
  }

  // deleteRate(id: number): Observable<Object> {
  //   return this.httpClient.delete<any>(`${this.baseURL}rates/${id}`, {
  //     responseType: 'text' as 'json',
  //   });
  // }
  deleteRate(id: number): Observable<Object> {
    let tokens = window.localStorage.getItem('token');
    let tokenStr = 'Bearer ' + tokens;
    const headers = new HttpHeaders().set('Authorization', tokenStr);
    return this.httpClient.delete(`${this.baseURL}rates/${id}`, {
      headers,
      responseType: 'text',
    });
  }
}
