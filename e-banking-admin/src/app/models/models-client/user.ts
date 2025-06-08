export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  avatar?: string;
  accounts?: string[];
  lastLogin?: Date;
}
