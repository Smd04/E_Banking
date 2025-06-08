import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

interface Notification {
  title: string;
  message: string;
  type: 'success' | 'error' | 'info';
  duration?: number;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private notificationSubject = new BehaviorSubject<Notification | null>(null);
  currentNotification$ = this.notificationSubject.asObservable();
  private timeoutId: any;
  notifications$: any;

  show(notification: Notification): void {
    // Annule toute notification précédente
    this.dismiss();

    // Définit une durée par défaut
    const duration = notification.duration || 5000;

    // Affiche la nouvelle notification
    this.notificationSubject.next(notification);

    // Configure la disparition automatique
    if (duration > 0) {
      this.timeoutId = setTimeout(() => {
        this.dismiss();
      }, duration);
    }
  }

  dismiss(): void {
    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }
    this.notificationSubject.next(null);
  }

  success(message: string, title = 'Succès', duration?: number): void {
    this.show({ title, message, type: 'success', duration });
  }

  error(message: string, title = 'Erreur', duration?: number): void {
    this.show({ title, message, type: 'error', duration });
  }

  info(message: string, title = 'Information', duration?: number): void {
    this.show({ title, message, type: 'info', duration });
  }
}
