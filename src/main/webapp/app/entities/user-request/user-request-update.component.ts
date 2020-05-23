import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUSER_REQUEST, USER_REQUEST } from 'app/shared/model/user-request.model';
import { USER_REQUESTService } from './user-request.service';

@Component({
  selector: 'jhi-user-request-update',
  templateUrl: './user-request-update.component.html'
})
export class USER_REQUESTUpdateComponent implements OnInit {
  isSaving = false;
  submitted_dateDp: any;
  approval_dateDp: any;

  editForm = this.fb.group({
    id: [],
    request_id: [],
    submitted_by: [],
    submitted_date: [],
    approved_by: [],
    approval_date: [],
    status: [],
    account_numer: []
  });

  constructor(protected uSER_REQUESTService: USER_REQUESTService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uSER_REQUEST }) => {
      this.updateForm(uSER_REQUEST);
    });
  }

  updateForm(uSER_REQUEST: IUSER_REQUEST): void {
    this.editForm.patchValue({
      id: uSER_REQUEST.id,
      request_id: uSER_REQUEST.request_id,
      submitted_by: uSER_REQUEST.submitted_by,
      submitted_date: uSER_REQUEST.submitted_date,
      approved_by: uSER_REQUEST.approved_by,
      approval_date: uSER_REQUEST.approval_date,
      status: uSER_REQUEST.status,
      account_numer: uSER_REQUEST.account_numer
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uSER_REQUEST = this.createFromForm();
    if (uSER_REQUEST.id !== undefined) {
      this.subscribeToSaveResponse(this.uSER_REQUESTService.update(uSER_REQUEST));
    } else {
      this.subscribeToSaveResponse(this.uSER_REQUESTService.create(uSER_REQUEST));
    }
  }

  private createFromForm(): IUSER_REQUEST {
    return {
      ...new USER_REQUEST(),
      id: this.editForm.get(['id'])!.value,
      request_id: this.editForm.get(['request_id'])!.value,
      submitted_by: this.editForm.get(['submitted_by'])!.value,
      submitted_date: this.editForm.get(['submitted_date'])!.value,
      approved_by: this.editForm.get(['approved_by'])!.value,
      approval_date: this.editForm.get(['approval_date'])!.value,
      status: this.editForm.get(['status'])!.value,
      account_numer: this.editForm.get(['account_numer'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUSER_REQUEST>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
