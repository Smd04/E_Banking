import { Component, HostListener } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  standalone: true,
  imports: [NgbDropdownModule],
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isCollapsed = true;

  toggleMenu() {
    this.isCollapsed = !this.isCollapsed;
  }

  closeMenu() {
    if (window.innerWidth < 992) {
      this.isCollapsed = true;
    }
  }

  getMaxHeight(): number | null {
    if (this.isCollapsed) return null;
    return window.innerHeight - 56; // 56px = hauteur du header
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    if (window.innerWidth >= 992) {
      this.isCollapsed = false;
    } else if (!this.isCollapsed) {
      // Force le recalcul de la hauteur maximale
      this.isCollapsed = true;
      setTimeout(() => this.isCollapsed = false, 10);
    }
  }

  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.navbar') && !this.isCollapsed) {
      this.isCollapsed = true;
    }
  }
}
