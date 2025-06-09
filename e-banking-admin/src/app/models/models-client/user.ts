export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string |null;
  avatar?: string;
  accounts?: string[];
  lastLogin?: Date;
  adress?:string;
  username?:string ;
}
