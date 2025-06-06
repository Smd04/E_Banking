export interface VerifyRequest {
  credentialId: string;
  challenge: string;
  clientDataJSON: string;
  signature: string;
}
