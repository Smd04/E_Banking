
export class Transaction {
  type!: string;
  amount!: number;
  status!: string;
  compteId!: number;
  userId!: number;
  timestamp !:[number, number, number, number?, number?];
  description!:string;
  compteDest !:string;

  get dateTransaction(): string {
    if (!this.timestamp) return '';
    const [year, month, day, hour = 0, minute = 0] = this.timestamp;
    const d = new Date(year, month - 1, day, hour, minute);
    console.log("date",d.toLocaleDateString());
    return d.toLocaleDateString();
  }

}
