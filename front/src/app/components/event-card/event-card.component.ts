import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.css'],
 
})
export class EventCardComponent {
  @Input() titulo: string = '';
  @Input() paragrafo: string = '';
}
