export interface Compte {
  id: number;
  type: string;
  balance: number;
  currency: string;
  status: string;
  user: {
    id: number;
    nom?: string;
    prenom?: string;
    email?: string;

  };
  transactions?: any[];
  card?: {
    id: number;
    numero?: string;
    dateExpiration?: string;

  };
  AccountNumber:string
  accountId:number

}
