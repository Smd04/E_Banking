import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

import { Observable } from 'rxjs';

import {AuthService} from './services/auth.service';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const auth = inject(AuthService);
  const token = auth.token();

  console.log('1 token',token);

  if (!token) {
    console.log('token non trouvée');
    return next(req)
  }

  const newReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  });
       console.log('token envoyé"',token);
      console.log("newReq", newReq);

  return next(newReq)
}
