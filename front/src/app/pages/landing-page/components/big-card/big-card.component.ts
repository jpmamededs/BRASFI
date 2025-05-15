import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { infoBrasfi } from '../../../../data/dataBRASFI'

@Component({
  selector: 'app-big-card',
  imports: [],
  templateUrl: './big-card.component.html',
  styleUrl: './big-card.component.css'
})
export class BigCardComponent implements OnChanges{
  @Input()
  id: string | null = "0";

  title: String = "";
  content: String = "";

  ngOnChanges(): void {
    this.setValuesToComponent(this.id);
  }

  setValuesToComponent(id: string | null) {
    const result = infoBrasfi.filter(info => info.id == id)[0];
    this.title = result.title;
    this.content = result.content;
  }
}
