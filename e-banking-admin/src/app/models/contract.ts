// src/app/models/contract.ts
export interface Contract {
  id: number;
  type: string;
  startDate: string;
  endDate?: string;
  amount: number;
  status: string;
  userId: number;
  agentId: number;
  bankId: number;
  createdAt?: string;
  updatedAt?: string;
}
