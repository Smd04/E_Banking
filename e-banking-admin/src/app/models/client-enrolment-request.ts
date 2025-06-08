export interface ClientEnrolmentRequest {
  name: string;
  username: string;
  email: string;
  phone: string;
  password: string;
  type: string;
  currency: string;
  initialDeposit: number;
  address: string;
  city: string;
  status: string;
}
