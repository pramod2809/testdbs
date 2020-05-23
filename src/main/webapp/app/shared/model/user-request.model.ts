import { Moment } from 'moment';

export interface IUSER_REQUEST {
  id?: number;
  request_id?: number;
  submitted_by?: string;
  submitted_date?: Moment;
  approved_by?: string;
  approval_date?: Moment;
  status?: string;
  account_numer?: number;
}

export class USER_REQUEST implements IUSER_REQUEST {
  constructor(
    public id?: number,
    public request_id?: number,
    public submitted_by?: string,
    public submitted_date?: Moment,
    public approved_by?: string,
    public approval_date?: Moment,
    public status?: string,
    public account_numer?: number
  ) {}
}
