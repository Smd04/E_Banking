// src/app/models/contract.model.ts
export interface ContractRequest {
  type: string;
  startDate: string; // Format: YYYY-MM-DD
  endDate?: string;  // Optionnel
  amount: number;
  status: string;
  userId: number;
  agentId: number;
  bankId: number;
}

export interface ContractResponse {
  message?: string;
  error?: string;
}
