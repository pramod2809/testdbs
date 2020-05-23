import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUSER_REQUEST } from 'app/shared/model/user-request.model';

@Component({
  selector: 'jhi-user-request-detail',
  templateUrl: './user-request-detail.component.html'
})
export class USER_REQUESTDetailComponent implements OnInit {
  uSER_REQUEST: IUSER_REQUEST | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uSER_REQUEST }) => (this.uSER_REQUEST = uSER_REQUEST));
  }

  previousState(): void {
    window.history.back();
  }
}
