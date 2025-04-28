import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { infoBrasfi } from '../../../../data/dataBRASFI'

@Component({
  selector: 'app-small-card',
  imports: [],
  templateUrl: './small-card.component.html',
  styleUrl: './small-card.component.css'
})
export class SmallCardComponent implements OnInit {

  @Input() id: string | null = "0";
  @Output()cardClicked = new EventEmitter<string | null>();
  @Input() isSelected: boolean = false;

  title: String = "";
  
  ngOnInit(): void {
    this.setValuesToComponent(this.id);
  }

  setValuesToComponent(id: string | null) {
    const result = infoBrasfi.filter(info => info.id == id)[0];
    this.title = result.title;
  }

  handleClick() {
    this.cardClicked.emit(this.id);
  }
}
