import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {LDSHDTO} from "../dto/LDSH.dto";
import {Observable} from "rxjs/Observable";

@Injectable()
export class LdshService {

  constructor(private http: HttpClient) { }

  // Return all LDSHs registered in the system.
  list(): Observable<any> {
    return this.http.get(environment.CONSTANTS.API_ROOT + '/ldsh');
  }

  // Fetch a specific LDSH by Id.
  get(ldshId: string): Observable<any> {
    return this.http.get(environment.CONSTANTS.API_ROOT + '/ldsh/' + ldshId);
  }

  // Save or create an LDSH.
  save(ldshDTO: LDSHDTO): Observable<any> {
    return this.http.post(environment.CONSTANTS.API_ROOT + '/ldsh', JSON.stringify(ldshDTO),
      {headers:{'Content-Type': 'application/json'}});
  }

  // Delete an LDSH.
  delete(ldshId: string): Observable<any> {
    return this.http.delete(environment.CONSTANTS.API_ROOT + '/ldsh/' + ldshId);
  }

}
