export interface Paiement {
  compteDebite: String;
  montant: Number;
  type: String;
  statut: String;
  referenceFacture: String;
  datePaiement: Date;
}
