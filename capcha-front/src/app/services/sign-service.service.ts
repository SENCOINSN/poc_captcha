import { HttpClient } from '@angular/common/http';
import {
  inject,
  Injectable,
} from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class SignServiceService {
  http=inject(HttpClient);


  constructor() { }

  public signUp(data:any):Observable<any>{
    return this.http.post<any>(`${environment.apiUrl}signup`,data)
  }
}
